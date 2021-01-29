package dev.hoon.basic.domain.account.model;

import dev.hoon.basic.domain.account.meta.SexType;

public interface AccountProjection {

    interface Simple {

        long getId();

        String getName();

        String getEmail();

    }

    interface Detail {

        long getId();

        String getName();

        String getNickName();

        int getCountryCode();

        String getPhone();

        String getEmail();

        SexType getSex();

        default String getMobileNumber() {

            return "+" + getCountryCode() + getPhone();
        }
    }
}
