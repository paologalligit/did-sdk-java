package com.hedera.hashgraph.zeroknowledge.vc;


import com.hedera.hashgraph.zeroknowledge.mock.*;

import java.util.Arrays;

public class CredentialSubjectMerkleTree implements MerkleTreeLeaf {
    private final byte[] propertyKey;
    private final byte[] propertyValue;

    public CredentialSubjectMerkleTree(byte[] propertyKey, byte[] propertyValue) {
        this.propertyKey = propertyKey;
        this.propertyValue = propertyValue;
    }

    @Override
    public FieldElement getLeafAsFieldElement() throws MerkleTreeLeafException {
        try {
            FieldElement keyField = FieldElement.deserialize(propertyKey);
            FieldElement valueFields = FieldElement.deserialize(propertyValue);

            PoseidonHash hash = PoseidonHash.getInstanceConstantLength(2);
            hash.update(keyField);
            hash.update(valueFields);

            FieldElement hashedValue = hash.finalizeHash();

            closeAll(keyField, valueFields, hash);

            return hashedValue;
        } catch (FieldElementException e) {
            throw new MerkleTreeLeafException("Cannot calculate leaf", e);
        }
    }

    private void closeAll(AutoCloseable ...autoCloseable) {
        Arrays.stream(autoCloseable).forEach(closeable -> {
            try {
                closeable.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void close() throws Exception {

    }
}
