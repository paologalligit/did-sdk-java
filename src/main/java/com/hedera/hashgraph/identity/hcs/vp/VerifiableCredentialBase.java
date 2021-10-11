package com.hedera.hashgraph.identity.hcs.vp;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.hedera.hashgraph.identity.hcs.vc.Issuer;

import com.hedera.hashgraph.identity.hcs.vc.IssuerTypeAdapterFactory;
import org.threeten.bp.Instant;

import java.util.List;

public abstract class VerifiableCredentialBase {
    @Expose(serialize = true, deserialize = false)
    @SerializedName(VerifiableCredentialJsonProperties.CONTEXT)
    protected List<String> context;

    @Expose(serialize = true, deserialize = true)
    @SerializedName(VerifiableCredentialJsonProperties.TYPE)
    protected List<String> type;

    @Expose(serialize = true, deserialize = true)
    @SerializedName(VerifiableCredentialJsonProperties.ISSUER)
    @JsonAdapter(IssuerTypeAdapterFactory.class)
    protected Issuer issuer;

    @Expose(serialize = true, deserialize = true)
    @SerializedName(VerifiableCredentialJsonProperties.ISSUANCE_DATE)
    protected Instant issuanceDate;
}
