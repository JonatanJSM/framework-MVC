package parseos;

import lecturaJSON.lectorJSON;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class PoolDBParser{
    JSONObject PoolConfigurationJSON;
    String[] configuracionPoolDB;

    public PoolDBParser(){
        lectorJSON lector = new lectorJSON();
        JSONArray configJSON = (JSONArray) lector.getObjetosPrincipalenJSON().get("configPool");
        PoolConfigurationJSON = (JSONObject) configJSON.get(0);
        try {
            verificarAtributos();
            extraerConfiguracionDB();
        } catch (MissingAttributeException e) {
            System.out.println("Algún atributo está mal escrito o no se existe");
        }
    }

    private void verificarAtributos() throws MissingAttributeException {
        if (!(PoolConfigurationJSON.containsKey("poolSize") && PoolConfigurationJSON.containsKey("connectionDB"))){
            throw new MissingAttributeException("Algún atributo está mal escrito o no se existe");
        }
    }

    private void extraerConfiguracionDB(){
        configuracionPoolDB = new String[2];
        configuracionPoolDB[0]= (String) PoolConfigurationJSON.get("poolSize");
        configuracionPoolDB[1]= (String) PoolConfigurationJSON.get("connectionDB");
    }

    public String[] getConfiguracionPoolDB(){
        return configuracionPoolDB;
    }

}

