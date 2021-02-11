package Model;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.Scanner;

public class Desempregado extends Beneficiario {

	private int mesesDesempregado;

	public Desempregado(String nomeCompleto, Date dataNascimento, String estado, Categoria categoria, int mesesDesempregado) {
		super(nomeCompleto, dataNascimento, estado, categoria);
		this.mesesDesempregado = mesesDesempregado;
		this.calcularBeneficio();
	}
	
	public Desempregado(String nomeCompleto, Date dataNascimento, String estado, Categoria categoria) {
		super(nomeCompleto, dataNascimento, estado, categoria);
		Scanner scan = new Scanner(System.in);
		System.out.println("Quantos meses está desempregado?");
		this.mesesDesempregado = scan.nextInt();
		this.calcularBeneficio();
	}
	
	@Override
	public void inserirDb() throws SQLException {
		super.inserirDb();
		Statement statement = this.getConnection().createStatement();
		String insert = "INSERT INTO desempregado (beneficiarioId, mesesDesempregado) VALUES (LAST_INSERT_ID(),"+(this.getMesesDesempregado())+")";
		statement.execute(insert);
	}

	@Override
	public String toString() {
		return super.toString() + " há " + String.valueOf(this.getMesesDesempregado()) + " meses";
	}
	
	public int getMesesDesempregado() {
		return mesesDesempregado;
	}

	public void setMesesDesempregado(int mesesDesempregado) {
		this.mesesDesempregado = mesesDesempregado;
	}
	
	private void calcularBeneficio() {
		if(this.getIdadeAnos() < 18) {
			this.addRegraAplicada(Regra.A);
			this.setMesesBeneficio(0);
			this.setValorBeneficio(0);
			return;
		}
		
		this.addRegraAplicada(Regra.L);
		int mesesBeneficio = 6; // Regra L 6 meses
		double valorBeneficio = 1500 + Math.random() * (2300 - 1500);
		
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
