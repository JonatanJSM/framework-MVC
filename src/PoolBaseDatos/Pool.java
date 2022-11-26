package PoolBaseDatos;

import parseos.DBParser;
import javax.swing.*;
import java.sql.Connection;
import java.util.ArrayList;

public class Pool extends SwingWorker<Void, Void> {
    private int numeroConexiones, numeroConexionesEnUso = 0, numeroConexionModificada, baseDatoss;
    private final ArrayList<ConexionBaseDatos> conexiones = new ArrayList<>();
    private boolean EnEspera = false;
    private String[][] configuracionbd;

    public Pool(int numeroConexiones, int baseDatos){
        DBParser dbParser = new DBParser();
        configuracionbd = dbParser.getConfiguracionDB();
        this.numeroConexiones = numeroConexiones;
        baseDatoss = baseDatos;
        iniciarConexiones(configuracionbd[baseDatos-1][0], configuracionbd[baseDatos-1][1], configuracionbd[baseDatos-1][2]);
    }

    private void iniciarConexiones(String url, String usuario, String contrasena){
        for(int i = 0; i<numeroConexiones; i++){
            conexiones.add(new ConexionBaseDatos(url, usuario, contrasena,i));
        }
    }

    public void agregarNuevasConexiones(int indice){
        for(int i=0; i<indice; i++){
            conexiones.add(new ConexionBaseDatos(configuracionbd[baseDatoss-1][0], configuracionbd[baseDatoss-1][1], configuracionbd[baseDatoss-1][2],numeroConexiones));
            numeroConexiones++;
        }
        System.out.println("Se agregaron correctamente las conexiones");
    }

    public Connection getConexion(){
        Connection conexionPedida = null;
        boolean obtenerReultado = false;
         if(numeroConexionesEnUso == numeroConexiones){
             try {
                 System.out.println("Ya no se pudo retornar una conexion");
                 throw new ExceptionConecionBaseDatos("Ya no hay conexiones");
             } catch (ExceptionConecionBaseDatos e) {
                 throw new RuntimeException(e);
             }
         }else{
             int i = 0;
             while(i < conexiones.size()){
                 if(conexiones.get(i).isConexionActiva() && !conexiones.get(i).isEnUso()){
                     conexionPedida = conexiones.get(i).getConnection();
                     i = numeroConexiones + 20;
                     obtenerReultado = true;
                     numeroConexionesEnUso++;
                 }
                 i++;
                 if(i > conexiones.size() && !obtenerReultado){
                     i = 0;
                 }
             }
             return conexionPedida;
         }
    }

    public void cerrarConexion(Connection connection){
        boolean conexionCerrada = true;
        int i = 0;
        while(conexionCerrada && i<conexiones.size()){
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

    public void dejarUsoConexion(Connection connection){
        boolean conexionCerrada = true;
        int i = 0;
        while(conexionCerrada && i<conexiones.size()){
            if(conexiones.get(i).getConnectionTest().equals(connection)){
                conexionCerrada = false;
                conexiones.get(i).dejarEnUso();
                numeroConexionesEnUso--;
            }
            i++;
        }
    }

    private void cerrarConeciones(){
        int contador = 0;
        for(int i = 0; i<conexiones.size(); i++){
            if(contador == numeroConexionModificada){
                EnEspera = false;
                break;
            }
            if(conexiones.get(i).isConexionActiva() && !conexiones.get(i).isEnUso()){
                conexiones.get(i).desactivarConexion();
                numeroConexiones--;
                contador++;
            }
        }
    }

    @Override
    protected Void doInBackground() throws Exception {
        if(numeroConexionModificada > numeroConexiones){
            System.out.println("Es mayor el numero de conexiones que se quieren cerrar a las que existen");
        }else {
            int conexionesNoUsadas = numeroConexiones - numeroConexionesEnUso;
            if(conexionesNoUsadas >= numeroConexionModificada){
                cerrarConeciones();
                this.cancel(true);
            }else{
                EnEspera = true;
            }
        }
        while(EnEspera && (!isDone())){
            if(numeroConexionModificada > numeroConexiones){
                System.out.println("Es mayor el numero de conexiones que se quieren cerrar a las que existen");
            }else {
                int conexionesNoUsadas = numeroConexiones - numeroConexionesEnUso;
                if(conexionesNoUsadas >= numeroConexionModificada){
                    cerrarConeciones();
                    this.cancel(true);
                }else{
                    EnEspera = true;
                }
            }
            try{
                //Para que haga una pausa en la revisión del documento
                Thread.sleep(500);
            }catch (InterruptedException e){}
        }
        if(!isEnEspera())
            this.cancel(true);
        return null;
    }

    public void done(){
        System.out.println("Se hicieron las modificaciones indicadas");
    }


    public void setNuevoNumeroConexiones(int numeroConexionModificada) {
        this.numeroConexionModificada = numeroConexionModificada;
    }

    public boolean isEnEspera() {
        return EnEspera;
    }

    public int getNumeroConexiones() {
        return numeroConexiones;
    }

    public int getNumeroConexionesEnUso(){
        return numeroConexionesEnUso;
    }

}
