package com.hedera.hashgraph.zeroknowledge.vp;

import com.google.common.collect.Lists;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.hedera.hashgraph.identity.hcs.vc.HcsVcDocumentJsonProperties;
import org.threeten.bp.Instant;

import java.util.List;

public abstract class HcsPresentationBase extends VerifiableCredentialBase {
    @Expose(serialize = true, deserialize = false)
    @SerializedName(HcsVpDocumentJsonProperties.CONTEXT)
    protected List<String> context;

    @Expose(serialize = true, deserialize = true)
    @SerializedName(HcsVpDocumentJsonProperties.ID)
    protected String id;

    @Expose(serialize = true, deserialize = true)
    @SerializedName(HcsVpDocumentJsonProperties.TYPE)
    protected List<String> type;

    @Expose(serialize = true, deserialize = true)
    @SerializedName(HcsVpDocumentJsonProperties.HOLDER)
    protected String holder;

    @Expose(serialize = true, deserialize = true)
    @SerializedName(HcsVcDocumentJsonProperties.ISSUANCE_DATE)
    protected Instant issuanceDate;

    public HcsPresentationBase() {
        this.type = Lists.newArrayList(HcsVpDocumentJsonProperties.VERIFIABLE_CREDENTIAL_TYPE);
        this.issuanceDate = Instant.now();
    }
}
