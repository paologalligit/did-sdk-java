package com.hedera.hashgraph.zeroknowledge.proof;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ZkSnarkProof {
    private static final String TYPE = "ZkSnarkProof";

    @Expose(serialize = true, deserialize = true)
    @SerializedName(ZkSnarkProofJsonProperties.TYPE)
    private String type;

    @Expose(serialize = true, deserialize = true)
    @SerializedName(ZkSnarkProofJsonProperties.SIGNATURE)
    private String signature;

    @Expose(serialize = true, deserialize = true)
    @SerializedName(ZkSnarkProofJsonProperties.SNARK_PROOF)
    private String snarkProof;

    public ZkSnarkProof() {
        this.type = TYPE;
    }

    public String getType() {
        return type;
    }

    public String getSnarkProof() {
        return snarkProof;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setSnarkProof(String snarkProof) {
        this.snarkProof = snarkProof;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }
}
