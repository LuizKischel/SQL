package Model;
import java.time.LocalDate;
import java.time.Period;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public abstract class Pessoa {
	
	private String nomeCompleto;
	private Date dataNascimento;
	private String estado;
	
	public Pessoa(String nomeCompleto, Date dataNascimento, String estado) {
		super();
		this.nomeCompleto = nomeCompleto;
		this.dataNascimento = dataNascimento;
		this.estado = estado;
	}
	
	public int getIdadeAnos() {
		Calendar calendario = Calendar.getInstance();
		calendario.setTime(this.dataNascimento);
		int ano = calendario.get(Calendar.YEAR);
		int mes = calendario.get(Calendar.MONTH) + 1;
		int dia = calendario.get(Calendar.DATE);
		LocalDate dataNascimento = LocalDate.of(ano, mes, dia);
		LocalDate dataHoje = LocalDate.now();
		Period diferenca = Period.between(dataNascimento, dataHoje);
		return diferenca.getYears();
	}
	
	public String getNomeCompleto() {
		return nomeCompleto;
	}
	public void setNomeCompleto(String nomeCompleto) {
		this.nomeCompleto = nomeCompleto;
	}
	public Date getDataNascimento() {
		return dataNascimento;
	}
	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	
}
