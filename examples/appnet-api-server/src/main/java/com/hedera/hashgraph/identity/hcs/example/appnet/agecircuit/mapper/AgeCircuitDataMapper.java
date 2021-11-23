package com.hedera.hashgraph.identity.hcs.example.appnet.agecircuit.mapper;

import com.hedera.hashgraph.identity.hcs.example.appnet.agecircuit.model.AgeCircuitProofPublicInput;
import com.hedera.hashgraph.identity.hcs.example.appnet.agecircuit.model.AgeCircuitVerifyPublicInput;
import com.hedera.hashgraph.identity.hcs.example.appnet.agecircuit.model.ProofAgePublicInput;
import com.hedera.hashgraph.identity.hcs.example.appnet.agecircuit.model.VerifyAgePublicInput;
import com.hedera.hashgraph.identity.hcs.example.appnet.vc.DrivingLicense;
import com.hedera.hashgraph.zeroknowledge.circuit.mapper.CircuitDataMapper;
import com.hedera.hashgraph.zeroknowledge.circuit.model.CircuitProofPublicInput;
import com.hedera.hashgraph.zeroknowledge.circuit.model.CircuitVerifyPublicInput;
import com.hedera.hashgraph.zeroknowledge.circuit.model.ZeroKnowledgeVerifyPublicInput;
import com.hedera.hashgraph.zeroknowledge.exception.CircuitPublicInputMapperException;
import com.hedera.hashgraph.zeroknowledge.merkletree.factory.MerkleTreeFactory;
import com.hedera.hashgraph.zeroknowledge.proof.ZeroKnowledgeSignature;
import com.hedera.hashgraph.zeroknowledge.utils.ByteUtils;
import com.hedera.hashgraph.zeroknowledge.vc.CredentialSubjectMerkleTreeLeaf;
import io.github.cdimascio.dotenv.Dotenv;
import io.horizen.common.librustsidechains.DeserializationException;
import io.horizen.common.librustsidechains.FieldElement;
import io.horizen.common.merkletreenative.BaseMerkleTree;
import io.horizen.common.merkletreenative.FieldBasedMerklePath;
import io.horizen.common.poseidonnative.PoseidonHash;
import io.horizen.common.schnorrnative.SchnorrKeyPair;
import io.horizen.common.schnorrnative.SchnorrPublicKey;
import io.horizen.common.schnorrnative.SchnorrSecretKey;
import io.horizen.common.schnorrnative.SchnorrSignature;

import java.nio.charset.StandardCharsets;
import java.util.List;

public class AgeCircuitDataMapper implements CircuitDataMapper<ProofAgePublicInput<DrivingLicense>, VerifyAgePublicInput> {
    private final MerkleTreeFactory merkleTreeFactory;

    public AgeCircuitDataMapper(MerkleTreeFactory merkleTreeFactory) {
        this.merkleTreeFactory = merkleTreeFactory;
    }

    @Override
    public CircuitProofPublicInput fromPublicInputProofToCircuitInputProof(ProofAgePublicInput<DrivingLicense> proofAgePublicInput) throws CircuitPublicInputMapperException {
        Dotenv dotenv = Dotenv.configure().load();

        List<DrivingLicense> credentialSubjects = proofAgePublicInput.getCredentialSubject();
        DrivingLicense credentialSubject = credentialSubjects.get(0);
        ZeroKnowledgeSignature<DrivingLicense> zeroKnowledgeSignature = proofAgePublicInput.getZeroKnowledgeSignature();

        try {
            CredentialSubjectMerkleTreeLeaf dayLeaf = new CredentialSubjectMerkleTreeLeaf("day", credentialSubject.getBirthDateDay());
            CredentialSubjectMerkleTreeLeaf monthLeaf = new CredentialSubjectMerkleTreeLeaf("month", credentialSubject.getBirthDateMonth());
            CredentialSubjectMerkleTreeLeaf yearLeaf = new CredentialSubjectMerkleTreeLeaf("year", credentialSubject.getBirthDateYear());

            FieldElement dayLabel = FieldElement.deserialize("day".getBytes(StandardCharsets.UTF_8));
            FieldElement monthLabel = FieldElement.deserialize("month".getBytes(StandardCharsets.UTF_8));
            FieldElement yearLabel = FieldElement.deserialize("year".getBytes(StandardCharsets.UTF_8));

            FieldElement dayValue = FieldElement.createFromLong(credentialSubject.getBirthDateDay());
            FieldElement monthValue = FieldElement.createFromLong(credentialSubject.getBirthDateMonth());
            FieldElement yearValue = FieldElement.createFromLong(credentialSubject.getBirthDateYear());

            BaseMerkleTree merkleTree = merkleTreeFactory.getMerkleTree(credentialSubjects);

            FieldBasedMerklePath dayMerklePath = merkleTree.getMerklePath(dayLeaf.toFieldElement());
            FieldBasedMerklePath monthMerklePath = merkleTree.getMerklePath(monthLeaf.toFieldElement());
            FieldBasedMerklePath yearMerklePath = merkleTree.getMerklePath(yearLeaf.toFieldElement());

            merkleTree.finalizeTreeInPlace();
            FieldElement merkleTreeRoot = merkleTree.root();

            byte[] zkSignatureBytes = ByteUtils.hexStringToByteArray(zeroKnowledgeSignature.getSignature());
            SchnorrSignature zkSignature = SchnorrSignature.deserialize(zkSignatureBytes);

            FieldElement challenge = FieldElement.deserialize(proofAgePublicInput.getChallenge().getBytes(StandardCharsets.UTF_8));
            PoseidonHash hashChallenge = PoseidonHash.getInstanceConstantLength(1);
            hashChallenge.update(challenge);
            FieldElement challengeHashed = hashChallenge.finalizeHash();

            SchnorrSecretKey secretKey = SchnorrSecretKey.deserialize(ByteUtils.hexStringToByteArray(proofAgePublicInput.getSecretKey()));
            SchnorrKeyPair keyPair = new SchnorrKeyPair(
                    secretKey,
                    secretKey.getPublicKey()
            );
            SchnorrSignature signedChallenge = keyPair.signMessage(challengeHashed);

            FieldElement currentYear = FieldElement.createFromLong(proofAgePublicInput.getVcDocumentDate());
            FieldElement ageThreshold = FieldElement.createFromLong(getAgeThresholdInMillis(proofAgePublicInput.getAgeThreshold()));

            String holderPublicKeyValue = proofAgePublicInput.getHolderPublicKey();
            String authorityPublicKeyValue = proofAgePublicInput.getAuthorityPublicKey();

            SchnorrPublicKey holderPublicKey = SchnorrPublicKey.deserialize(ByteUtils.hexStringToByteArray(holderPublicKeyValue));
            SchnorrPublicKey authorityPublicKey = SchnorrPublicKey.deserialize(ByteUtils.hexStringToByteArray(authorityPublicKeyValue));

            FieldElement documentId = FieldElement.deserialize(proofAgePublicInput.getDocumentId().getBytes(StandardCharsets.UTF_8));

            String provingKeyPath = dotenv.get("PROVING_KEY_PATH");

            return new AgeCircuitProofPublicInput(
                    dayValue, monthValue, yearValue, dayLabel, monthLabel, yearLabel,
                    dayMerklePath, monthMerklePath, yearMerklePath, merkleTreeRoot,
                    signedChallenge, zkSignature, currentYear, ageThreshold,
                    holderPublicKey, authorityPublicKey, challenge, documentId, provingKeyPath
            );
        } catch (Exception e) {
            throw new CircuitPublicInputMapperException(
                    String.format("Cannot map public input to proof circuit input. Public input: %s", proofAgePublicInput),
                    e
            );
        }
    }

    private long getAgeThresholdInMillis(int ageThreshold) {
        long millisInSecond = 1000;
        int secondsInMinute = 60;
        int minutesInHour = 60;
        int hoursInDay = 24;
        int daysInYear = 365;

        long millisInYear = millisInSecond * secondsInMinute * minutesInHour * hoursInDay * daysInYear;

        return ageThreshold * millisInYear;
    }

    @Override
    public CircuitVerifyPublicInput fromPublicInputVerifyToCircuitInputVerify(VerifyAgePublicInput publicInput) throws CircuitPublicInputMapperException {
        try {
            FieldElement currentDateTimestamp = FieldElement.createFromLong(publicInput.getCurrentDateTimestamp());
            FieldElement ageThreshold = FieldElement.createFromLong(getAgeThresholdInMillis(publicInput.getAgeThreshold()));
            FieldElement challenge = FieldElement.deserialize(publicInput.getChallenge().getBytes(StandardCharsets.UTF_8));
            FieldElement documentId = FieldElement.deserialize(publicInput.getDocumentId().getBytes(StandardCharsets.UTF_8));
            SchnorrPublicKey holderPublicKey = SchnorrPublicKey.deserialize(ByteUtils.hexStringToByteArray(publicInput.getHolderPublicKey()));
            SchnorrPublicKey authorityPublicKey = SchnorrPublicKey.deserialize(ByteUtils.hexStringToByteArray(publicInput.getAuthorityPublicKey()));

            return new AgeCircuitVerifyPublicInput(
                    publicInput.getProof(), currentDateTimestamp, ageThreshold,
                    holderPublicKey, authorityPublicKey, challenge, documentId,
                    publicInput.getVerificationKeyPath()
            );
        } catch (DeserializationException e) {
            throw new CircuitPublicInputMapperException(
                    String.format("Cannot map public input to verify circuit input. Public input: %s", publicInput),
                    e
            );
        }
    }
}
