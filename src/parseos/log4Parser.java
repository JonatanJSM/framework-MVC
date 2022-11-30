package parseos;

import lecturaJSON.MissingKeyException;
import lecturaJSON.lectorJSON;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class log4Parser {
    JSONObject configLog4J;
    String[] configuracion;

    public log4Parser(){
        lectorJSON lector = new lectorJSON();
        JSONArray configJSON = (JSONArray) lector.getObjetosPrincipalenJSON().get("configLOG4j");
        configLog4J= (JSONObject) configJSON.get(0);
        try {
            verificarAtributos();
            extraerConfiguración();
        } catch (MissingAttributeException e) {
            System.out.println("Algún atributo está mal escrito o no se existe");
        }
    }

    private void verificarAtributos() throws MissingAttributeException {
        if (configLog4J.containsKey("Active")){
            if(!(configLog4J.containsKey("FileSize"))){
                throw new MissingAttributeException("El atributo 'FileSize' no existe dentro de la configuración de Log4j");
            }
        }else{
            throw new MissingAttributeException("El atributo 'Active' no existe dentro de la configuración de Log4j");
        }
    }

    private void extraerConfiguración() throws MissingAttributeException {
        configuracion = new String[2];
        configuracion[0]= (String) configLog4J.get("Active");
        if(configuracion[0].equals("True")){
            configuracion[1]=(String) configLog4J.get("FileSize");
            if(!verificarTamaño()){
                throw new MissingAttributeException("El tamaño de archivo para logs es inválido.'/n'Recuerde usar un número seguido de 'KB'");
            }
        }else{
            configuracion[1]="0";
        }
    }

    public boolean verificarTamaño(){
        String s = configuracion[1];
        for(int i=0;i<s.length()-2;i++) {
            char c=s.charAt(i);
            if (c < '0' || c > '9') {
            }
        }
        if(!s.contains("KB")){
            return false;
        }
        return true;
    }

    public String[] getConfiguracion(){
        return configuracion;
    }
}
