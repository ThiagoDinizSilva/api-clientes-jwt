package br.com.neoapp.mvpClientes.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.neoapp.mvpClientes.config.security.TokenService;
import br.com.neoapp.mvpClientes.controller.dto.TokenDto;
import br.com.neoapp.mvpClientes.controller.form.LoginForm;
import br.com.neoapp.mvpClientes.modelo.Usuario;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(value = "Autenticacao")
@RequestMapping("/auth")
public class AutenticacaoController {

	@Autowired
	private AuthenticationManager authManager;

	@Autowired
	private TokenService tokenService;

	@ApiOperation(value = "Autentica um usuário")
	@PostMapping
	public ResponseEntity<TokenDto> autenticar(@RequestBody @Valid LoginForm formulario) {
		UsernamePasswordAuthenticationToken dadosLogin = formulario.converter();
		try {

			Authentication autenticado = authManager.authenticate(dadosLogin);
			Usuario logado = (Usuario) autenticado.getPrincipal();
			String token = tokenService.gerarToken(logado);

			return ResponseEntity.ok(new TokenDto(token, "Bearer"));

		} catch (AuthenticationException e) {
			return ResponseEntity.badRequest().build();
		}

	}

}
