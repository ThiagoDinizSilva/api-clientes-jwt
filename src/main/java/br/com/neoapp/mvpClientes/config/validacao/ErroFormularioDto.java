package br.com.neoapp.mvpClientes.config.validacao;

public class ErroFormularioDto {

	private String campo;
	private String erro;
	public ErroFormularioDto(String campo, String erro) {
		super();
		this.campo = campo;
		this.erro = erro;
	}
	
	//getters e setters
	public String getCampo() {
		return campo;
	}
	public String getErro() {
		return erro;
	}
	
	
}
