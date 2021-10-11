package com.hedera.hashgraph.identity.hcs.example.appnet.vp;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ZkSnarkProof {
    private static final String TYPE = "ZkSnarkProof";

    @Expose(serialize = true, deserialize = true)
    @SerializedName(ZkSnarkProofJsonProperties.TYPE)
    private String type;

    @Expose(serialize = true, deserialize = true)
    @SerializedName(ZkSnarkProofJsonProperties.CREDENTIAL_SUBJECT_MERKLE_TREE_ROOT)
    private String credentialSubjectMerkleTreeRoot;

    @Expose(serialize = true, deserialize = true)
    @SerializedName(ZkSnarkProofJsonProperties.CREDENTIAL_SUBJECT_MERKLE_TREE_ROOT_SIGNATURE)
    private String credentialSubjectMerkleTreeRootSignature;

    @Expose(serialize = true, deserialize = true)
    @SerializedName(ZkSnarkProofJsonProperties.SNARK_PROOF)
    private String snarkProof;

    public ZkSnarkProof() {
        this.type = TYPE;
    }

    public String getType() {
        return type;
    }

    public String getCredentialSubjectMerkleTreeRoot() {
        return credentialSubjectMerkleTreeRoot;
    }

    public String getCredentialSubjectMerkleTreeRootSignature() {
        return credentialSubjectMerkleTreeRootSignature;
    }

    public String getSnarkProof() {
        return snarkProof;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setCredentialSubjectMerkleTreeRoot(String credentialSubjectMerkleTreeRoot) {
        this.credentialSubjectMerkleTreeRoot = credentialSubjectMerkleTreeRoot;
    }

    public void setCredentialSubjectMerkleTreeRootSignature(String credentialSubjectMerkleTreeRootSignature) {
        this.credentialSubjectMerkleTreeRootSignature = credentialSubjectMerkleTreeRootSignature;
    }

    public void setSnarkProof(String snarkProof) {
        this.snarkProof = snarkProof;
    }
}
