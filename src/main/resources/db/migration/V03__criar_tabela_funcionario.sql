CREATE TABLE funcionario(
	codigo_funcionario BIGINT(20) PRIMARY KEY NOT NULL AUTO_INCREMENT,
	nome VARCHAR(50) NOT NULL,
	cpf VARCHAR(11) NOT NULL,
	endereco VARCHAR(50) NOT NULL,
	cidade VARCHAR(50) NOT NULL,
	estado VARCHAR(2) NOT NULL,
	cargo VARCHAR(20) NOT NULL,
	salario DOUBLE NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;