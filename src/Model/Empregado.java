package Model;

import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Empregado extends Beneficiario {

	private boolean isAposentado;
	
	public Empregado(String nomeCompleto, Date dataNascimento, String estado, Categoria categoria, boolean isAposentado) {
		super(nomeCompleto, dataNascimento, estado, categoria);
		this.isAposentado = isAposentado;
		this.calcularBeneficio();
	}

	public Empregado(String nomeCompleto, Date dataNascimento, String estado, Categoria categoria) {
		super(nomeCompleto, dataNascimento, estado, categoria);
		Scanner scan = new Scanner(System.in);
		System.out.println("É aposentado? (S/N)");
		String resposta = scan.next().toLowerCase();
		this.isAposentado = resposta.equals("s") || resposta.equals("si") || resposta.equals("sim") || resposta.equals("y") || resposta.equals("yes");
		this.calcularBeneficio();
	}
	
	@Override
	public void inserirDb() throws SQLException {
		super.inserirDb();
		Statement statement = this.getConnection().createStatement();
		String insert = "INSERT INTO empregado (beneficiarioId, isAposentado) VALUES (LAST_INSERT_ID(),"+(this.isAposentado() ? 1 : 0)+")";
		statement.execute(insert);
	}
	
	@Override
	public String toString() {
		return super.toString() + (this.isAposentado ? " aposentado" : " não aposentado");
	}

	public boolean isAposentado() {
		return isAposentado;
	}

	public void setAposentado(boolean isAposentado) {
		this.isAposentado = isAposentado;
	}
	
	private void calcularBeneficio() {
		if(this.getIdadeAnos() < 18) { // Regra A 18 anos
			this.addRegraAplicada(Regra.A);
			this.setMesesBeneficio(0);
			this.setValorBeneficio(0);
			return;
		}
		this.addRegraAplicada(Regra.U);
		int mesesBeneficio = 3; // Regra U 3 meses
		double valorBeneficio = 1000 + Math.random() * (1800 - 1000);
		
		if(this.isAposentado) {
			valorBeneficio *= 1.3; // Regra B 30%
			this.addRegraAplicada(Regra.B);
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
