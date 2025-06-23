CREATE TABLE tb_customer (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    cpf varchar(20),
    address VARCHAR(255),
    phone VARCHAR(20),
    whatsapp VARCHAR(20) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    user_id BIGINT NOT NULL,
    media_id BIGINT NOT NULL,
    status varchar(255) NOT NULL,
    create_by varchar(255) NOT NULL DEFAULT 'system_user',
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_modified_by VARCHAR(255),
    last_modified_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    foreign key (user_id) references tb_user(id)
);

