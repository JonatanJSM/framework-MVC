package PoolBaseDatos;

import parseos.DBParser;

import java.sql.Connection;
import java.util.ArrayList;

public class Pool {
    private int numeroConexiones, numeroConexionesEnUso=0, indiceActual=0;
    private final ArrayList<ConexionBaseDatos> conexiones = new ArrayList<>();

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
             return (Connection) new NullConecionBaseDatos();
         }else{
             int i = indiceActual;
             while(i<numeroConexiones){
                 if(conexiones.get(i).isConexionActiva() && !conexiones.get(i).isEnUso()){
                     result = conexiones.get(i).getConnection();
                     indiceActual = i;
                     i = numeroConexiones +20;
                     obtenerReultado = true;
                     numeroConexionesEnUso++;
                 }
                 i++;
                 if(i>numeroConexiones && !obtenerReultado){
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
            if(conexiones.get(i).getConnection().equals(connection)){
                conexionCerrada = false;
                conexiones.get(i).desactivarConexion();
            }
        }
    }

    public void iniciarConexiones(String url, String usuario, String contrasena){
        for(int i = 0; i<numeroConexiones; i++){
            conexiones.add(new ConexionBaseDatos(url, usuario, contrasena,i+1));
        }
    }

}
