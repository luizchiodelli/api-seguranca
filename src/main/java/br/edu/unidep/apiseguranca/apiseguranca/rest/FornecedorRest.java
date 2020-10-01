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

import br.edu.unidep.apiseguranca.apiseguranca.model.Fornecedor;
import br.edu.unidep.apiseguranca.apiseguranca.repository.FornecedorRepository;

@RestController
@RequestMapping("/fornecedores")
public class FornecedorRest {

	@Autowired
	FornecedorRepository repositorio;
	
	@GetMapping("/ok")
	public String ok() {
		return "ok";
	}

	@GetMapping
	public List<Fornecedor> listar() {
		return repositorio.findAll();
	}
	
	@GetMapping("/{codigo_fornecedor}")
	public ResponseEntity<Fornecedor> buscarPeloCodigo(
			@PathVariable Long codigo_fornecedor) {
		Optional<Fornecedor> fornecedor = repositorio.findById(codigo_fornecedor);
		
		return ResponseEntity.ok(fornecedor.get());
	}
	
	@PostMapping
	public ResponseEntity<Fornecedor> criar(@RequestBody Fornecedor fornecedor,
			HttpServletResponse response) {
		
		Fornecedor fornecedorSalva = repositorio.save(fornecedor);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().
				path("/{codigo}").buildAndExpand(
						fornecedorSalva.getCodigo()).toUri();
		
		response.setHeader("Location", uri.toASCIIString());
		
		return ResponseEntity.ok(fornecedorSalva);
	}
	
	@DeleteMapping("/{codigo_fornecedor}")
	public void remover(@PathVariable Long codigo_fornecedor) {
		Optional<Fornecedor> fornecedor = repositorio.findById(codigo_fornecedor);
		if (fornecedor.isPresent()) {
			repositorio.deleteById(codigo_fornecedor);
		}
	}
	
	@PutMapping("/{codigo_fornecedor}")
	public Fornecedor alterar(@RequestBody Fornecedor novoFornecedor,
			@PathVariable Long codigo_fornecedor) {
		return repositorio.findById(codigo_fornecedor)
			      .map(fornecedor -> {
			    	fornecedor.setNome(novoFornecedor.getNome());
			    	fornecedor.setCnpj(novoFornecedor.getCnpj());
			    	fornecedor.setCidade(novoFornecedor.getCidade());
			    	fornecedor.setEstado(novoFornecedor.getEstado());
			        return repositorio.save(fornecedor);
			      })
			      .orElseGet(() -> {
			        novoFornecedor.setCodigo(codigo_fornecedor);
			        return repositorio.save(novoFornecedor);
			      });
	}
}
