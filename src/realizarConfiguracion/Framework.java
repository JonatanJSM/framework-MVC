package realizarConfiguracion;

import lecturaJSON.lectorJSON;
import java.util.HashMap;
import java.util.Map;

public class Framework {
    private Map<String, Transaccion> listaTransacciones = new HashMap<String, Transaccion>();
    lectorJSON lector = new lectorJSON();
    String configuracionMVC[][];

    public Framework(){
        lector.obtenerConfiguracionesMVC("confis");
        configuracionMVC = lector.getConfiguracionMVC();
        generarTransacciones();
    }

    public void generarTransacciones(){
        for (int i= 0; i<configuracionMVC.length;i++){
            listaTransacciones.put(configuracionMVC[i][0],new Transaccion(configuracionMVC[i][1],configuracionMVC[i][2],configuracionMVC[i][3]));
        }
    }

    public Transaccion getTransaccion(String nombreTransaccion){
        return listaTransacciones.get(nombreTransaccion);
    }
}
