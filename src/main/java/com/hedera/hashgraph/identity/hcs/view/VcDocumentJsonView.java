package com.hedera.hashgraph.identity.hcs.view;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.hedera.hashgraph.identity.hcs.vc.CredentialSubject;
import com.hedera.hashgraph.identity.hcs.vc.Issuer;
import com.hedera.hashgraph.identity.utils.JsonUtils;
import org.threeten.bp.Instant;

import java.util.LinkedHashMap;
import java.util.List;

public abstract class VcDocumentJsonView<T extends CredentialSubject> {
    protected String[] jsonPropertiesOrder;
    private String id;
    private List<String> type;
    private Issuer issuer;
    private Instant issuanceDate;
    private List<String> context;
    private List<T> credentialSubject;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getType() {
        return type;
    }

    public void setType(List<String> type) {
        this.type = type;
    }

    public Issuer getIssuer() {
        return issuer;
    }

    public void setIssuer(Issuer issuer) {
        this.issuer = issuer;
    }

    public Instant getIssuanceDate() {
        return issuanceDate;
    }

    public void setIssuanceDate(Instant issuanceDate) {
        this.issuanceDate = issuanceDate;
    }

    public List<String> getContext() {
        return context;
    }

    public void setContext(List<String> context) {
        this.context = context;
    }

    public List<T> getCredentialSubject() {
        return credentialSubject;
    }

    public void setCredentialSubject(List<T> credentialSubject) {
        this.credentialSubject = credentialSubject;
    }

    public VcDocumentJsonView(String[] jsonPropertiesOrder) {
        this.jsonPropertiesOrder = jsonPropertiesOrder;
    }

    public String toJson() {
        Gson gson = JsonUtils.getGson();

        JsonObject root = gson.toJsonTree(this).getAsJsonObject();
        LinkedHashMap<String, JsonElement> map = new LinkedHashMap<>();

        for (String property : jsonPropertiesOrder) {
                map.put(property, root.get(property));
        }

        return gson.toJson(map);
    }
}
