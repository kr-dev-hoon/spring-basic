-- Create Database
CREATE DATABASE basic default CHARACTER SET UTF8;

-- Create User
CREATE USER 'hoon'@'%' IDENTIFIED BY 'password';

-- Add Grant
GRANT ALL PRIVILEGES ON hoon.* TO 'hoon'@'%';

-- TODO : FK 설정 추가하기.

-- Create Account Table
CREATE TABLE ACCOUNT
(
    ID           INT          NOT NULL AUTO_INCREMENT,
    NAME         VARCHAR(20)  UNIQUE NOT NULL,
    NICKNAME     VARCHAR(30)  NOT NULL,
    PASSWORD     VARCHAR(200) NOT NULL,
    COUNTRY_CODE INT          NOT NULL,
    PHONE        VARCHAR(20)  NOT NULL,
    EMAIL        VARCHAR(100) NOT NULL,
    SEX          VARCHAR(1) CHECK (SEX IN ('M', 'F')) COMMENT 'M : MAIL, F : FEMAIL',
    PRIMARY KEY (ID),
    INDEX IDX_ACCOUNT_EMAIL (EMAIL),
    INDEX IDX_ACCOUNT_NAME (NAME)
) ENGINE = InnoDB
  CHARSET = utf8mb4;

-- Create Orders Table
CREATE TABLE ORDERS
(
    ID           INT         NOT NULL AUTO_INCREMENT,
    ACCOUNT_ID   INT         NOT NULL,
    PRODUCT_ID   INT         NOT NULL,
    ORDER_TICKET VARCHAR(12) NOT NULL UNIQUE,
    CREATED_AT   TIMESTAMP   NOT NULL,
    PRIMARY KEY (ID),
    INDEX IDX_ORDER_DATE (CREATED_AT),
    INDEX IDX_ORDER_ID (ORDER_TICKET)
) ENGINE = InnoDB
  CHARSET = utf8mb4;

-- INSERT ACCOUNT

INSERT INTO ACCOUNT(NAME, NICKNAME, PASSWORD, COUNTRY_CODE, PHONE, EMAIL, SEX)
VALUES ('BREAD', '10.5'),
       ('COOKIE', '8'),
       ('WATER', '5'),
       ('PHONE', '101'),
       ('RICE', '10');

-- INSERT Product

INSERT INTO PRODUCT(PRODUCT_NAME, PRICE)
VALUES ('BREAD', '10.5'),
       ('COOKIE', '8'),
       ('WATER', '5'),
       ('PHONE', '101'),
       ('RICE', '10'),
       ('😜😀😊😃', '5.8');