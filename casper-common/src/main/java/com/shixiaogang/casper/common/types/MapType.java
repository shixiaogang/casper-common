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

package com.shixiaogang.casper.common.types;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * The data type for map values.
 */
@Getter
@EqualsAndHashCode
@ToString
public class MapType implements DataType {

    private static final long serialVersionUID = 1L;

    // The type of key fields.
    private final DataType keyType;

    // The type of value fields.
    private final DataType valueType;

    public MapType(DataType keyType, DataType valueType) {
        this.keyType = keyType;
        this.valueType = valueType;
    }
}
