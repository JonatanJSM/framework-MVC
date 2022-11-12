package lecturaJSON;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;

public class lectorJSON {
    private String llavesJSON[];
    private JSONObject objetosPrincipalenJSON;
    private String configuracionMVC[][];

    public String[][] getConfiguracionMVC() {
        return configuracionMVC;
    }

    public lectorJSON(){
        obtenerContenidoTXT();
        guardarLlaves();
    }

    public String[] getTransacciones(){
        String transacciones[] = new String[configuracionMVC.length];
        for(int i=0;i<configuracionMVC.length;i++){
            transacciones[i] = configuracionMVC[i][0];
        }
        System.out.println(Arrays.toString(transacciones));
        return transacciones;
    }

    //Estas llaves son los apartados: configuracionesMVC, Log4j, base de datos, pools
    private void guardarLlaves(){
        Set<String> keys = objetosPrincipalenJSON.keySet();
        llavesJSON = new String[keys.size()];
        int i = 0;
        for ( String key : keys) {
            llavesJSON[i] = (String) key;
            i++;
        }
        try {
            verificarLlaves();
        }catch(MissingKeyException e){
            System.out.println("El archivo de configuración está incompleto o tiene alguna configuración mal escrita");
            System.exit(1);
        }
    }

    private void verificarLlaves() throws MissingKeyException {
        int llavesVerificadas=0;
        String[] defaultKeys = {"configTransac", "configLOG4j","configPool", "configBD"};
        for(String key : defaultKeys) {
            for (String llave : llavesJSON) {
                if(key.equals(llave)){
                    llavesVerificadas++;
                    break;
                }
            }
        }

        if(llavesVerificadas!=4){
            throw new MissingKeyException();
        }
    }

    private void obtenerContenidoTXT(){
        JSONParser jsonParser = new JSONParser();
        try(FileReader reader = new FileReader("json4.txt")){
            Object obj = jsonParser.parse(reader);
            objetosPrincipalenJSON = new JSONObject((Map) obj);
            //System.out.println(obj);
        }catch(FileNotFoundException e){
            System.out.println("No se encontró el archivo");
            System.exit(1);
        }catch(IOException e){
            System.out.println(" ");
            System.exit(1);
        }
        catch(ParseException e){
            System.out.println("Algún error en el formato JSON");
            System.exit(1);
        }
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

    private void inicializarMatrizConfiguracionMVC(String llave){
        int i=0;
        JSONArray arr = (JSONArray) objetosPrincipalenJSON.get(llave);
        for(Object arreglo: arr){
            i++;
        }
        configuracionMVC = new String[i][4];
    }

    public void imprimirConfiguraciones(){
        for(int i=0;i<configuracionMVC.length;i++){
            System.out.println("TransaaccionName "+configuracionMVC[i][0]);
            System.out.println("Control "+configuracionMVC[i][1]);
            System.out.println("Modelo "+configuracionMVC[i][2]);
            System.out.println("Funcion "+configuracionMVC[i][3]+"\n");
        }
    }

}