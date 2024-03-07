/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.shixiaogang.casper.common.utils;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;

import static com.shixiaogang.casper.common.utils.TimeUtils.parseDuration;
import static com.shixiaogang.casper.common.utils.TimeUtils.parseLocalDateTime;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit tests for {@link TimeUtils}.
 */
public class TimeUtilsTest {

    @Test
    public void testParseDuration() {
        assertEquals(Duration.ofDays(34), parseDuration("34d"));
        assertEquals(Duration.ofMillis(13), parseDuration("13 ms"));
        assertEquals(Duration.ofNanos(72), parseDuration(" 72 ns "));
    }

    @Test
    public void testParseLocalDateTime() {
        assertEquals(
            LocalDateTime.of(2024, 3, 24, 10, 26, 0),
            parseLocalDateTime("2024-03-24 10:26:00"));
    }
}
