package PoolBaseDatos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBaseDatos {
    private boolean conexionActiva, enUso;
    private Connection connection;

    public ConexionBaseDatos(String url, String usuario, String contrasena){
        activarConexion(url, usuario, contrasena);
    }

    public void activarConexion(String url, String usuario, String contrasena){
        try {
            String driver = "com.mysql.cj.jdbc.Driver";
            Class.forName(driver);
            connection = DriverManager.getConnection(url,usuario,contrasena);
            conexionActiva = true;
            enUso = false;
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("No se conectó a la base de datos");
        }
    }

    public void desactivarConexion(){
        try {
            connection.close();
            conexionActiva = false;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Connection getConnection() {
        enUso = true;
        return connection;
    }

    public boolean isConexionActiva() {
        return conexionActiva;
    }

    public boolean isEnUso() {
        return enUso;
    }
}
