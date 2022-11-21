package parseos;

import lecturaJSON.lectorJSON;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.Arrays;

public class DBParser {
    JSONObject DBConfigurationJSON;
    String[][] configuracionDB;

    public DBParser(){
        lectorJSON lector = new lectorJSON();
        DBConfigurationJSON = lector.getObjetosPrincipalenJSON();
        try {
            validarAtributosTransaccion();
        } catch (MissingAttributeException e) {
            System.out.println("Algún atributo está mal escrito o no se existe");
        }
        extraerConfiguracionDB();
    }

    private boolean verificarAtributos(JSONObject configuration){
        return (configuration.containsKey("urlDB") && configuration.containsKey("usuarioDB") && configuration.containsKey("contrasenaDB"));
    }

    private void validarAtributosTransaccion() throws MissingAttributeException {
        JSONArray configuraciones = (JSONArray) DBConfigurationJSON.get("configBD");
        int i = 0;
        for (Object conf : configuraciones) {
            JSONObject auxConfig = (JSONObject) conf;
            if(!verificarAtributos(auxConfig)){
                i++;
                throw new MissingAttributeException("Algún atributo de la configuración de la BD "+i+" está incorrecto");
            }
            i++;
        }
    }

    private void inicializarSizeConfigurationDB(){
        int i=0;
        JSONArray arr = (JSONArray) DBConfigurationJSON.get("configBD");
        for(Object arreglo: arr){
            i++;
        }
        configuracionDB = new String[i][3];
    }

    private void extraerConfiguracionDB(){
        inicializarSizeConfigurationDB();
        JSONArray configuraciones = (JSONArray) DBConfigurationJSON.get("configBD");
        int i = 0;
        for(Object configuracionN: configuraciones){
            JSONObject auxAtributos = (JSONObject) configuracionN;
            configuracionDB[i][0]= (String) auxAtributos.get("urlDB");
            configuracionDB[i][1]= (String) auxAtributos.get("usuarioDB");
            configuracionDB[i][2]= (String) auxAtributos.get("contrasenaDB");
            i++;
        }
    }

    public String[][] getConfiguracionDB(){
        return configuracionDB;
    }

}
