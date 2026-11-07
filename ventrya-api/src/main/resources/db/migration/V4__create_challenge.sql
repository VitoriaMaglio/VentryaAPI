CREATE SEQUENCE seq_ventrya_challenge START WITH 1 INCREMENT BY 1 NOCACHE;
CREATE TABLE ventrya_challenge(
    id NUMBER(19,0) PRIMARY KEY,
    title VARCHAR2(255 CHAR) NOT NULL,
    description VARCHAR2(2000 CHAR) NOT NULL,
    professional_area VARCHAR2(255 CHAR) NOT NULL,
    difficulty VARCHAR2(255 CHAR) NOT NULL,
    requirements VARCHAR2(2000 CHAR),
    dead_line DATE NOT NULL,
    status VARCHAR2(255 CHAR) NOT NULL,
    created_at TIMESTAMP(6) NOT NULL,
    organization_id NUMBER(19,0) NOT NULL,

    CONSTRAINT fk_ventrya_challenge_organization
        FOREIGN KEY (organization_id)
        REFERENCES ventrya_organization(id)
);