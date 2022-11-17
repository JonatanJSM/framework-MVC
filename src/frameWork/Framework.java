package frameWork;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import parseos.MVCparser;
import parseos.log4Parser;

import java.util.HashMap;
import java.util.Map;

public class Framework {
    static Logger log = Logger.getLogger(Framework.class);
    private Map<String, Transaccion> listaTransacciones = new HashMap<String, Transaccion>();
    MVCparser lector;
    //private log4Parser logConfigurator;
    String configuracionMVC[][];

    public Framework(){
        //logConfigurator = new log4Parser();
        //iniciarLogger();
        log.info("Se inició el logger");
        lector = new MVCparser();
        lector.obtenerConfiguracionesMVC();
        configuracionMVC = lector.getConfiguracionMVC();
        generarTransacciones();
    }

    /*public void iniciarLogger(){
        String[] configuracion = logConfigurator.getConfiguracion();
        if(configuracion[0].equals("False")){
            Logger.getRootLogger().setLevel(Level.OFF);
        }
    }*/

    public void generarTransacciones(){
        for (int i= 0; i<configuracionMVC.length;i++){
            listaTransacciones.put(configuracionMVC[i][0],new Transaccion(configuracionMVC[i][1],configuracionMVC[i][2],configuracionMVC[i][3]));
        }
    }

    public Transaccion getTransaccion(String nombreTransaccion){
        return listaTransacciones.get(nombreTransaccion);
    }

    public static void main(String[] args) {
        Framework f = new Framework();
    }
}
