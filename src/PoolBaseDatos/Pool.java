package PoolBaseDatos;

import parseos.DBParser;

import javax.swing.*;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class Pool extends SwingWorker<Void, Void> {
    private int numeroConexiones, numeroConexionesEnUso = 0;
    private final ArrayList<ConexionBaseDatos> conexiones = new ArrayList<>();

    private boolean EnEspera = false;
    private int numeroConexionModificada;

    public void setNumeroConexionModificada(int numeroConexionModificada) {
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

    private String[][] configuracionbd;
    int baseDatoss;
    public Pool(int numeroConexiones, int baseDatos){
        DBParser dbParser = new DBParser();
        configuracionbd = dbParser.getConfiguracionDB();
        this.numeroConexiones = numeroConexiones;
        baseDatoss = baseDatos;
        iniciarConexiones(configuracionbd[baseDatos-1][0], configuracionbd[baseDatos-1][1], configuracionbd[baseDatos-1][2]);
    }

    public void agregarConeciones(int indice){
        for(int i=0;i<indice;i++){
            conexiones.add(new ConexionBaseDatos(configuracionbd[baseDatoss-1][0], configuracionbd[baseDatoss-1][1], configuracionbd[baseDatoss-1][2],numeroConexiones));
            numeroConexiones++;
        }
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
                 System.out.println("Se comienza en la"+ conexiones.get(i).getClaveBaseDatos());
                 if(conexiones.get(i).isConexionActiva() && !conexiones.get(i).isEnUso()){
                     result = conexiones.get(i).getConnection();
                     System.out.println("La que se dio fue"+ conexiones.get(i).getClaveBaseDatos());
                     i = numeroConexiones + 20;
                     obtenerReultado = true;
                     numeroConexionesEnUso++;
                 }
                 i++;
                 if(i > conexiones.size() && !obtenerReultado){
                     i = 0;
                 }
             }
             return result;
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

    public void iniciarConexiones(String url, String usuario, String contrasena){
        for(int i = 0; i<numeroConexiones; i++){
            conexiones.add(new ConexionBaseDatos(url, usuario, contrasena,i));
        }
    }

    public void dejarConexion(Connection connection){
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

    public void cerrarConeciones(){
        int contador = 0;
        for(int i = 0; i<conexiones.size(); i++){
            if(contador == numeroConexionModificada){
                EnEspera = false;
                break;
            }
            if(conexiones.get(i).isConexionActiva() && !conexiones.get(i).isEnUso()){
                conexiones.get(i).desactivarConexion();
                numeroConexionesEnUso--;
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
            System.out.println("Se hacen calculos");
            int conexionesNoUsadas = numeroConexiones - numeroConexionesEnUso;
            if(conexionesNoUsadas >= numeroConexionModificada){
                System.out.println("Sí se cierran");
                cerrarConeciones();
            }else{
                System.out.println("Se prepara cilco");
                EnEspera = true;
            }
        }
        while(EnEspera && (!isDone())){
            System.out.println("Estoy en el ciclo");
            if(numeroConexionModificada > numeroConexiones){
                System.out.println("Es mayor el numero de conexiones que se quieren cerrar a las que existen");
            }else {
                int conexionesNoUsadas = numeroConexiones - numeroConexionesEnUso;
                if(conexionesNoUsadas >= numeroConexionModificada){
                    cerrarConeciones();
                        System.out.println("no debe entrar aqui");
                }else{
                    EnEspera = true;

                }
            }
            try{
                Thread.sleep(500);
            }catch (InterruptedException e){}
        }

        if(!isEnEspera())
            this.cancel(true);

        if(isCancelled())
            System.out.println("Se detuvo por alguna razón desconocida");
        return null;
    }

    public void done(){
        System.out.println("Se canceló :c");
    }


}
