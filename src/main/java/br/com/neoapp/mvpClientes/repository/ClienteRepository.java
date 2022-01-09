package br.com.neoapp.mvpClientes.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.neoapp.mvpClientes.modelo.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
	
	void deleteByCpf(String id);

	Optional<Cliente> findByCpf(String cpf);

	Page<Cliente> findByNomeCompletoIgnoreCaseContaining(String nome, Pageable paginacao);
	
	@Query("SELECT c FROM Cliente c WHERE (:nomeCompleto is null or c.nomeCompleto LIKE '%'|| cast(:nomeCompleto as text) || '%') "
			+ "and(:cpf is null or c.cpf = :cpf) "
			+ "and(:email is null or c.email = :email)")
	Page<Cliente> FindByOptionalParams(@Param("nomeCompleto") String nomeCompleto, @Param("cpf") String cpf,@Param("email") String email, Pageable paginacao);

}
