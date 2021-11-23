package com.hedera.hashgraph.identity.hcs.example.appnet.agecircuit.interactor;

import com.hedera.hashgraph.identity.hcs.example.appnet.agecircuit.mapper.AgeCircuitDataMapper;
import com.hedera.hashgraph.identity.hcs.example.appnet.agecircuit.model.AgeCircuitProofPublicInput;
import com.hedera.hashgraph.identity.hcs.example.appnet.agecircuit.model.AgeCircuitVerifyPublicInput;
import com.hedera.hashgraph.identity.hcs.example.appnet.agecircuit.model.ProofAgePublicInput;
import com.hedera.hashgraph.identity.hcs.example.appnet.agecircuit.model.VerifyAgePublicInput;
import com.hedera.hashgraph.identity.hcs.example.appnet.agecircuit.provider.ZkSnarkAgeProofProvider;
import com.hedera.hashgraph.identity.hcs.example.appnet.dto.BirthDate;
import com.hedera.hashgraph.identity.hcs.example.appnet.mock.TestCredentialSubject;
import com.hedera.hashgraph.identity.hcs.example.appnet.presenter.DrivingLicenseZeroKnowledgeVcPresenter;
import com.hedera.hashgraph.identity.hcs.example.appnet.vc.*;
import com.hedera.hashgraph.identity.hcs.example.appnet.vp.DriverAboveAgePresentation;
import com.hedera.hashgraph.identity.hcs.example.appnet.vp.DriverAboveAgeVerifiableCredential;
import com.hedera.hashgraph.identity.hcs.example.appnet.vp.DrivingLicenseVpGenerator;
import com.hedera.hashgraph.identity.hcs.vc.Issuer;
import com.hedera.hashgraph.sdk.PrivateKey;
import com.hedera.hashgraph.zeroknowledge.circuit.model.CircuitProofPublicInput;
import com.hedera.hashgraph.zeroknowledge.circuit.model.ZeroKnowledgeProofPublicInput;
import com.hedera.hashgraph.zeroknowledge.exception.VerifiablePresentationGenerationException;
import com.hedera.hashgraph.zeroknowledge.merkletree.factory.MerkleTreeFactoryImpl;
import com.hedera.hashgraph.zeroknowledge.proof.PresentationProof;
import com.hedera.hashgraph.zeroknowledge.proof.ZeroKnowledgeSignature;
import com.hedera.hashgraph.zeroknowledge.proof.ZkSignature;
import com.hedera.hashgraph.zeroknowledge.utils.ByteUtils;
import com.hedera.hashgraph.zeroknowledge.utils.MerkleTreeUtils;
import com.hedera.hashgraph.zeroknowledge.vc.CredentialSubjectMerkleTreeLeaf;
import io.horizen.common.librustsidechains.*;
import io.horizen.common.merkletreenative.BaseMerkleTree;
import io.horizen.common.merkletreenative.FieldBasedMerklePath;
import io.horizen.common.merkletreenative.MerkleTreeException;
import io.horizen.common.poseidonnative.PoseidonHash;
import io.horizen.common.schnorrnative.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.threeten.bp.Instant;

import java.lang.reflect.InvocationTargetException;
import java.nio.charset.StandardCharsets;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AgeCircuitTest {
    private final AgeCircuitInteractor ageCircuitInteractor = new AgeCircuitInteractor();
    private final MerkleTreeFactoryImpl merkleTreeFactory = new MerkleTreeFactoryImpl();
    private final AgeCircuitDataMapper circuitDataMapper = new AgeCircuitDataMapper(merkleTreeFactory);
    private final ZkSnarkAgeProofProvider ageProofProvider = new ZkSnarkAgeProofProvider(ageCircuitInteractor, circuitDataMapper);
    private static final String PROVING_KEY_PATH = "/tmp/proving.key";
    private static final String VERIFICATION_KEY_PATH = "/tmp/verification.key";

    @Test
    public void testCreateProof() throws DeserializationException, MerkleTreeException, InitializationException, FinalizationException, SchnorrSignatureException, FieldElementConversionException, InvocationTargetException, IllegalAccessException {
        // Arrange
        TestCredentialSubject credentialSubject = new TestCredentialSubject("Paolo", "Galli", 3, 12, 1991);

        FieldElement dayLabel = FieldElement.deserialize("day".getBytes(StandardCharsets.UTF_8));
        FieldElement monthLabel = FieldElement.deserialize("month".getBytes(StandardCharsets.UTF_8));
        FieldElement yearLabel = FieldElement.deserialize("year".getBytes(StandardCharsets.UTF_8));

        FieldElement dayValue = FieldElement.createFromLong(3);
        FieldElement monthValue = FieldElement.createFromLong(12);
        FieldElement yearValue = FieldElement.createFromLong(1991);

        BaseMerkleTree merkleTree = merkleTreeFactory.getMerkleTree(Collections.singletonList(credentialSubject));


        CredentialSubjectMerkleTreeLeaf dayLeaf = new CredentialSubjectMerkleTreeLeaf("day",3);
        CredentialSubjectMerkleTreeLeaf monthLeaf = new CredentialSubjectMerkleTreeLeaf("month", 12);
        CredentialSubjectMerkleTreeLeaf yearLeaf = new CredentialSubjectMerkleTreeLeaf("year", 1991);

        FieldElement merkleTreeRoot = merkleTree.root();

        FieldBasedMerklePath dayMerklePath = merkleTree.getMerklePath(dayLeaf.toFieldElement());
        FieldBasedMerklePath monthMerklePath = merkleTree.getMerklePath(monthLeaf.toFieldElement());
        FieldBasedMerklePath yearMerklePath = merkleTree.getMerklePath(yearLeaf.toFieldElement());

        Instant timestamp = Instant.now();
        FieldElement challenge = FieldElement.deserialize("fake-challenge".getBytes(StandardCharsets.UTF_8));
        FieldElement currentYear = FieldElement.createFromLong(timestamp.toEpochMilli());
        FieldElement ageThreshold = FieldElement.createFromLong(18 * 31536000000L);
        FieldElement documentId = FieldElement.deserialize("fake-documentId".getBytes(StandardCharsets.UTF_8));

        SchnorrKeyPair holderKeyPair = SchnorrKeyPair.generate();
        SchnorrPublicKey holderPublicKey = holderKeyPair.getPublicKey();

        SchnorrKeyPair authorityKeyPair = SchnorrKeyPair.generate();
        SchnorrPublicKey authorityPublicKey = authorityKeyPair.getPublicKey();


        PoseidonHash hashChallenge = PoseidonHash.getInstanceConstantLength(1);
        hashChallenge.update(challenge);
        FieldElement challengeHashed = hashChallenge.finalizeHash();
        SchnorrSignature signedChallenge = holderKeyPair.signMessage(challengeHashed);

        FieldElement documentRootHashed = MerkleTreeUtils.computeHash(documentId, merkleTreeRoot);

        SchnorrSignature zkSignature = authorityKeyPair.signMessage(documentRootHashed);


        CircuitProofPublicInput circuitProofPublicInput = new AgeCircuitProofPublicInput(
                dayValue, monthValue, yearValue, dayLabel, monthLabel, yearLabel, dayMerklePath, monthMerklePath, yearMerklePath,
                merkleTreeRoot, signedChallenge, zkSignature, currentYear, ageThreshold, holderPublicKey, authorityPublicKey,
                challenge, documentId, PROVING_KEY_PATH
        );

        ageCircuitInteractor.setupCircuit(PROVING_KEY_PATH, VERIFICATION_KEY_PATH);;

        // Act
        byte[] proofResult = null;
        try {
            proofResult = ageCircuitInteractor.generateProof(circuitProofPublicInput);
        } catch (Exception e) {
            fail("An unexpected exception occurred: " + e.getMessage());
        }

        // Assert
        assertNotNull(proofResult);

        // Arrange
        AgeCircuitVerifyPublicInput ageCircuitVerifyPublicInput = new AgeCircuitVerifyPublicInput(
                proofResult, currentYear, ageThreshold, holderPublicKey, authorityPublicKey,
                challenge, documentId, VERIFICATION_KEY_PATH
        );

        // Act
        boolean verifyResult = ageCircuitInteractor.verifyProof(ageCircuitVerifyPublicInput);

        // Assert
        assertTrue(verifyResult);
    }

    @Test
    public void testCreateProof2() throws Exception {
        // Arrange
        SchnorrKeyPair holderKeyPair = SchnorrKeyPair.generate();
        SchnorrPublicKey holderPublicKey = holderKeyPair.getPublicKey();
        SchnorrSecretKey holderSecretKey = holderKeyPair.getSecretKey();
        String holderSecretKeyHex = ByteUtils.bytesToHex(holderSecretKey.serializeSecretKey());

        SchnorrKeyPair authorityKeyPair = SchnorrKeyPair.generate();
        SchnorrSecretKey authoritySecretKey = authorityKeyPair.getSecretKey();
        SchnorrPublicKey authorityPublicKey = authorityKeyPair.getPublicKey();


        DrivingLicenseZeroKnowledgeDocument licenseDocument = new DrivingLicenseZeroKnowledgeDocument();
        licenseDocument.setId("fake-documentId");
        licenseDocument.setIssuer(new Issuer(ByteUtils.bytesToHex(authorityPublicKey.serializePublicKey())));
        Instant timestamp = Instant.now();
        licenseDocument.setIssuanceDate(Instant.ofEpochMilli(1637595909349L));
        DrivingLicense drivingLicense = new DrivingLicense(ByteUtils.bytesToHex(holderPublicKey.serializePublicKey()), "fake-firstName", "fake-lastName",
                new ArrayList<>(), new BirthDate(3, 12, 1991));
        licenseDocument.setCredentialSubject(Collections.singletonList(drivingLicense));

        ZeroKnowledgeSignature<DrivingLicense> zeroKnowledgeSignature = new ZkSignature<>(new MerkleTreeFactoryImpl());
        zeroKnowledgeSignature.sign(authoritySecretKey.serializeSecretKey(), licenseDocument);
        licenseDocument.setZeroKnowledgeSignature(zeroKnowledgeSignature);
        
        ageCircuitInteractor.setupCircuit(PROVING_KEY_PATH, VERIFICATION_KEY_PATH);
        
        Map<String, Object> presentationMetadata = new HashMap<>();
        presentationMetadata.put("ageThreshold", 18);
        presentationMetadata.put("challenge", "fake-challenge");
        presentationMetadata.put("secretKey", holderSecretKeyHex);

        ProofAgePublicInput<DrivingLicense> publicInput = getProofPublicInput(licenseDocument, presentationMetadata);

        // Act
        byte[] proofResult = null;
        try {
            proofResult = ageProofProvider.createProof(publicInput);
        } catch (Exception e) {
            fail("An unexpected exception occurred: " + e.getMessage());
        }

        // Assert
        assertNotNull(proofResult);

        // Arrange
        long currentDateTimestamp = 1637595909349L;
        int ageThreshold = 18;
        String holderPublicKeyString = ByteUtils.bytesToHex(holderPublicKey.serializePublicKey());
        String authorityPublicKeyString = ByteUtils.bytesToHex(authorityPublicKey.serializePublicKey());
        String challenge = "fake-challenge";
        String documentId = "fake-documentId";

        VerifyAgePublicInput verifyAgePublicInput = new VerifyAgePublicInput(
                proofResult, currentDateTimestamp, ageThreshold, holderPublicKeyString, authorityPublicKeyString,
                challenge, documentId, VERIFICATION_KEY_PATH
        );

        // Act
        boolean verifyResult = ageProofProvider.verifyProof(verifyAgePublicInput);

        // Assert
        assertTrue(verifyResult);
    }

    private ProofAgePublicInput getProofPublicInput(DrivingLicenseZeroKnowledgeDocument document, Map<String, Object> presentationMetadata) {
        int ageThreshold = Integer.parseInt(presentationMetadata.get("ageThreshold").toString());
        // TODO: this is not the holder public key, we need to extract it
        String holderPublicKey = document.getCredentialSubject().get(0).getId();
        // TODO: this is not the authority public key, we need to extract it
        String authorityPublicKey = document.getIssuer().getId();
        long vcDocumentDate = document.getIssuanceDate().toEpochMilli();

        return new ProofAgePublicInput<>(
                document.getCredentialSubject(),
                document.getZeroKnowledgeSignature(),
                presentationMetadata.get("challenge").toString(),
                presentationMetadata.get("secretKey").toString(),
                ageThreshold,
                holderPublicKey,
                authorityPublicKey,
                document.getId(),
                vcDocumentDate
        );
    }

    @Test
    public void testCreateProofFullFlow() throws DeserializationException, FieldElementConversionException, MerkleTreeException, InitializationException, InvocationTargetException, IllegalAccessException, FinalizationException, SchnorrSignatureException, VerifiablePresentationGenerationException {
        // Arrange
        // the keys
        SchnorrKeyPair holderKeyPair = SchnorrKeyPair.generate();
        SchnorrPublicKey holderPublicKey = holderKeyPair.getPublicKey();
        SchnorrSecretKey holderSecretKey = holderKeyPair.getSecretKey();
        String holderSecretKeyHex = ByteUtils.bytesToHex(holderSecretKey.serializeSecretKey());

        SchnorrKeyPair authorityKeyPair = SchnorrKeyPair.generate();
        SchnorrPublicKey authorityPublicKey = authorityKeyPair.getPublicKey();
        SchnorrSecretKey authoritySecretKey = authorityKeyPair.getSecretKey();

        DrivingLicenseZeroKnowledgeVcPresenter presenter = new DrivingLicenseZeroKnowledgeVcPresenter();

        // the vc document
        DrivingLicenseZeroKnowledgeDocument licenseDocument = new DrivingLicenseZeroKnowledgeDocument();
        licenseDocument.setId("fake-id");
        licenseDocument.setIssuer(new Issuer(ByteUtils.bytesToHex(authorityPublicKey.serializePublicKey())));
        licenseDocument.setIssuanceDate(Instant.now());
        DrivingLicense drivingLicense = new DrivingLicense(ByteUtils.bytesToHex(holderPublicKey.serializePublicKey()), "fake-firstName", "fake-lastName",
                new ArrayList<>(), new BirthDate(3, 12, 1991));

        licenseDocument.addCredentialSubject(drivingLicense);
        CredentialSchema schema = new CredentialSchema(
                "http://localhost:5050/driving-license-schema.json",
                DrivingLicenseDocument.CREDENTIAL_SCHEMA_TYPE
        );

        licenseDocument.setCredentialSchema(schema);

        Ed25519CredentialProof proof = new Ed25519CredentialProof(ByteUtils.bytesToHex(authorityPublicKey.serializePublicKey()));
        proof.sign(PrivateKey.generate(), presenter.fromDocumentToString(licenseDocument));
        licenseDocument.setProof(proof);

        ZkSignature<DrivingLicense> zkSignature = new ZkSignature<>(
                new MerkleTreeFactoryImpl()
        );
        zkSignature.sign(authoritySecretKey.serializeSecretKey(), licenseDocument);
        licenseDocument.setZeroKnowledgeSignature(zkSignature);

        Map<String, Object> presentationMetadata = new HashMap<>();
        presentationMetadata.put("ageThreshold", 18);
        presentationMetadata.put("challenge", "fake-challenge");
        presentationMetadata.put("secretKey", holderSecretKeyHex);

        DrivingLicenseVpGenerator generator = new DrivingLicenseVpGenerator(ageProofProvider);

        // Act
        DriverAboveAgePresentation presentation = generator.generatePresentation(Collections.singletonList(licenseDocument), presentationMetadata);
        List<DriverAboveAgeVerifiableCredential> verifiableCredentials = presentation.getVerifiableCredential();
        PresentationProof presentationProof = verifiableCredentials.get(0).getProof();
        byte[] proofResult = ByteUtils.hexStringToByteArray(presentationProof.getProof());

        // Arrange
        FieldElement currentDateTimestamp = FieldElement.createFromLong(1637595909349L);
        FieldElement ageThreshold = FieldElement.createFromLong(18);
        FieldElement challenge = FieldElement.deserialize("fake-challenge".getBytes(StandardCharsets.UTF_8));
        FieldElement documentId = FieldElement.deserialize("fake-id".getBytes(StandardCharsets.UTF_8));

        AgeCircuitVerifyPublicInput verifyPublicInput = new AgeCircuitVerifyPublicInput(
                proofResult, currentDateTimestamp, ageThreshold, holderPublicKey, authorityPublicKey,
                challenge, documentId, VERIFICATION_KEY_PATH
        );

        // Assert
        ageCircuitInteractor.verifyProof(verifyPublicInput);
    }
}