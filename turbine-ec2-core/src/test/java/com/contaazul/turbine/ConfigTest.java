package com.contaazul.turbine;

import org.assertj.core.api.Assertions;
import org.junit.Test;

/**
 * Test case for Config.
 * @author Carlos Alexandro Becker
 */
public final class ConfigTest {
    /**
     * Config instance.
     */
    private final Config config = new Config.FromProperties();

    /**
     * Config can never return a null value.
     */
    @Test
    public void returnsEmpty() {
        Assertions.assertThat(this.config.clusters()).isNotNull();
        Assertions.assertThat(this.config.defaultTag()).isNotNull();
        Assertions.assertThat(this.config.tag("a-n-y")).isNotNull();
        Assertions.assertThat(this.config.value("a-n-y")).isNotNull();
    }
}
