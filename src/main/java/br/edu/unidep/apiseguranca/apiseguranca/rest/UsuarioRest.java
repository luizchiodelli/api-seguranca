package br.edu.unidep.apiseguranca.apiseguranca.rest;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.edu.unidep.apiseguranca.apiseguranca.model.Usuario;
import br.edu.unidep.apiseguranca.apiseguranca.repository.UsuarioRepository;

@RestController
@RequestMapping("/usuarios")
public class UsuarioRest {

	@Autowired
	UsuarioRepository repositorio;
	
	@GetMapping("/ok")
	public String ok() {
		return "ok";
	}

	@GetMapping
	public List<Usuario> listar() {
		return repositorio.findAll();
	}
	
	@GetMapping("/{codigo_usuario}")
	public ResponseEntity<Usuario> buscarPeloCodigo(
			@PathVariable Long codigo_usuario) {
		Optional<Usuario> usuario = repositorio.findById(codigo_usuario);
		
		return ResponseEntity.ok(usuario.get());
	}
	
	@PostMapping
	public ResponseEntity<Usuario> criar(@RequestBody Usuario usuario,
			HttpServletResponse response) {
		
		Usuario usuarioSalva = repositorio.save(usuario);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().
				path("/{codigo}").buildAndExpand(
						usuarioSalva.getCodigo()).toUri();
		
		response.setHeader("Location", uri.toASCIIString());
		
		return ResponseEntity.ok(usuarioSalva);
	}
	
	@DeleteMapping("/{codigo_usuario}")
	public void remover(@PathVariable Long codigo_usuario) {
		Optional<Usuario> usuario = repositorio.findById(codigo_usuario);
		if (usuario.isPresent()) {
			repositorio.deleteById(codigo_usuario);
		}
	}
	
	@PutMapping("/{codigo_usuario}")
	public Usuario alterar(@RequestBody Usuario novoUsuario,
			@PathVariable Long codigo_usuario) {
		return repositorio.findById(codigo_usuario)
			      .map(usuario -> {
			    	  usuario.setNome(novoUsuario.getNome());
			    	  usuario.setEmail(novoUsuario.getEmail());
			    	  usuario.setSenha(novoUsuario.getSenha());
			    	  usuario.setTipoUsuario(novoUsuario.getTipoUsuario());
			    	  usuario.setFuncionario(novoUsuario.getFuncionario());
			        return repositorio.save(usuario);
			      })
			      .orElseGet(() -> {
			        novoUsuario.setCodigo(codigo_usuario);
			        return repositorio.save(novoUsuario);
			      });
	}
}
