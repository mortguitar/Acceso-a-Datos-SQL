package com.mortega.basesdedatos;

import com.mortega.basesdedatos.ui.Vista;

public class Aplicacion {
	public static void main (String [] args) {
        //Las incializaciones de lo elementos para el MVC
        Vista vista = new Vista();
        Modelo modelo = new Modelo();
        Controlador controlador = new Controlador(vista, modelo);
    }
}
