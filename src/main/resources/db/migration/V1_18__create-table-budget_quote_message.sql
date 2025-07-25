CREATE TABLE tb_budget_quote_message (
    id BIGINT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    budget_id BIGINT NOT NULL,
    message TEXT NOT NULL,
    sent_by_customer BOOLEAN NOT NULL DEFAULT FALSE,
    sent_at TIMESTAMP NOT NULL,
    accept_personalization BOOLEAN NOT NULL DEFAULT FALSE,
    status varchar(255) NOT NULL DEFAULT 'ACTIVE',
    create_by varchar(255) NOT NULL DEFAULT 'system_user',
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_modified_by VARCHAR(255),
    last_modified_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
