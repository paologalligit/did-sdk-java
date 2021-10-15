package com.hedera.hashgraph.identity.hcs.example.appnet.vp;

import com.hedera.hashgraph.identity.hcs.example.appnet.gingerlib.Gingerlib;
import com.hedera.hashgraph.identity.hcs.example.appnet.vc.DrivingLicense;
import com.hedera.hashgraph.identity.hcs.example.appnet.vc.DrivingLicenseDocument;
import com.hedera.hashgraph.identity.hcs.example.appnet.vc.Ed25519CredentialProof;
import com.hedera.hashgraph.identity.hcs.vp.VpBuilder;

import java.util.List;

public class DrivingLicenseVpGenerator implements VpBuilder<DrivingLicenseDocument, DriverAboveAgePresentation> {
    @Override
    public DriverAboveAgePresentation generatePresentation(List<DrivingLicenseDocument> vcDocuments) {
        DrivingLicenseDocument licenseDocument = vcDocuments.get(0);

        DriverAboveAgePresentation driverAboveAgePresentation = new DriverAboveAgePresentation();

        // Set the credential subjects
        List<DrivingLicense> credentialSubjects = licenseDocument.getCredentialSubject();
        DrivingLicense credentialSubject = credentialSubjects.get(0);
        driverAboveAgePresentation.setHolder(credentialSubject.getId());

        // Set the verifiable credentials
        DriverAboveAgeVerifiableCredential verifiableCredential = new DriverAboveAgeVerifiableCredential();
        verifiableCredential.setContext(licenseDocument.getContext());
        verifiableCredential.setId(licenseDocument.getId());
        verifiableCredential.setType(licenseDocument.getType());
        verifiableCredential.setCredentialSchema(licenseDocument.getCredentialSchema());
        verifiableCredential.setIssuer(licenseDocument.getIssuer());
        verifiableCredential.setIssuanceDate(licenseDocument.getIssuanceDate());
        verifiableCredential.addCredentialSubjectClaim("ageOver", "18");

        // Set the proof
        ZkSnarkProof proof = new ZkSnarkProof();
        Ed25519CredentialProof documentProof = licenseDocument.getProof();
        proof.setCredentialSubjectMerkleTreeRoot(documentProof.getCredentialSubjectMerkleTreeRoot());
        proof.setCredentialSubjectMerkleTreeRootSignature(documentProof.getCredentialSubjectMerkleTreeRootSignature());
        proof.setSnarkProof(Gingerlib.computeSnarkProof());
        verifiableCredential.setProof(proof);
        driverAboveAgePresentation.addVerifiableCredential(verifiableCredential);

        return driverAboveAgePresentation;
    }
}
