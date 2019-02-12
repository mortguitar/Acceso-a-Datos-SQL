package com.mortega.basesdedatos;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.mortega.basesdedatos.base.Vehiculos;
import com.mortega.basesdedatos.base.Vehiculos.Motor;
import com.mortega.basesdedatos.base.Vehiculos.Cambio;
import com.mortega.basesdedatos.base.Vehiculos.Tipo;

public class Modelo {
    private static final String FICHERO_CONFIGURACION = "vehiculosql.conf";
    private static Connection conexion;

    public void conectarSQL() throws ClassNotFoundException, SQLException, IOException {

        Properties props = new Properties();
        props.load(new FileInputStream(FICHERO_CONFIGURACION));
        String host = props.getProperty("host");
        String usuario = props.getProperty("usuario");
        String baseDatos = props.getProperty("baseDatos");
        String contrasena = props.getProperty("contrasena");


        Class.forName("com.mysql.cj.jdbc.Driver");
        conexion = DriverManager.getConnection("jdbc:mysql://"+ host +":3306/" +
                baseDatos, usuario, contrasena);

    }

    public void conectarPostgres() throws ClassNotFoundException, SQLException, IOException {

    }

    public void desconectar() throws SQLException { conexion.close(); }

    public boolean iniciarSesion(String usuario, String contrasena)
            throws SQLException {

        String sql = "SELECT id FROM usuarios WHERE usuario = ? " +
                "AND contrasena = ?";

        PreparedStatement sentencia = conexion.prepareStatement(sql);
        sentencia.setString(1, usuario);
        sentencia.setString(2, contrasena);

        ResultSet resultado = sentencia.executeQuery();
        boolean encontrado = resultado.next();
        sentencia.close();

        return encontrado;
    }

    public static void guardar(Vehiculos vehiculos)
            throws SQLException {

        String sql = "INSERT INTO vehiculos (marca, modelo, motor, tipo, cambio, anio, imagen)" +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement sentencia = conexion.prepareStatement(sql);

        sentencia.setString(1, vehiculos.getMarca());
        sentencia.setString(2, vehiculos.getModelo());
        sentencia.setString(3, vehiculos.getMotor().name());
        sentencia.setString(4, vehiculos.getTipo().name());
        sentencia.setString(5, vehiculos.getCambio().name());
        sentencia.setInt(6, vehiculos.getAnio());
        sentencia.setString(7, vehiculos.getImagen());

        sentencia.executeUpdate();
        sentencia.close();
    }

    public static void modificar(int vehiculoId, Vehiculos vehiculos)
            throws SQLException {

        String sql = "BEGIN TRANSACTION;\n" +
                "UPDATE vehiculos \n" +
                "SET marca = ?\n" +
                "SET modelo = ?\n" +
                "SET motor = ?\n" +
                "SET tipo = ?\n" +
                "SET cambio = ?\n" +
                "SET anio = ?\n" +
                "SET imagen = ?\n" +
                "WHERE id = ? ;\n" +
                "\n" +
                "COMMIT TRANSACTION ;\n";


        PreparedStatement sentencia = conexion.prepareStatement(sql);

        sentencia.setString(1, vehiculos.getMarca());
        sentencia.setString(2, vehiculos.getModelo());
        sentencia.setString(3, vehiculos.getMotor().name());
        sentencia.setString(4, vehiculos.getTipo().name());
        sentencia.setString(5, vehiculos.getCambio().name());
        sentencia.setInt(6, vehiculos.getAnio());
        sentencia.setString(7, vehiculos.getImagen());
        sentencia.setLong(8, vehiculos.getId());

        sentencia.executeUpdate();
        sentencia.close();
    }

    public static void eliminar(long id)
        throws SQLException {

        String sql = "DELETE FROM vehiculos WHERE id = ?";

        PreparedStatement sentencia = conexion.prepareStatement(sql);
        sentencia.setLong(8, id);

        sentencia.executeUpdate();
        sentencia.close();
    }

    public static void eliminarTodo()
            throws SQLException {

        String sql = "DELETE FROM vehiculos";

        PreparedStatement sentencia = conexion.prepareStatement(sql);
        sentencia.executeUpdate();
        sentencia.close();
    }

    public static List<Vehiculos> getVehiculos() throws SQLException {

        Vehiculos vehiculos;
        List<Vehiculos> autos = new ArrayList<>();
        String sql = "SELECT * FROM vehiculos";
        
        PreparedStatement sentence = conexion.prepareStatement(sql);
        ResultSet result = sentence.executeQuery();

        while (result.next()) {
            vehiculos = obtenerVehiculo(result);
            autos.add(vehiculos);
        }

        return autos;
    }

    private static Vehiculos obtenerVehiculo(ResultSet resultado)
            throws SQLException {

        Vehiculos vehiculos = new Vehiculos();

        vehiculos.setId(resultado.getLong(1));
        vehiculos.setMarca(resultado.getString(2));
        vehiculos.setModelo(resultado.getString(3));
        vehiculos.setMotor(Motor.valueOf(resultado.getString(4)));
        vehiculos.setTipo(Tipo.valueOf(resultado.getString(5)));
        vehiculos.setCambio(Cambio.valueOf(resultado.getString(6)));
        vehiculos.setAnio(resultado.getInt(7));
        vehiculos.setImagen(resultado.getString(8));

        return vehiculos;
    }
}
