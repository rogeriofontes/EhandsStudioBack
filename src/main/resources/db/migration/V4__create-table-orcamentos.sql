CREATE TABLE orcamentos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    data_orcamento DATETIME(10),
    imagem_url VARCHAR(255),
    status VARCHAR(255),
    cliente_id BIGINT,
    produtos_id BIGINT
);