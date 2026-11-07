CREATE SEQUENCE seq_organization START WITH 1 INCREMENT BY 1 NOCACHE;
CREATE TABLE user(
                     id NUMBER(19,0) PRIMARY KEY,
                     name VARCHAR2(255 CHAR) NOT NULL,
                     email VARCHAR2(255 CHAR) NOT NULL,
                     description VARCHAR2(255 CHAR) NOT NULL,
                     web_Site VARCHAR2(255 CHAR) NOT NULL,
                     created_At TIMESTAMP(6) NOT NULL,

                     CONSTRAINT uk_users_email UNIQUE (email)
);