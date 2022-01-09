package br.com.neoapp.mvpClientes.controller.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;

import org.springframework.data.domain.Page;

import br.com.neoapp.mvpClientes.modelo.Cliente;

public class ClienteDto implements Serializable {

	private static final long serialVersionUID = -1340073394877843260L;
	private String nomeCompleto;
	private int idade;
	private String email;
	private String cpf;

	public ClienteDto(Cliente cliente) {
		this.nomeCompleto = cliente.getNomeCompleto();
		this.email = cliente.getEmail();
		this.idade = Period.between(cliente.getDataNascimento(), LocalDate.now()).getYears();

		this.cpf = cliente.getCpf();
	}

	public String getNomeCompleto() {
		return nomeCompleto;
	}

	public int getIdade() {
		return idade;
	}

	public String getEmail() {
		return email;
	}

	public String getCpf() {
		return cpf;
	}

	public static Page<ClienteDto> converter(Page<Cliente> clientes) {
		return clientes.map(ClienteDto::new);
	}

}
