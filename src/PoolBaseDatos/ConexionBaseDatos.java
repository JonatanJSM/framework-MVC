package PoolBaseDatos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBaseDatos {
    private boolean conexionActiva, enUso;
    private Connection connection; private int claveBaseDatos;

    public ConexionBaseDatos(String url, String usuario, String contrasena, int claveBaseDatos){
        activarConexion(url, usuario, contrasena);
        this.claveBaseDatos = claveBaseDatos;
    }

    public ConexionBaseDatos(){}

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

    public int getClaveBaseDatos() {
        return claveBaseDatos;
    }

    @Override
    public boolean equals(Object obj) {
        ConexionBaseDatos conexionBaseDatos = (ConexionBaseDatos)obj;
        return this.claveBaseDatos == conexionBaseDatos.getClaveBaseDatos();
    }

    public boolean isNull(){
        return false;
    }
}
