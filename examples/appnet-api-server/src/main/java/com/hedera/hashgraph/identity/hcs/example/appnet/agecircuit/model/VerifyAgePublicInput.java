package com.hedera.hashgraph.identity.hcs.example.appnet.agecircuit.model;

import com.hedera.hashgraph.zeroknowledge.circuit.model.ZeroKnowledgeVerifyPublicInput;

public final class VerifyAgePublicInput implements ZeroKnowledgeVerifyPublicInput {
    private final byte[] proof;
    private final long currentDateTimestamp;
    private final int ageThreshold;
    private final String holderPublicKey;
    private final String authorityPublicKey;
    private final String challenge;
    private final String documentId;
    private final String verificationKeyPath;

    public VerifyAgePublicInput(byte[] proof, long currentDateTimestamp, int ageThreshold, String holderPublicKey, String authorityPublicKey, String challenge, String documentId, String verificationKeyPath) {
        this.proof = proof;
        this.currentDateTimestamp = currentDateTimestamp;
        this.ageThreshold = ageThreshold;
        this.holderPublicKey = holderPublicKey;
        this.authorityPublicKey = authorityPublicKey;
        this.challenge = challenge;
        this.documentId = documentId;
        this.verificationKeyPath = verificationKeyPath;
    }

    public byte[] getProof() {
        return proof;
    }

    public long getCurrentDateTimestamp() {
        return currentDateTimestamp;
    }

    public int getAgeThreshold() {
        return ageThreshold;
    }

    public String getHolderPublicKey() {
        return holderPublicKey;
    }

    public String getAuthorityPublicKey() {
        return authorityPublicKey;
    }

    public String getChallenge() {
        return challenge;
    }

    public String getDocumentId() {
        return documentId;
    }

    public String getVerificationKeyPath() {
        return verificationKeyPath;
    }

}
