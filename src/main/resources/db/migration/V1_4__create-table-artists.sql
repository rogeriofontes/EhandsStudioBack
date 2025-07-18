CREATE TABLE tb_artist (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    image_url VARCHAR(255),
    address VARCHAR(255),
    email VARCHAR(255) NOT NULL UNIQUE,
    phone VARCHAR(20),
    whatsapp VARCHAR(20) NOT NULL,
    cpf varchar(20),
    artist_category_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL UNIQUE,
    media_id BIGINT NOT NULL,
    status varchar(255) NOT NULL,
    create_by varchar(255) NOT NULL DEFAULT 'system_user',
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_modified_by VARCHAR(255),
    last_modified_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    foreign key (artist_category_id) references tb_artist_category(id),
    foreign key (user_id) references tb_user(id)
 );

