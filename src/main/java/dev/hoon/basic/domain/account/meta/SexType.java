package dev.hoon.basic.domain.account.meta;

import java.util.Arrays;

public enum SexType {

    MAIL("M"), FEMAIL("F");

    private String code;

    SexType(String code) {

        this.code = code;
    }

    public String getCode() {

        return code;
    }

    public static SexType getByCode(String code) {

        return Arrays.stream(SexType.values())
                .filter(it -> it.getCode().equals(code))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        String.format("Not Found SexType Enum : %s", code)));
    }
}
