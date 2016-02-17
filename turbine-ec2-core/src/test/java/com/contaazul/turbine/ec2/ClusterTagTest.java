package com.contaazul.turbine.ec2;

import com.contaazul.turbine.Config;
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
        Assertions.assertThat(
            new ClusterTag(
                "blah", new ClusterTagTest.FakeConfig("default-tag", null)
            ).get()
        ).isEqualTo("tag:default-tag");
    }

    /**
     * {@link ClusterTag} can get the default tag name if none is provided.
     */
    @Test
    public void usesGivenTag() {
        Assertions.assertThat(
            new ClusterTag(
                "blah", new ClusterTagTest.FakeConfig("default-tag", "blah-tag")
            ).get()
        ).isEqualTo("tag:blah-tag");
    }

    /**
     * {@link ClusterTag} can throw an exception in when no tags are configured.
     */
    @Test
    public void throwsExceptionWhenNoTags() {
        Assertions.assertThatThrownBy(
            () -> new ClusterTag(
                "blah", new ClusterTagTest.FakeConfig(null, null)
            ).get()
        ).isInstanceOf(RuntimeException.class)
            .hasMessageContaining("No tags specified for 'blah'");
    }

    /**
     * Fake config impl. for cluster tag tests.
     */
    private static class FakeConfig implements Config {
        /**
         * Expected default tag.
         */
        private final transient String defaultTag;

        /**
         * Expected tag.
         */
        private final transient String tag;

        /**
         * Ctor.
         * @param defaultTag Expected default tag.
         * @param tag Expected tag.
         */
        private FakeConfig(final String defaultTag, final String tag) {
            this.defaultTag = defaultTag;
            this.tag = tag;
        }

        @Override
        public String clusters() {
            return null;
        }

        @Override
        public String defaultTag() {
            return this.defaultTag;
        }

        @Override
        public String tag(final String cluster) {
            return this.tag;
        }
    }
}
