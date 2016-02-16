package com.contaazul;

import org.assertj.core.api.Assertions;
import org.junit.Test;

/**
 * Test case for {@link ClusterTag}.
 * @author Carlos Alexandro Becker
 */
public final class ClusterTagTest {
    /**
     * {@link ClusterTag} can get the default tag name if none is provided.
     */
    @Test
    public void usesDefaultTagIfNoSpecificTagProvided() {
        Assertions.assertThat(new ClusterTag("blah").get())
            .isEqualTo("tag:default-tag");
    }
    /**
     * {@link ClusterTag} can get the default tag name if none is provided.
     */
    @Test
    public void usesGivenTag() {
        Assertions.assertThat(new ClusterTag("cluster1").get())
            .isEqualTo("tag:cluster1-tag");
    }
}
