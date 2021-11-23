package com.hedera.hashgraph.zeroknowledge.vc;

import io.horizen.common.librustsidechains.*;
import io.horizen.common.poseidonnative.PoseidonHash;

import java.nio.charset.StandardCharsets;

import static com.hedera.hashgraph.zeroknowledge.utils.MerkleTreeUtils.getFieldElementByAllowedTypes;

public class CredentialSubjectMerkleTreeLeaf implements FieldElementConvertible {
    private final String propertyLabel;
    private final Object propertyValue;

    public CredentialSubjectMerkleTreeLeaf(String propertyLabel, Object propertyValue) {
        this.propertyLabel = propertyLabel;
        this.propertyValue = propertyValue;
    }

    @Override
    public FieldElement toFieldElement() throws FieldElementConversionException {
        FieldElement keyField, valueFields;
        PoseidonHash hash;

        try {
            keyField = FieldElement.deserialize(propertyLabel.getBytes(StandardCharsets.UTF_8));
//            valueFields = getFieldElementByAllowedTypes(propertyValue);
            if (propertyValue instanceof Integer) {
                valueFields = FieldElement.createFromLong((int) propertyValue);
            } else if (propertyValue instanceof Long) {
                valueFields = FieldElement.createFromLong((long) propertyValue);
            } else {
                valueFields = FieldElement.deserialize(propertyValue.toString().getBytes(StandardCharsets.UTF_8));
            }

            hash = PoseidonHash.getInstanceConstantLength(2);
            hash.update(keyField);
            hash.update(valueFields);

            return hash.finalizeHash();
        } catch (DeserializationException | IllegalArgumentException e) {
            throw new FieldElementConversionException(
                    String.format("Cannot deserialize label or value to field element. Label: '%s', value: '%s'", propertyLabel, propertyValue),
                    e
            );
        } catch (FinalizationException e) {
            throw new FieldElementConversionException("Cannot finalize merkle leaf hash");
        } finally {
//            closeAll(keyField, valueFields, hash);
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
