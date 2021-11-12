package com.hedera.hashgraph.identity.hcs.presenter;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.hedera.hashgraph.identity.hcs.vc.CredentialSubject;
import com.hedera.hashgraph.identity.hcs.vc.HcsVcDocumentBase;
import com.hedera.hashgraph.identity.hcs.vc.HcsVcDocumentJsonProperties;
import com.hedera.hashgraph.identity.hcs.vc.Issuer;
import com.hedera.hashgraph.identity.utils.JsonUtils;
import org.threeten.bp.Instant;

import java.util.LinkedHashMap;
import java.util.List;

import static com.hedera.hashgraph.identity.utils.JsonUtils.getJsonElementAsList;

public abstract class HcsVcDocumentVcPresenter<T extends HcsVcDocumentBase<? extends CredentialSubject>> implements VcPresenter<T> {
    private static final String[] JSON_PROPERTIES_ORDER = {"@context", "id", "type",
            "credentialSubject", "issuer", "issuanceDate", "proof"};
    protected static final Gson gson = JsonUtils.getGson();

    @Override
    public String fromDocumentToString(T vcDocument) {
        // First turn to normal JSON
        JsonObject root = gson.toJsonTree(vcDocument).getAsJsonObject();
        // Then put JSON properties in ordered map
        LinkedHashMap<String, JsonElement> map = new LinkedHashMap<>();
        for (String property : JSON_PROPERTIES_ORDER) {
            if (root.has(property)) {
                map.put(property, root.get(property));
            }
        }
        // Turn map to JSON
        return gson.toJson(map);
    }

    @Override
    public T fromStringToDocument(String stringDocument) {
        JsonObject jsonDocument = gson.fromJson(stringDocument, JsonObject.class);
        return fromJsonToDocument(jsonDocument);
    }

    protected T fromJsonToDocument(JsonObject jsonDocument) {
        T decodedDocument = initializeNewBlankDocument();

        String docId = jsonDocument.get(HcsVcDocumentJsonProperties.ID).getAsString();
        decodedDocument.setId(docId);

        List<String> docTypes = getJsonElementAsList(jsonDocument.get(HcsVcDocumentJsonProperties.TYPE));
        decodedDocument.setType(docTypes);

        String issuer = jsonDocument.get(HcsVcDocumentJsonProperties.ISSUER).getAsString();
        decodedDocument.setIssuer(new Issuer(issuer));

        String issuanceDate = jsonDocument.get(HcsVcDocumentJsonProperties.ISSUANCE_DATE).getAsString();
        decodedDocument.setIssuanceDate(Instant.parse(issuanceDate));

        List<String> docContext = getJsonElementAsList(jsonDocument.get(HcsVcDocumentJsonProperties.CONTEXT));
        decodedDocument.setContext(docContext);

        return decodedDocument;
    }

    protected abstract T initializeNewBlankDocument();
}