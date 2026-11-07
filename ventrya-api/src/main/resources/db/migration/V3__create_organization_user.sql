CREATE TABLE ventrya_organization_user (
    organization_id NUMBER(19,0) NOT NULL,
    user_id NUMBER(19,0) NOT NULL,

    CONSTRAINT pk_ventrya_organization_user
        PRIMARY KEY (organization_id, user_id),

    CONSTRAINT fk_ventrya_org_user_organization
        FOREIGN KEY (organization_id)
        REFERENCES ventrya_organization(id),

    CONSTRAINT fk_ventrya_org_user_user
        FOREIGN KEY (user_id)
        REFERENCES ventrya_user(id)
);