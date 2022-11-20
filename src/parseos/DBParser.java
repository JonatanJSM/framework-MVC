package parseos;

import lecturaJSON.lectorJSON;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class DBParser {
    JSONObject DBConfigurationJSON;
    String[] configuracionDB;

    public DBParser(){
        lectorJSON lector = new lectorJSON();
        JSONArray configJSON = (JSONArray) lector.getObjetosPrincipalenJSON().get("configBD");
        DBConfigurationJSON = (JSONObject) configJSON.get(0);
        try {
            verificarAtributos();
            extraerConfiguraciónDB();
        } catch (MissingAttributeException e) {
            System.out.println("Algún atributo está mal escrito o no se existe");
        }
    }

    private void verificarAtributos() throws MissingAttributeException {
        if (!(DBConfigurationJSON.containsKey("urlDB") && DBConfigurationJSON.containsKey("usuarioDB") && DBConfigurationJSON.containsKey("contrasenaDB"))){
            throw new MissingAttributeException("Algún atributo está mal escrito o no se existe");
        }
    }

    private void extraerConfiguraciónDB(){
        configuracionDB = new String[3];
        configuracionDB[0]= (String) DBConfigurationJSON.get("urlDB");
        configuracionDB[1]= (String) DBConfigurationJSON.get("usuarioDB");
        configuracionDB[2]= (String) DBConfigurationJSON.get("contrasenaDB");
    }

    public String[] getConfiguracionDB(){
        return configuracionDB;
    }

}
