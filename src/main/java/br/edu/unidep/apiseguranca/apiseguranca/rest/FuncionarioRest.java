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

import br.edu.unidep.apiseguranca.apiseguranca.model.Funcionario;
import br.edu.unidep.apiseguranca.apiseguranca.repository.FuncionarioRepository;

@RestController
@RequestMapping("/funcionarios")
public class FuncionarioRest {

	@Autowired
	FuncionarioRepository repositorio;
	
	@GetMapping("/ok")
	public String ok() {
		return "ok";
	}

	@GetMapping
	public List<Funcionario> listar() {
		return repositorio.findAll();
	}
	
	@GetMapping("/{codigo_funcionario}")
	public ResponseEntity<Funcionario> buscarPeloCodigo(
			@PathVariable Long codigo_funcionario) {
		Optional<Funcionario> funcionario = repositorio.findById(codigo_funcionario);
		
		return ResponseEntity.ok(funcionario.get());
	}
	
	@PostMapping
	public ResponseEntity<Funcionario> criar(@RequestBody Funcionario funcionario,
			HttpServletResponse response) {
		
		Funcionario funcionarioSalva = repositorio.save(funcionario);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().
				path("/{codigo}").buildAndExpand(
						funcionarioSalva.getCodigo()).toUri();
		
		response.setHeader("Location", uri.toASCIIString());
		
		return ResponseEntity.ok(funcionarioSalva);
	}
	
	@DeleteMapping("/{codigo_funcionario}")
	public void remover(@PathVariable Long codigo_funcionario) {
		Optional<Funcionario> funcionario = repositorio.findById(codigo_funcionario);
		if (funcionario.isPresent()) {
			repositorio.deleteById(codigo_funcionario);
		}
	}
	
	@PutMapping("/{codigo_funcionario}")
	public Funcionario alterar(@RequestBody Funcionario novoFuncionario,
			@PathVariable Long codigo_funcionario) {
		return repositorio.findById(codigo_funcionario)
			      .map(funcionario -> {
			    	  funcionario.setNome(novoFuncionario.getNome());
			    	  funcionario.setCpf(novoFuncionario.getCpf());
			    	  funcionario.setEndereco(novoFuncionario.getEndereco());
			    	  funcionario.setCidade(novoFuncionario.getCidade());
			    	  funcionario.setEstado(novoFuncionario.getEstado());
			    	  funcionario.setCargo(novoFuncionario.getCargo());
			    	  funcionario.setSalario(novoFuncionario.getSalario());
			        return repositorio.save(funcionario);
			      })
			      .orElseGet(() -> {
			        novoFuncionario.setCodigo(codigo_funcionario);
			        return repositorio.save(novoFuncionario);
			      });
	}
}
