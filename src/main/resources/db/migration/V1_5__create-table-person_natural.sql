CREATE TABLE tb_person_natural (
    id BIGINT PRIMARY KEY,
    document_number VARCHAR(255) NOT NULL,
    identify_number VARCHAR(255) NOT NULL,
    birth_date DATE NOT NULL,
    status varchar(255) NOT NULL DEFAULT 'ACTIVE',
    create_by varchar(255) NOT NULL DEFAULT 'system_user',
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_modified_by VARCHAR(255),
    last_modified_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
