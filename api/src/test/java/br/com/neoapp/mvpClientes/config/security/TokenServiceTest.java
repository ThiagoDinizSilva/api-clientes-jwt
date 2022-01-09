package br.com.neoapp.mvpClientes.config.security;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.neoapp.mvpClientes.modelo.Usuario;



@RunWith(SpringRunner.class)
@SpringBootTest
public class TokenServiceTest {

	
	@Autowired
	private TokenService tokenService;
	
	@Test
	public void deveriaGerarUmTokenValido() {
		String gerarToken = tokenService.gerarToken(new Usuario(1L, "nome", "email@email.com", null));
		Assert.assertTrue(tokenService.ehValido(gerarToken));
		Assert.assertNotNull(gerarToken);
	}

}
