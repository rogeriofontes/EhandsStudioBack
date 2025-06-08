CREATE TABLE budgets (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    data_budget DATETIME,
    image_url VARCHAR(255),
    status VARCHAR(255),
    customers_id BIGINT,
    products_id BIGINT
);
