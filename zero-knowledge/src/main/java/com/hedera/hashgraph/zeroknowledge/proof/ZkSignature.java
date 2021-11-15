package com.hedera.hashgraph.zeroknowledge.proof;

import com.google.gson.annotations.Expose;
import com.hedera.hashgraph.identity.hcs.vc.CredentialSubject;
import com.hedera.hashgraph.identity.hcs.vc.HcsVcDocumentBase;
import com.hedera.hashgraph.sdk.PrivateKey;
import com.hedera.hashgraph.zeroknowledge.merkletree.factory.MerkleTreeFactory;
import com.hedera.hashgraph.zeroknowledge.utils.MerkleTreeUtils;
import io.horizen.common.librustsidechains.*;
import io.horizen.common.merkletreenative.BaseMerkleTree;
import io.horizen.common.merkletreenative.MerkleTreeException;

import java.lang.reflect.InvocationTargetException;

public class ZkSignature<T extends CredentialSubject> implements ZeroKnowledgeSignature<T> {
    @Expose
    private String signature;

    private final MerkleTreeFactory merkleTreeFactory;

    public ZkSignature(MerkleTreeFactory merkleTreeFactory) {
        this(null, merkleTreeFactory);
    }

    public ZkSignature(String signature, MerkleTreeFactory merkleTreeFactory) {
        this.signature = signature;
        this.merkleTreeFactory = merkleTreeFactory;
    }

    @Override
    public String getSignature() {
        return this.signature;
    }

    @Override
    public void sign(PrivateKey privateKey, HcsVcDocumentBase<T> vcDocument) throws InvocationTargetException, IllegalAccessException, FieldElementConversionException, MerkleTreeException, InitializationException, FinalizationException, DeserializationException {
        // Generating signature steps
        // 1. retrieve document Id and the merkleTreeRoot
        String documentId = vcDocument.getId();
        BaseMerkleTree merkleTree = merkleTreeFactory.getMerkleTreeRoot(vcDocument.getCredentialSubject());

        FieldElement merkleTreeRoot = merkleTree.root();

        FieldElement hash = computeHash(documentId, merkleTreeRoot);
        // TODO: sign the hash
        this.signature = "fake-zeroKnowledgeSignature";
    }

    private FieldElement computeHash(String documentId, FieldElement merkleTreeRoot) throws DeserializationException, FinalizationException {
        return MerkleTreeUtils.computeHash(documentId, merkleTreeRoot);
    }
}
