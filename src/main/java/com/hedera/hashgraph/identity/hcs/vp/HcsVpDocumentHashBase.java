package com.hedera.hashgraph.identity.hcs.vp;

import com.google.common.collect.Lists;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.hedera.hashgraph.identity.hcs.vc.HcsVcDocumentJsonProperties;

import org.threeten.bp.Instant;
import java.util.List;

/**
 * The part of the VP document that is used for hash calculation.
 */
public abstract class HcsVpDocumentHashBase {
    @Expose(serialize = true, deserialize = true)
    @SerializedName(HcsVcDocumentJsonProperties.ID)
    protected String id;

    @Expose(serialize = true, deserialize = true)
    @SerializedName(HcsVcDocumentJsonProperties.TYPE)
    protected List<String> type;

    @Expose(serialize = true, deserialize = true)
    @SerializedName(HcsVcDocumentJsonProperties.ISSUANCE_DATE)
    protected Instant issuanceDate;

    /**
     * Creates a new VP document instance.
     */
    protected HcsVpDocumentHashBase() {
        this.type = Lists.newArrayList(HcsVpDocumentJsonProperties.VERIFIABLE_CREDENTIAL_TYPE);
    }
}
