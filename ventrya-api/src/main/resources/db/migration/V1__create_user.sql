CREATE SEQUENCE seq_ventrya_users START WITH 1 INCREMENT BY 1 NOCACHE;
CREATE TABLE ventrya_user(
                     id NUMBER(19,0) PRIMARY KEY,
                     name VARCHAR2(255 CHAR) NOT NULL,
                     email VARCHAR2(255 CHAR) NOT NULL,
                     password VARCHAR2(255 CHAR) NOT NULL,
                     professional_area VARCHAR2(255 CHAR) NOT NULL,
                     created_At TIMESTAMP(6) NOT NULL,
                     role VARCHAR2(255 CHAR) NOT NULL,

                     CONSTRAINT uk_users_email UNIQUE (email)

);