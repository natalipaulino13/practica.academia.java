package paqMain;

import java.util.*;
import java.io.*;
import javax.swing.*;
import java.awt.*;
import paq.*;

public class Main {

	static Scanner scLine = new Scanner(System.in);
	static Scanner scInt = new Scanner(System.in);

	static ArrayList<Persona> personal = new ArrayList();

	public static void main(String[] args) {

		File f = new File("personal.txt");
		if (f.exists()) {
			try {
				BufferedReader br = new BufferedReader(new FileReader(f));
				String linea;
				while ((linea = br.readLine()) != null) {
					linea = linea.trim();
					if (linea.equals(""))
						continue;
					String[] datos = linea.split("; ");
					if (datos[0].equalsIgnoreCase("PROFESOR")) {
						personal.add(new Profesor(datos[1], datos[2], datos[3], Integer.parseInt(datos[4]),
								Boolean.parseBoolean(datos[5]), Double.parseDouble(datos[7]),
								datos[6], Integer.parseInt(datos[8])));
					} else if (datos[0].equalsIgnoreCase("ADMINISTRATIVO")) {
						personal.add(new Administrativo(datos[1], datos[2], datos[3], Integer.parseInt(datos[4]),
								Boolean.parseBoolean(datos[5]), Double.parseDouble(datos[7]),
								datos[6], Integer.parseInt(datos[8])));
					}
				}
				br.close();
				System.out.println("Datos cargados desde personal.txt");
			} catch (IOException e) {
				System.out.println("El fichero no existe");
			}
		}

		JFrame frame = new JFrame("Añadir / Eliminar Profesor");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(420, 320);
		frame.setLocation(100, 100);

		JPanel panel = new JPanel();

		JLabel label1 = new JLabel("DNI");
		JTextField txtDni = new JTextField(15);
		panel.add(label1);
		panel.add(txtDni);

		JLabel label2 = new JLabel("Nombre");
		JTextField txtNombre = new JTextField(12);
		panel.add(label2);
		panel.add(txtNombre);

		JLabel label3 = new JLabel("Apellidos");
		JTextField txtApellidos = new JTextField(18);
		panel.add(label3);
		panel.add(txtApellidos);

		JLabel label4 = new JLabel("Edad");
		JTextField txtEdad = new JTextField(4);
		panel.add(label4);
		panel.add(txtEdad);

		JRadioButton rb1 = new JRadioButton("Mujer");
		rb1.setSelected(true);
		JRadioButton rb2 = new JRadioButton("Hombre");
		ButtonGroup grupo = new ButtonGroup();
		grupo.add(rb1);
		grupo.add(rb2);
		panel.add(rb1);
		panel.add(rb2);

		JLabel label5 = new JLabel("Asignatura");
		JTextField txtAsignatura = new JTextField(15);
		panel.add(label5);
		panel.add(txtAsignatura);

		JLabel label6 = new JLabel("Salario");
		JTextField txtSalario = new JTextField(8);
		panel.add(label6);
		panel.add(txtSalario);

		JLabel label7 = new JLabel("Horas lectivas");
		JTextField txtHoras = new JTextField(4);
		panel.add(label7);
		panel.add(txtHoras);

		JButton boton1 = new JButton("Añadir");
		JButton boton2 = new JButton("Eliminar");
		panel.add(boton1);
		panel.add(boton2);

		boton1.addActionListener(e -> {
			String dni = txtDni.getText().trim();
			String nombre = txtNombre.getText().trim();
			String apellidos = txtApellidos.getText().trim();
			String edadS = txtEdad.getText().trim();
			String asignatura = txtAsignatura.getText().trim();
			String salarioS = txtSalario.getText().trim();
			String horasS = txtHoras.getText().trim();

			if (dni.equals("") || nombre.equals("") || apellidos.equals("") || edadS.equals("")
					|| asignatura.equals("") || salarioS.equals("") || horasS.equals("")) {
				JOptionPane.showMessageDialog(frame, "Faltan datos por introducir.");
				return;
			}

			int edad;
			double salario;
			int horas;

			try {
				edad = Integer.parseInt(edadS);
				if (edad <= 0)
					throw new NumberFormatException();
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(frame, "La edad debe ser un número entero positivo.");
				return;
			}

			try {
				salario = Double.parseDouble(salarioS);
				if (salario <= 0)
					throw new NumberFormatException();
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(frame, "El salario debe ser un número positivo.");
				return;
			}

			try {
				horas = Integer.parseInt(horasS);
				if (horas <= 0)
					throw new NumberFormatException();
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(frame, "Las horas lectivas deben ser un número entero positivo.");
				return;
			}

			boolean existe = false;
			for (int i = 0; i < personal.size(); i++) {
				if (personal.get(i).getDni().equalsIgnoreCase(dni)) {
					existe = true;
					break;
				}
			}
			if (existe) {
				JOptionPane.showMessageDialog(frame, "Ya existe una persona con ese DNI.");
				return;
			}

			boolean sexo = rb1.isSelected();
			personal.add(new Profesor(dni, nombre, apellidos, edad, sexo, salario, asignatura, horas));

			try {
				BufferedWriter bw = new BufferedWriter(new FileWriter("personal.txt"));
				for (int i = 0; i < personal.size(); i++) {
					bw.write(personal.get(i).toString());
					bw.newLine();
				}
				bw.close();
			} catch (IOException ex) {
				System.out.println("Error al guardar el fichero: " + ex.getMessage());
			}

			JOptionPane.showMessageDialog(frame, "Profesor introducido correctamente.");
			txtDni.setText("");
			txtNombre.setText("");
			txtApellidos.setText("");
			txtEdad.setText("");
			txtAsignatura.setText("");
			txtSalario.setText("");
			txtHoras.setText("");
			rb1.setSelected(true);
		});

		boton2.addActionListener(e -> {
			String dni = txtDni.getText().trim();
			if (dni.equals("")) {
				JOptionPane.showMessageDialog(frame, "Introduce el DNI para eliminar.");
				return;
			}

			boolean eliminado = false;
			for (int i = 0; i < personal.size(); i++) {
				if (personal.get(i).getDni().equalsIgnoreCase(dni)) {
					personal.remove(i);
					eliminado = true;
					break;
				}
			}

			if (eliminado) {
				try {
					BufferedWriter bw = new BufferedWriter(new FileWriter("personal.txt"));
					for (int i = 0; i < personal.size(); i++) {
						bw.write(personal.get(i).toString());
						bw.newLine();
					}
					bw.close();
				} catch (IOException ex) {
					System.out.println("Error al guardar el fichero: " + ex.getMessage());
				}
				JOptionPane.showMessageDialog(frame, "Persona eliminada correctamente.");
				txtDni.setText("");
			} else {
				JOptionPane.showMessageDialog(frame, "No existe ninguna persona con ese DNI.");
			}
		});

		frame.setContentPane(panel);
		frame.setVisible(true);

		JFrame frame2 = new JFrame("Añadir / Eliminar Administrativo");
		frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame2.setSize(370, 310);
		frame2.setLocation(500, 100);

		JPanel panel2 = new JPanel();

		JLabel label8 = new JLabel("DNI");
		JTextField txtDni2 = new JTextField(15);
		panel2.add(label8);
		panel2.add(txtDni2);

		JLabel label9 = new JLabel("Nombre");
		JTextField txtNombre2 = new JTextField(12);
		panel2.add(label9);
		panel2.add(txtNombre2);

		JLabel label10 = new JLabel("Apellidos");
		JTextField txtApellidos2 = new JTextField(18);
		panel2.add(label10);
		panel2.add(txtApellidos2);

		JLabel label11 = new JLabel("Edad");
		JTextField txtEdad2 = new JTextField(4);
		panel2.add(label11);
		panel2.add(txtEdad2);

		JRadioButton rb3 = new JRadioButton("Mujer");
		rb3.setSelected(true);
		JRadioButton rb4 = new JRadioButton("Hombre");
		ButtonGroup grupo2 = new ButtonGroup();
		grupo2.add(rb3);
		grupo2.add(rb4);
		panel2.add(rb3);
		panel2.add(rb4);

		JLabel label12 = new JLabel("Departamento");
		JTextField txtDepartamento = new JTextField(15);
		panel2.add(label12);
		panel2.add(txtDepartamento);

		JLabel label13 = new JLabel("Salario");
		JTextField txtSalario2 = new JTextField(8);
		panel2.add(label13);
		panel2.add(txtSalario2);

		JLabel label14 = new JLabel("Antigüedad");
		JTextField txtAntiguedad = new JTextField(4);
		panel2.add(label14);
		panel2.add(txtAntiguedad);

		JButton aniadir = new JButton("Añadir");
		JButton eliminar = new JButton("Eliminar");
		panel2.add(aniadir);
		panel2.add(eliminar);

		aniadir.addActionListener(e -> {
			String dni = txtDni2.getText().trim();
			String nombre = txtNombre2.getText().trim();
			String apellidos = txtApellidos2.getText().trim();
			String edadS = txtEdad2.getText().trim();
			String departamento = txtDepartamento.getText().trim();
			String salarioS = txtSalario2.getText().trim();
			String antigS = txtAntiguedad.getText().trim();

			if (dni.equals("") || nombre.equals("") || apellidos.equals("") || edadS.equals("")
					|| departamento.equals("") || salarioS.equals("") || antigS.equals("")) {
				JOptionPane.showMessageDialog(frame2, "Faltan datos por introducir.");
				return;
			}

			int edad;
			double salario;
			int antiguedad;

			try {
				edad = Integer.parseInt(edadS);
				if (edad <= 0)
					throw new NumberFormatException();
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(frame2, "La edad debe ser un número entero positivo.");
				return;
			}

			try {
				salario = Double.parseDouble(salarioS);
				if (salario <= 0)
					throw new NumberFormatException();
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(frame2, "El salario debe ser un número positivo.");
				return;
			}

			try {
				antiguedad = Integer.parseInt(antigS);
				if (antiguedad < 0)
					throw new NumberFormatException();
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(frame2, "La antigüedad debe ser un número entero no negativo.");
				return;
			}

			boolean existe = false;
			for (int i = 0; i < personal.size(); i++) {
				if (personal.get(i).getDni().equalsIgnoreCase(dni)) {
					existe = true;
					break;
				}
			}
			if (existe) {
				JOptionPane.showMessageDialog(frame2, "Ya existe una persona con ese DNI.");
				return;
			}

			boolean sexo = rb3.isSelected();
			personal.add(new Administrativo(dni, nombre, apellidos, edad, sexo, salario, departamento, antiguedad));

			try {
				BufferedWriter bw = new BufferedWriter(new FileWriter("personal.txt"));
				for (int i = 0; i < personal.size(); i++) {
					bw.write(personal.get(i).toString());
					bw.newLine();
				}
				bw.close();
			} catch (IOException ex) {
				System.out.println("Error al guardar el fichero: " + ex.getMessage());
			}

			JOptionPane.showMessageDialog(frame2, "Administrativo introducido correctamente.");
			txtDni2.setText("");
			txtNombre2.setText("");
			txtApellidos2.setText("");
			txtEdad2.setText("");
			txtDepartamento.setText("");
			txtSalario2.setText("");
			txtAntiguedad.setText("");
			rb3.setSelected(true);
		});

		eliminar.addActionListener(e -> {
			String dni = txtDni2.getText().trim();
			if (dni.equals("")) {
				JOptionPane.showMessageDialog(frame2, "Introduce el DNI para eliminar.");
				return;
			}

			boolean eliminado = false;
			for (int i = 0; i < personal.size(); i++) {
				if (personal.get(i).getDni().equalsIgnoreCase(dni)) {
					personal.remove(i);
					eliminado = true;
					break;
				}
			}

			if (eliminado) {
				try {
					BufferedWriter bw = new BufferedWriter(new FileWriter("personal.txt"));
					for (int i = 0; i < personal.size(); i++) {
						bw.write(personal.get(i).toString());
						bw.newLine();
					}
					bw.close();
				} catch (IOException ex) {
					System.out.println("Error al guardar el fichero: " + ex.getMessage());
				}
				JOptionPane.showMessageDialog(frame2, "Persona eliminada.");
				txtDni2.setText("");
			} else {
				JOptionPane.showMessageDialog(frame2, "No existe ninguna persona con ese DNI.");
			}
		});

		frame2.setContentPane(panel2);
		frame2.setVisible(true);

		boolean salir = false;

		do {
			System.out.println("\n---MENÚ---");
			System.out.println("1.-Mostrar todo el personal");
			System.out.println("2.-Mostrar solo profesores");
			System.out.println("3.-Mostrar solo administrativos");
			System.out.println("4.-Buscar por dni");
			System.out.println("5.-Guardar datos en archivo");
			System.out.println("6.-Salir");
			System.out.println("Elige una opción: ");
			int opcion = scInt.nextInt();

			switch (opcion) {
			case 1:
				mostrarPersonal();
				break;

			case 2:
				mostrarProfesor();
				break;

			case 3:
				mostrarAdministrativo();
				break;

			case 4:
				buscarPorDni();
				break;

			case 5:
				guardarDatos();
				break;

			case 6:
				salir = true;
				System.out.println("Saliendo del menú...");
				break;
			}

		} while (!salir);

	}

	private static void guardarDatos() {
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter("personal.txt"));
			for (int i = 0; i < personal.size(); i++) {
				bw.write(personal.get(i).toString());
				bw.newLine();
			}
			bw.close();
			System.out.println("Datos guardados en personal.txt");
		} catch (IOException e) {
			System.out.println("Error al guardar el fichero");
		}
		System.out.println("\nPulsa enter para continuar");
		scLine.nextLine();
	}

	private static void buscarPorDni() {
		System.out.println("Introduce el DNI a buscar: ");
		String buscar = scLine.nextLine();

		for (int i = 0; i < personal.size(); i++) {
			if (personal.get(i).getDni().equalsIgnoreCase(buscar)) {
				System.out.println("Persona encontrada: " + personal.get(i));
				System.out.println("\nPulsa enter para continuar");
				scLine.nextLine();
				return;
			}
		}

		System.out.println("No existe persona con DNI: " + buscar);
		System.out.println("\nPulsa enter para continuar");
		scLine.nextLine();
	}

	private static void mostrarAdministrativo() {
		boolean hayAdmi = false;
		System.out.println("Listado de administrativos");
		for (int i = 0; i < personal.size(); i++) {
			if (personal.get(i) instanceof Administrativo) {
				hayAdmi = true;
				System.out.println(i + ".-" + personal.get(i));
			}
		}
		if (!hayAdmi)
			System.out.println("No hay administrativos registrados.");

		System.out.println("\nPulsa enter para continuar");
		scLine.nextLine();
	}

	private static void mostrarProfesor() {
		boolean hayProfesor = false;
		System.out.println("Listado de profesores");
		for (int i = 0; i < personal.size(); i++) {
			if (personal.get(i) instanceof Profesor) {
				hayProfesor = true;
				System.out.println(i + ".-" + personal.get(i));
			}
		}
		if (!hayProfesor)
			System.out.println("No hay profesores registrados.");

		System.out.println("\nPulsa enter para continuar");
		scLine.nextLine();
	}

	private static void mostrarPersonal() {
		System.out.println("Listado de todo el personal");
		for (int i = 0; i < personal.size(); i++) {
			System.out.println(i + ".-" + personal.get(i));
		}
		if (personal.size() == 0)
			System.out.println("No hay personal");

		System.out.println("\nPulsa enter para continuar");
		scLine.nextLine();
	}

}
