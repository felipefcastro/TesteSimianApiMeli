package br.com.meli.domain.exception;

public class DnaNaoPermitidoException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public DnaNaoPermitidoException(String mensagem) {
		super(mensagem);
	}

}
