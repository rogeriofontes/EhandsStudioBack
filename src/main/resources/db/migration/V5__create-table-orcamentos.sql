CREATE TABLE orcamentos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    data_orcamento DATETIME,
    imagem_url VARCHAR(255),
    statuss VARCHAR(255),
    clientes_id BIGINT,
    produtos_id BIGINT
);