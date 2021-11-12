package com.hedera.hashgraph.zeroknowledge.vp;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.hedera.hashgraph.identity.hcs.vc.HcsDocumentHashBase;

import java.util.List;

public abstract class VerifiableCredentialBase extends HcsDocumentHashBase {
    @Expose(deserialize = false)
    @SerializedName(VerifiableCredentialJsonProperties.CONTEXT)
    protected List<String> context;
}