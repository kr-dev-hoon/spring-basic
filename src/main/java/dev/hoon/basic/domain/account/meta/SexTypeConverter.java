package dev.hoon.basic.domain.account.meta;

import javax.persistence.AttributeConverter;

public class SexTypeConverter implements AttributeConverter<SexType, String>{

    @Override
    public String convertToDatabaseColumn(SexType attribute) {

        return attribute.getCode();
    }

    @Override
    public SexType convertToEntityAttribute(String dbData) {

        return SexType.getByCode(dbData);
    }
}
