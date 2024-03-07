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

    public static BooleanType booleanType() {
        return BooleanType.INSTANCE;
    }

    public static ByteType byteType() {
        return ByteType.INSTANCE;
    }

    public static CharType charType() {
        return CharType.INSTANCE;
    }

    public static ShortType shortType() {
        return ShortType.INSTANCE;
    }

    public static IntType intType() {
        return IntType.INSTANCE;
    }

    public static LongType longType() {
        return LongType.INSTANCE;
    }

    public static FloatType floatType() {
        return FloatType.INSTANCE;
    }

    public static DoubleType doubleType() {
        return DoubleType.INSTANCE;
    }

    public static DecimalType decimalType(int precision, int scale) {
        return new DecimalType(precision, scale);
    }

    public static StringType stringType() {
        return StringType.INSTANCE;
    }

    public static DateType dateType() {
        return DateType.INSTANCE;
    }

    public static TimeType timeType() {
        return TimeType.INSTANCE;
    }

    public static TimestampType timestampType() {
        return TimestampType.INSTANCE;
    }

    public static IntervalType intervalType() {
        return IntervalType.INSTANCE;
    }

    public static SizeType sizeType() {
        return SizeType.INSTANCE;
    }

    public static <T> EnumType<T> enumType(Class<T> clazz) {
        return new EnumType<>(clazz);
    }

    public static ArrayType arrayType(DataType elementType) {
        return new ArrayType(elementType);
    }

    public static MapType mapType(DataType keyType, DataType valueType) {
        return new MapType(keyType, valueType);
    }

    public static RowFieldType field(String name, DataType type) {
        return new RowFieldType(name, type);
    }

    public static RowType rowType(RowFieldType... fieldTypes) {
        return new RowType(Arrays.asList(fieldTypes));
    }

    public static RowType rowType(DataType... fieldTypes) {
        RowType.Builder rowTypeBuilder = RowType.builder();

        int fieldIndex = 0;
        for (DataType fieldType : fieldTypes) {
            rowTypeBuilder.field("f" + fieldIndex, fieldType);
            fieldIndex++;
        }

        return rowTypeBuilder.build();
    }
}
