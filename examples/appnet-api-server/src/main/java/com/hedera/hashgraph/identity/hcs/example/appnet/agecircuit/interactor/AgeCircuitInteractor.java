package com.hedera.hashgraph.identity.hcs.example.appnet.agecircuit.interactor;

import com.hedera.hashgraph.identity.hcs.example.appnet.agecircuit.model.AgeCircuitProofPublicInput;
import com.hedera.hashgraph.identity.hcs.example.appnet.agecircuit.model.AgeCircuitVerifyPublicInput;
import com.hedera.hashgraph.zeroknowledge.circuit.CircuitInteractor;
import com.hedera.hashgraph.zeroknowledge.circuit.model.CircuitProofPublicInput;
import com.hedera.hashgraph.zeroknowledge.circuit.model.CircuitVerifyPublicInput;
import io.horizenlabs.agecircuit.AgeCircuitProof;

public class AgeCircuitInteractor implements CircuitInteractor {
    @Override
    public void setupCircuit(String provingKeyPath, String verificationKeyPath) {
        AgeCircuitProof ageCircuitProof = new AgeCircuitProof();

        generateDLogKeys(1 << 17, 1 << 15);

        ageCircuitProof.setup(provingKeyPath, verificationKeyPath);
    }

    private void generateDLogKeys(int maxSegmentSize, int supportedSegmentSize) {
        AgeCircuitProof.generateDLogKeys(maxSegmentSize, supportedSegmentSize);
    }

    @Override
    public byte[] generateProof(CircuitProofPublicInput circuitProofPublicInput) {
        if (!(circuitProofPublicInput instanceof AgeCircuitProofPublicInput)) {
            throw new IllegalArgumentException();
        }

        AgeCircuitProofPublicInput publicInput = (AgeCircuitProofPublicInput) circuitProofPublicInput;
        AgeCircuitProof ageCircuitProof = new AgeCircuitProof();

        generateDLogKeys(1 << 17, 1 << 15);

        return ageCircuitProof.createProof(
                publicInput.getDayValue(), publicInput.getMonthValue(), publicInput.getYearValue(),
                publicInput.getDayLabel(), publicInput.getMonthLabel(), publicInput.getYearLabel(),
                publicInput.getDayMerklePath(), publicInput.getMonthMerklePath(), publicInput.getYearMerklePath(),
                publicInput.getMerkleTreeRoot(), publicInput.getSignedChallenge(), publicInput.getZkSignature(),
                publicInput.getCurrentYear(), publicInput.getAgeThreshold(), publicInput.getHolderPublicKey(),
                publicInput.getAuthorityPublicKey(), publicInput.getChallenge(), publicInput.getDocumentId(),
                publicInput.getProvingKeyPath()
        );
    }

    @Override
    public boolean verifyProof(CircuitVerifyPublicInput circuitVerifyPublicInput) {
        if (!(circuitVerifyPublicInput instanceof AgeCircuitVerifyPublicInput)) {
            throw new IllegalArgumentException();
        }

        AgeCircuitVerifyPublicInput publicInput = (AgeCircuitVerifyPublicInput) circuitVerifyPublicInput;

        generateDLogKeys(1 << 17, 1 << 15);

        return AgeCircuitProof.verifyProof(
                publicInput.getProof(), publicInput.getCurrentDateTimestamp(), publicInput.getAgeThreshold(),
                publicInput.getHolderPublicKey(), publicInput.getAuthorityPublicKey(), publicInput.getChallenge(),
                publicInput.getDocumentId(), publicInput.getVerificationKeyPath()
        );
    }
}
