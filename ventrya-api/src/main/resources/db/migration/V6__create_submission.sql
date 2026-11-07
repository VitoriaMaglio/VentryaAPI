CREATE SEQUENCE seq_ventrya_submission
    START WITH 1
    INCREMENT BY 1 NOCACHE;

CREATE TABLE ventrya_submission (
    id NUMBER(19,0) PRIMARY KEY,
    application_id NUMBER(19,0) NOT NULL,
    description VARCHAR2(2000 CHAR) NOT NULL,
    file_url VARCHAR2(255 CHAR),
    status VARCHAR2(255 CHAR) NOT NULL,
    submitted_at TIMESTAMP(6) NOT NULL,

    CONSTRAINT fk_ventrya_submission_application
        FOREIGN KEY (application_id)
        REFERENCES ventrya_application(id),

    CONSTRAINT uk_ventrya_submission_application
        UNIQUE (application_id)
);
