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

import java.io.Serializable;
import java.net.URI;
import java.net.URISyntaxException;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.apache.commons.lang3.StringUtils.compareIgnoreCase;
import static org.apache.commons.lang3.StringUtils.equalsIgnoreCase;
import static org.apache.commons.lang3.StringUtils.isEmpty;

/**
 * The interface for the paths.
 *
 * <p>A path can be used to locate a file or an object in a storage system. It
 * is hierarchical and composed of a sequence of directory and file names
 * separated by a special delimiter.</p>
 *
 * <p>An empty path that does not consist any name represents the root path.</p>
 */
public final class Path implements Comparable<Path>, Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * The separator for file and directory names.
     */
    public static final String SLASH = "/";

    private final String scheme;

    private final String authority;

    private final String rootPath;

    private final String relativePath;

    /**
     * Creates a path from the given text.
     *
     * @param text The text representing the path.
     */
    public Path(String text) {
        this(getUri(text));
    }

    /**
     * Creates a path from the given uri.
     *
     * @param uri The uri representing the path.
     */
    public Path(URI uri) {
        /*
         * Normalize the path so that
         * 1. All redundant slashes are removed.
         * 2. All CURRENT_DIR segments are removed.
         * 3. If a PARENT_DIR segment is preceded by a non PARENT_DIR segment,
         * then both segments are removed.
         */
        URI normalizedUri = uri.normalize();

        this.scheme = normalizedUri.getScheme();
        this.authority = normalizedUri.getAuthority();

        String path = normalizedUri.getPath();

        String rootPath;
        String relativePath;

        if (!isEmpty(path) && path.charAt(0) == '/') {
            String windowsRoot = getWindowsRootPath(path.substring(1));
            if (!isEmpty(windowsRoot)) {
                rootPath =
                    windowsRoot.length() == 2 ?
                        SLASH + windowsRoot + SLASH :
                        SLASH + windowsRoot;
                relativePath = path.substring(1 + windowsRoot.length());
            } else {
                rootPath = SLASH;
                relativePath = path.substring(1);
            }
        } else {
            rootPath = "";
            relativePath = path == null ? "" : path;
        }

        // Remove the tailing slash from the relative path
        if (!isEmpty(relativePath) && relativePath.endsWith(SLASH)) {
            relativePath = relativePath.substring(0, relativePath.length() - 1);
        }

        /*
         * Check if the path contains symbolic segment.
         *
         * After the normalization performed previously, all CURRENT_DIR
         * segments are removed, and all PARENT_DIR must appear in the front of
         * the path if any.
         */
        if (!isEmpty(relativePath)) {
            int startSlashIndex = relativePath.indexOf(SLASH);
            String startSegment =
                startSlashIndex == -1 ?
                    relativePath :
                    relativePath.substring(0, startSlashIndex);
            if (startSegment.equals("..")) {
                throw new IllegalArgumentException(
                    String.format("The path contains symbolic segments: %s",
                        path));
            }
        }

        this.rootPath = rootPath;
        this.relativePath = relativePath;
    }

    /**
     * Resolves a child path against a parent path.
     *
     * @param parent The parent path.
     * @param child The child path.
     */
    public Path(Path parent, String child) {
        this(parent, new Path(child));
    }

    /**
     * Resolves a child path against a parent path.
     *
     * @param parent The parent path.
     * @param child The child path.
     */
    public Path(String parent, String child) {
        this(new Path(parent), new Path(child));
    }

    /**
     * Resolves a child path against a parent path.
     *
     * @param parent The parent path.
     * @param child The child path.
     */
    public Path(Path parent, Path child) {
        checkNotNull(parent);
        checkNotNull(child);

        if (!isEmpty(child.scheme) && !child.scheme.equals(parent.scheme)) {
            throw new IllegalArgumentException(
                String.format("The child path's scheme is inconsistent with " +
                        "the parent path's scheme, expected %s, but was %s.",
                    parent.scheme, child.scheme));
        }

        if (!isEmpty(child.authority) &&
                !child.authority.equals(parent.authority)) {
            throw new IllegalArgumentException(
                String.format("The child path's authority is inconsistent " +
                    "with the parent path's authority, expected %s, but " +
                    "was %s.", parent.authority, child.authority));
        }

        if (!isEmpty(child.rootPath)) {
            throw new IllegalArgumentException(
                String.format("The child path is absolute: %s.", child));
        }

        this.scheme = parent.scheme;
        this.authority = parent.authority;
        this.rootPath = parent.rootPath;

        if (isEmpty(child.relativePath)) {
            this.relativePath = parent.relativePath;
        } else {
            this.relativePath =
                parent.relativePath + SLASH + child.relativePath;
        }
    }

    private Path(
        String scheme,
        String authority,
        String rootPath,
        String relativePath
    ) {
        this.scheme = scheme;
        this.authority = authority;
        this.rootPath = rootPath;
        this.relativePath = relativePath;
    }

    /**
     * Returns the scheme of the path.
     *
     * @return The scheme of the path.
     */
    public String getScheme() {
        return scheme;
    }

    /**
     * Returns the authority of the path.
     *
     * @return The authority of the path.
     */
    public String getAuthority() {
        return authority;
    }

    /**
     * Returns the path component of the path.
     *
     * @return The path component of the path.
     */
    public String getPath() {
        if (isEmpty(rootPath)) {
            return relativePath;
        } else {
            return rootPath + relativePath;
        }
    }

    /**
     * Returns the name of the path, i.e., the last segment in the path.
     *
     * @return The name of the path.
     */
    public String getName() {
        int lastSlashPos = relativePath.lastIndexOf(SLASH);
        return lastSlashPos == -1 ?
            relativePath :
            relativePath.substring(lastSlashPos + 1);
    }

    /**
     * Converts the path to uri.
     *
     * @return The uri converted from the path.
     */
    public URI toUri() {
        try {
            return new URI(scheme, authority, getPath(), null, null);
        } catch (URISyntaxException e) {
            throw new IllegalStateException("Cannot transform path to uri.", e);
        }
    }

    /**
     * Returns true if the path is absolute.
     *
     * @return True if the path is absolute.
     */
    boolean isAbsolute() {
        return !isEmpty(rootPath);
    }

    /**
     * Returns the parent of the path.
     *
     * <p>Null is returned if the parent does not exist or is unknown, i.e., the
     * path is either the root path or a relative empty path.</p>
     *
     * @return The parent of the path.
     */
    public Path getParent() {
        if (isEmpty(relativePath)) {
            return null;
        } else {
            int lastSlashPos = relativePath.lastIndexOf(SLASH);
            String parentRelativePath =
                lastSlashPos == -1 ?
                    "" :
                    relativePath.substring(0, lastSlashPos);
            return new Path(
                scheme,
                authority,
                rootPath,
                parentRelativePath
            );
        }
    }

    private static URI getUri(String text) {
        checkNotNull(text);

        /*
         * Adding slash in front of the text so that Windows drivers are not
         * parsed as schemes.
         */
        if (!isEmpty(getWindowsRootPath(text))) {
            text = "/" + text;
        }

        try {
            return new URI(text);
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException(
                String.format("The path's format is invalid: '%s'.", text), e);
        }
    }

    private static String getWindowsRootPath(String path) {
        if (path.length() >= 2 &&
            Character.isLetter(path.charAt(0)) &&
            path.charAt(1) == ':' &&
            (path.length() == 2 || path.charAt(2) == '/')) {
            return path.length() == 2 ? path : path.substring(0, 3);
        } else {
            return "";
        }
    }

    @Override
    public int compareTo(Path other) {
        int res = compareIgnoreCase(scheme, other.scheme);
        if (res != 0) {
            return res;
        }

        res = compareIgnoreCase(authority, other.authority);
        if (res != 0) {
            return res;
        }

        res = compareIgnoreCase(rootPath, other.rootPath);
        if (res != 0) {
            return res;
        }

        return compareIgnoreCase(relativePath, other.relativePath);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Path)) {
            return false;
        }

        Path that = (Path) other;
        if (!equalsIgnoreCase(scheme, that.scheme)) {
            return false;
        } else if (!equalsIgnoreCase(authority, that.authority)) {
            return false;
        } else if (!equalsIgnoreCase(rootPath, that.rootPath)) {
            return false;
        } else {
            return equalsIgnoreCase(relativePath, that.relativePath);
        }
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = 31 * result +
            (scheme == null ? 0 : scheme.toLowerCase().hashCode());
        result = 31 * result +
            (authority == null ? 0 : authority.toLowerCase().hashCode());
        result = 31 * result +
            (rootPath == null ? 0 : rootPath.toLowerCase().hashCode());
        result = 31 * result +
            (relativePath == null ? 0 : relativePath.toLowerCase().hashCode());

        return result;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        if (!isEmpty(scheme)) {
            stringBuilder.append(scheme);
            stringBuilder.append(":");
        }

        if (!isEmpty(authority)) {
            stringBuilder.append("//");
            stringBuilder.append(authority);
        }

        stringBuilder.append(rootPath);
        stringBuilder.append(relativePath);

        return stringBuilder.toString();
    }
}
