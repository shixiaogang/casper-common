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

import javax.annotation.Nonnull;

import java.io.IOException;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * An implementation of {@link PositionedInputStream} which is backed by a
 * byte array.
 */
public class ByteArrayInputStream extends PositionedInputStream {

    // The buffer where the data is stored.
    private final byte[] bytes;

    // The number of valid bytes in the buffer.
    private final int count;

    // The index of the next byte to read.
    private int position;

    // The currently marked position.
    private int mark;

    /**
     * Creates a new instance which is backed by the given byte array.
     *
     * @param bytes The input buffer.
     */
    public ByteArrayInputStream(byte[] bytes) {
        this(bytes, 0, bytes.length);
    }

    /**
     * Creates a new instance which is backed by the given byte array, and
     * starts reading from the given position.
     *
     * @param bytes The input buffer.
     * @param offset The offset in the buffer of the first byte to read.
     * @param length The maximum number of bytes to read from the buffer.
     */
    public ByteArrayInputStream(byte[] bytes, int offset, int length) {
        checkNotNull(bytes);
        checkArgument(offset >= 0 && offset < bytes.length);

        this.bytes = bytes;
        this.count = Math.min(bytes.length, offset + length);
        this.position = offset;
        this.mark = offset;
    }

    @Override
    public long getPosition() throws IOException {
        return position;
    }

    @Override
    public int available() {
        return count - position;
    }

    @Override
    public void seek(long targetPosition) {
        checkArgument(targetPosition >= 0 && targetPosition <= count);
        this.position = (int) targetPosition;
    }

    @Override
    public long skip(long numBytesToSkip) {
        if (numBytesToSkip == 0) {
            return 0;
        }

        long numBytesRemaining = count - position;
        if (numBytesToSkip < numBytesRemaining) {
            position += (int) numBytesToSkip;
            return numBytesToSkip;
        } else {
            position = count;
            return numBytesRemaining;
        }
    }

    @Override
    public boolean markSupported() {
        return true;
    }

    @Override
    public void mark(int readAheadLimit) {
        mark = position;
    }

    @Override
    public void reset() {
        position = mark;
    }

    @Override
    public int read() {
        return position < count ? 0xFF & bytes[position++] : -1;
    }

    @Override
    public int read(@Nonnull byte[] buffer, int offset, int length) {
        if (length <= 0) {
            return 0;
        }

        if (position >= this.count) {
            return -1;
        }

        int numBytesToRead = Math.min(this.count - position, length);
        System.arraycopy(bytes, position, buffer, offset, numBytesToRead);

        position += numBytesToRead;

        return numBytesToRead;
    }

    @Override
    public void close() {
        // Nothing to do
    }

}
