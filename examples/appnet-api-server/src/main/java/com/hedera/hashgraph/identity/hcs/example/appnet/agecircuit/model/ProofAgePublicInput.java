package com.hedera.hashgraph.identity.hcs.example.appnet.agecircuit.model;

import com.hedera.hashgraph.identity.hcs.vc.CredentialSubject;
import com.hedera.hashgraph.zeroknowledge.circuit.model.ZeroKnowledgeProofPublicInput;
import com.hedera.hashgraph.zeroknowledge.proof.ZeroKnowledgeSignature;

import java.util.List;

public final class ProofAgePublicInput<T extends CredentialSubject> implements ZeroKnowledgeProofPublicInput {
    private final List<T> credentialSubject;
    private final ZeroKnowledgeSignature<T> zeroKnowledgeSignature;
    private final String challenge;
    private final String secretKey;
    private final String documentId;
    private final int ageThreshold;
    private final String holderPublicKey;
    private final String authorityPublicKey;
    private final long vcDocumentDate;

    public ProofAgePublicInput(List<T> credentialSubject, ZeroKnowledgeSignature<T> zeroKnowledgeSignature, String challenge, String secretKey, int ageThreshold, String holderPublicKey, String authorityPublicKey, String documentId, long vcDocumentDate) {
        this.credentialSubject = credentialSubject;
        this.zeroKnowledgeSignature = zeroKnowledgeSignature;
        this.challenge = challenge;
        this.secretKey = secretKey;
        this.ageThreshold = ageThreshold;
        this.documentId = documentId;
        this.holderPublicKey = holderPublicKey;
        this.authorityPublicKey = authorityPublicKey;
        this.vcDocumentDate = vcDocumentDate;
    }

    public String getHolderPublicKey() {
        return holderPublicKey;
    }

    public String getAuthorityPublicKey() {
        return authorityPublicKey;
    }

    public String getDocumentId() {
        return documentId;
    }

    public int getAgeThreshold() {
        return ageThreshold;
    }

    public String getChallenge() {
        return challenge;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public List<T> getCredentialSubject() {
        return credentialSubject;
    }

    public ZeroKnowledgeSignature<T> getZeroKnowledgeSignature() {
        return zeroKnowledgeSignature;
    }

    public long getVcDocumentDate() {
        return vcDocumentDate;
    }

    @Override
    public String toString() {
        return "ProofAgePublicInput {" +
                "credentialSubject =" + credentialSubject +
                ", zeroKnowledgeSignature = " + zeroKnowledgeSignature +
                ", challenge = '" + challenge + '\'' +
                ", secretKey = '" + secretKey + '\'' +
                ", documentId = '" + documentId + '\'' +
                ", ageThreshold = " + ageThreshold +
                ", holderPublicKey = '" + holderPublicKey + '\'' +
                ", authorityPublicKey = '" + authorityPublicKey + '\'' +
                ", vcDocumentDate = " + vcDocumentDate +
                '}';
    }
}
