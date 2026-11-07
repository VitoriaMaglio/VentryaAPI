CREATE TABLE organization_user(
    organization_id NUMBER(19,0) NOT NULL,
    user_id NUMBER(19,0) NOT NULL,

    CONSTRAINT pk_organization_user
        PRIMARY KEY (organization_id, user_id),

    CONSTRAINT fk_org_user_organization
        FOREIGN KEY (organization_id)
        REFERENCES organizations(id),

    CONSTRAINT fk_org_user_user
        FOREIGN KEY (user_id)
        REFERENCES user(id)
);
