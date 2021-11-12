package com.hedera.hashgraph.zeroknowledge.merkletree.factory;

import com.hedera.hashgraph.zeroknowledge.mock.credentialsubject.BadCredentialSubject;
import com.hedera.hashgraph.zeroknowledge.mock.credentialsubject.TestCredentialSubject;
import com.hedera.hashgraph.zeroknowledge.vc.CredentialSubjectMerkleTreeLeaf;
import io.horizen.common.librustsidechains.FieldElement;
import io.horizen.common.librustsidechains.FieldElementConversionException;
import io.horizen.common.librustsidechains.FinalizationException;
import io.horizen.common.librustsidechains.InitializationException;
import io.horizen.common.merkletreenative.BaseMerkleTree;
import io.horizen.common.merkletreenative.MerkleTreeException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.InvocationTargetException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;

import static com.hedera.hashgraph.zeroknowledge.utils.MerkleTreeUtils.serializeFieldObjectToByteArray;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class MerkleTreeFactoryImplTest {
    @InjectMocks
    private MerkleTreeFactoryImpl merkleTreeFactory;

    private final String name = "fake-name";
    private final String surname = "fake-lastName";
    private final int age = 18;

    @Test
    public void merkleTreeRootIsCorrectlyBuilt() {
        // Arrange
        TestCredentialSubject credentialSubject = new TestCredentialSubject(name, surname, age);

        // Act
        try {
            merkleTreeFactory.getMerkleTreeRoot(Collections.singletonList(credentialSubject));
        } catch (Exception e) {
            fail("An unexpected exception was thrown");
        }

        // Assert

    }

    @Test
    public void merkleTreeIsBuilt_AllLeavesAreInTheTree() throws FieldElementConversionException, MerkleTreeException, InitializationException, InvocationTargetException, IllegalAccessException, FinalizationException {
        // Arrange
        TestCredentialSubject credentialSubject = new TestCredentialSubject(name, surname, age);

        CredentialSubjectMerkleTreeLeaf nameLeaf = new CredentialSubjectMerkleTreeLeaf(
                "name".getBytes(StandardCharsets.UTF_8), serializeFieldObjectToByteArray(name)
        );
        CredentialSubjectMerkleTreeLeaf surnameLeaf = new CredentialSubjectMerkleTreeLeaf(
                "surname".getBytes(StandardCharsets.UTF_8), serializeFieldObjectToByteArray(surname)
        );
        CredentialSubjectMerkleTreeLeaf ageLeaf = new CredentialSubjectMerkleTreeLeaf(
                "age".getBytes(StandardCharsets.UTF_8), serializeFieldObjectToByteArray(age)
        );

        FieldElement nameFieldElement = nameLeaf.toFieldElement();
        FieldElement surnameFieldElement = surnameLeaf.toFieldElement();
        FieldElement ageFieldElement = ageLeaf.toFieldElement();


        // Act
        BaseMerkleTree merkleTree = merkleTreeFactory.getMerkleTreeRoot(Collections.singletonList(credentialSubject));


        // Assert
        assertNotNull(merkleTree);
        assertTrue(merkleTree.isLeafInTree(nameFieldElement));
        assertTrue(merkleTree.isLeafInTree(surnameFieldElement));
        assertTrue(merkleTree.isLeafInTree(ageFieldElement));
    }

    @Test
    public void ifANotSupportedTypeIsPassedAsCredentialLeafValue_AMerkleTreeExceptionIsThrown() {
        // Arrange
        double badProp = 12.3;
        BadCredentialSubject credentialSubject = new BadCredentialSubject(badProp);


        // Act
        Exception exception = assertThrows(MerkleTreeException.class, () -> {
            merkleTreeFactory.getMerkleTreeRoot(Collections.singletonList(credentialSubject));
        });

        // Assert
        assertTrue(exception.getMessage().contains("Cannot serialize credential subject field"));
    }
}