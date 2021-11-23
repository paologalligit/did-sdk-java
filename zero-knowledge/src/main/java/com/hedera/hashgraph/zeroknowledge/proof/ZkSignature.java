package com.hedera.hashgraph.zeroknowledge.proof;

import com.hedera.hashgraph.identity.hcs.vc.CredentialSubject;
import com.hedera.hashgraph.identity.hcs.vc.HcsVcDocumentBase;
import com.hedera.hashgraph.zeroknowledge.merkletree.factory.MerkleTreeFactory;
import com.hedera.hashgraph.zeroknowledge.utils.ByteUtils;
import com.hedera.hashgraph.zeroknowledge.utils.MerkleTreeUtils;
import io.horizen.common.librustsidechains.*;
import io.horizen.common.merkletreenative.BaseMerkleTree;
import io.horizen.common.merkletreenative.MerkleTreeException;
import io.horizen.common.schnorrnative.*;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.StandardCharsets;

public class ZkSignature<T extends CredentialSubject> implements ZeroKnowledgeSignature<T> {
    private String signature;

    private final MerkleTreeFactory merkleTreeFactory;

    public ZkSignature(MerkleTreeFactory merkleTreeFactory) {
        this(null, merkleTreeFactory);
    }

    public ZkSignature(String signature, MerkleTreeFactory merkleTreeFactory) {
        this.signature = signature;
        this.merkleTreeFactory = merkleTreeFactory;
    }

    @Override
    public String getSignature() {
        return this.signature;
    }

    @Override
    public void sign(byte[] privateKey, HcsVcDocumentBase<T> vcDocument) throws InvocationTargetException, IllegalAccessException, FieldElementConversionException, MerkleTreeException, InitializationException, FinalizationException, DeserializationException, SchnorrSignatureException {
        FieldElement documentId = FieldElement.deserialize(vcDocument.getId().getBytes(StandardCharsets.UTF_8));
        BaseMerkleTree merkleTree = merkleTreeFactory.getMerkleTree(vcDocument.getCredentialSubject());

        merkleTree.finalizeTreeInPlace();
        FieldElement merkleTreeRoot = merkleTree.root();

        FieldElement hash = computeHash(documentId, merkleTreeRoot);

        SchnorrSecretKey secretKey = SchnorrSecretKey.deserialize(privateKey);
        SchnorrPublicKey publicKey = secretKey.getPublicKey();

        SchnorrKeyPair keyPair = new SchnorrKeyPair(
                secretKey,
                publicKey
        );

        SchnorrSignature schnorrSignature = keyPair.signMessage(hash);
        this.signature = ByteUtils.bytesToHex(schnorrSignature.serializeSignature());
    }

    private FieldElement computeHash(FieldElement documentId, FieldElement merkleTreeRoot) throws DeserializationException, FinalizationException {
        return MerkleTreeUtils.computeHash(documentId, merkleTreeRoot);
    }
}
