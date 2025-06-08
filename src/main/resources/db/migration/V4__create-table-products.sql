CREATE TABLE products (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(255),
    size VARCHAR(255),
    image_url VARCHAR(255),
    price DECIMAL(10, 2),
    category_id BIGINT,
    artist_id BIGINT
);