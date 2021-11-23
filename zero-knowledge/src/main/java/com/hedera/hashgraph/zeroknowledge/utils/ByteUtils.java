package com.hedera.hashgraph.zeroknowledge.utils;

import io.horizen.common.librustsidechains.DeserializationException;
import io.horizen.common.librustsidechains.FieldElement;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public final class ByteUtils {
    private static final ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);

    public static byte[] longToBytes(long x) {
        buffer.putLong(0, x);
        return buffer.array();
    }

    public static long bytesToLong(byte[] bytes) {
        buffer.put(bytes, 0, bytes.length);
        buffer.flip();
        return buffer.getLong();
    }

    public static byte[] intToBytes(int value) {
        ByteBuffer buffer = ByteBuffer.allocate(Integer.BYTES);
        buffer.putInt(value);
        buffer.rewind();
        return buffer.array();
    }

    public static List<byte[]> chunkByteArrayToFieldElementsList(byte[] documentId) throws DeserializationException {
        int documentIdLength = documentId.length;
        int chunksNum = (int) Math.ceil((double) documentIdLength / FieldElement.FIELD_ELEMENT_LENGTH);
        List<byte[]> result = new ArrayList<>();

        for (int chunk = 0; chunk < chunksNum; chunk++) {
            result.add(getFieldElement(chunk, documentId));
        }

        return result;
    }

    private static byte[] getFieldElement(int chunk, byte[] documentId) throws DeserializationException {
        byte[] newChunk = new byte[FieldElement.FIELD_ELEMENT_LENGTH];
        if (FieldElement.FIELD_ELEMENT_LENGTH - 1 <= documentId.length - (chunk * FieldElement.FIELD_ELEMENT_LENGTH)) {
            System.arraycopy(documentId, chunk * FieldElement.FIELD_ELEMENT_LENGTH, newChunk, 0, FieldElement.FIELD_ELEMENT_LENGTH - 1);
        } else {

        }
//      Arrays.copyOfRange(documentId, chunk, chunk * FieldElement.FIELD_ELEMENT_LENGTH - 1);

        return newChunk;
    }

    private static final byte[] HEX_ARRAY = "0123456789abcdef".getBytes(StandardCharsets.US_ASCII);
    public static String bytesToHex(byte[] bytes) {
        byte[] hexChars = new byte[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars, StandardCharsets.UTF_8);
    }

    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i+1), 16));
        }
        return data;
    }
}