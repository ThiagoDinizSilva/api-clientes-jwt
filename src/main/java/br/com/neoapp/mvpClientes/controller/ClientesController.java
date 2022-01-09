package br.com.neoapp.mvpClientes.controller;

import java.net.URI;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.neoapp.mvpClientes.controller.dto.ClienteDetalhadoDto;
import br.com.neoapp.mvpClientes.controller.dto.ClienteDto;
import br.com.neoapp.mvpClientes.controller.form.ClienteForm;
import br.com.neoapp.mvpClientes.modelo.Cliente;
import br.com.neoapp.mvpClientes.repository.ClienteRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(value = "Autenticacao")
@RequestMapping("/clientes")
public class ClientesController {

	@Autowired
	private ClienteRepository clienteRepository;

	@GetMapping
	@ApiOperation(value = "Lista os usuários")
	public Page<ClienteDto> listarClientes(@RequestParam(required = false) String nome,
			@RequestParam(required = false) String cpf, @RequestParam(required = false) String email,
			@PageableDefault(sort = "nomeCompleto", direction = Direction.DESC) Pageable paginacao) {

		if (nome == null && cpf == null && email == null) {
			Page<Cliente> clientes = clienteRepository.findAll(paginacao);

			return ClienteDto.converter(clientes);

		} else {
			System.out.println("nome: " + nome + ";" + "cpf: " + cpf + ";" + "email: " + email + ";");
			Page<Cliente> clientes = clienteRepository.FindByOptionalParams(nome, cpf, email, paginacao);
			System.out.println(clientes);
			return ClienteDto.converter(clientes);
		}
	}

	@GetMapping("/{id}")
	@ApiOperation(value = "Traz Detalhes do Usuario")
	public ResponseEntity<ClienteDetalhadoDto> detalharCliente(@PathVariable String id) {
		Optional<Cliente> cliente = clienteRepository.findByCpf(id);
		if (cliente.isPresent()) {

			return ResponseEntity.ok(new ClienteDetalhadoDto(cliente.get()));
		}

		return ResponseEntity.notFound().build();
	}

	@PostMapping
	@ApiOperation(value = "Adiciona um usuário")
	public ResponseEntity<?> cadastrar(@RequestBody @Valid ClienteForm formulario,
			UriComponentsBuilder uriBuilder) {
		Cliente cliente = formulario.converter(clienteRepository);
		if(clienteRepository.findByCpf(cliente.getCpf()).isPresent()) {
			return ResponseEntity.badRequest().body("Este CPF já foi cadastrado anteriormente");
		}
		
		clienteRepository.save(cliente);
		URI uri = uriBuilder.path("/clientes/{id}").buildAndExpand(cliente.getCpf()).toUri();
		return ResponseEntity.created(uri).body(new ClienteDto(cliente));

	}

	@PutMapping("/{id}")
	@ApiOperation(value = "Atualiza um usuário")
	@Transactional
	public ResponseEntity<ClienteDto> atualizar(@PathVariable String id, @RequestBody @Valid ClienteForm formulario) {
		Cliente cliente = formulario.atualizar(id, clienteRepository);

		return ResponseEntity.ok(new ClienteDto(cliente));
	}

	@DeleteMapping("/{id}")
	@ApiOperation(value = "Deleta um usuário")
	@Transactional
	public ResponseEntity<?> remover(@PathVariable String id) {
		Optional<Cliente> optional = clienteRepository.findByCpf(id);
		if (optional.isPresent()) {
			clienteRepository.deleteByCpf(id);
			return ResponseEntity.ok().build();
		}

		return ResponseEntity.notFound().build();
	}

}
