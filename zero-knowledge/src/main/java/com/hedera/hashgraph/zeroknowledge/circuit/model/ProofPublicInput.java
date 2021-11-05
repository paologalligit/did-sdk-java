package com.hedera.hashgraph.zeroknowledge.circuit.model;

public final class ProofPublicInput {
    private final String holderPublicKey;
    private final String issuerPublicKey;
    private final String documentId;
    private final int ageThreshold;
    private final int currentYear;
    private final String challenge;


    public ProofPublicInput(String holderPublicKey, String issuerPublicKey, String documentId, int ageThreshold, int currentYear, String challenge) {
        this.holderPublicKey = holderPublicKey;
        this.issuerPublicKey = issuerPublicKey;
        this.documentId = documentId;
        this.ageThreshold = ageThreshold;
        this.currentYear = currentYear;
        this.challenge = challenge;
    }

    public String getHolderPublicKey() {
        return holderPublicKey;
    }

    public String getIssuerPublicKey() {
        return issuerPublicKey;
    }

    public String getDocumentId() {
        return documentId;
    }

    public int getAgeThreshold() {
        return ageThreshold;
    }

    public int getCurrentYear() {
        return currentYear;
    }

    public String getChallenge() {
        return challenge;
    }
}
