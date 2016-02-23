package com.contaazul.turbine.ec2;

import com.contaazul.turbine.Config;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.Test;

/**
 * Test case for {@link ClusterList}.
 * @author Carlos Alexandro Becker
 */
public final class ClusterListTest {
    /**
     * {@link ClusterList} can return an empty list in case clusters is not
     * configured and return an empty string.
     */
    @Test
    public void emptyClustersReturnEmptyList() {
        final List<String> clusters = new ClusterList(
            new ClusterListTest.FakeConfig("")
        ).get();
        Assertions.assertThat(clusters)
            .isEmpty();
    }

    /**
     * {@link ClusterList} can return a valid list of clusters.
     */
    @Test
    public void validClustersReturnItsValues() {
        final List<String> clusters = new ClusterList(
            new ClusterListTest.FakeConfig("cluster1,cluster2,cluster3")
        ).get();
        Assertions.assertThat(clusters)
            .hasSize(3)
            .contains("cluster1", "cluster2", "cluster3");
    }

    /**
     * Fake config impl. for cluster list tests.
     */
    private static class FakeConfig implements Config {
        /**
         * Expected clusters.
         */
        private final transient String clusters;

        /**
         * Ctor.
         * @param clusters Expected clusters.
         */
        private FakeConfig(final String clusters) {
            this.clusters = clusters;
        }

        @Override
        public String clusters() {
            return this.clusters;
        }

        @Override
        public String defaultTag() {
            return "";
        }

        @Override
        public String tag(final String cluster) {
            return "";
        }

        @Override
        public String value(final String cluster) {
            return "";
        }

        @Override
        public String region() {
            return "";
        }
    }
}
