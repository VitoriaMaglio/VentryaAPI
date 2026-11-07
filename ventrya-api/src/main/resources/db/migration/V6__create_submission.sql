CREATE SEQUENCE seq_submission
    START WITH 1
    INCREMENT BY 1 NOCACHE;

CREATE TABLE submission (
    id NUMBER(19,0) PRIMARY KEY,
    application_id NUMBER(19,0) NOT NULL,
    description VARCHAR2(2000 CHAR) NOT NULL,
    file_url VARCHAR2(255 CHAR),
    status VARCHAR2(255 CHAR) NOT NULL,
    submitted_at TIMESTAMP(6) NOT NULL,

    CONSTRAINT fk_submission_application
        FOREIGN KEY (application_id)
        REFERENCES application(id),

    CONSTRAINT uk_submission_application
        UNIQUE (application_id)
);
