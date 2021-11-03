package com.hedera.hashgraph.zeroknowledge.vc;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface MerkleTreeLeaf {
    String keyName();
}
