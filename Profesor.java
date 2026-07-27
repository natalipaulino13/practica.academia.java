package paq;

import java.util.*;

public class Profesor extends Persona {

	private String asignatura;
	private int horasLectivas;



	public Profesor(String dni, String nombre, String apellidos, int edad, boolean sexo, double salario,
			String asignatura, int horasLectivas) {
		super(dni, nombre, apellidos, edad, sexo, salario);
		this.asignatura = asignatura;
		this.horasLectivas = horasLectivas;
	}

	public String getAsignatura() {
		return asignatura;
	}

	public void setAsignatura(String asignatura) {
		this.asignatura = asignatura;
	}

	public int getHorasLectivas() {
		return horasLectivas;
	}

	public void setHorasLectivas(int horasLectivas) {
		this.horasLectivas = horasLectivas;
	}

	@Override
	public String toString() {
		return "PROFESOR;" + super.dni + ";" + super.nombre + ";" + super.apellidos + ";" + super.edad + ";"
				+ super.sexo + ";" + this.asignatura + ";" + super.salario + ";" + this.horasLectivas;
	}

	
	
}
