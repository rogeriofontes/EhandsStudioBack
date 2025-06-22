CREATE TABLE tb_image (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    folder VARCHAR(1000),
    category_id BIGINT NOT NULL,
    artist_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    status varchar(255) NOT NULL,
    create_by varchar(255) NOT NULL DEFAULT 'system_user',
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_modified_by VARCHAR(255),
    last_modified_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    foreign key (category_id) references tb_category(id),
    foreign key (artist_id) references tb_artist(id),
    foreign key (product_id) references tb_product(id)
);

