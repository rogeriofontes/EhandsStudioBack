CREATE TABLE product_tags (
    product_id BIGINT NOT NULL,
    tag_id BIGINT NOT NULL,
    PRIMARY KEY (product_id, tag_id),
    FOREIGN KEY (product_id) REFERENCES tb_product(id) ON DELETE CASCADE,
    FOREIGN KEY (tag_id) REFERENCES tb_product_tag(id) ON DELETE CASCADE
);

