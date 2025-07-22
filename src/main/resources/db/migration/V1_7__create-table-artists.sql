CREATE TABLE tb_artist (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    image_url VARCHAR(255),
    artist_category_id BIGINT NOT NULL,
    person_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL UNIQUE,
    media_id BIGINT NOT NULL,
    status varchar(255) NOT NULL DEFAULT 'ACTIVE',
    create_by varchar(255) NOT NULL DEFAULT 'system_user',
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_modified_by VARCHAR(255),
    last_modified_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    foreign key (artist_category_id) references tb_artist_category(id),
    foreign key (user_id) references tb_user(id)
 );

