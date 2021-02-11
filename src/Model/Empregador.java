package Model;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.Scanner;

public class Empregador extends Beneficiario {

	private int numeroFuncionarios;
	
	public Empregador(String nomeCompleto, Date dataNascimento, String estado, Categoria categoria, int numeroFuncionarios) {
		super(nomeCompleto, dataNascimento, estado, categoria);
		this.numeroFuncionarios = numeroFuncionarios;
		this.calcularBeneficio();
	}

	public Empregador(String nomeCompleto, Date dataNascimento, String estado, Categoria categoria) {
		super(nomeCompleto, dataNascimento, estado, categoria);
		Scanner scan = new Scanner(System.in);
		System.out.println("Quantos funcionários emprega?");
		this.numeroFuncionarios = scan.nextInt();
		this.calcularBeneficio();
	}
	
	@Override
	public void inserirDb() throws SQLException {
		super.inserirDb();
		Statement statement = this.getConnection().createStatement();
		String insert = "INSERT INTO empregador (beneficiarioId, numeroFuncionarios) VALUES (LAST_INSERT_ID(),"+(this.getNumeroFuncionarios())+")";
		statement.execute(insert);
	}
	
	@Override
	public String toString() {
		return super.toString() + " com " + String.valueOf(this.getNumeroFuncionarios()) + " funcionários";
	}

	public int getNumeroFuncionarios() {
		return numeroFuncionarios;
	}

	public void setNumeroFuncionarios(int numeroFuncionarios) {
		this.numeroFuncionarios = numeroFuncionarios;
	}
	
	private void calcularBeneficio() {
		if(this.getIdadeAnos() < 18) {
			this.addRegraAplicada(Regra.A);
			this.setMesesBeneficio(0);
			this.setValorBeneficio(0);
			return;
		}
		int mesesBeneficio = 2 + (int) (Math.random() * (12 - 2));
		double valorBeneficio = 200 * this.numeroFuncionarios;
		
		if(this.getNumeroFuncionarios() <= 10) {
			valorBeneficio *= 1.18;
			mesesBeneficio = 10;
			this.addRegraAplicada(Regra.G);
			this.addRegraAplicada(Regra.R);
		}
		
		if(this.getEstado().toLowerCase().equals("pr")) {
			this.addRegraAplicada(Regra.Z); 
			valorBeneficio *= 1.09; // Regra Z PR 18%
		}
		
		if(this.getEstado().toLowerCase().equals("sc")) {
			this.addRegraAplicada(Regra.N); 
			valorBeneficio *= 1.05; // Regra N SC 5%
		}
		
		this.setMesesBeneficio(mesesBeneficio);
		this.setValorBeneficio(Math.round(valorBeneficio * 100.0) / 100.0);
	}
	
}
