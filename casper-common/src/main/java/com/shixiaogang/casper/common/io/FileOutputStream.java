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

import java.io.IOException;

/**
 * Interface for an output stream written to a file.
 */
public abstract class FileOutputStream extends PositionedOutputStream {

    /**
     * Flushes the stream, writing any buffered data to the underlying storage.
     *
     * <p>After the method has been called, the stream must not hold any
     * buffered data any more. But it does not necessarily mean that the data
     * is persistent. Data is completely persistent only when the calling to
     * {@link #sync()} and {@link #close()} successfully completes.</p>
     *
     * @throws IOException Thrown if an I/O error occurs.
     */
    public abstract void flush() throws IOException;

    /**
     * Flushes the stream, persisting all data to the underlying storage.
     *
     * <p>After the method has been called, all data must have been persistent
     * on the devices.</p>
     *
     * @throws IOException Thrown if an I/O error occurs.
     */
    public abstract void sync() throws IOException;

}
