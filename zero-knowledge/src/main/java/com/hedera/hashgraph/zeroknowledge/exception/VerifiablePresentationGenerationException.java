package com.hedera.hashgraph.zeroknowledge.exception;

public class VerifiablePresentationGenerationException extends Exception {
    public VerifiablePresentationGenerationException(String message, Exception e) {
        super(message, e);
    }
}
