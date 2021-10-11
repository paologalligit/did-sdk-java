package com.hedera.hashgraph.identity.hcs.example.appnet.gingerlib;

public class Gingerlib {
    public static String buildMerkleTreeRootFromCredentialSubject(String credentialSubject) {
        return "b09a57d476ea01c7f91756adff1d560e579057ac99a28d3f30e259b30ecc9dc7";
    }

    public static String signMerkleTreeRoot(String privateKey, String merkleTreeRoot) {
        return "1756adff1d560e5b099057ac99a28d3f30e259b30eca55b099057ac99a27d476ea01c7f97c9dc7ca57d476e";
    }

    public static String computeSnarkProof() {
        return "3f301c7f97d4730eca55b09476ea07ca9057ac99a2757ac99a2d6e7c9dc1756adff1d560e5b09908de259b5";
    }
}
