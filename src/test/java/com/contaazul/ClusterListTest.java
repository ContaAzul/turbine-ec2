package com.contaazul;

import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.Test;

/**
 * Test case for {@link ClusterList}.
 * @author Carlos Alexandro Becker
 */
public final class ClusterListTest {
    /**
     * {@link ClusterList} can return "default" in case clusters is not
     * configured and return null.
     */
    @Test
    public void nullClustersReturnDefault() {
        final List<String> clusters = new ClusterList(
            new Config() {
                @Override
                public String clusters() {
                    return null;
                }
            }
        ).get();
        Assertions.assertThat(clusters)
            .hasSize(1)
            .contains("default");
    }

    /**
     * {@link ClusterList} can return "default" in case clusters is not
     * configured and return an empty string.
     */
    @Test
    public void emptyClustersReturnDefault() {
        final List<String> clusters = new ClusterList(
            new Config() {
                @Override
                public String clusters() {
                    return "";
                }
            }
        ).get();
        Assertions.assertThat(clusters)
            .hasSize(1)
            .contains("default");
    }

    /**
     * {@link ClusterList} can return a valid list of clusters.
     */
    @Test
    public void validClustersReturnItsValues() {
        final List<String> clusters = new ClusterList(
            new Config() {
                @Override
                public String clusters() {
                    return "cluster1,cluster2,cluster3";
                }
            }
        ).get();
        Assertions.assertThat(clusters)
            .hasSize(3)
            .contains("cluster1", "cluster2", "cluster3");
    }
}
