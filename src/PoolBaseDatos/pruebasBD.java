package PoolBaseDatos;


import parseos.PoolDBParser;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class pruebasBD {
    public static void main(String[] args) {
        PoolDBParser parser = new PoolDBParser();
        String[] configuracionPoolDB = parser.getConfiguracionPoolDB();
        int tamanio = Integer.parseInt(configuracionPoolDB[0]);
        int base = Integer.parseInt(configuracionPoolDB[1]);
        System.out.println("Tamaño es: "+tamanio+" base es: "+base);


        Pool pool = new Pool(tamanio,base);

        Connection conection1 = pool.getConexion();
        Connection conection2 = pool.getConexion();
        Connection conection3 = pool.getConexion();
        Connection conection4 = pool.getConexion();


        // Pedir más de las que se definen
        // Connection conection5 = pool.getConexion();

        Statement st;
        ResultSet rs;
        try {
            st = conection4.createStatement();
            rs = st.executeQuery("select * from arquitectura");
            while(rs.next()){
                System.out.println(rs.getString("Cadena"));
            }
            //Se cierra la conexion 4
            pool.cerrarConexion(conection4);

            // Se deja de usar la 2
            pool.dejarConexion(conection3);
            Connection connection6 = pool.getConexion();
            Connection connection7 = pool.getConexion();
            Connection connection8 = pool.getConexion();
            Connection connection0 = pool.getConexion();
            Connection connection10 = pool.getConexion();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
