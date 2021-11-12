package com.hedera.hashgraph.zeroknowledge.merkletree.factory;

import com.hedera.hashgraph.identity.hcs.vc.CredentialSubject;
import com.hedera.hashgraph.zeroknowledge.vc.CredentialSubjectMerkleTreeLeaf;
import com.hedera.hashgraph.zeroknowledge.vc.MerkleTreeLeaf;
import io.horizen.common.librustsidechains.FieldElementConversionException;
import io.horizen.common.librustsidechains.FinalizationException;
import io.horizen.common.librustsidechains.InitializationException;
import io.horizen.common.merkletreenative.BaseMerkleTree;
import io.horizen.common.merkletreenative.MerkleTreeException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static com.hedera.hashgraph.zeroknowledge.utils.MerkleTreeUtils.serializeFieldObjectToByteArray;

public class MerkleTreeFactoryImpl implements MerkleTreeFactory {
    @Override
    public <T extends CredentialSubject> BaseMerkleTree getMerkleTreeRoot(List<T> credentialSubjects) throws InvocationTargetException, IllegalAccessException, FinalizationException, MerkleTreeException, InitializationException, FieldElementConversionException {
        BaseMerkleTree inputTree = initializeMerkleTree();

        Class<? extends CredentialSubject> credentialSubjectsClass = credentialSubjects.get(0).getClass();
        insertLeavesIntoMerkleTree(credentialSubjects, credentialSubjectsClass, inputTree);

        inputTree.finalizeTreeInPlace();

        return inputTree;
    }

    private <T extends CredentialSubject> void insertLeavesIntoMerkleTree(List<T> credentialSubjects, Class<? extends CredentialSubject> aClass, BaseMerkleTree inputTree) throws IllegalAccessException, InvocationTargetException, MerkleTreeException, FieldElementConversionException {
        for (T credentialSubject : credentialSubjects) {
            for (Method method : aClass.getDeclaredMethods()) {
                if (method.isAnnotationPresent(MerkleTreeLeaf.class)) {
                    MerkleTreeLeaf annotation = method.getAnnotation(MerkleTreeLeaf.class);
                    Object field = method.invoke(credentialSubject, (Object[]) null);

                    byte[] keyBytes = annotation.keyName().getBytes(StandardCharsets.UTF_8);
                    byte[] valueBytes;

                    try {
                        valueBytes = serializeFieldObjectToByteArray(field);
                    } catch (IllegalArgumentException e) {
                        throw new MerkleTreeException(
                                String.format("Cannot serialize credential subject field %s with value: %s", annotation.keyName(), field),
                                e
                        );
                    }

                    CredentialSubjectMerkleTreeLeaf currentLeaf = new CredentialSubjectMerkleTreeLeaf(
                            keyBytes, valueBytes
                    );

                    inputTree.append(currentLeaf.toFieldElement());
                }
            }
        }
    }

    private BaseMerkleTree initializeMerkleTree() throws InitializationException {
        return BaseMerkleTree.init(10);
    }
}
