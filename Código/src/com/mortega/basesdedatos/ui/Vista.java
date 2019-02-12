package com.mortega.basesdedatos.ui;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.*;
import javax.swing.border.TitledBorder;

import com.mortega.basesdedatos.base.Vehiculos;

public class Vista extends JFrame {

    public JPanel panelImagen;

	public JTextField marcaText;
	public JTextField modeloText;
	public JTextField anioText;
	public JTextField buscarText;

	public JComboBox<Vehiculos.Cambio> cambioBox;
	public JComboBox<Vehiculos.Motor> motorBox;
	public JComboBox<Vehiculos.Tipo> tipoBox;

	public JList<Vehiculos> listaVehiculos;
	public DefaultListModel<Vehiculos> mVehiculos;

	public JButton agregarB;
	public JButton guardarB;
	public JButton modificarB;
	public JButton eliminarB;
	public JButton nuevoB;
	public JButton eliminarTodoB;


	public JLabel imagenL;
	public JFileChooser guardarComo;
	
	/*
	 * Create the frame.
	 */
	public Vista() {

		JFrame ventana = new JFrame();
		ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ventana.setBounds(100, 100, 518, 687);
		ventana.getContentPane().setLayout(null);
		
		JLabel marcaL = new JLabel("Marca:");
		marcaL.setBounds(21, 11, 74, 22);
		ventana.getContentPane().add(marcaL);
		
		JLabel modeloL = new JLabel("Modelo:");
		modeloL.setBounds(21, 44, 46, 22);
		ventana.getContentPane().add(modeloL);
		
		JLabel motorL = new JLabel("Motor: ");
		motorL.setBounds(21, 77, 46, 22);
		ventana.getContentPane().add(motorL);
		
		JLabel tipoL = new JLabel("Tipo: ");
		tipoL.setBounds(203, 11, 46, 22);
		ventana.getContentPane().add(tipoL);
		
		JLabel cambioL = new JLabel("Cambio:");
		cambioL.setBounds(203, 44, 46, 22);
		ventana.getContentPane().add(cambioL);
		
		JLabel anioL = new JLabel("Año de Produccion:");
		anioL.setBounds(202, 77, 119, 22);
		ventana.getContentPane().add(anioL);
		
		marcaText = new JTextField();
		marcaText.setBounds(63, 12, 130, 20);
		ventana.getContentPane().add(marcaText);
		marcaText.setColumns(10);
		
		tipoBox = new JComboBox<>();
		tipoBox.setBounds(259, 11, 130, 22);
		ventana.getContentPane().add(tipoBox);
		
		motorBox = new JComboBox<>();
		motorBox.setBounds(63, 77, 129, 22);
		ventana.getContentPane().add(motorBox);
		
		modeloText = new JTextField();
		modeloText.setBounds(63, 44, 130, 20);
		ventana.getContentPane().add(modeloText);
		modeloText.setColumns(10);
		
		cambioBox = new JComboBox<>();
		cambioBox.setBounds(259, 44, 130, 22);
		ventana.getContentPane().add(cambioBox);
		
		anioText = new JTextField();
		anioText.setBounds(321, 78, 68, 20);
		ventana.getContentPane().add(anioText);
		anioText.setColumns(10);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(21,172,236,343);
		ventana.getContentPane().add(scrollPane);
		
		listaVehiculos = new JList<>();
		scrollPane.setViewportView(listaVehiculos);
		ventana.setVisible(true);

		mVehiculos = new DefaultListModel<>();
		listaVehiculos.setModel(mVehiculos);
		
		guardarB = new JButton("Guardar");
		guardarB.setActionCommand("guardar");
		guardarB.setBounds(21, 526, 142, 23);
		ventana.getContentPane().add(guardarB);
		
		modificarB = new JButton("Modificar");
		modificarB.setActionCommand("modificar");
		modificarB.setBounds(185, 526, 142, 23);
		ventana.getContentPane().add(modificarB);
		
		eliminarB = new JButton("Eliminar");
		eliminarB.setActionCommand("eliminar");
		eliminarB.setBounds(350, 526, 142, 23);
		ventana.getContentPane().add(eliminarB);
		
		buscarText = new JTextField();
		buscarText.setBounds(21, 583, 471, 22);
		ventana.getContentPane().add(buscarText);
		buscarText.setColumns(10);
		
		JLabel buscarL = new JLabel("Buscar ...");
		buscarL.setBounds(21, 560, 46, 22);
		ventana.getContentPane().add(buscarL);

		buscarText.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				JTextField busqueda = (JTextField) e.getSource();
				//Obtener contenido del JTextField
				String cadena = busqueda.getText();
				if (cadena.trim().length() > 0) {
					DefaultListModel<Vehiculos> tmp = new DefaultListModel<>();
					for (int i=0; i < mVehiculos.getSize(); i++) {
						if (mVehiculos.getElementAt(i).toString().toLowerCase().contains(cadena.toLowerCase())) {
							tmp.addElement(mVehiculos.getElementAt(i));
						}
					}
					listaVehiculos.setModel(tmp);
				}
				else {
					listaVehiculos.setModel(mVehiculos);
				}
			}
		});
		
		agregarB = new JButton("Agregar");
		agregarB.setActionCommand("agregar");
		agregarB.setBounds(21, 123, 471, 38);
		ventana.getContentPane().add(agregarB);
		
		nuevoB = new JButton("Nuevo");
		nuevoB.setActionCommand("nuevo");
		nuevoB.setBounds(403, 11, 89, 88);
		ventana.getContentPane().add(nuevoB);

		guardarComo = new JFileChooser();

		imagenL = new JLabel("");
		imagenL.setBounds(259, 165, 233, 354);
		imagenL.setIcon(new ImageIcon(""));
		imagenL.setBorder(
				new TitledBorder(UIManager.getBorder("TitledBorder.border"),
				"Agregar una imagen... ", TitledBorder.LEADING, TitledBorder.TOP,
				null, new Color(0,0,0))
		);
		ventana.getContentPane().add(imagenL);
		
		eliminarTodoB = new JButton("Eliminar Todo");
		eliminarTodoB.setBounds(21, 614, 471, 23);
		ventana.getContentPane().add(eliminarTodoB);

		ventana.setLocationRelativeTo(null);
		ventana.repaint();
	}
}
