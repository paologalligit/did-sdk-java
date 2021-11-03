package com.hedera.hashgraph.zeroknowledge.proof;

import com.google.gson.annotations.Expose;
import com.hedera.hashgraph.identity.hcs.vc.CredentialSubject;
import com.hedera.hashgraph.identity.hcs.vc.HcsVcDocumentBase;
import com.hedera.hashgraph.sdk.PrivateKey;
import com.hedera.hashgraph.zeroknowledge.mock.FieldElement;
import com.hedera.hashgraph.zeroknowledge.mock.FieldElementException;
import com.hedera.hashgraph.zeroknowledge.mock.MerkleTreeLeafException;
import com.hedera.hashgraph.zeroknowledge.utils.MerkleTreeUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class ZkSignature<T extends CredentialSubject> implements ZeroKnowledgeSignature<T> {
    @Expose
    private String signature;

    public ZkSignature() {
        this(null);
    }

    public ZkSignature(String signature) {
        this.signature = signature;
    }

    @Override
    public String getSignature() {
        return this.signature;
    }

    @Override
    public void sign(PrivateKey privateKey, HcsVcDocumentBase<T> vcDocument) throws FieldElementException, InvocationTargetException, IllegalAccessException, MerkleTreeLeafException {
        // Generating signature steps
        // 1. retrieve document Id and the merkleTreeRoot
        String documentId = vcDocument.getId();
        FieldElement merkleTreeRoot = getMerkleTreeRoot(vcDocument.getCredentialSubject());

        FieldElement hash = computeHash(documentId, merkleTreeRoot);
        // TODO: sign the hash
        this.signature = "fake-zeroKnowledgeSignature";
    }

    private FieldElement getMerkleTreeRoot(List<T> credentialSubject) throws InvocationTargetException, IllegalAccessException, MerkleTreeLeafException {
        return MerkleTreeUtils.getMerkleTreeRoot(credentialSubject, credentialSubject.get(0).getClass());
    }

    private FieldElement computeHash(String documentId, FieldElement merkleTreeRoot) throws FieldElementException {
        return MerkleTreeUtils.computeHash(documentId, merkleTreeRoot);
    }
}
