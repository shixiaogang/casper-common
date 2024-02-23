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

import java.net.URI;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit tests for {@link Path}.
 */
public class PathTest {
    @Test
    public void testConstruct() throws URISyntaxException {
        Path path;

        path = new Path("");
        assertNull(path.getScheme());
        assertNull(path.getAuthority());
        assertEquals("", path.getPath());
        assertEquals("", path.getName());
        assertEquals(new URI(""), path.toUri());
        assertFalse(path.isAbsolute());
        assertNull(path.getParent());

        path = new Path("/");
        assertNull(path.getScheme());
        assertNull(path.getAuthority());
        assertEquals("/", path.getPath());
        assertEquals("", path.getName());
        assertEquals(new URI("/"), path.toUri());
        assertTrue(path.isAbsolute());
        assertNull(path.getParent());

        path = new Path("path");
        assertNull(path.getScheme());
        assertNull(path.getAuthority());
        assertEquals("path", path.getPath());
        assertEquals("path", path.getName());
        assertEquals(new URI("path"), path.toUri());
        assertFalse(path.isAbsolute());
        assertEquals(new Path(""), path.getParent());

        path = new Path("/path");
        assertNull(path.getScheme());
        assertNull(path.getAuthority());
        assertEquals("/path", path.getPath());
        assertEquals("path", path.getName());
        assertEquals(new URI("/path"), path.toUri());
        assertTrue(path.isAbsolute());
        assertEquals(new Path("/"), path.getParent());

        path = new Path("path/");
        assertNull(path.getScheme());
        assertNull(path.getAuthority());
        assertEquals("path", path.getPath());
        assertEquals("path", path.getName());
        assertEquals(new URI("path"), path.toUri());
        assertFalse(path.isAbsolute());
        assertEquals(new Path(""), path.getParent());

        path = new Path("hdfs:///my/path");
        assertEquals("hdfs", path.getScheme());
        assertNull(path.getAuthority());
        assertEquals("/my/path", path.getPath());
        assertEquals("path", path.getName());
        assertEquals(new URI("hdfs:///my/path"), path.toUri());
        assertTrue(path.isAbsolute());
        assertEquals(new Path("hdfs:///my"), path.getParent());

        path = new Path("hdfs://my/path");
        assertEquals("hdfs", path.getScheme());
        assertEquals("my", path.getAuthority());
        assertEquals("/path", path.getPath());
        assertEquals("path", path.getName());
        assertEquals(new URI("hdfs://my/path"), path.toUri());
        assertTrue(path.isAbsolute());
        assertEquals(new Path("hdfs://my/"), path.getParent());

        path = new Path("hdfs://user@host/my/path");
        assertEquals("hdfs", path.getScheme());
        assertEquals("user@host", path.getAuthority());
        assertEquals("/my/path", path.getPath());
        assertEquals("path", path.getName());
        assertEquals(new URI("hdfs://user@host/my/path"), path.toUri());
        assertTrue(path.isAbsolute());
        assertEquals(new Path("hdfs://user@host/my"), path.getParent());
    }

    @Test
    public void testConstructWindows() throws URISyntaxException {
        Path path;

        path = new Path("C:/");
        assertNull(path.getScheme());
        assertNull(path.getAuthority());
        assertEquals("/C:/", path.getPath());
        assertEquals("", path.getName());
        assertEquals(new URI("/C:/"), path.toUri());
        assertTrue(path.isAbsolute());
        assertNull(path.getParent());

        path = new Path("C:/my/path");
        assertNull(path.getScheme());
        assertNull(path.getAuthority());
        assertEquals("/C:/my/path", path.getPath());
        assertEquals("path", path.getName());
        assertEquals(new URI("/C:/my/path"), path.toUri());
        assertTrue(path.isAbsolute());
        assertEquals(new Path("C:/my"), path.getParent());

        path = new Path("hdfs:///C:");
        assertEquals("hdfs", path.getScheme());
        assertNull(path.getAuthority());
        assertEquals("/C:/", path.getPath());
        assertEquals("", path.getName());
        assertEquals(new URI("hdfs:///C:/"), path.toUri());
        assertTrue(path.isAbsolute());
        assertNull(path.getParent());

        path = new Path("CC:/");
        assertEquals("CC", path.getScheme());
        assertNull(path.getAuthority());
        assertEquals("/", path.getPath());
        assertEquals("", path.getName());
        assertEquals(new URI("CC:/"), path.toUri());
        assertTrue(path.isAbsolute());
        assertNull(path.getParent());
    }

    @Test
    public void testConstructSymbolicSegment() throws URISyntaxException {
        Path path;

        path = new Path(".");
        assertEquals("", path.getPath());
        assertEquals("", path.getName());
        assertEquals(new URI(""), path.toUri());
        assertNull(path.getParent());

        path = new Path("my/./path");
        assertEquals("my/path", path.getPath());
        assertEquals("path", path.getName());
        assertEquals(new URI("my/path"), path.toUri());
        assertEquals(new Path("my"), path.getParent());

        path = new Path("my/../path");
        assertEquals("path", path.getPath());
        assertEquals("path", path.getName());
        assertEquals(new URI("path"), path.toUri());
        assertEquals(new Path(""), path.getParent());
    }

    @Test
    public void testConstructIllegal() {
        assertThrows(IllegalArgumentException.class,
            () -> new Path("hdfs://###/my/path"));

        assertThrows(IllegalArgumentException.class,
            () -> new Path(".."));
    }

    @Test
    public void testResolve() throws URISyntaxException {
        Path path;

        path = new Path("parent", "");
        assertEquals(new URI("parent"), path.toUri());

        path = new Path("parent", "child");
        assertEquals(new URI("parent/child"), path.toUri());

        path = new Path("hdfs:///parent", "child");
        assertEquals(new URI("hdfs:///parent/child"), path.toUri());

        path = new Path(new Path("hdfs://user@host/parent"), "child");
        assertEquals(new URI("hdfs://user@host/parent/child"), path.toUri());
    }

    @Test
    public void testResolveIllegal() {

        assertThrows(IllegalArgumentException.class,
            () -> new Path("hdfs:///parent", "file:child"));

        assertThrows(IllegalArgumentException.class,
            () -> new Path("parent", "hdfs:///child"));

        assertThrows(IllegalArgumentException.class,
            () -> new Path("hdfs://user@host/parent",
                "hdfs://user2@host/child"));

        assertThrows(IllegalArgumentException.class,
            () -> new Path("parent", "hdfs://user2@host/child"));

        assertThrows(IllegalArgumentException.class,
            () -> new Path("hdfs://user@host/parent", "/child"));
    }
}
