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

import java.util.Arrays;

/**
 * A utility class to create {@link DataType}.
 */
public class DataTypes {

    //CHECKSTYLE.OFF: MethodName

    public static final BooleanType BOOLEAN = new BooleanType();
    public static final ByteType BYTE = new ByteType();
    public static final CharType CHAR = new CharType();
    public static final ShortType SHORT = new ShortType();
    public static final IntType INT = new IntType();
    public static final LongType LONG = new LongType();
    public static final FloatType FLOAT = new FloatType();
    public static final DoubleType DOUBLE = new DoubleType();
    public static final StringType STRING = new StringType();
    public static final DateType DATE = new DateType();
    public static final TimeType TIME = new TimeType();
    public static final TimestampType TIMESTAMP = new TimestampType();
    public static final IntervalType INTERVAL = new IntervalType();
    public static final SizeType SIZE = new SizeType();

    public static DecimalType DECIMAL(int precision, int scale) {
        return new DecimalType(precision, scale);
    }

    public static <T> EnumType<T> ENUM(Class<T> clazz) {
        return new EnumType<>(clazz);
    }

    public static ArrayType ARRAY(DataType elementType) {
        return new ArrayType(elementType);
    }

    public static MapType MAP(DataType keyType, DataType valueType) {
        return new MapType(keyType, valueType);
    }

    public static RowFieldType FIELD(String name, DataType type) {
        return new RowFieldType(name, type);
    }

    public static RowType ROW(RowFieldType... fieldTypes) {
        return new RowType(Arrays.asList(fieldTypes));
    }

    public static RowType ROW(DataType... fieldTypes) {
        RowType.Builder rowTypeBuilder = RowType.builder();

        int fieldIndex = 0;
        for (DataType fieldType : fieldTypes) {
            rowTypeBuilder.field("f" + fieldIndex, fieldType);
            fieldIndex++;
        }

        return rowTypeBuilder.build();
    }

    //CHECKSTYLE.ON: MethodName
}
