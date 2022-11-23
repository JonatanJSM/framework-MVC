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

        Connection conection0 = pool.getConexion();
        Connection conection1 = pool.getConexion();
        Connection conection2 = pool.getConexion();
        Connection conection3 = pool.getConexion();

        //Cerrar conexion
        pool.cerrarConexion(conection2);
        pool.cerrarConexion(conection0);

        //Dejar de usar conexion
        pool.dejarConexion(conection1);
        //pool.dejarConexion(conection0);

        // Se utiliza la que se dejó de usar
        Connection conection5 = pool.getConexion();
        //Connection conection6 = pool.getConexion();
        //Connection conection7 = pool.getConexion();



        //Se cierran todas
        //pool.cerrarConexion(conection1);
        //pool.cerrarConexion(conection3);
        //pool.cerrarConexion(conection0);
        //System.out.println("En uso:"+pool.getNumeroConexionesEnUso()+ " Numero de conex:"+pool.getNumeroConexiones());
        //Connection conection6 = pool.getConexion();

        //System.out.println("En uso:"+pool.getNumeroConexionesEnUso()+ " Numero de conex:"+pool.getNumeroConexiones());

        Statement st;
        ResultSet rs;
        try {
            st = conection3.createStatement();
            rs = st.executeQuery("select * from arquitectura");
            while(rs.next()){
                System.out.println(rs.getString("Cadena"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
