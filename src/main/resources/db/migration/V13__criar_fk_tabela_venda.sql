ALTER TABLE venda ADD CONSTRAINT fk_cliente_venda
FOREIGN KEY(codigo_cliente) REFERENCES cliente(codigo_cliente);

ALTER TABLE venda ADD CONSTRAINT fk_marca_venda
FOREIGN KEY(codigo_marca) REFERENCES marca(codigo_marca);
