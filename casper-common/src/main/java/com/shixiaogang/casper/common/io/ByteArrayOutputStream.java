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
import java.util.Arrays;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * An implementation of {@link PositionedOutputStream} which is backed by a
 * byte array.
 */
public class ByteArrayOutputStream extends PositionedOutputStream {

    // The buffer where the data is stored.
    private byte[] bytes;

    // The number of valid bytes in the buffer.
    private int position;

    /**
     * Creates a new instance.
     */
    public ByteArrayOutputStream() {
        this(64);
    }

    /**
     * Creates a new instance with the buffer's capacity is initially the given
     * size.
     *
     * @param initialSize The initail size of the buffer.
     */
    public ByteArrayOutputStream(int initialSize) {
        checkArgument(initialSize > 0);
        this.bytes = new byte[initialSize];
        this.position = 0;
    }

    /**
     * Returns the bytes in the output stream.
     *
     * @return The bytes in the output stream.
     */
    public byte[] toByteArray() {
        return Arrays.copyOf(bytes, position);
    }

    @Override
    public long getPosition() throws IOException {
        return position;
    }

    /**
     * Resets the output stream. All currently accumulated output in the stream
     * will be discarded.
     */
    public void reset() {
        position = 0;
    }

    /**
     * Seeks to the given position. The write operation called next will start
     * from that position.
     *
     * @param targetPosition The new position where the writing starts.
     */
    public void seek(int targetPosition) {
        checkArgument(targetPosition >= 0);

        ensureCapacity(targetPosition + 1);

        if (targetPosition > position) {
            Arrays.fill(bytes, position, targetPosition, (byte) 0);
        }

        position = targetPosition;
    }

    @Override
    public void write(int b) throws IOException {
        ensureCapacity(position + 1);
        bytes[position] = (byte) b;
        position++;
    }

    @Override
    public void write(@Nonnull byte[] buffer, int offset, int length) {
        checkArgument(offset >= 0);
        checkArgument(length >= 0);
        checkArgument(offset + length <= buffer.length);

        ensureCapacity(position + length);
        System.arraycopy(buffer, offset, bytes, position, length);
        position += length;
    }

    @Override
    public void close() {
        // Nothing to do
    }

    private void ensureCapacity(int minCapacity) {
        if (minCapacity - bytes.length <= 0) {
            return;
        }

        int oldCapacity = bytes.length;
        int newCapacity = oldCapacity << 1;
        if (newCapacity - minCapacity < 0) {
            newCapacity = minCapacity;
        }

        if (newCapacity < 0) {
            if (minCapacity < 0) {
                throw new OutOfMemoryError();
            }

            newCapacity = Integer.MAX_VALUE;
        }

        bytes = Arrays.copyOf(bytes, newCapacity);
    }
}
