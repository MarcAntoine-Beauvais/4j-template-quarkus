package com.me.my_application.entity.converter;

import javax.persistence.AttributeConverter;

public abstract class JpaEnumConverter<T extends Enum<T>> implements AttributeConverter<T, String> {

    public abstract Class<T> getEnumClass();

    @Override
    public String convertToDatabaseColumn(T attribute) {
        return (attribute == null) ?
                null :
                attribute.name().toLowerCase();
    }

    @Override
    public T convertToEntityAttribute(String dbData) {
        return (dbData == null) ?
                null :
                Enum.valueOf(this.getEnumClass(), dbData.toUpperCase());
    }
}
