package com.hedera.hashgraph.identity.hcs.example.appnet;

import io.github.cdimascio.dotenv.Dotenv;
import io.horizenlabs.agecircuit.AgeCircuitProof;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
