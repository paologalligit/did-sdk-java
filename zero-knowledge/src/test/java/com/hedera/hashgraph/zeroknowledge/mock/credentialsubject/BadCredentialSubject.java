package com.hedera.hashgraph.zeroknowledge.mock.credentialsubject;

import com.hedera.hashgraph.identity.hcs.vc.CredentialSubject;
import com.hedera.hashgraph.zeroknowledge.vc.MerkleTreeLeaf;

public final class BadCredentialSubject extends CredentialSubject {
    private final double doubleField;

    public BadCredentialSubject(double doubleField) {
        this.doubleField = doubleField;
    }

    @MerkleTreeLeaf(keyName = "doubleField")
    public double getDoubleField() {
        return doubleField;
    }
}
