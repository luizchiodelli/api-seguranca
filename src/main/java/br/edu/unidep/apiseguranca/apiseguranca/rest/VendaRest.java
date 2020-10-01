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

import br.edu.unidep.apiseguranca.apiseguranca.model.Venda;
import br.edu.unidep.apiseguranca.apiseguranca.repository.VendaRepository;

@RestController
@RequestMapping("/vendas")
public class VendaRest {

	@Autowired
	VendaRepository repositorio;
	
	@GetMapping("/ok")
	public String ok() {
		return "ok";
	}

	@GetMapping
	public List<Venda> listar() {
		return repositorio.findAll();
	}
	
	@GetMapping("/{codigo_venda}")
	public ResponseEntity<Venda> buscarPeloCodigo(
			@PathVariable Long codigo_venda) {
		Optional<Venda> venda = repositorio.findById(codigo_venda);
		
		return ResponseEntity.ok(venda.get());
	}
	
	@PostMapping
	public ResponseEntity<Venda> criar(@RequestBody Venda venda,
			HttpServletResponse response) {
		
		Venda vendaSalva = repositorio.save(venda);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().
				path("/{codigo}").buildAndExpand(
						vendaSalva.getCodigo()).toUri();
		
		response.setHeader("Location", uri.toASCIIString());
		
		return ResponseEntity.ok(vendaSalva);
	}
	
	@DeleteMapping("/{codigo_venda}")
	public void remover(@PathVariable Long codigo_venda) {
		Optional<Venda> venda = repositorio.findById(codigo_venda);
		if (venda.isPresent()) {
			repositorio.deleteById(codigo_venda);
		}
	}
	
	@PutMapping("/{codigo_venda}")
	public Venda alterar(@RequestBody Venda novaVenda,
			@PathVariable Long codigo_venda) {
		return repositorio.findById(codigo_venda)
			      .map(venda -> {
			    	  venda.setCliente(novaVenda.getCliente());
			    	  venda.setMarca(novaVenda.getMarca());
			    	  venda.setQuantidade(novaVenda.getQuantidade());
			    	  venda.setValor(novaVenda.getValor());
			        return repositorio.save(venda);
			      })
			      .orElseGet(() -> {
			    	  novaVenda.setCodigo(codigo_venda);
			        return repositorio.save(novaVenda);
			      });
	}
}
