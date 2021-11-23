package com.hedera.hashgraph.zeroknowledge.utils;

import io.horizen.common.librustsidechains.DeserializationException;
import io.horizen.common.librustsidechains.FieldElement;
import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class ByteUtilsTest {

    @Ignore
    public void testChunkByteArrayToFieldElementsList() throws DeserializationException {
        // Arrange
        String documentId = "https://example.appnet.com/driving-license/e0b5110d-bee8-4c81-9c00-835402073ed0";
        byte[] documentIdBytes = documentId.getBytes(StandardCharsets.UTF_8);

        // Act
        List<byte[]> byteChunks = ByteUtils.chunkByteArrayToFieldElementsList(documentIdBytes);
        byte[] concatenatedChunks = concatenateChunks(byteChunks, documentIdBytes.length);

        // Assert
        assertEquals(documentIdBytes, concatenatedChunks);
    }

    private byte[] concatenateChunks(List<byte[]> byteChunks, int originalLength) {
        byte[] result = new byte[originalLength];
        int i = 0;
        for (; i < byteChunks.size() - 1; i++) {
            int resultStartIndex = i == 0 ? 0 : (i * FieldElement.FIELD_ELEMENT_LENGTH) - (i + 1);
            System.arraycopy(byteChunks.get(i), i * FieldElement.FIELD_ELEMENT_LENGTH, result, resultStartIndex, FieldElement.FIELD_ELEMENT_LENGTH - 1);
        }
//        System.arraycopy(byteChunks.get(i), i * FieldElement.FIELD_ELEMENT_LENGTH, result, (i * FieldElement.FIELD_ELEMENT_LENGTH) - (i + 1), FieldElement.FIELD_ELEMENT_LENGTH - 1);
        return result;
    }
}