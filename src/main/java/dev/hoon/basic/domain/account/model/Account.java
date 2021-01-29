package dev.hoon.basic.domain.account.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.hoon.basic.domain.account.meta.SexType;
import dev.hoon.basic.domain.account.meta.SexTypeConverter;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "Account")
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Getter
public class Account implements Serializable {

    private static final long serialVersionUID = 5887422154578158768L;

    @Id
    @Column(name = "ID", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "NAME", nullable = false, unique = true, length = 20)
    private String name;

    @Column(name = "NICKNAME", nullable = false, length = 30)
    private String nickName;

    @Column(name = "PASSWORD", nullable = false, length = 512)
    @JsonIgnore
    private String password;

    @Column(name = "COUNTRY_CODE", nullable = false)
    private int countryCode;

    @Column(name = "PHONE", nullable = false)
    private String phone;

    @Column(name = "EMAIL", unique = true, nullable = false)
    private String email;

    @Column(name = "SEX")
    @Convert(converter = SexTypeConverter.class)
    private SexType sex;
}
