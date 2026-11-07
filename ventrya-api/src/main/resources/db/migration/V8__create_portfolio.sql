CREATE SEQUENCE seq_ventrya_portfolio
    START WITH 1
    INCREMENT BY 1 NOCACHE;

CREATE TABLE ventrya_portfolio (
    id NUMBER(19,0) PRIMARY KEY,
    student_id NUMBER(19,0) NOT NULL,
    submission_id NUMBER(19,0) NOT NULL,
    title VARCHAR2(255 CHAR) NOT NULL,
    generated_description VARCHAR2(4000 CHAR),
    skills VARCHAR2(2000 CHAR),
    status VARCHAR2(255 CHAR) NOT NULL,
    created_at TIMESTAMP(6) NOT NULL,

    CONSTRAINT fk_ventrya_portfolio_student
        FOREIGN KEY (student_id)
        REFERENCES ventrya_user(id),

    CONSTRAINT fk_ventrya_portfolio_submission
        FOREIGN KEY (submission_id)
        REFERENCES ventrya_submission(id),

    CONSTRAINT uk_ventrya_portfolio_submission
        UNIQUE (submission_id)
);
