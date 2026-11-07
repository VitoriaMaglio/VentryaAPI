CREATE SEQUENCE seq_application
    START WITH 1
    INCREMENT BY 1 NOCACHE;

CREATE TABLE application (
    id NUMBER(19,0) PRIMARY KEY,
    student_id NUMBER(19,0) NOT NULL,
    challenge_id NUMBER(19,0) NOT NULL,
    status VARCHAR2(255 CHAR) NOT NULL,
    applied_at TIMESTAMP(6) NOT NULL,

    CONSTRAINT fk_application_student
        FOREIGN KEY (student_id)
        REFERENCES users(id),

    CONSTRAINT fk_application_challenge
        FOREIGN KEY (challenge_id)
        REFERENCES challenge(id),

    CONSTRAINT uk_application_student_challenge
        UNIQUE (student_id, challenge_id)
);