package Model;

public enum Regra {

	G('G', "Para empregadores que tenham at� 10 funcion�rios haver� um acr�scimo de 18% sobre o valor total."),
	A('A', "O benef�cio s� ser� concedido para maiores de 18 anos."),
	B('B', "Para aposentados o valor do benef�cio ter� um acr�scimo de 30%."),
	R('R', "O Benef�cio ser� de 10 meses para empregadores com at� 10 funcion�rios."),
	L('L', "O benef�cio ser� de 6 meses para desempregados."),
	U('U', "O Benef�cio ser� de 3 meses para empregados."),
	Z('Z', "O benef�cio de pessoas que moram no Paran� ter� acr�scimo de 9%."),
	N('N', "O benef�cio de pessoas que moram em Santa Catarina ter� acr�scimo de 5%;");
	
	private char letra;
	private String descricao;
	
	private Regra(char letra, String descricao) {
		this.letra = letra;
		this.descricao = descricao;
	}
	public char getLetra() {
		return letra;
	}
	public String getDescricao() {
		return descricao;
	}
}
