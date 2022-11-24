package frameWork;

import PoolBaseDatos.Pool;
import log4J.log4JManager;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import parseos.MVCparser;
import parseos.PoolDBParser;
import parseos.log4Parser;

import javax.swing.*;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

public class Framework extends SwingWorker<Void, Void> {

    private Map<String, Transaccion> listaTransacciones = new HashMap<String, Transaccion>();
    MVCparser lector;
    private log4Parser logConfigurator; private boolean modificacionPendienteJSON=false;
    String configuracionMVC[][];

    String[] configuracionPool;

    Pool pool;
    static Logger log;

    public Framework(){
        logConfigurator = new log4Parser();
        iniciarLogger();
        log.info("Se inició el logger");
        lector = new MVCparser();
        lector.obtenerConfiguracionesMVC();
        log.info("Se obtuvo la configuración MVC");
        configuracionMVC = lector.getConfiguracionMVC();
        generarTransacciones();
        log.info("Se generaron las transacciones");

        PoolDBParser pool = new PoolDBParser();
        configuracionPool = pool.getConfiguracionPoolDB();

        int tamanio = Integer.parseInt(configuracionPool[0]);
        int base = Integer.parseInt(configuracionPool[1]);
        this.pool = new Pool(tamanio,base);
    }

    @Override
    protected Void doInBackground() throws Exception {

       // String []configuracionPool2 = pool.getConfiguracionPoolDB();
        //System.out.println("El valor es"+configuracionPool2[0]);
        //if(!configuracionPool[0].equals(configuracionPool2[0])){
          //  modificacionPendienteJSON = true;
            //this.pool.execute();
          //  System.out.println("Hubo modificacion");
        //}
        modificacionPendienteJSON = true;
        while(modificacionPendienteJSON){
            PoolDBParser pool = new PoolDBParser();
            String []configuracionPool2 = pool.getConfiguracionPoolDB();
            //System.out.println("El valor es"+configuracionPool2[0]);
            boolean bandera = false;
            
            if(!configuracionPool[0].equals(configuracionPool2[0])){
                System.out.println("Hubo modificacion");
                if(Integer.parseInt(configuracionPool[0])<Integer.parseInt(configuracionPool2[0])){
                    int agregardas = Integer.parseInt(configuracionPool2[0])- Integer.parseInt(configuracionPool[0]);
                    this.pool.agregarConeciones(agregardas);
                    configuracionPool[0] = configuracionPool2[0];
                    System.out.println("Se agregaron correamente");
                }else {
                    this.pool.setNumeroConexionModificada(Integer.parseInt(configuracionPool2[0]));
                    this.pool.execute();

                    bandera = true;
                }
            }


            //this.pool.setNumeroConexionModificada(Integer.parseInt(configuracionPool2[0]));
            if (this.pool.isDone()){
                System.out.println("Ya se cerraron");
                configuracionPool[0] = configuracionPool2[0];
            }

            try{
                Thread.sleep(500);
            }catch (InterruptedException e){}

        }

        if(isCancelled())
            System.out.println("Se detuvo por alguna razón desconocida la revision documenro");
        return null;
    }

    public void iniciarLogger(){
        String[] configuracion = logConfigurator.getConfiguracion();
        if(configuracion[0].equals("False")){
            log=Logger.getLogger(Framework.class);
            Logger.getRootLogger().setLevel(Level.OFF);
        }else{
            log = log4JManager.getLogger(Framework.class,configuracion[1]);
        }
    }

    public void generarTransacciones(){
        for (int i= 0; i<configuracionMVC.length;i++){
            listaTransacciones.put(configuracionMVC[i][0],new Transaccion(configuracionMVC[i][1],configuracionMVC[i][2],configuracionMVC[i][3]));
        }
    }

    public Transaccion getTransaccion(String nombreTransaccion){
        return listaTransacciones.get(nombreTransaccion);
    }

    public Logger getLogger(){
        return log;
    }

    public Connection getConexion(){
        return this.pool.getConexion();
    }

    public void dejarConexion(Connection con){
        this.pool.dejarConexion(con);
    }

    public void cancarlar(Connection con){
        this.pool.cerrarConexion(con);
    }

}
