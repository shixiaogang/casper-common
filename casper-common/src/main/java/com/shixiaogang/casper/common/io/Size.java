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

package com.shixiaogang.casper.common.io;

import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Locale;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * The class measuring the amount of data.
 *
 * <p>Both binary and decimal prefixes are supported. The capacity of hard disks
 * is usually specified with decimal prefixes; while the capacity of memory
 * modules is usually specified with binary prefixes. Following the IEC
 * standard, binary prefixes include kibi (ki, 2^10), mebi (mi, 2^20), gibi
 * (gi, 2^30), tebi (ti, 2^40), pebi (pi, 2^50), and exbi (ei, 2^60). The
 * decimal prefixes include kilo (k, 10^3), mega (m, 10^6), giga (g, 10^9),
 * tera (t, 10^12), peta (p, 10^15), and exa (e, 10^18).</p>
 */
@EqualsAndHashCode
public final class Size implements Comparable<Size>, Serializable {

    public static final long BYTES_PER_KIBI = 1024L;
    public static final long BYTES_PER_MEBI = 1024L * BYTES_PER_KIBI;
    public static final long BYTES_PER_GIBI = 1024L * BYTES_PER_MEBI;
    public static final long BYTES_PER_TEBI = 1024L * BYTES_PER_GIBI;
    public static final long BYTES_PER_PEBI = 1024L * BYTES_PER_TEBI;
    public static final long BYTES_PER_EXBI = 1024L * BYTES_PER_PEBI;

    public static final long BYTES_PER_KILO = 1000L;
    public static final long BYTES_PER_MEGA = 1000L * BYTES_PER_KILO;
    public static final long BYTES_PER_GIGA = 1000L * BYTES_PER_MEGA;
    public static final long BYTES_PER_TERA = 1000L * BYTES_PER_GIGA;
    public static final long BYTES_PER_PETA = 1000L * BYTES_PER_TERA;
    public static final long BYTES_PER_EXA = 1000L * BYTES_PER_PETA;

    private static final String UNIT_EXBI = "ei";
    private static final String UNIT_PEBI = "pi";
    private static final String UNIT_TEBI = "ti";
    private static final String UNIT_GIBI = "gi";
    private static final String UNIT_MEBI = "mi";
    private static final String UNIT_KIBI = "ki";
    private static final String UNIT_EXA = "e";
    private static final String UNIT_PETA = "p";
    private static final String UNIT_TERA = "t";
    private static final String UNIT_GIGA = "g";
    private static final String UNIT_MEGA = "m";
    private static final String UNIT_KILO = "k";

    private final long bytes;

    private Size(long bytes) {
        this.bytes = bytes;
    }

    /**
     * Creates a {@code Size} representing the given number of bytes.
     *
     * @param numBytes The number of bytes.
     * @return A {@code Size} representing the given number of bytes.
     */
    public static Size ofBytes(long numBytes) {
        return new Size(numBytes);
    }

    /**
     * Creates a {@code Size} representing the given number of exbibytes.
     *
     * @param numExbiBytes The number of exbibytes.
     * @return A {@code Size} representing the given number of exibibytes.
     */
    public static Size ofExbiBytes(long numExbiBytes) {
        return of(numExbiBytes, BYTES_PER_EXBI);
    }

    /**
     * Creates a {@code Size} representing the given number of pebibytes.
     *
     * @param numPebiBytes The number of pebibytes.
     * @return A {@code Size} representing the given number of pebibytes.
     */
    public static Size ofPebiBytes(long numPebiBytes) {
        return of(numPebiBytes, BYTES_PER_PEBI);
    }

    /**
     * Creates a {@code Size} representing the given number of tebibytes.
     *
     * @param numTebiBytes The number of tebibytes.
     * @return A {@code Size} representing the given number of tebibytes.
     */
    public static Size ofTebiBytes(long numTebiBytes) {
        return of(numTebiBytes, BYTES_PER_TEBI);
    }

    /**
     * Creates a {@code Size} representing the given number of gibibytes.
     *
     * @param numGibiBytes The number of gibibytes.
     * @return A {@code Size} representing the given number of gibibytes.
     */
    public static Size ofGibiBytes(long numGibiBytes) {
        return of(numGibiBytes, BYTES_PER_GIBI);
    }

    /**
     * Creates a {@code Size} representing the given number of mebibytes.
     *
     * @param numMebiBytes The number of mebibytes.
     * @return A {@code Size} representing the given number of mebibytes.
     */
    public static Size ofMebiBytes(long numMebiBytes) {
        return of(numMebiBytes, BYTES_PER_MEBI);
    }

    /**
     * Creates a {@code Size} representing the given number of kibibytes.
     *
     * @param numKibiBytes The number of kibibytes.
     * @return A {@code Size} representing the given number of kibibytes.
     */
    public static Size ofKibiBytes(long numKibiBytes) {
        return of(numKibiBytes, BYTES_PER_KIBI);
    }

    /**
     * Creates a {@code Size} representing the given number of exabytes.
     *
     * @param numExaBytes The number of exabytes.
     * @return A {@code Size} representing the given number of exabytes.
     */
    public static Size ofExaBytes(long numExaBytes) {
        return of(numExaBytes, BYTES_PER_EXA);
    }

    /**
     * Creates a {@code Size} representing the given number of petabytes.
     *
     * @param numPetaBytes The number of petabytes.
     * @return A {@code Size} representing the given number of petabytes.
     */
    public static Size ofPetaBytes(long numPetaBytes) {
        return of(numPetaBytes, BYTES_PER_PETA);
    }

    /**
     * Creates a {@code Size} representing the given number of terabytes.
     *
     * @param numTeraBytes The number of terabytes.
     * @return A {@code Size} representing the given number of terabytes.
     */
    public static Size ofTeraBytes(long numTeraBytes) {
        return of(numTeraBytes, BYTES_PER_TERA);
    }

    /**
     * Creates a {@code Size} representing the given number of gigabytes.
     *
     * @param numGigaBytes The number of gigabytes.
     * @return A {@code Size} representing the given number of gigabytes.
     */
    public static Size ofGigaBytes(long numGigaBytes) {
        return of(numGigaBytes, BYTES_PER_GIGA);
    }

    /**
     * Creates a {@code Size} representing the given number of megabytes.
     *
     * @param numMegaBytes The number of megabytes.
     * @return A {@code Size} representing the given number of megabytes.
     */
    public static Size ofMegaBytes(long numMegaBytes) {
        return of(numMegaBytes, BYTES_PER_MEGA);
    }

    /**
     * Creates a {@code Size} representing the given number of kilobytes.
     *
     * @param numKiloBytes The number of bytes.
     * @return A {@code Size} representing the given number of kilobytes.
     */
    public static Size ofKiloBytes(long numKiloBytes) {
        return of(numKiloBytes, BYTES_PER_KILO);
    }

    /**
     * Gets the number of bytes represented by the {@code Size}.
     *
     * @return The number of bytes represented by the {@code Size}.
     */
    public long toBytes() {
        return bytes;
    }

    /**
     * Converts the {@code Size} to exbibytes.
     *
     * @return The number of exbibytes represented by the {@code Size}.
     */
    public long toExbiBytes() {
        return to(BYTES_PER_EXBI);
    }

    /**
     * Converts the {@code Size} to pebibytes.
     *
     * @return The number of pebibytes represented by the {@code Size}.
     */
    public long toPebiBytes() {
        return to(BYTES_PER_PEBI);
    }

    /**
     * Converts the {@code Size} to tebibytes.
     *
     * @return The number of tebibytes represented by the {@code Size}.
     */
    public long toTebiBytes() {
        return to(BYTES_PER_TEBI);
    }

    /**
     * Converts the {@code Size} to gibibytes.
     *
     * @return The number of gibibytes represented by the {@code Size}.
     */
    public long toGibiBytes() {
        return to(BYTES_PER_GIBI);
    }

    /**
     * Converts the {@code Size} to mebibytes.
     *
     * @return The number of mebibytes represented by the {@code Size}.
     */
    public long toMebiBytes() {
        return to(BYTES_PER_MEBI);
    }

    /**
     * Converts the {@code Size} to kibibytes.
     *
     * @return The number of kibibytes represented by the {@code Size}.
     */
    public long toKibiBytes() {
        return to(BYTES_PER_KIBI);
    }

    /**
     * Converts the {@code Size} to exabytes.
     *
     * @return The number of exabytes represented by the {@code Size}.
     */
    public long toExaBytes() {
        return to(BYTES_PER_EXA);
    }

    /**
     * Converts the {@code Size} to petabytes.
     *
     * @return The number of petabytes represented by the {@code Size}.
     */
    public long toPetaBytes() {
        return to(BYTES_PER_PETA);
    }

    /**
     * Converts the {@code Size} to terabytes.
     *
     * @return The number of terabytes represented by the {@code Size}.
     */
    public long toTeraBytes() {
        return to(BYTES_PER_TERA);
    }

    /**
     * Converts the {@code Size} to gigabytes.
     *
     * @return The number of gigabytes represented by the {@code Size}.
     */
    public long toGigaBytes() {
        return to(BYTES_PER_GIGA);
    }

    /**
     * Converts the {@code Size} to megabytes.
     *
     * @return The number of megabytes represented by the {@code Size}.
     */
    public long toMegaBytes() {
        return to(BYTES_PER_MEGA);
    }

    /**
     * Converts the {@code Size} to kilobytes.
     *
     * @return The number of kilobytes represented by the {@code Size}.
     */
    public long toKiloBytes() {
        return to(BYTES_PER_KILO);
    }

    /**
     * Compares this size to the given size.
     *
     * @param other The other size to be compared.
     * @return negative if less, positive if greater, and zero if equals.
     */
    @Override
    public int compareTo(Size other) {
        return Long.compare(bytes, other.bytes);
    }

    /**
     * Returns the result of adding this size with the given size.
     *
     * @param other The size to add.
     * @return The result of adding this size with  the given size.
     */
    public Size plus(Size other) {
        long newBytes = Math.addExact(bytes, other.bytes);
        return new Size(newBytes);
    }

    /**
     * Returns the result of subtracting the given size from this size.
     *
     * @param other The size to subtract.
     * @return The result of subtracting the given size from this size.
     */
    public Size minus(Size other) {
        long newBytes = Math.subtractExact(bytes, other.bytes);
        return new Size(newBytes);
    }

    /**
     * Parses the given text to a size.
     *
     * <p>The text is in format "{number} {unit}", e.g., "123B", "321 MiB". If
     * no unit is specified, it will be considered as bytes.</p>
     *
     * <p>Supported units include
     * <ul>
     *     <li>EXBI: "ei"</li>
     *     <li>PEBI: "pi"</li>
     *     <li>TEBI: "ti"</li>
     *     <li>GIBI: "gi"</li>
     *     <li>MEBI: "mi"</li>
     *     <li>KIBI: "ki"</li>
     *     <li>EXA: "e"</li>
     *     <li>PETA: "p"</li>
     *     <li>TERA: "t"</li>
     *     <li>GIGA: "g"</li>
     *     <li>MEGA: "m"</li>
     *     <li>KILO: "k"</li>
     * </ul></p>
     *
     * @param text The text to parse.
     * @return The size parsed from the given text.
     */
    public static Size parse(String text) {
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

        if (unitStr.endsWith("b")) {
            unitStr = unitStr.substring(0, unitStr.length() - 1);
        }

        switch (unitStr) {
            case UNIT_EXBI:
                return Size.ofExbiBytes(num);
            case UNIT_PEBI:
                return Size.ofPebiBytes(num);
            case UNIT_TEBI:
                return Size.ofTebiBytes(num);
            case UNIT_GIBI:
                return Size.ofGibiBytes(num);
            case UNIT_MEBI:
                return Size.ofMebiBytes(num);
            case UNIT_KIBI:
                return Size.ofKibiBytes(num);
            case UNIT_EXA:
                return Size.ofExaBytes(num);
            case UNIT_PETA:
                return Size.ofPetaBytes(num);
            case UNIT_TERA:
                return Size.ofTeraBytes(num);
            case UNIT_GIGA:
                return Size.ofGigaBytes(num);
            case UNIT_MEGA:
                return Size.ofMegaBytes(num);
            case UNIT_KILO:
                return Size.ofKiloBytes(num);
            case "":
                return Size.ofBytes(num);
            default:
                throw new IllegalArgumentException(
                    String.format("The text does not contain a valid unit %s.",
                        text));
        }
    }

    @Override
    public String toString() {
        return String.format("%dB", bytes);
    }

    private static Size of(long amount, long unit) {
        long bytes = Math.multiplyExact(amount, unit);
        return new Size(bytes);
    }

    private long to(long unit) {
        return bytes / unit;
    }
}

