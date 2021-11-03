package com.hedera.hashgraph.zeroknowledge.utils;

import com.hedera.hashgraph.identity.hcs.vc.CredentialSubject;
import com.hedera.hashgraph.zeroknowledge.mock.*;
import com.hedera.hashgraph.zeroknowledge.vc.CredentialSubjectMerkleTreeLeaf;
import com.hedera.hashgraph.zeroknowledge.vc.MerkleTreeLeaf;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.List;

// TODO: can this be a factory?
public final class MerkleTreeUtils {
    public static FieldElement computeHash(String documentId, FieldElement merkleTreeRoot) throws FieldElementException {
        FieldElement documentIdField = FieldElement.deserialize(documentId.getBytes(StandardCharsets.UTF_8));

        PoseidonHash hash = PoseidonHash.getInstanceConstantLength(2);
        hash.update(documentIdField);
        hash.update(merkleTreeRoot);

        documentIdField.close();
        merkleTreeRoot.close();
        hash.close();

        return hash.finalizeHash();
    }

    public static <T extends CredentialSubject> FieldElement getMerkleTreeRoot(List<T> credentialSubjects, Class<? extends CredentialSubject> aClass) throws InvocationTargetException, IllegalAccessException, MerkleTreeLeafException {
        BaseMerkleTree inputTree = initializeMerkleTree();

        insertLeavesIntoMerkleTree(credentialSubjects, aClass, inputTree);

        inputTree.finalizeTreeInPlace();

        return inputTree.root();
    }

    private static <T extends CredentialSubject> void insertLeavesIntoMerkleTree(List<T> credentialSubjects, Class<? extends CredentialSubject> aClass, BaseMerkleTree inputTree) throws IllegalAccessException, InvocationTargetException, MerkleTreeLeafException {
        for (T credentialSubject : credentialSubjects) {
            for (Method method : aClass.getDeclaredMethods()) {
                if (method.isAnnotationPresent(MerkleTreeLeaf.class)) {
                    MerkleTreeLeaf annotation = method.getAnnotation(MerkleTreeLeaf.class);
                    Object field = method.invoke(credentialSubject, (Object[]) null);

                    byte[] keyBytes = annotation.keyName().getBytes(StandardCharsets.UTF_8);
                    byte[] valueBytes;

                    try {
                        valueBytes = serializeFieldObjectToByteArray(field);
                    } catch (IOException e) {
                        throw new MerkleTreeLeafException(
                                String.format("Cannot serialize credential subject field %s with value: %s", annotation.keyName(), field)
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

    private static BaseMerkleTree initializeMerkleTree() {
        return BaseMerkleTree.init(10);
    }

    private static byte[] serializeFieldObjectToByteArray(Object field) throws IOException {
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(field);
            oos.flush();
            return bos.toByteArray();
        }
    }
}
