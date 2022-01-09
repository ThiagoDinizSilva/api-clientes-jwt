package br.com.neoapp.mvpClientes.repository;

import java.time.LocalDate;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.neoapp.mvpClientes.modelo.Cliente;
import org.junit.Assert;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ClienteRepositoryTest {

	@Autowired
	private ClienteRepository repository;

	private String nome = "teste";
	private String cpf = "00000000000";
	private String email = "teste@junit.com";
	private String contato = "12345678";
	Cliente cliente1 = new Cliente(cpf, nome, LocalDate.of(1990, 01, 01), email, contato);

	@Test
	public void DeveriaAdicionarUmCliente() {
		Cliente clientes = repository.save(cliente1);
		System.out.println(clientes);
	}

	@Test
	public void DeveriaBuscarClientesUltilizandoParametros() {
		repository.save(cliente1);
		Page<Cliente> buscandoClientePorParametros = repository.FindByOptionalParams(nome, cpf, email, null);
		System.out.println(buscandoClientePorParametros.getContent());
		Assert.assertNotNull(buscandoClientePorParametros.getContent());

		Page<Cliente> buscandoClientePorNomeSemCaseSensitive = repository.findByNomeCompletoIgnoreCaseContaining(nome, null);
		Assert.assertFalse(buscandoClientePorNomeSemCaseSensitive.getContent().isEmpty());

	}


}
