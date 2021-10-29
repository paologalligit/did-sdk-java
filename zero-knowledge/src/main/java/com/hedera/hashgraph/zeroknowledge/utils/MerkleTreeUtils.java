package com.hedera.hashgraph.zeroknowledge.utils;

import com.hedera.hashgraph.identity.hcs.vc.CredentialSubject;
import com.hedera.hashgraph.zeroknowledge.mock.BaseMerkleTree;
import com.hedera.hashgraph.zeroknowledge.mock.FieldElement;
import com.hedera.hashgraph.zeroknowledge.mock.FieldElementException;
import com.hedera.hashgraph.zeroknowledge.mock.PoseidonHash;

import java.nio.charset.StandardCharsets;
import java.util.List;

public final class MerkleTreeUtils {
    public static FieldElement computeHash(String documentId, FieldElement merkleTreeRoot) throws FieldElementException {
        FieldElement documentIdField = FieldElement.deserialize(documentId.getBytes(StandardCharsets.UTF_8));
        FieldElement merkleTreeRootField = new FieldElement(); //FieldElement.deserialize(merkleTreeRoot.getBytes(StandardCharsets.UTF_8));

        PoseidonHash hash = PoseidonHash.getInstanceConstantLength(2);
        hash.update(documentIdField);
        hash.update(merkleTreeRootField);

        documentIdField.close();
        merkleTreeRootField.close();
        hash.close();

        return hash.finalizeHash();
    }

    public static <T extends CredentialSubject> FieldElement getMerkleTreeRoot(List<T> credentialSubjects) {
        BaseMerkleTree inputTree = BaseMerkleTree.init(2);

        for (T credentialSubject : credentialSubjects) {
            // TODO: get all class' attributes key/value
        }

        return new FieldElement();
    }
}
