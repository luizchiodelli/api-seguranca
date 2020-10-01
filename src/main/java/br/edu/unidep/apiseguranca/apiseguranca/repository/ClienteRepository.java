package br.edu.unidep.apiseguranca.apiseguranca.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.unidep.apiseguranca.apiseguranca.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

}
