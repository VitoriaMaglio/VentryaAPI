CREATE SEQUENCE seq_feedback
    START WITH 1
    INCREMENT BY 1 NOCACHE;

CREATE TABLE feedback (
    id NUMBER(19,0) PRIMARY KEY,
    submission_id NUMBER(19,0) NOT NULL,
    comment VARCHAR2(2000 CHAR) NOT NULL,
    rating NUMBER(10,0),
    strengths VARCHAR2(2000 CHAR),
    improvements VARCHAR2(2000 CHAR),
    created_at TIMESTAMP(6) NOT NULL,

    CONSTRAINT fk_feedback_submission
        FOREIGN KEY (submission_id)
        REFERENCES submission(id),

    CONSTRAINT uk_feedback_submission
        UNIQUE (submission_id),

    CONSTRAINT ck_feedback_rating
        CHECK (rating BETWEEN 1 AND 5)
);
