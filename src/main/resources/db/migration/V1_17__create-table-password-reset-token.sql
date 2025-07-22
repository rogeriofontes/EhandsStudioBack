CREATE TABLE tb_password_reset_token (
    id BIGINT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    user_id BIGINT NOT NULL,
    token VARCHAR(255) NOT NULL UNIQUE,
    expiry_date TIMESTAMP NOT NULL,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    status varchar(255) NOT NULL DEFAULT 'ACTIVE',
    create_by varchar(255) NOT NULL DEFAULT 'system_user',
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_modified_by VARCHAR(255),
    last_modified_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
