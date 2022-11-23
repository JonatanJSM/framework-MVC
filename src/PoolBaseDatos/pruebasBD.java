package PoolBaseDatos;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class pruebasBD {
    public static void main(String[] args) {
        ConexionBaseDatos conection = new ConexionBaseDatos("jdbc:mysql://localhost:3306/prubea","root","",1);
        Statement st;
        ResultSet rs;
        try {
            st = conection.getConnection().createStatement();
            rs = st.executeQuery("select * from arquitectura");
            while(rs.next()){
                System.out.println(rs.getString("Cadena"));
            }
            conection.desactivarConexion();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
