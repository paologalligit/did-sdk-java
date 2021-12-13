package com.hedera.hashgraph.identity.hcs.example.appnet;

import io.github.cdimascio.dotenv.Dotenv;
import io.horizenlabs.agecircuit.AgeCircuitProof;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This entrypoint is used to generate the proving and verification keys; the former used by the circuit to generate
 * a zero knowledge proof, the latter to verify it.
 * This operation is executed only once, since both the prover and verifier are going to use the same keys to generate/verify
 * the proof with the same circuit.
 * In a real environment, both the keys will be created once per circuit and hosted in a public repo.
 * Whoever wants to generate or verify a proof will download them locally.
 */
public class ZeroKnowledgeKeyGenerator {
    private static final Logger log = LoggerFactory.getLogger(ZeroKnowledgeKeyGenerator.class);

    public static void main(String[] args) {
        AgeCircuitProof.generateDLogKeys(1 << 17, 1 <<15);
        Dotenv dotenv = Dotenv.configure().load();

        String provingKeyPath = dotenv.get("PROVING_KEY_PATH");
        String verificationKeyPath = dotenv.get("VERIFICATION_KEY_PATH");

        log.info("Initializing proving and verification key path...");
        new AgeCircuitProof().setup(provingKeyPath, verificationKeyPath);
        log.info("Done creating proving and verification keys!");
    }
}
