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

import br.edu.unidep.apiseguranca.apiseguranca.model.Marca;
import br.edu.unidep.apiseguranca.apiseguranca.repository.MarcaRepository;

@RestController
@RequestMapping("/marcas")
public class MarcaRest {
	
	@Autowired
	MarcaRepository repositorio;
	
	@GetMapping("/ok")
	public String ok() {
		return "ok";
	}

	@GetMapping
	public List<Marca> listar() {
		return repositorio.findAll();
	}
	
//	@GetMapping(path = {"/{id}"})
//	public ResponseEntity buscarPorId(@PathVariable("id") Long id) {
//		return ResponseEntity.ok(repositorio.findById(id));
//	}
	
	@GetMapping("/{codigo_marca}")
	public ResponseEntity<Marca> buscarPeloCodigo(
			@PathVariable Long codigo_marca) {
		Optional<Marca> marca = repositorio.findById(codigo_marca);
		
		return ResponseEntity.ok(marca.get());
	}
	
	@PostMapping
	public ResponseEntity<Marca> criar(@RequestBody Marca marca,
			HttpServletResponse response) {
		
		Marca marcaSalva = repositorio.save(marca);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().
				path("/{codigo}").buildAndExpand(
						marcaSalva.getCodigo()).toUri();
		
		response.setHeader("Location", uri.toASCIIString());
		
		return ResponseEntity.ok(marcaSalva);
	}
	
	@DeleteMapping("/{codigo_marca}")
	public void remover(@PathVariable Long codigo_marca) {
		Optional<Marca> marca = repositorio.findById(codigo_marca);
		if (marca.isPresent()) {
			repositorio.deleteById(codigo_marca);
		}
	}
	
	@PutMapping("/{codigo_marca}")
	public Marca alterar(@RequestBody Marca novaMarca,
			@PathVariable Long codigo_marca) {
		return repositorio.findById(codigo_marca)
			      .map(marca -> {
			        marca.setNome(novaMarca.getNome());
			        marca.setFornecedor(novaMarca.getFornecedor());
			        return repositorio.save(marca);
			      })
			      .orElseGet(() -> {
			        novaMarca.setCodigo(codigo_marca);
			        return repositorio.save(novaMarca);
			      });
	}
	
}
