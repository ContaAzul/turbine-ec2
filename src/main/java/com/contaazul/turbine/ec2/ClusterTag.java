/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016 Carlos Alexandro Becker
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.contaazul.turbine.ec2;

import com.contaazul.turbine.Config;
import com.google.common.base.Strings;

/**
 * Gets the appropriate tag for a given cluster.
 * @author Carlos Alexandro Becker (caarlos0@gmail.com)
 */
public final class ClusterTag {

    /**
     * Cluster name.
     */
    private final transient String cluster;

    /**
     * Config.
     */
    private final transient Config config;

    /**
     * Ctor.
     * @param cluster Cluster name.
     * @param config Config.
     */
    public ClusterTag(final String cluster, final Config config) {
        this.cluster = cluster;
        this.config = config;
    }

    /**
     * Gets the adequate tag from config.
     * @return Tag.
     */
    public String get() {
        String tag = this.config.tag(this.cluster);
        if (Strings.isNullOrEmpty(tag)) {
            tag = this.config.defaultTag();
        }
        return String.format("tag:%s", tag);
    }
}
