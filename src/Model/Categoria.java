package Model;

public enum Categoria {

	EMPREGADO("Empregado", Empregado.class),
	EMPREGADOR("Empregador", Empregador.class),
	DESEMPREGADO("Desempregado", Desempregado.class);
	
	private String descricao;
	private Class classe;
	
	private Categoria(String descricao, Class classe) {
		this.descricao = descricao;
		this.classe = classe;
	}
	
	public String getDescricao () {
		return descricao;
	}
	
	public Class getClasse() {
		return this.classe;
	}
	
	public static boolean isValid(String descricao) {
		for(Categoria categoria : Categoria.values()) {
			if(categoria.getDescricao().toLowerCase().equals(descricao.toLowerCase())) {
				return true;
			}
		}
		return false;
	}
	
	public static Categoria getCategoria(String descricao) {
		for(Categoria categoria : Categoria.values()) {
			if(categoria.getDescricao().toLowerCase().equals(descricao.toLowerCase())) {
				return categoria;
			}
		}
		return null;
	}
	
}
