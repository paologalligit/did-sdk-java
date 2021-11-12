package com.hedera.hashgraph.zeroknowledge.merkletree.factory;

import com.hedera.hashgraph.identity.hcs.vc.CredentialSubject;
import io.horizen.common.librustsidechains.FieldElementConversionException;
import io.horizen.common.librustsidechains.FinalizationException;
import io.horizen.common.librustsidechains.InitializationException;
import io.horizen.common.merkletreenative.BaseMerkleTree;
import io.horizen.common.merkletreenative.MerkleTreeException;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public interface MerkleTreeFactory {
    <T extends CredentialSubject> BaseMerkleTree getMerkleTreeRoot(List<T> credentialSubject) throws FieldElementConversionException, MerkleTreeException, InitializationException, InvocationTargetException, IllegalAccessException, FinalizationException;
}
