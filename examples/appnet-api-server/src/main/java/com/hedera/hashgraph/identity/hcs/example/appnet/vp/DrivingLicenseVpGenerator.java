package com.hedera.hashgraph.identity.hcs.example.appnet.vp;

import com.hedera.hashgraph.identity.hcs.example.appnet.vc.DrivingLicense;
import com.hedera.hashgraph.identity.hcs.example.appnet.vc.DrivingLicenseZeroKnowledgeDocument;
import com.hedera.hashgraph.zeroknowledge.circuit.ZeroKnowledgeProofProvider;
import com.hedera.hashgraph.zeroknowledge.circuit.model.ProofPublicInput;
import com.hedera.hashgraph.zeroknowledge.proof.ZkSnarkProof;
import com.hedera.hashgraph.zeroknowledge.vp.VpZkSnarkBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

public class DrivingLicenseVpGenerator extends VpZkSnarkBuilder<DrivingLicenseZeroKnowledgeDocument, DriverAboveAgePresentation> {

    public static final int AGE_THRESHOLD = 18;

    public DrivingLicenseVpGenerator(ZeroKnowledgeProofProvider zeroKnowledgeProofProvider) {
        super(zeroKnowledgeProofProvider);
    }

    @Override
    public DriverAboveAgePresentation generatePresentation(List<DrivingLicenseZeroKnowledgeDocument> vcDocuments, Map<String, Object> presentationMetadata) {
        DrivingLicenseZeroKnowledgeDocument licenseDocument = vcDocuments.get(0);

        DriverAboveAgePresentation driverAboveAgePresentation = new DriverAboveAgePresentation();

        // Set the credential subjects
        setCredentialSubjects(licenseDocument, driverAboveAgePresentation);

        // Set the verifiable credentials
        DriverAboveAgeVerifiableCredential verifiableCredential = getVerifiableCredential(licenseDocument);

        // Set the proof
        setProof(licenseDocument, driverAboveAgePresentation, verifiableCredential, presentationMetadata);

        return driverAboveAgePresentation;
    }

    private void setCredentialSubjects(DrivingLicenseZeroKnowledgeDocument licenseDocument, DriverAboveAgePresentation driverAboveAgePresentation) {
        List<DrivingLicense> credentialSubjects = licenseDocument.getCredentialSubject();
        DrivingLicense credentialSubject = credentialSubjects.get(0);
        driverAboveAgePresentation.setHolder(credentialSubject.getId());
    }

    @NotNull
    private DriverAboveAgeVerifiableCredential getVerifiableCredential(DrivingLicenseZeroKnowledgeDocument licenseDocument) {
        DriverAboveAgeVerifiableCredential verifiableCredential = new DriverAboveAgeVerifiableCredential();
        verifiableCredential.setContext(licenseDocument.getContext());
        verifiableCredential.setId(licenseDocument.getId());
        verifiableCredential.setType(licenseDocument.getType());
        verifiableCredential.setCredentialSchema(licenseDocument.getCredentialSchema());
        verifiableCredential.setIssuer(licenseDocument.getIssuer());
        verifiableCredential.setIssuanceDate(licenseDocument.getIssuanceDate());
        verifiableCredential.addCredentialSubjectClaim("ageOver", "18");
        return verifiableCredential;
    }

    private void setProof(
            DrivingLicenseZeroKnowledgeDocument licenseDocument,
            DriverAboveAgePresentation driverAboveAgePresentation,
            DriverAboveAgeVerifiableCredential verifiableCredential,
            Map<String, Object> presentationMetadata
    ) {
        ZkSnarkProof proof = new ZkSnarkProof();
        String signature = licenseDocument.getZeroKnowledgeSignature().getSignature();
        proof.setZkSignature(signature);
        ProofPublicInput publicInput = getPublicInputFromVcDocument(licenseDocument, presentationMetadata);
        byte[] snarkProof = generateSnarkProof(publicInput);
        proof.setSnarkProof(new String(snarkProof));
        verifiableCredential.setProof(proof);
        driverAboveAgePresentation.addVerifiableCredential(verifiableCredential);
    }

    private ProofPublicInput getPublicInputFromVcDocument(DrivingLicenseZeroKnowledgeDocument vcDocument, Map<String, Object> presentationMetadata) {
        DrivingLicense drivingLicense = vcDocument.getCredentialSubject().get(0);
        String holderPublicKey = drivingLicense.getId();
        String issuerPublicKey = vcDocument.getIssuer().getId();
        String documentId = vcDocument.getId();
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        String challenge = presentationMetadata.get("challenge").toString();

        return new ProofPublicInput(
                holderPublicKey,
                issuerPublicKey,
                documentId,
                AGE_THRESHOLD,
                currentYear,
                challenge
        );
    }
}
