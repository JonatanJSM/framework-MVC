package parseos;

import lecturaJSON.lectorJSON;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.util.Arrays;

public class MVCparser {
    private JSONObject objetosPrincipalenJSON;
    private String configuracionMVC[][];

    public MVCparser(){
        lectorJSON lector = new lectorJSON();
        objetosPrincipalenJSON = lector.getObjetosPrincipalenJSON();
        try {
            validarAtributosTransaccion();
        } catch (MissingAttributeException e) {
            System.out.println(e.getMessage());
        }
        obtenerConfiguracionesMVC();
    }

    private void inicializarMatrizConfiguracionMVC(String llave){
        int i=0;
        JSONArray arr = (JSONArray) objetosPrincipalenJSON.get(llave);
        for(Object arreglo: arr){
            i++;
        }
        configuracionMVC = new String[i][4];
    }

    public void obtenerConfiguracionesMVC(){
        inicializarMatrizConfiguracionMVC("configTransac");
        JSONArray configuraciones = (JSONArray) objetosPrincipalenJSON.get("configTransac");
        int i = 0;
        for(Object configuracionN: configuraciones){
            JSONObject auxAtributosObjectoJSON = (JSONObject) configuracionN;
            configuracionMVC[i][0] = (String) auxAtributosObjectoJSON.get("TransaccionName");
            configuracionMVC[i][1] = (String) auxAtributosObjectoJSON.get("Control1");
            configuracionMVC[i][2] = (String) auxAtributosObjectoJSON.get("Modelo2");
            configuracionMVC[i][3] = (String) auxAtributosObjectoJSON.get("Funcion3");
            i++;
        }
    }

    private void validarAtributosTransaccion() throws MissingAttributeException {
        JSONArray configuraciones = (JSONArray) objetosPrincipalenJSON.get("configTransac");
        int i = 0;
        for (Object transaccion: configuraciones) {
            JSONObject auxTransaccion = (JSONObject) transaccion;
            if(!verificarAtributosTransacciones(auxTransaccion)){
                i++;
                throw new MissingAttributeException("Algún atributo de la transacción "+i+" está incorrecto");
            }
            i++;
        }
    }

    private boolean verificarAtributosTransacciones(JSONObject transaccion){
        return !(!transaccion.containsKey("TransaccionName") || !transaccion.containsKey("Control1") || !transaccion.containsKey("Modelo2") || !transaccion.containsKey("Funcion3"));
    }

    public void imprimirConfiguraciones(){
        for(int i=0;i<configuracionMVC.length;i++){
            System.out.println("TransaaccionName "+configuracionMVC[i][0]);
            System.out.println("Control "+configuracionMVC[i][1]);
            System.out.println("Modelo "+configuracionMVC[i][2]);
            System.out.println("Funcion "+configuracionMVC[i][3]+"\n");
        }
    }

    public String[] getTransacciones(){
        String transacciones[] = new String[configuracionMVC.length];
        for(int i=0;i<configuracionMVC.length;i++){
            transacciones[i] = configuracionMVC[i][0];
        }
        System.out.println(Arrays.toString(transacciones));
        return transacciones;
    }

    public String[][] getConfiguracionMVC() {
        return configuracionMVC;
    }

}
