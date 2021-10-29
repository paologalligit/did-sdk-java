package com.hedera.hashgraph.identity.hcs.example.appnet.presenter;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.hedera.hashgraph.identity.hcs.example.appnet.vc.DrivingLicenseZeroKnowledgeDocument;
import com.hedera.hashgraph.zeroknowledge.presenter.VcDocumentZeroKnowledgePresenter;

import java.util.LinkedHashMap;

public class DrivingLicenseZeroKnowledgePresenter extends VcDocumentZeroKnowledgePresenter<DrivingLicenseZeroKnowledgeDocument> {
    private static final String SIGNATURE_PROPERTY = "signature";
    private static final String PROOF_PROPERTY = "proof";
    private static final String[] JSON_PROPERTIES_ORDER = {"@context", "id", "type", "credentialSchema",
            "credentialSubject", "issuer", "issuanceDate", "proof"};

    @Override
    public String fromDocumentToString(DrivingLicenseZeroKnowledgeDocument vcDocument) {
        JsonObject parentJsonObject = super.fromDocumentToJson(vcDocument);
        insertSignatureIntoProofField(vcDocument, parentJsonObject);

        LinkedHashMap<String, JsonElement> map = new LinkedHashMap<>();

        for (String property : JSON_PROPERTIES_ORDER) {
            if (parentJsonObject.has(property)) {
                map.put(property, parentJsonObject.get(property));
            }
        }
        // Turn map to JSON
        return gson.toJson(map);
    }

    private void insertSignatureIntoProofField(DrivingLicenseZeroKnowledgeDocument vcDocument, JsonObject parentJsonObject) {
        JsonPrimitive signatureJsonPrimitive = parentJsonObject.getAsJsonPrimitive(SIGNATURE_PROPERTY);
        JsonObject proofJsonObject = new JsonObject();
        boolean proofIsSet = vcDocument.getProof() != null;
        if (proofIsSet) {
            JsonObject proofObject = vcDocument.getProof().toNormalizedJsonElement(false).getAsJsonObject();
            proofObject.add(SIGNATURE_PROPERTY, signatureJsonPrimitive);
            proofJsonObject.add(PROOF_PROPERTY, proofObject);
            parentJsonObject.add(PROOF_PROPERTY, proofJsonObject.getAsJsonObject(PROOF_PROPERTY));
        } else {
            parentJsonObject.add(PROOF_PROPERTY, signatureJsonPrimitive);
        }
    }
}
