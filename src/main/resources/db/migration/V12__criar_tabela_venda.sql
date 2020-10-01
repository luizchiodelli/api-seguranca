CREATE TABLE venda(
	codigo_venda BIGINT(20) PRIMARY KEY NOT NULL AUTO_INCREMENT,
	codigo_cliente BIGINT(20),
	codigo_marca BIGINT(20),
	quantidade INTEGER,
	valor DOUBLE
)