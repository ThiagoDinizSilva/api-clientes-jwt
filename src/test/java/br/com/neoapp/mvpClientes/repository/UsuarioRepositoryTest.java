package br.com.neoapp.mvpClientes.repository;

import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.neoapp.mvpClientes.modelo.Usuario;



@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UsuarioRepositoryTest {
	
	@Autowired
	private UsuarioRepository repository;
	
	private String nome = "teste";
	private String email = "teste@junit.com";
	private Usuario usuario = new Usuario(1L, nome, email, null);
	

	
	@Test
	public void DeveriaEncontrarUmUsuario() {
		
		repository.save(usuario);
		Optional<Usuario> usuario = repository.findByEmail(email);
		Assert.assertTrue(usuario.isPresent());
	}

}
