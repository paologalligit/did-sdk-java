package com.hedera.hashgraph.identity.hcs.presenter;

import com.google.gson.JsonObject;
import com.hedera.hashgraph.identity.hcs.vc.CredentialSubject;
import com.hedera.hashgraph.identity.hcs.vc.HcsVcDocumentBase;

public interface Presenter<T extends HcsVcDocumentBase<? extends CredentialSubject>> {
    String fromDocumentToString(T vcDocument);
    JsonObject fromDocumentToJson(T vcDocument);
    T fromStringToDocument(String stringDocument);
    T fromJsonToDocument(JsonObject jsonDocument);
}