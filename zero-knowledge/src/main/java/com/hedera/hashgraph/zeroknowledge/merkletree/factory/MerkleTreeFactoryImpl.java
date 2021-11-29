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
import java.util.List;

public class MerkleTreeFactoryImpl implements MerkleTreeFactory {
    @Override
    public <T extends CredentialSubject> BaseMerkleTree getMerkleTree(List<T> credentialSubjects) throws InvocationTargetException, IllegalAccessException, FinalizationException, MerkleTreeException, InitializationException, FieldElementConversionException {
        BaseMerkleTree inputTree = initializeMerkleTree();

        insertLeavesIntoMerkleTree(credentialSubjects, inputTree);

        inputTree.finalizeTreeInPlace();

        return inputTree;
    }

    private <T extends CredentialSubject> void insertLeavesIntoMerkleTree(List<T> credentialSubjects, BaseMerkleTree inputTree) throws IllegalAccessException, InvocationTargetException, MerkleTreeException, FieldElementConversionException {
        for (T credentialSubject : credentialSubjects) {
            Class<? extends CredentialSubject> aClass = credentialSubject.getClass();

            for (Method method : aClass.getDeclaredMethods()) {
                if (method.isAnnotationPresent(MerkleTreeLeaf.class)) {
                    MerkleTreeLeaf annotation = method.getAnnotation(MerkleTreeLeaf.class);
                    Object field = method.invoke(credentialSubject, (Object[]) null);

                    String keyLabel = annotation.labelName();

                    CredentialSubjectMerkleTreeLeaf currentLeaf = new CredentialSubjectMerkleTreeLeaf(keyLabel, field);

                    inputTree.append(currentLeaf.toFieldElement());
                }
            }
        }
    }

    private BaseMerkleTree initializeMerkleTree() throws InitializationException {
        return BaseMerkleTree.init(8, 3);
    }
}
