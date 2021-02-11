package Model;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import Connection.ConnectionFactory;

public abstract class Beneficiario extends Pessoa {

	private Categoria categoria;
	private int mesesBeneficio;
	private double valorBeneficio;
	private ArrayList<Regra> regrasAplicadas = new ArrayList<Regra>();
	private Connection connection = new ConnectionFactory().getConnection("root", "", 3306, "beneficiario");

	public Beneficiario(String nomeCompleto, Date dataNascimento, String estado, Categoria categoria) {
		super(nomeCompleto, dataNascimento, estado);
		this.categoria = categoria;
	}
	
	public void inserirDb() throws SQLException {
		Statement statement = this.getConnection().createStatement();
		String insert = "INSERT INTO beneficiario (nomeCompleto, dataNascimento, estado, mesesBeneficio, valorBeneficio, categoriaId) VALUES ("
				+ "'" + this.getNomeCompleto() + "',"
					+ "'" + new SimpleDateFormat("yyyy-MM-dd").format(this.getDataNascimento()) + "',"
							+ "'" + this.getEstado() + "',"
									+ this.getMesesBeneficio() + ","
									+ this.getValorBeneficio() + ","
									+ "(SELECT id FROM categoria WHERE descricao LIKE '"+this.getCategoria().getDescricao()+"'))";				
		statement.execute(insert);
	}
	
	public void atualizarDb(int id) throws SQLException {
		Statement statement = this.getConnection().createStatement();
		String update = "UPDATE beneficiario SET nomeCompleto = '"+this.getNomeCompleto()+"', dataNascimento = '"+ new SimpleDateFormat("yyyy-MM-dd").format(this.getDataNascimento())+"', estado = '"+this.getEstado()+"', categoriaId = " + "(SELECT id FROM categoria WHERE descricao LIKE '"+this.getCategoria().getDescricao()+"'), valorBeneficio = " + this.getValorBeneficio() + ", mesesBeneficio = " + this.getMesesBeneficio() + " WHERE id = " + id;          
		statement.execute(update);
	}
	
	public String toString() {
		String dataNascimento = new SimpleDateFormat("dd/MM/yyyy").format(this.getDataNascimento());
		return this.getNomeCompleto() + " nascido em " + dataNascimento + " no estado " + this.getEstado() + " se encaixa na categoria de " + this.getCategoria().getDescricao();
	}
	
	public String beneficio() {
		if(this.isBeneficiario()) {
			return this.getNomeCompleto() + " tem direito ao benefício no valor de R$ " + String.valueOf(this.valorBeneficio) + " durante " + String.valueOf(this.mesesBeneficio) + " meses.";
		}
		return this.getNomeCompleto() + " não tem direito ao benefício.";
	}
	
	public String regrasAplicadas() {
		String regrasAplicadas = "Regras aplicadas:\n";
		if(this.regrasAplicadas.size() == 0) {
			regrasAplicadas += "Não foi aplicada nenhuma regra específica.\n";
		}
		for (int i = 0; i < this.regrasAplicadas.size(); i++) {
			Regra regraAplicada = this.regrasAplicadas.get(i);
			regrasAplicadas += "> " + regraAplicada.getLetra() + " - " + regraAplicada.getDescricao() + "\n";
		}
		return regrasAplicadas;
	}
	
	public int compareTempo(Beneficiario beneficiario) {
		if(this.mesesBeneficio < beneficiario.getMesesBeneficio()) {
			return 1;
		} else if(this.mesesBeneficio > beneficiario.getMesesBeneficio()) {
			return -1;
		} else {
			return 0;
		}
	}
	
	public int compareValor(Beneficiario beneficiario) {
		if(this.valorBeneficio < beneficiario.getValorBeneficio()) {
			return 1;
		} else if(this.valorBeneficio > beneficiario.getValorBeneficio()) {
			return -1;
		} else {
			return 0;
		}
	}
	
	public boolean isBeneficiario() {
		return !(this.mesesBeneficio == 0 || this.valorBeneficio == 0);
	}

	
	
	public Connection getConnection() {
		return connection;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public int getMesesBeneficio() {
		return mesesBeneficio;
	}

	public void setMesesBeneficio(int mesesBeneficio) {
		this.mesesBeneficio = mesesBeneficio;
	}

	public double getValorBeneficio() {
		return valorBeneficio;
	}

	public void setValorBeneficio(double valorBeneficio) {
		this.valorBeneficio = valorBeneficio;
	}
	
	public void addRegraAplicada(Regra regra) {
		this.regrasAplicadas.add(regra);
	}
	
}
