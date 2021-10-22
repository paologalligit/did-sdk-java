package com.hedera.hashgraph.identity.hcs.example.appnet.vp;

import com.hedera.hashgraph.identity.hcs.example.appnet.vc.DrivingLicense;
import com.hedera.hashgraph.identity.hcs.example.appnet.vc.DrivingLicenseDocument;
import com.hedera.hashgraph.identity.hcs.example.appnet.vc.Ed25519CredentialProof;
import com.hedera.hashgraph.identity.hcs.vp.VpBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class DrivingLicenseVpGenerator implements VpBuilder<DrivingLicenseDocument, DriverAboveAgePresentation> {
    @Override
    public DriverAboveAgePresentation generatePresentation(List<DrivingLicenseDocument> vcDocuments) {
        DrivingLicenseDocument licenseDocument = vcDocuments.get(0);

        DriverAboveAgePresentation driverAboveAgePresentation = new DriverAboveAgePresentation();

        // Set the credential subjects
        setCredentialSubjects(licenseDocument, driverAboveAgePresentation);

        // Set the verifiable credentials
        DriverAboveAgeVerifiableCredential verifiableCredential = getVerifiableCredential(licenseDocument);

        // Set the proof
        setProof(licenseDocument, driverAboveAgePresentation, verifiableCredential);

        return driverAboveAgePresentation;
    }

    private void setCredentialSubjects(DrivingLicenseDocument licenseDocument, DriverAboveAgePresentation driverAboveAgePresentation) {
        List<DrivingLicense> credentialSubjects = licenseDocument.getCredentialSubject();
        DrivingLicense credentialSubject = credentialSubjects.get(0);
        driverAboveAgePresentation.setHolder(credentialSubject.getId());
    }

    @NotNull
    private DriverAboveAgeVerifiableCredential getVerifiableCredential(DrivingLicenseDocument licenseDocument) {
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

    private void setProof(DrivingLicenseDocument licenseDocument, DriverAboveAgePresentation driverAboveAgePresentation, DriverAboveAgeVerifiableCredential verifiableCredential) {
        ZkSnarkProof proof = new ZkSnarkProof();
        Ed25519CredentialProof documentProof = licenseDocument.getProof();
        String signature = documentProof.getSignature();
        proof.setSignature(signature);
        proof.setSnarkProof("zkSnarkProof");
        verifiableCredential.setProof(proof);
        driverAboveAgePresentation.addVerifiableCredential(verifiableCredential);
    }
}
