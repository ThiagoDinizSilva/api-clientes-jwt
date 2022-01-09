package br.com.neoapp.mvpClientes.modelo;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Cliente {

	@Id
	@Column(length = 11, unique = true)
	private String cpf;
	
	private String nomeCompleto;

	@Column(columnDefinition = "DATE")
	private LocalDate dataNascimento;

	private String email;
	
	private String contato;

	public Cliente() {
	}

	public Cliente(String cpf, String nomeCompleto, LocalDate dataNascimento, String email, String contato) {

		// string.replaceAll no cpf com regex pra remover qualquer coisa que não sejam
		// números
		this.cpf = cpf.replaceAll("\\D", "");
		this.nomeCompleto = nomeCompleto;
		this.dataNascimento = dataNascimento;
		this.email = email;
		this.contato = contato;
	}

	public String getCpf() {
		return cpf;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getContato() {
		return contato;
	}

	public void setContato(String contato) {
		this.contato = contato;
	}

	


}
