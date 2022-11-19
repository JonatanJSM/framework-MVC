package frameWork;

import log4J.log4JManager;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import parseos.MVCparser;
import parseos.log4Parser;

import java.util.HashMap;
import java.util.Map;

public class Framework {
    static Logger log;
    private Map<String, Transaccion> listaTransacciones = new HashMap<String, Transaccion>();
    MVCparser lector;
    private log4Parser logConfigurator;
    String configuracionMVC[][];

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

}
