package com.hedera.hashgraph.identity.hcs.example.appnet.agecircuit.interactor;

import com.hedera.hashgraph.identity.hcs.example.appnet.agecircuit.model.AgeCircuitVerifyPublicInput;
import com.hedera.hashgraph.zeroknowledge.circuit.interactor.CircuitVerifierInteractor;
import io.horizenlabs.agecircuit.AgeCircuitProof;

import static io.horizenlabs.agecircuit.AgeCircuitProof.generateDLogKeys;

public class AgeCircuitVerifierInteractor implements CircuitVerifierInteractor<AgeCircuitVerifyPublicInput> {
    @Override
    public boolean verifyProof(AgeCircuitVerifyPublicInput publicInput) {
        generateDLogKeys(1 << 17, 1 << 15);

        return AgeCircuitProof.verifyProof(
                publicInput.getProof(), publicInput.getCurrentYear(), publicInput.getCurrentMonth(), publicInput.getCurrentDay(),
                publicInput.getAgeThreshold(), publicInput.getHolderPublicKey(), publicInput.getAuthorityPublicKey(),
                publicInput.getChallenge(), publicInput.getDocumentId(), publicInput.getVerificationKeyPath()
        );
    }
}
