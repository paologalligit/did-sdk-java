package com.hedera.hashgraph.zeroknowledge.utils;

import io.horizen.common.librustsidechains.DeserializationException;
import io.horizen.common.librustsidechains.FieldElement;
import io.horizen.common.librustsidechains.FinalizationException;
import io.horizen.common.poseidonnative.PoseidonHash;

import java.nio.charset.StandardCharsets;

public final class MerkleTreeUtils {
    public static FieldElement computeHash(String documentId, FieldElement merkleTreeRoot) throws DeserializationException, FinalizationException {
        FieldElement documentIdField = FieldElement.deserialize(documentId.getBytes(StandardCharsets.UTF_8));

        PoseidonHash hash = PoseidonHash.getInstanceConstantLength(2);
        hash.update(documentIdField);
        hash.update(merkleTreeRoot);

        documentIdField.close();
        merkleTreeRoot.close();
        hash.close();

        return hash.finalizeHash();
    }

    /* TODO: this could be replaced by having all the annotated methods return a ConvertibleInterface of some kind that implements
        a toByte method
     */
    public static byte[] serializeFieldObjectToByteArray(Object field) throws IllegalArgumentException {
        if (field instanceof Long) {
            return ByteUtils.longToBytes((long) field);
        } else if (field instanceof Integer) {
            return ByteUtils.intToBytes((int) field);
        } else if (field instanceof String) {
            return ((String) field).getBytes(StandardCharsets.UTF_8);
        } else {
            throw new IllegalArgumentException("Credential subject leaf type must be a long or String");
        }
    }
}
