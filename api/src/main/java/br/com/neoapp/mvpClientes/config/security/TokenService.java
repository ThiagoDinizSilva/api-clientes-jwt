package br.com.neoapp.mvpClientes.config.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import br.com.neoapp.mvpClientes.modelo.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class TokenService {

	@Value("${api.jwt.expiration}")
	private String expiration;

	@Value("${api.jwt.secret}")
	private String secret;

	public String gerarToken(Usuario logado) {

		Date agora = new Date();
		Date exp = new Date(agora.getTime() + Long.parseLong(expiration));

		return Jwts.builder().setIssuer("neoappMvpClientes")
				.setSubject(logado.getId().toString())
				.setIssuedAt(agora)
				.setExpiration(exp)
				.signWith(SignatureAlgorithm.HS256, secret)
				.compact();
	}

	public boolean ehValido(String token) {
		try {
			Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token);
			return true;			
		} catch (Exception e) {
			return false;
		}
	}

	public Long getIdUsuario(String token) {
		Claims usuario = Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token).getBody();
		return Long.parseLong(usuario.getSubject());
		
	}


}
