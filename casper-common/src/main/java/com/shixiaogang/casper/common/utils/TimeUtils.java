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

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoUnit;
import java.util.Locale;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE;
import static java.time.format.DateTimeFormatter.ISO_LOCAL_TIME;

/**
 * Utilities for time intervals and instants.
 */
public class TimeUtils {

    /**
     * Parse the given text to duration.
     *
     * <p>The text is in format "{number} {unit}", e.g. "123ms", "321 s". If no
     * time unit is specified, it will be considered as seconds.
     *
     * <p>Supported time units include
     * <ul>
     *   <li>DAYS： "d",
     *   <li>HOURS： "h",
     *   <li>MINUTES： "m",
     *   <li>SECONDS： "s",
     *   <li>MILLISECONDS： "ms",
     *   <li>MICROSECONDS： "us",
     *   <li>NANOSECONDS： "ns",
     * </ul></p>
     *
     * @param text the text to parse.
     * @return The duration parsed from the given text.
     */
    public static Duration parseDuration(String text) {
        checkNotNull(text);

        String trimmedText = text.trim();
        checkArgument(!trimmedText.isEmpty());

        int pos = 0;
        while (pos < trimmedText.length()) {
            char ch = trimmedText.charAt(pos);
            if (ch >= '0' && ch <= '9') {
                pos++;
            } else {
                break;
            }
        }

        String numStr = trimmedText.substring(0, pos);

        long num;
        try {
            num = Long.parseLong(numStr);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(
                String.format("The text does not start with a valid number: " +
                    "%s.", text));
        }

        String unitStr =
            trimmedText.substring(pos).trim().toLowerCase(Locale.US);

        switch (unitStr) {
            case "d":
                return Duration.of(num, ChronoUnit.DAYS);
            case "h":
                return Duration.of(num, ChronoUnit.HOURS);
            case "m":
                return Duration.of(num, ChronoUnit.MINUTES);
            case "s":
            case "":
                return Duration.of(num, ChronoUnit.SECONDS);
            case "ms":
                return Duration.of(num, ChronoUnit.MILLIS);
            case "us":
                return Duration.of(num, ChronoUnit.MICROS);
            case "ns":
                return Duration.of(num, ChronoUnit.NANOS);
            default:
                throw new IllegalArgumentException(
                    String.format("The text does not contain a valid unit %s.",
                        text));
        }
    }

    /**
     * Parses the given text to {@link LocalDateTime}.
     *
     * <p>The text should in format {@code yyyy-MM-dd hh:mm:ss.SSS}.</p>
     *
     * @param text The text to be parsed.
     * @return The {@link LocalDateTime} parsed from the given text.
     */
    public static LocalDateTime parseLocalDateTime(String text) {
        checkNotNull(text);

        DateTimeFormatter formatter =
            new DateTimeFormatterBuilder()
                .parseCaseInsensitive()
                .append(ISO_LOCAL_DATE)
                .appendLiteral(' ')
                .append(ISO_LOCAL_TIME)
                .toFormatter();

        return formatter.parse(text).query(LocalDateTime::from);
    }
}
