package com.hedera.hashgraph.zeroknowledge.vc;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.hedera.hashgraph.identity.hcs.vc.CredentialSubject;
import com.hedera.hashgraph.identity.hcs.vc.HcsVcDocumentBase;
import com.hedera.hashgraph.identity.hcs.vc.HcsVcDocumentJsonProperties;
import com.hedera.hashgraph.sdk.PrivateKey;
import com.hedera.hashgraph.zeroknowledge.mock.FieldElement;
import com.hedera.hashgraph.zeroknowledge.mock.FieldElementException;
import com.hedera.hashgraph.zeroknowledge.utils.MerkleTreeUtils;

import java.util.LinkedHashMap;
import java.util.Map;

public class HcsVcDocumentZeroKnowledge<T extends CredentialSubject> extends HcsVcDocumentBase<T> {
    @Expose(serialize = true, deserialize = true)
    @SerializedName(HcsVcDocumentJsonProperties.SIGNATURE)
    protected String signature;

    public String getSignature() {
        return signature;
    }

    @Override
    public Map<String, Object> getCustomHashableFieldsHook() {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put(HcsVcDocumentJsonProperties.SIGNATURE, this.signature);
        return map;
    }

    public void generateSignature(PrivateKey privateKey) throws FieldElementException {
        // Generating signature steps
        // 1. retrieve document Id and the merkleTreeRoot
        String documentId = getId();
        FieldElement merkleTreeRoot = getMerkleTreeRoot();

        FieldElement hash = computeHash(documentId, merkleTreeRoot);
        // TODO: sign the hash
        this.signature = "fake-zeroKnowledgeSignature";
    }

    protected FieldElement getMerkleTreeRoot() {
        return MerkleTreeUtils.getMerkleTreeRoot(credentialSubject);
    }

    private FieldElement computeHash(String documentId, FieldElement merkleTreeRoot) throws FieldElementException {
        return MerkleTreeUtils.computeHash(documentId, merkleTreeRoot);
    }
}