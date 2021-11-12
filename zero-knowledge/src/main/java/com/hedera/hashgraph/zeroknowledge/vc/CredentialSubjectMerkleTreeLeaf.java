package com.hedera.hashgraph.zeroknowledge.vc;

import io.horizen.common.librustsidechains.*;
import io.horizen.common.poseidonnative.PoseidonHash;

public class CredentialSubjectMerkleTreeLeaf implements FieldElementConvertible {
    private final byte[] propertyKey;
    private final byte[] propertyValue;

    public CredentialSubjectMerkleTreeLeaf(byte[] propertyKey, byte[] propertyValue) {
        this.propertyKey = propertyKey;
        this.propertyValue = propertyValue;
    }

    @Override
    public FieldElement toFieldElement() throws FieldElementConversionException {
        FieldElement keyField = null, valueFields = null;
        PoseidonHash hash = null;

        try {
            keyField = FieldElement.deserialize(propertyKey);
            valueFields = FieldElement.deserialize(propertyValue);

            hash = PoseidonHash.getInstanceConstantLength(2);
            hash.update(keyField);
            hash.update(valueFields);

            return hash.finalizeHash();
        } catch (DeserializationException e) {
            throw new FieldElementConversionException("Cannot deserialize key or value to field element");
        } catch (FinalizationException e) {
            throw new FieldElementConversionException("Cannot finalize merkle leaf hash");
        } finally {
            closeAll(keyField, valueFields, hash);
        }
    }

    private void closeAll(AutoCloseable ...autoCloseable) {
        for (AutoCloseable closeable : autoCloseable) {
            try {
                closeable.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
