package com.hedera.hashgraph.identity.hcs.example.appnet.agecircuit.provider;

import com.hedera.hashgraph.identity.hcs.example.appnet.agecircuit.model.ProofAgePublicInput;
import com.hedera.hashgraph.identity.hcs.example.appnet.agecircuit.model.VerifyAgePublicInput;
import com.hedera.hashgraph.identity.hcs.example.appnet.vc.DrivingLicense;
import com.hedera.hashgraph.zeroknowledge.circuit.CircuitInteractor;
import com.hedera.hashgraph.zeroknowledge.circuit.ZeroKnowledgeSnarkProofProviderBase;
import com.hedera.hashgraph.zeroknowledge.circuit.mapper.CircuitDataMapper;
import com.hedera.hashgraph.zeroknowledge.exception.CircuitPublicInputMapperException;
import com.hedera.hashgraph.zeroknowledge.exception.ZeroKnowledgeProofProviderException;
import com.hedera.hashgraph.zeroknowledge.exception.ZeroKnowledgeVerifyProviderException;

public class ZkSnarkAgeProofProvider extends ZeroKnowledgeSnarkProofProviderBase<ProofAgePublicInput<DrivingLicense>, VerifyAgePublicInput> {
    public ZkSnarkAgeProofProvider(CircuitInteractor circuitInteractor, CircuitDataMapper<ProofAgePublicInput<DrivingLicense>, VerifyAgePublicInput> circuitDataMapper) {
        super(circuitInteractor, circuitDataMapper);
    }

    @Override
    public byte[] createProof(ProofAgePublicInput<DrivingLicense> publicInput) throws ZeroKnowledgeProofProviderException {
        try {
            return circuitInteractor.generateProof(circuitDataMapper.fromPublicInputProofToCircuitInputProof(publicInput));
        } catch (CircuitPublicInputMapperException e) {
            throw new ZeroKnowledgeProofProviderException(
                    String.format("Cannot create proof, error while creating proof with public input %s", publicInput),
                    e
            );
        }
    }

    @Override
    public boolean verifyProof(VerifyAgePublicInput publicInput) throws ZeroKnowledgeVerifyProviderException {
        try {
            return circuitInteractor.verifyProof(circuitDataMapper.fromPublicInputVerifyToCircuitInputVerify(publicInput));
        } catch (CircuitPublicInputMapperException e) {
            throw new ZeroKnowledgeVerifyProviderException(
                    String.format("Cannot verify proof, error while verifying proof with public input %s", publicInput),
                    e
            );
        }
    }
}
