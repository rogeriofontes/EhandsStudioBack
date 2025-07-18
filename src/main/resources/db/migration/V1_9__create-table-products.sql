CREATE TABLE tb_product (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    code VARCHAR(50) NOT NULL UNIQUE,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(255),
    technical_data TEXT,
    size VARCHAR(50),
    image_url VARCHAR(255),
    price DECIMAL(10, 2),
    discount INT DEFAULT 0,
    product_category_id BIGINT NOT NULL,
    artist_id BIGINT NOT NULL,
    media_id BIGINT NOT NULL,
    status varchar(255) NOT NULL,
    create_by varchar(255) NOT NULL DEFAULT 'system_user',
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_modified_by VARCHAR(255),
    last_modified_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    foreign key (product_category_id) references tb_product_category(id),
    foreign key (artist_id) references tb_artist(id)
);

