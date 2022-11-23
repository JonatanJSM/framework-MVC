package PoolBaseDatos;

import parseos.DBParser;

import java.sql.Connection;
import java.util.ArrayList;

public class Pool {
    private int numeroConexiones, numeroConexionesEnUso = 0;
    private final ArrayList<ConexionBaseDatos> conexiones = new ArrayList<>();

    public int getNumeroConexiones() {
        return numeroConexiones;
    }

    public int getNumeroConexionesEnUso(){
        return numeroConexionesEnUso;
    }

    private String[][] configuracionbd;
    public Pool(int numeroConexiones, int baseDatos){
        DBParser dbParser = new DBParser();
        configuracionbd = dbParser.getConfiguracionDB();
        this.numeroConexiones = numeroConexiones;

        iniciarConexiones(configuracionbd[baseDatos-1][0], configuracionbd[baseDatos-1][1], configuracionbd[baseDatos-1][2]);
    }

    public Connection getConexion(){
        Connection result = null;
        boolean obtenerReultado = false;
         if(numeroConexionesEnUso == numeroConexiones){
             try {
                 throw new ExceptionConecionBaseDatos("Ya no hay conexiones");
             } catch (ExceptionConecionBaseDatos e) {
                 throw new RuntimeException(e);
             }
         }else{
             int i = 0;
             while(i < conexiones.size()){
                 if(conexiones.get(i).isConexionActiva() && !conexiones.get(i).isEnUso()){
                     result = conexiones.get(i).getConnection();
                     i = numeroConexiones + 20;
                     obtenerReultado = true;
                     numeroConexionesEnUso++;
                 }
                 i++;
                 if(i>conexiones.size() && !obtenerReultado){
                     i = 0;
                     System.out.println("Ya me ciclé, ayuda");
                 }
             }
             return result;
         }
    }

    public void cerrarConexion(Connection connection){
        boolean conexionCerrada = true;
        int i = 0;
        while(conexionCerrada){
            if(conexiones.get(i).getConnectionTest().equals(connection)){
                conexionCerrada = false;
                numeroConexionesEnUso--;
                numeroConexiones--;
                System.out.println(i);
                conexiones.get(i).desactivarConexion();
            }
            i++;
        }
    }

    public void iniciarConexiones(String url, String usuario, String contrasena){
        for(int i = 0; i<numeroConexiones; i++){
            conexiones.add(new ConexionBaseDatos(url, usuario, contrasena,i));
        }
    }

    public void dejarConexion(Connection connection){
        boolean conexionCerrada = true;
        int i = 0;
        while(conexionCerrada){
            if(conexiones.get(i).getConnectionTest().equals(connection)){
                conexionCerrada = false;
                conexiones.get(i).dejarEnUso();
                numeroConexionesEnUso--;
            }
            i++;
        }
    }

}
