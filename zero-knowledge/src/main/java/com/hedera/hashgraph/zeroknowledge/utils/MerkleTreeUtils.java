package com.hedera.hashgraph.zeroknowledge.utils;

import io.horizen.common.librustsidechains.DeserializationException;
import io.horizen.common.librustsidechains.FieldElement;
import io.horizen.common.librustsidechains.FinalizationException;
import io.horizen.common.poseidonnative.PoseidonHash;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static com.hedera.hashgraph.zeroknowledge.utils.ByteUtils.chunkByteArrayToFieldElementsList;

public final class MerkleTreeUtils {
    public static FieldElement computeHash(String documentId, FieldElement merkleTreeRoot) throws DeserializationException, FinalizationException {
        // This will disappear when issue https://github.com/HorizenOfficial/sc_cryptolib_common/issues/4 is implemented
        List<byte[]> byteChunks = chunkByteArrayToFieldElementsList(documentId.getBytes(StandardCharsets.UTF_8));
        List<FieldElement> fieldElementChunks = serializeBytesToFieldsElementsList(byteChunks);

        int hashableParamsLength = fieldElementChunks.size() + 1;
        try (PoseidonHash hash = PoseidonHash.getInstanceConstantLength(hashableParamsLength)) {
            for (FieldElement chunk : fieldElementChunks) {
                hash.update(chunk);
            }
            hash.update(merkleTreeRoot);

            merkleTreeRoot.close();

            return hash.finalizeHash();
        }
    }

    private static List<FieldElement> serializeBytesToFieldsElementsList(List<byte[]> byteChunks) throws DeserializationException {
        List<FieldElement> result = new ArrayList<>();
        for (byte[] currentChunk : byteChunks) {
            result.add(FieldElement.deserialize(currentChunk));
        }
        return result;
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
