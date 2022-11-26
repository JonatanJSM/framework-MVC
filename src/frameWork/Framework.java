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
    private MVCparser lectorMCV;
    private log4Parser logConfigurator; private boolean modificacionPendienteJSON = false;

    private Pool pool;

    String configuracionMVC[][];
    String[] configuracionPool;
    static Logger log;

    public Framework(){
        logConfigurator = new log4Parser();
        iniciarLogger();
        log.info("Se inició el logger");
        lectorMCV = new MVCparser();
        lectorMCV.obtenerConfiguracionesMVC();
        log.info("Se obtuvo la configuración MVC");
        configuracionMVC = lectorMCV.getConfiguracionMVC();
        generarTransacciones();
        log.info("Se generaron las transacciones");

        PoolDBParser poolDBParser = new PoolDBParser();
        configuracionPool = poolDBParser.getConfiguracionPoolDB();

        int tamanio = Integer.parseInt(configuracionPool[0]);
        int baseDatos = Integer.parseInt(configuracionPool[1]);
        this.pool = new Pool(tamanio,baseDatos);
    }

    private void iniciarLogger(){
        String[] configuracion = logConfigurator.getConfiguracion();
        if(configuracion[0].equals("False")){
            log=Logger.getLogger(Framework.class);
            Logger.getRootLogger().setLevel(Level.OFF);
        }else{
            log = log4JManager.getLogger(Framework.class,configuracion[1]);
        }
    }

    private void generarTransacciones(){
        for (int i= 0; i<configuracionMVC.length;i++){
            listaTransacciones.put(configuracionMVC[i][0],new Transaccion(configuracionMVC[i][1],configuracionMVC[i][2],configuracionMVC[i][3]));
        }
    }

    @Override
    protected Void doInBackground() {
        modificacionPendienteJSON = true;

        while(modificacionPendienteJSON){
            PoolDBParser poolDBParser = new PoolDBParser();
            String[] configuracionPool2 = poolDBParser.getConfiguracionPoolDB();

            if(!configuracionPool[0].equals(configuracionPool2[0])){
                if(Integer.parseInt(configuracionPool[0])<Integer.parseInt(configuracionPool2[0])){
                    int agregardas = Integer.parseInt(configuracionPool2[0])- Integer.parseInt(configuracionPool[0]);
                    this.pool.agregarNuevasConexiones(agregardas);
                    configuracionPool[0] = configuracionPool2[0];
                }else {
                    this.pool.setNuevoNumeroConexiones(Integer.parseInt(configuracionPool2[0]));
                    this.pool.execute();
                }
            }
            if (this.pool.isDone()){
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

    public Transaccion getTransaccion(String nombreTransaccion){
        return listaTransacciones.get(nombreTransaccion);
    }

    public Logger getLogger(){
        return log;
    }

    public Connection getConexion(){
        return this.pool.getConexion();
    }

    public void dejarUsoConexion(Connection con){
        this.pool.dejarUsoConexion(con);
    }

    public void cancelarConexion(Connection con){
        this.pool.cerrarConexion(con);
    }

}
