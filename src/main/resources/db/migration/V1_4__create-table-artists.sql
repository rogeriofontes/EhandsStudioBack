CREATE TABLE tb_artist (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    address VARCHAR(255),
    phone VARCHAR(20),
    email VARCHAR(255) NOT NULL UNIQUE,
    insta VARCHAR(100),
    face VARCHAR(100),
    image_url VARCHAR(255),
    whatsapp VARCHAR(20) NOT NULL,
    cpf varchar(20),
    category_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL UNIQUE,
    media_id BIGINT NOT NULL,
    status varchar(255) NOT NULL,
    create_by varchar(255) NOT NULL DEFAULT 'system_user',
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_modified_by VARCHAR(255),
    last_modified_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    foreign key (category_id) references tb_category(id),
    foreign key (user_id) references tb_user(id)
 );

