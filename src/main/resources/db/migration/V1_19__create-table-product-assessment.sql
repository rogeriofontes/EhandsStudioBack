CREATE TABLE tb_product_assessment (
    id BIGINT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    name VARCHAR(255) NOT NULL,
    rating INT NOT NULL CHECK (rating >= 1 AND rating <= 5),
    customer_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    message TEXT,
    sent_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status varchar(255) NOT NULL DEFAULT 'ACTIVE',
    create_by varchar(255) NOT NULL DEFAULT 'system_user',
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_modified_by VARCHAR(255),
    last_modified_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
