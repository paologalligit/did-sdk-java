package com.hedera.hashgraph.zeroknowledge.presenter;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.hedera.hashgraph.identity.utils.JsonUtils;
import com.hedera.hashgraph.zeroknowledge.vp.HcsVpDocumentBase;
import com.hedera.hashgraph.zeroknowledge.vp.HcsVpDocumentJsonProperties;
import com.hedera.hashgraph.zeroknowledge.vp.VerifiableCredentialBase;
import org.threeten.bp.Instant;

import java.util.LinkedHashMap;
import java.util.List;

import static com.hedera.hashgraph.identity.utils.JsonUtils.getJsonElementAsList;

public abstract class ZeroKnowledgeVpPresenter<T extends HcsVpDocumentBase<? extends VerifiableCredentialBase>> implements VpPresenter<T> {
    private static final String[] JSON_PROPERTIES_ORDER = {"@context", "id", "type", "issuanceDate", "holder"};
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

        String docId = jsonDocument.get(HcsVpDocumentJsonProperties.ID).getAsString();
        decodedDocument.setId(docId);

        List<String> docTypes = getJsonElementAsList(jsonDocument.get(HcsVpDocumentJsonProperties.TYPE));
        decodedDocument.setType(docTypes);

        String holder = jsonDocument.get(HcsVpDocumentJsonProperties.HOLDER).getAsString();
        decodedDocument.setHolder(holder);

        String issuanceDate = jsonDocument.get(HcsVpDocumentJsonProperties.ISSUANCE_DATE).getAsString();
        decodedDocument.setIssuanceDate(Instant.parse(issuanceDate));

        List<String> docContext = getJsonElementAsList(jsonDocument.get(HcsVpDocumentJsonProperties.CONTEXT));
        decodedDocument.setContext(docContext);

        return decodedDocument;
    }

    protected abstract T initializeNewBlankDocument();
}
