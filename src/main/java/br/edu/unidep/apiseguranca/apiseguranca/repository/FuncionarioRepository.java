package br.edu.unidep.apiseguranca.apiseguranca.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.unidep.apiseguranca.apiseguranca.model.Funcionario;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Long>{

}
