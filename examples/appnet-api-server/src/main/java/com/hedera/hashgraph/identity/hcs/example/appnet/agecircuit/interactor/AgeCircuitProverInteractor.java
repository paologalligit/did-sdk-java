package com.hedera.hashgraph.identity.hcs.example.appnet.agecircuit.interactor;

import com.hedera.hashgraph.identity.hcs.example.appnet.agecircuit.model.AgeCircuitProofPublicInput;
import com.hedera.hashgraph.zeroknowledge.circuit.interactor.CircuitProverInteractor;
import io.horizenlabs.agecircuit.AgeCircuitProof;

import static io.horizenlabs.agecircuit.AgeCircuitProof.generateDLogKeys;

public class AgeCircuitProverInteractor implements CircuitProverInteractor<AgeCircuitProofPublicInput> {
    @Override
    public byte[] generateProof(AgeCircuitProofPublicInput publicInput) {
        AgeCircuitProof ageCircuitProof = new AgeCircuitProof();

        generateDLogKeys(1 << 17, 1 << 15);

        return ageCircuitProof.createProof(
                publicInput.getDayValue(), publicInput.getMonthValue(), publicInput.getYearValue(),
                publicInput.getDayLabel(), publicInput.getMonthLabel(), publicInput.getYearLabel(),
                publicInput.getDayMerklePath(), publicInput.getMonthMerklePath(), publicInput.getYearMerklePath(),
                publicInput.getMerkleTreeRoot(), publicInput.getSignedChallenge(), publicInput.getZkSignature(),
                publicInput.getCurrentYear(), publicInput.getCurrentMonth(), publicInput.getCurrentDay(),
                publicInput.getAgeThreshold(), publicInput.getHolderPublicKey(), publicInput.getAuthorityPublicKey(),
                publicInput.getChallenge(), publicInput.getDocumentId(), publicInput.getProvingKeyPath()
        );
    }
}
