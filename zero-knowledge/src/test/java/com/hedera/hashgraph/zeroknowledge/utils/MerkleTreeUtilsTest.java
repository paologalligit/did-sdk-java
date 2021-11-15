package com.hedera.hashgraph.zeroknowledge.utils;

import io.horizen.common.librustsidechains.FieldElement;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.hedera.hashgraph.zeroknowledge.utils.MerkleTreeUtils.computeHash;
import static org.junit.jupiter.api.Assertions.fail;

@ExtendWith(MockitoExtension.class)
class MerkleTreeUtilsTest {
    @Test
    public void testCorrectComputeHash() {
        // Arrange
        String documentId = "https://example.appnet.com/driving-license/e0b5110d-bee8-4c81-9c00-835402073ed0";
        long fakeSeed = 123;
        FieldElement merkleTreeRoot = FieldElement.createRandom(fakeSeed);

        // Act
        try {
            computeHash(documentId, merkleTreeRoot);
        } catch (Exception e) {
            fail("An unexpected exception was thrown");
        }

        // Assert
    }
}