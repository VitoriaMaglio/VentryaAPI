CREATE SEQUENCE seq_ventrya_interview
    START WITH 1
    INCREMENT BY 1 NOCACHE;

CREATE TABLE ventrya_interview (
    id NUMBER(19,0) PRIMARY KEY,
    student_id NUMBER(19,0) NOT NULL,
    professional_area VARCHAR2(255 CHAR) NOT NULL,
    question VARCHAR2(2000 CHAR) NOT NULL,
    answer VARCHAR2(4000 CHAR),
    ai_feedback VARCHAR2(4000 CHAR),
    created_at TIMESTAMP(6) NOT NULL,

    CONSTRAINT fk_ventrya_interview_student
        FOREIGN KEY (student_id)
        REFERENCES ventrya_user(id)
);
