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

package com.shixiaogang.casper.io;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Unit tests for {@link Size}.
 */
public class SizeTest {

    @Test
    public void testTransform() {
        Size size = Size.ofBytes(846611237151882L);
        long sizeInBytes = size.toBytes();

        long sizeInExa = size.toExaBytes();
        long sizeInPeta = size.toPetaBytes();
        long sizeInTera = size.toTeraBytes();
        long sizeInGiga = size.toGigaBytes();
        long sizeInMega = size.toMegaBytes();
        long sizeInKilo = size.toKiloBytes();
        assertEquals(sizeInExa, sizeInPeta / 1000);
        assertEquals(sizeInPeta, sizeInTera / 1000);
        assertEquals(sizeInTera, sizeInGiga / 1000);
        assertEquals(sizeInGiga, sizeInMega / 1000);
        assertEquals(sizeInMega, sizeInKilo / 1000);
        assertEquals(sizeInKilo, sizeInBytes / 1000);

        long sizeInExbi = size.toExbiBytes();
        long sizeInPebi = size.toPebiBytes();
        long sizeInTebi = size.toTebiBytes();
        long sizeInGibi = size.toGibiBytes();
        long sizeInMebi = size.toMebiBytes();
        long sizeInKibi = size.toKibiBytes();
        assertEquals(sizeInExbi, sizeInPebi / 1024);
        assertEquals(sizeInPebi, sizeInTebi / 1024);
        assertEquals(sizeInTebi, sizeInGibi / 1024);
        assertEquals(sizeInGibi, sizeInMebi / 1024);
        assertEquals(sizeInMebi, sizeInKibi / 1024);
        assertEquals(sizeInKibi, sizeInBytes / 1024);
    }

    @Test
    public void testParse() {
        assertEquals(Size.ofBytes(1), Size.parse("1"));
        assertEquals(Size.ofBytes(1), Size.parse("1"));

        assertEquals(Size.ofKiloBytes(2), Size.parse("2k"));
        assertEquals(Size.ofKiloBytes(2), Size.parse("2kb"));
        assertEquals(Size.ofMegaBytes(3), Size.parse("3m"));
        assertEquals(Size.ofMegaBytes(3), Size.parse("3mb"));
        assertEquals(Size.ofGigaBytes(4), Size.parse("4g"));
        assertEquals(Size.ofGigaBytes(4), Size.parse("4gb"));
        assertEquals(Size.ofTeraBytes(5), Size.parse("5t"));
        assertEquals(Size.ofTeraBytes(5), Size.parse("5tb"));
        assertEquals(Size.ofPetaBytes(6), Size.parse("6p"));
        assertEquals(Size.ofPetaBytes(6), Size.parse("6pb"));
        assertEquals(Size.ofExaBytes(7), Size.parse("7e"));
        assertEquals(Size.ofExaBytes(7), Size.parse("7eb"));

        assertEquals(Size.ofKibiBytes(2), Size.parse("2ki"));
        assertEquals(Size.ofKibiBytes(2), Size.parse("2kib"));
        assertEquals(Size.ofMebiBytes(3), Size.parse("3mi"));
        assertEquals(Size.ofMebiBytes(3), Size.parse("3mib"));
        assertEquals(Size.ofGibiBytes(4), Size.parse("4gi"));
        assertEquals(Size.ofGibiBytes(4), Size.parse("4gib"));
        assertEquals(Size.ofTebiBytes(5), Size.parse("5ti"));
        assertEquals(Size.ofTebiBytes(5), Size.parse("5tib"));
        assertEquals(Size.ofPebiBytes(6), Size.parse("6pi"));
        assertEquals(Size.ofPebiBytes(6), Size.parse("6pib"));
        assertEquals(Size.ofExbiBytes(7), Size.parse("7ei"));
        assertEquals(Size.ofExbiBytes(7), Size.parse("7eib"));
    }

    @Test
    public void testParseIllegal() {
        assertThrows(IllegalArgumentException.class,
            () -> Size.parse("aaakb"));
        assertThrows(IllegalArgumentException.class,
            () -> Size.parse("1234ab"));
    }
}
