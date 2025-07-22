CREATE TABLE tb_person_legal (
    id BIGINT PRIMARY KEY,
    document_number VARCHAR(255) NOT NULL,
    company_name VARCHAR(255) NOT NULL,
    fantasy_name VARCHAR(255) NOT NULL,
    state_registration VARCHAR(10) NOT NULL,
    municipal_registration VARCHAR(255) NOT NULL,
    legal_representative VARCHAR(255) NOT NULL,
    legal_representative_phone VARCHAR(20) NOT NULL,
    status varchar(255) NOT NULL DEFAULT 'ACTIVE',
    create_by varchar(255) NOT NULL DEFAULT 'system_user',
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_modified_by VARCHAR(255),
    last_modified_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
