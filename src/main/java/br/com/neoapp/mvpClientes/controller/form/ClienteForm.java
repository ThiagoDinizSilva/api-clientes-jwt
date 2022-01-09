package br.com.neoapp.mvpClientes.controller.form;

import java.time.LocalDate;

import javax.validation.constraints.NotEmpty;

import br.com.neoapp.mvpClientes.modelo.Cliente;
import br.com.neoapp.mvpClientes.repository.ClienteRepository;

public class ClienteForm {

	@NotEmpty
	private String cpf;
	@NotEmpty
	private String email;
	@NotEmpty
	private String nomeCompleto;

	private LocalDate dataNascimento;
	private String contato;

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNomeCompleto() {
		return nomeCompleto;
	}

	public void setNomeCompleto(String nomeCompleto) {
		this.nomeCompleto = nomeCompleto;
	}

	public LocalDate getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getContato() {
		return contato;
	}

	public void setContato(String contato) {
		this.contato = contato;
	}

	public Cliente converter(ClienteRepository clienteRepository) {
		return new Cliente(cpf, nomeCompleto, dataNascimento, email, contato);
	}

	public Cliente atualizar(String id, ClienteRepository clienteRepository) {
		Cliente cliente = clienteRepository.findByCpf(id).get();
		cliente.setContato(this.contato);
		cliente.setDataNascimento(this.dataNascimento);
		cliente.setEmail(this.email);
		cliente.setNomeCompleto(this.nomeCompleto);
		
		return cliente;
	}

}
