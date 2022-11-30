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

    private void extraerConfiguracionDB() throws MissingAttributeException {
        configuracionPoolDB = new String[2];
        configuracionPoolDB[0]= (String) PoolConfigurationJSON.get("poolSize");
        configuracionPoolDB[1]= (String) PoolConfigurationJSON.get("connectionDB");
        if(!verificarPool()){
            throw new MissingAttributeException("El tamaño de pools es inválido");
        }
    }

    public String[] getConfiguracionPoolDB(){
        return configuracionPoolDB;
    }

    public boolean verificarPool(){
        String s = configuracionPoolDB[0];
        for(int i=0;i<s.length()-2;i++) {
            char c=s.charAt(i);
            if (c < '0' || c > '9') {
                return false;
            }
        }
        return true;
    }

}

