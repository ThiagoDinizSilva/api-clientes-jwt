package br.com.neoapp.mvpClientes.controller.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;

import org.springframework.data.domain.Page;

import br.com.neoapp.mvpClientes.modelo.Cliente;

public class ClienteDetalhadoDto implements Serializable {

	private static final long serialVersionUID = -6998851306069578874L;

	private String cpf;
	private String nomeCompleto;
	private LocalDate dataNascimento;
	private String email;
	private String contato;
	private int idade;

	public ClienteDetalhadoDto(Cliente cliente) {
		this.cpf = cliente.getCpf();
		this.nomeCompleto = cliente.getNomeCompleto();
		this.dataNascimento = cliente.getDataNascimento();
		this.email = cliente.getEmail();
		this.contato = cliente.getContato();
		this.idade = Period.between(cliente.getDataNascimento(), LocalDate.now()).getYears();
		System.out.println(getDataNascimento());

	}

	public String getCpf() {
		return cpf;
	}

	public String getNomeCompleto() {
		return nomeCompleto;
	}

	public LocalDate getDataNascimento() {
		return dataNascimento;
	}

	public String getEmail() {
		return email;
	}

	public String getContato() {
		return contato;
	}

	public int getIdade() {
		return idade;
	}

	public static Page<ClienteDto> converter(Page<Cliente> clientes) {
		return clientes.map(ClienteDto::new);
	}

}