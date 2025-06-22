CREATE TABLE tb_product (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(255),
    size VARCHAR(50),
    image_url VARCHAR(255),
    price DECIMAL(10, 2),
    category_id BIGINT NOT NULL,
    artist_id BIGINT NOT NULL,
    status varchar(255) NOT NULL,
    create_by varchar(255) NOT NULL DEFAULT 'system_user',
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_modified_by VARCHAR(255),
    last_modified_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    foreign key (category_id) references tb_category(id),
    foreign key (artist_id) references tb_artist(id)
);

