package com.hedera.hashgraph.zeroknowledge.utils;

import io.horizen.common.librustsidechains.DeserializationException;
import io.horizen.common.librustsidechains.FieldElement;
import io.horizen.common.librustsidechains.FinalizationException;
import io.horizen.common.poseidonnative.PoseidonHash;

import java.nio.charset.StandardCharsets;

public final class MerkleTreeUtils {
    public static FieldElement computeHash(FieldElement documentId, FieldElement merkleTreeRoot) throws FinalizationException {
        try (PoseidonHash hash = PoseidonHash.getInstanceConstantLength(2)) {
            hash.update(documentId);
            hash.update(merkleTreeRoot);

            return hash.finalizeHash();
        }
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

    public static FieldElement getFieldElementByAllowedTypes(Object field) throws IllegalArgumentException, DeserializationException {
        if (field instanceof Long) {
            return FieldElement.createFromLong((long) field);
        } else if (field instanceof Integer) {
            return FieldElement.createFromLong((int) field);
        } else if (field instanceof String) {
            return FieldElement.deserialize(((String) field).getBytes(StandardCharsets.UTF_8));
        } else {
            throw new IllegalArgumentException("Credential subject leaf type must be a long or String");
        }
    }
}
