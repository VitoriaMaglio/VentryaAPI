CREATE SEQUENCE seq_ventrya_organization
START WITH 1
INCREMENT BY 1
NOCACHE;

CREATE TABLE ventrya_organization (
    id NUMBER(19,0) PRIMARY KEY,
    name VARCHAR2(255 CHAR) NOT NULL,
    email VARCHAR2(255 CHAR) NOT NULL,
    description VARCHAR2(2000 CHAR) NOT NULL,
    web_site VARCHAR2(255 CHAR),
    created_at TIMESTAMP(6) NOT NULL,

    CONSTRAINT uk_ventrya_organization_email UNIQUE (email)
);