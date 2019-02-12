package com.mortega.basesdedatos;

import com.mortega.basesdedatos.base.Vehiculos;

import com.mortega.basesdedatos.ui.Login;
import com.mortega.basesdedatos.ui.Vista;
import com.mortega.basesdedatos.util.Util;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import static javax.swing.JFileChooser.APPROVE_OPTION;

public class Controlador implements ActionListener, ListSelectionListener, MouseListener {

    private Vista vista;
    private Modelo modelo;
    private Accion accion;
    private int id = 0;

    private File fSeleccionado;

    enum Accion {
        NUEVO, MODIFICAR
    }

    //Constructor predeterminado
    public Controlador(Vista vista, Modelo modelo) {
        this.vista = vista;
        this.modelo = modelo;

        accion = Accion.NUEVO;

        addListeners();
        modoEdicion(true);
        poblarCampos();

        try {
            modelo.conectarSQL();
            iniciarSesion();
        } catch (IOException ioe) {
            JOptionPane.showMessageDialog(null,
                    "No se ha podido conectar a la Base de Datos",
                    "Error", JOptionPane.ERROR_MESSAGE);
            ioe.printStackTrace();
        } catch (SQLException sqle) {
            JOptionPane.showMessageDialog(null,
                    "No se ha podido conectar a la Base de Datos",
                    "Error", JOptionPane.ERROR_MESSAGE);
            sqle.printStackTrace();
        } catch (ClassNotFoundException cnfe) {
            JOptionPane.showMessageDialog(null,
                    "No se ha podido conectar a la Base de Datos",
                    "Error", JOptionPane.ERROR_MESSAGE);
            cnfe.printStackTrace();
        }

        refrescarLista();
    }

    private void iniciarSesion() {
        //Variables y Objetos
        boolean autenticado = false;
        Login login = new Login();
        int intentos = 1;

        do {
            login.mostrarDialogo();
            String usuario = login.getUsuario();
            String contrasena = login.getContrasena();

            try {
                if (modelo.iniciarSesion(usuario, contrasena))
                    autenticado = true;

                if (!autenticado) {
                    if (intentos > 2) {

                        JOptionPane.showMessageDialog(null,
                                "Limite de intentos superados",
                                "Error", JOptionPane.ERROR_MESSAGE);
                        System.exit(0);
                    }

                    login.limpiarContrasena();
                    JOptionPane.showMessageDialog(null,
                            "Usuario/Contrasena incorrectos",
                            "Error", JOptionPane.ERROR_MESSAGE);

                    intentos++;
                }

            } catch (SQLException sqle) {
                JOptionPane.showMessageDialog(null,
                        "No se ha podido conectar a la com.mortega.basesdedatos.base de Datos",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        while (!autenticado);
    }

    private void refrescarLista() {
        //Eliminar la lista anterior mostrada
        vista.mVehiculos.removeAllElements();

        try {
            for (Vehiculos vehiculos : Modelo.getVehiculos()) {
                vista.mVehiculos.addElement(vehiculos);
            }
        } catch (SQLException sqle) {
            JOptionPane.showMessageDialog(null,
                    "No se puede recargar los elementos de la Base de Datos",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void poblarCampos() {
        //Motor
        for (Vehiculos.Motor motor : Vehiculos.Motor.values())
            vista.motorBox.addItem(motor);

        //Cambio
        for (Vehiculos.Cambio cambio : Vehiculos.Cambio.values())
            vista.cambioBox.addItem(cambio);

        //Tipo
        for (Vehiculos.Tipo tipo : Vehiculos.Tipo.values())
            vista.tipoBox.addItem(tipo);
    }

    private void addListeners() {
        //Llamada de cada boton o accion que queremos en nuestra aplicacion
        vista.nuevoB.addActionListener(this);
        vista.agregarB.addActionListener(this);
        vista.guardarB.addActionListener(this);
        vista.eliminarB.addActionListener(this);
        vista.eliminarTodoB.addActionListener(this);

        vista.listaVehiculos.addListSelectionListener(this);

        vista.imagenL.addMouseListener(this);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        //Acciones que van a hacer determinados botones
        switch (e.getActionCommand()) {
            case "nuevo":
                accion = Accion.NUEVO;
                modoEdicion(true);
                limpiarCampos();
                break;

            case "modificar":
                accion = Accion.MODIFICAR;
                modoEdicion(true);
                break;

            case "agregar":
                if (vista.marcaText.getText().equals("")) {
                    JOptionPane.showMessageDialog(null,
                            "La MARCA del vehiculo es obligatorio",
                            "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (vista.modeloText.getText().equals("")) {
                    JOptionPane.showMessageDialog(null,
                            "El MODELO del vehículo es obligatorio",
                            "Error",JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (vista.anioText.getText().equals("")) {
                    vista.anioText.setText("-");
                }

                if (!vista.anioText.getText().matches("[0-9]*")) {
                    JOptionPane.showMessageDialog(null,
                            "El nivel tiene que ser un número",
                            "Error",JOptionPane.ERROR_MESSAGE);

                    vista.anioText.selectAll();
                    vista.anioText.requestFocus();
                    return;
                }

                String marca = vista.marcaText.getText();
                String modelo = vista.modeloText.getText();
                Vehiculos.Motor motor = (Vehiculos.Motor) vista.motorBox.getSelectedItem();
                Vehiculos.Tipo tipo = (Vehiculos.Tipo) vista.tipoBox.getSelectedItem();
                Vehiculos.Cambio cambio = (Vehiculos.Cambio) vista.cambioBox.getSelectedItem();
                int anio = Integer.parseInt(vista.anioText.getText());

                String nombreImagen = null;
                if (fSeleccionado != null)
                    nombreImagen = fSeleccionado.getName();
                else
                    nombreImagen = "nopicture.png";

                //Agregando llo obtenido en nuestro archivo
                Vehiculos vehiculos = new Vehiculos();
                id++;

                vehiculos.setId(id);
                vehiculos.setMarca(marca);
                vehiculos.setModelo(modelo);
                vehiculos.setMotor(motor);
                vehiculos.setTipo(tipo);
                vehiculos.setCambio(cambio);
                vehiculos.setAnio(anio);
                vehiculos.setImagen(nombreImagen);

                switch (accion) {
                    case MODIFICAR:
                        int vehiculoId = vista.listaVehiculos.getSelectedIndex();

                        try {
                            Modelo.modificar(vehiculoId, vehiculos);
                        } catch (SQLException e1) { e1.printStackTrace(); }
                        break;

                    case NUEVO:
                        try {
                            Modelo.guardar(vehiculos);

                            try {
                                Util.copiarImagen (fSeleccionado.getAbsolutePath(), nombreImagen);
                            } catch (IOException ioe) {
                                ioe.printStackTrace();
                            }
                        } catch (SQLException ioe) { ioe.printStackTrace(); }
                        break;
                    default: break;
                }

                limpiarCampos();
                vista.mVehiculos.addElement(vehiculos);
                modoEdicion(true);
                break;

            case "guardar":
                String ruta = "";
                try {
                    if (vista.guardarComo.showSaveDialog(null) == APPROVE_OPTION)
                        ruta = vista.guardarComo.getSelectedFile().getAbsolutePath();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                modoEdicion(false);
                break;
            case "eliminar":
                if (JOptionPane.showConfirmDialog(null,
                        "Eliminar vehiculo","Eliminar",
                        JOptionPane.YES_NO_OPTION)
                    == JOptionPane.NO_OPTION)
                    return;

                Vehiculos vehiculoDelete = vista.listaVehiculos.getSelectedValue();

                try {
                    Modelo.eliminar(vehiculoDelete.getId());
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }

                refrescarLista();
                break;

            case "eliminarTodo":

                if (JOptionPane.showConfirmDialog(null,
                        "Eliminar datos de la tabla Vehículos","Eliminar",
                        JOptionPane.YES_NO_OPTION)
                        == JOptionPane.NO_OPTION)
                    return;

                try {
                    Modelo.eliminarTodo();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }

                refrescarLista();
                break;
        }
    }

    private void limpiarCampos() {
        vista.marcaText.setText("");
        vista.modeloText.setText("");
        vista.anioText.setText("");
    }

    private void modoEdicion (boolean activo) {

        if (activo) {
            vista.nuevoB.setEnabled(false);
            vista.agregarB.setEnabled(true);
            vista.guardarB.setEnabled(true);
            vista.modificarB.setEnabled(false);
            vista.eliminarB.setEnabled(false);
            vista.buscarText.setEditable(true);
        }
        else {
            vista.nuevoB.setEnabled(true);
            vista.guardarB.setEnabled(false);
            vista.modificarB.setEnabled(true);
            vista.eliminarB.setEnabled(true);
            vista.buscarText.setEditable(false);
        }
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        Vehiculos vehiculos = vista.listaVehiculos.getSelectedValue();

        //Si no está seleccionado... No muestra en las cajas de texto nada
        if (vehiculos == null)
            return;

        vista.marcaText.setText(vehiculos.getMarca());
        vista.modeloText.setText(vehiculos.getModelo());
        vista.motorBox.setSelectedItem(vehiculos.getMotor());
        vista.tipoBox.setSelectedItem(vehiculos.getTipo());
        vista.cambioBox.setSelectedItem(vehiculos.getCambio());
        vista.anioText.setText(Integer.toString(vehiculos.getAnio()));
        vista.imagenL.setIcon(new ImageIcon(String.valueOf(vehiculos.getImagen())));

        modoEdicion(false);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        //Cuando hacemos click sobre la imagen
        if (e.getSource() == vista.imagenL) {
            JFileChooser chooser = new JFileChooser();
            if (chooser.showOpenDialog(null) == JFileChooser.CANCEL_OPTION)
                return;

            fSeleccionado = chooser.getSelectedFile();
            vista.imagenL.setIcon(
                    new ImageIcon(fSeleccionado.getAbsolutePath()));
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
