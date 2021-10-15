package com.hedera.hashgraph.identity.hcs.vp;

import com.google.common.collect.Lists;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.hedera.hashgraph.identity.utils.SingleToArrayTypeAdapterFactory;

import java.util.List;

public class HcsVpDocumentBase<T extends VerifiableCredentialBase> extends HcsPresentationBase {
    @Expose(serialize = true, deserialize = true)
    @SerializedName(HcsVpDocumentJsonProperties.VERIFIABLE_CREDENTIAL)
    @JsonAdapter(SingleToArrayTypeAdapterFactory.class)
    protected List<T> verifiableCredential;

    public void setHolder(String holder) {
        this.holder = holder;
    }

    /**
     * Creates a new VP Document instance.
     */
    public HcsVpDocumentBase() {
        super();
        this.context = Lists.newArrayList(HcsVpDocumentJsonProperties.FIRST_CONTEXT_ENTRY);
    }

    /**
     * Add an additional type to `type` field of the VP document.
     *
     * @param type The type to add.
     */
    public void addType(final String type) {
        this.type.add(type);
    }

    public void addVerifiableCredential(T verifiableCredential) {
        this.verifiableCredential.add(verifiableCredential);
    }
}
