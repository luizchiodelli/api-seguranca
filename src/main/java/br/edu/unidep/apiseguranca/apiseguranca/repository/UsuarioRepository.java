package br.edu.unidep.apiseguranca.apiseguranca.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.unidep.apiseguranca.apiseguranca.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

}
