CREATE TABLE tb_customer (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL UNIQUE,
    person_id BIGINT NOT NULL,
    media_id BIGINT NOT NULL,
    status varchar(255) NOT NULL DEFAULT 'ACTIVE',
    create_by varchar(255) NOT NULL DEFAULT 'system_user',
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_modified_by VARCHAR(255),
    last_modified_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    foreign key (user_id) references tb_user(id)
);

