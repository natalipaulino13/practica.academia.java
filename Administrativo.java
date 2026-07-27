package paq;

import java.util.*;

public class Administrativo extends Persona {

	private String departamento;
	private int antiguedad;

	public Administrativo(String dni, String nombre, String apellidos, int edad, boolean sexo, double salario,
			String departamento, int antiguedad) {
		super(dni, nombre, apellidos, edad, sexo, salario);
		this.departamento = departamento;
		this.antiguedad = antiguedad;
	}

	public String getDepartamento() {
		return departamento;
	}

	public void setDepartamento(String departamento) {
		this.departamento = departamento;
	}

	public int getAntiguedad() {
		return antiguedad;
	}

	public void setAntiguedad(int antiguedad) {
		this.antiguedad = antiguedad;
	}

	@Override
	public String toString() {
		return "ADMINISTRATIVO;" + super.dni + ";" + super.nombre + ";" + super.apellidos + ";" + super.edad + ";"
				+ super.sexo + ";" + this.departamento + ";" + super.salario + ";" + this.antiguedad;
	}

	
	
	

}
