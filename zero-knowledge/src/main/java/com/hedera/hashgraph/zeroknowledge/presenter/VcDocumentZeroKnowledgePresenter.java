package com.hedera.hashgraph.zeroknowledge.presenter;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.hedera.hashgraph.identity.hcs.presenter.Presenter;
import com.hedera.hashgraph.identity.hcs.vc.CredentialSubject;
import com.hedera.hashgraph.identity.utils.JsonUtils;
import com.hedera.hashgraph.zeroknowledge.vc.HcsVcDocumentZeroKnowledge;

import java.util.LinkedHashMap;

public class VcDocumentZeroKnowledgePresenter<U extends HcsVcDocumentZeroKnowledge<? extends CredentialSubject>> implements Presenter<U> {
    private static final String PROOF_PROPERTY = "proof";
    private static final String SIGNATURE_PROPERTY = "signature";
    private static final String[] JSON_PROPERTIES_ORDER = {"@context", "id", "type",
            "credentialSubject", "issuer", "issuanceDate", "signature"};
    protected static final Gson gson = JsonUtils.getGson();

    @Override
    public String fromDocumentToString(U vcDocument) {
        // First turn to normal JSON
        JsonObject root = fromDocumentToJson(vcDocument);
        // Then put JSON properties in ordered map
        LinkedHashMap<String, JsonElement> map = new LinkedHashMap<>();
        for (String property : JSON_PROPERTIES_ORDER) {
            if (SIGNATURE_PROPERTY.equals(property)) {
                map.put(PROOF_PROPERTY, root.get(SIGNATURE_PROPERTY));
            } else if (root.has(property)) {
                map.put(property, root.get(property));
            }
        }
        // Turn map to JSON
        return gson.toJson(map);
    }

    @Override
    public JsonObject fromDocumentToJson(U vcDocument) {
        return gson.toJsonTree(vcDocument).getAsJsonObject();
    }

    @Override
    public U fromStringToDocument(String stringDocument) {
        JsonObject jsonDocument = gson.fromJson(stringDocument, JsonObject.class);
        return null;
    }

    @Override
    public U fromJsonToDocument(JsonObject jsonDocument) {
        return null;
    }
}