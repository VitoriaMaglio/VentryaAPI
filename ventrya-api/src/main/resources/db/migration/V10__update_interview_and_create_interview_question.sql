-- 1. Atualizar a tabela de entrevistas

ALTER TABLE ventrya_interview ADD (
    status VARCHAR2(30) DEFAULT 'IN_PROGRESS' NOT NULL,
    overall_feedback VARCHAR2(4000),
    completed_at TIMESTAMP
);

-- 2. Remover os campos que agora pertencem às perguntas

ALTER TABLE ventrya_interview DROP (
    question,
    answer,
    ai_feedback
);

-- 3. Criar a sequence da nova entidade

CREATE SEQUENCE seq_ventrya_interview_question
    START WITH 1
    INCREMENT BY 1
    NOCACHE
    NOCYCLE;

-- 4. Criar a tabela de perguntas

CREATE TABLE ventrya_interview_question (
    id NUMBER(19) NOT NULL,
    interview_id NUMBER(19) NOT NULL,
    question_number NUMBER(10) NOT NULL,
    question VARCHAR2(2000) NOT NULL,
    answer VARCHAR2(4000),
    ai_feedback VARCHAR2(4000),

    CONSTRAINT pk_ventrya_interview_question
        PRIMARY KEY (id),

    CONSTRAINT fk_ventrya_interview_question
        FOREIGN KEY (interview_id)
        REFERENCES ventrya_interview(id),

    CONSTRAINT uk_interview_question_number
        UNIQUE (interview_id, question_number),

    CONSTRAINT ck_interview_question_number
        CHECK (question_number BETWEEN 1 AND 5)
);