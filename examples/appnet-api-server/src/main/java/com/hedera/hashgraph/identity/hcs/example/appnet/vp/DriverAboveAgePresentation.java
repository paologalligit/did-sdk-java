package com.hedera.hashgraph.identity.hcs.example.appnet.vp;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.hedera.hashgraph.identity.hcs.vp.HcsVpDocumentBase;
import com.hedera.hashgraph.identity.utils.JsonUtils;

import org.threeten.bp.Instant;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class DriverAboveAgePresentation extends HcsVpDocumentBase<DriverAboveAgeVerifiableCredential> {
    public static final String ID = "http://example.gov/credentials/3732";
    public static final String DRIVER_ABOVE_AGE_CONTEXT = "https://www.w3.org/2018/credentials/examples/v1";
    private static final String DRIVER_ABOVE_AGE_TYPE = "DriverAboveAge";
    private static final String[] JSON_PROPERTIES_ORDER = {"@context", "id", "type", "issuanceDate",
            "holder", "verifiableCredential"};

    public DriverAboveAgePresentation() {
        super();

        this.id = ID;
        this.issuanceDate = Instant.now();
        this.context.add(DRIVER_ABOVE_AGE_CONTEXT);
        addType(DRIVER_ABOVE_AGE_TYPE);
        this.verifiableCredential = new ArrayList<>();
    }

    public void setHolder(String holder) {
        this.holder = holder;
    }

    public void addVerifiableCredential(DriverAboveAgeVerifiableCredential vcDocuments) {
        this.verifiableCredential.add(vcDocuments);
    }

    public String toNormalizedJson() {
        Gson gson = JsonUtils.getGson();

        // First turn to normal JSON
        JsonObject root = gson.toJsonTree(this).getAsJsonObject();
        // Then put JSON properties in ordered map
        LinkedHashMap<String, JsonElement> map = new LinkedHashMap<>();

        JsonArray verifiableCredentialArray = new JsonArray();
        for (String property : JSON_PROPERTIES_ORDER) {
            if (property.equals("verifiableCredential")) {
                for (DriverAboveAgeVerifiableCredential vc : this.verifiableCredential) {
                    verifiableCredentialArray.add(vc.toNormalizedJsonElement());
                }
                map.put(property, verifiableCredentialArray);
            } else {
                map.put(property, root.get(property));
            }
        }
        // Turn map to JSON
        return gson.toJson(map);
    }
}
