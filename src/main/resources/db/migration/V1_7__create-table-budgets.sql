CREATE TABLE tb_budget (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    budget_status VARCHAR(255),
    date_budget DATETIME,
    image_url VARCHAR(255),
    description TEXT,
    response TEXT,
    customer_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    artist_id BIGINT NOT NULL,
    media_id BIGINT NOT NULL,
    status varchar(255) NOT NULL,
    create_by varchar(255) NOT NULL DEFAULT 'system_user',
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_modified_by VARCHAR(255),
    last_modified_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    foreign key (customer_id) references tb_customer(id),
    foreign key (product_id) references tb_product(id),
    foreign key (artist_id) references tb_artist(id)
);


