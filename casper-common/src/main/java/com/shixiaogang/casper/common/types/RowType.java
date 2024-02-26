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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * The data type for row values.
 */
@Getter
@EqualsAndHashCode
@ToString
public class RowType implements DataType {

    private static final long serialVersionUID = 1L;

    // The type of the list elements.
    private final List<RowFieldType> fieldTypes;

    public RowType(List<RowFieldType> fieldTypes) {
        Set<String> fieldNames = new HashSet<>();
        for (RowFieldType fieldType : fieldTypes) {
            String fieldName = fieldType.getName();
            if (fieldNames.contains(fieldName)) {
                throw new IllegalArgumentException(
                    String.format("Duplicate field name: %s.", fieldName));
            }

            fieldNames.add(fieldName);
        }

        this.fieldTypes = fieldTypes;
    }

    public static Builder builder() {
        return new Builder();
    }

    /**
     * A builder for {@link RowType}.
     */
    public static class Builder {
        private final List<RowFieldType> fieldTypes;

        private Builder() {
            this.fieldTypes = new ArrayList<>();
        }

        public Builder field(String name, DataType type) {
            fieldTypes.add(new RowFieldType(name, type));
            return this;
        }

        public RowType build() {
            return new RowType(fieldTypes);
        }
    }
}
