package com.hedera.hashgraph.zeroknowledge.mock.credentialsubject;

import com.hedera.hashgraph.identity.hcs.vc.CredentialSubject;
import com.hedera.hashgraph.zeroknowledge.vc.MerkleTreeLeaf;

public final class TestCredentialSubject extends CredentialSubject {
    private final String name;
    private final String surname;
    private final int age;


    public TestCredentialSubject(String name, String surname, int age) {
        this.name = name;
        this.surname = surname;
        this.age = age;
    }

    @MerkleTreeLeaf(keyName = "name")
    public String getName() {
        return name;
    }

    @MerkleTreeLeaf(keyName = "surname")
    public String getSurname() {
        return surname;
    }

    @MerkleTreeLeaf(keyName = "age")
    public int getAge() {
        return age;
    }
}
