package Model;

public enum Regra {

	G('G', "Para empregadores que tenham até 10 funcionários haverá um acréscimo de 18% sobre o valor total."),
	A('A', "O benefício só será concedido para maiores de 18 anos."),
	B('B', "Para aposentados o valor do benefício terá um acréscimo de 30%."),
	R('R', "O Benefício será de 10 meses para empregadores com até 10 funcionários."),
	L('L', "O benefício será de 6 meses para desempregados."),
	U('U', "O Benefício será de 3 meses para empregados."),
	Z('Z', "O benefício de pessoas que moram no Paraná terá acréscimo de 9%."),
	N('N', "O benefício de pessoas que moram em Santa Catarina terá acréscimo de 5%;");
	
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
