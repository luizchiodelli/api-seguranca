ALTER TABLE marca ADD CONSTRAINT fk_fornecedor_marca 
FOREIGN KEY(codigo_fornecedor) REFERENCES fornecedor(codigo_fornecedor)