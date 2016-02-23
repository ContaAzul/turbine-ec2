package com.contaazul.turbine.ec2;

import com.contaazul.turbine.Config;
import org.assertj.core.api.Assertions;
import org.junit.Test;

/**
 * Test case for ClusterTag.
 * @author Carlos Alexandro Becker
 */
public final class ClusterTagTest {
    /**
     * ClusterTag can get the default tag name if none is provided.
     */
    @Test
    public void usesDefaultTagIfNoSpecificTagProvided() {
        Assertions.assertThat(
            new ClusterTag(
                "blah",
                new ClusterTagTest.FakeConfig("default-tag", "", "")
            ).name()
        ).isEqualTo("tag:default-tag");
    }

    /**
     * ClusterTag can get the default tag name if none is provided.
     */
    @Test
    public void usesGivenTag() {
        Assertions.assertThat(
            new ClusterTag(
                "blah",
                new ClusterTagTest.FakeConfig("default-tag", "blah-tag", "")
            ).name()
        ).isEqualTo("tag:blah-tag");
    }

    /**
     * ClusterTag can throw an exception in when no tags are configured.
     */
    @Test
    public void throwsExceptionWhenNoTags() {
        Assertions.assertThatThrownBy(
            () -> new ClusterTag(
                "blah", new ClusterTagTest.FakeConfig("", "", "")
            ).name()
        ).isInstanceOf(RuntimeException.class)
            .hasMessageContaining("No tags specified for 'blah'");
    }

    /**
     * ClusterTag can use the cluster name as value when no specific value
     * is given.
     */
    @Test
    public void usesClusterNameWhenNoValue() {
        Assertions.assertThat(
            new ClusterTag(
                "blah",
                new ClusterTagTest.FakeConfig("", "", "")
            ).value()
        ).isEqualTo("blah");
    }

    /**
     * ClusterTag can use the specified value as filter value.
     */
    @Test
    public void usesGivenValue() {
        Assertions.assertThat(
            new ClusterTag(
                "blah",
                new ClusterTagTest.FakeConfig("", "", "blah-sandbox")
            ).value()
        ).isEqualTo("blah-sandbox");
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
         * Expected value.
         */
        private final transient String value;

        /**
         * Ctor.
         * @param defaultTag Expected default tag.
         * @param tag Expected tag.
         */
        private FakeConfig(
            final String defaultTag,
            final String tag,
            final String value
        ) {
            this.defaultTag = defaultTag;
            this.tag = tag;
            this.value = value;
        }

        @Override
        public String clusters() {
            return "";
        }

        @Override
        public String defaultTag() {
            return this.defaultTag;
        }

        @Override
        public String tag(final String cluster) {
            return this.tag;
        }

        @Override
        public String value(final String cluster) {
            return this.value;
        }

        @Override
        public String region(final String cluster) {
            return "";
        }

        @Override
        public String region() {
            return "";
        }
    }
}
