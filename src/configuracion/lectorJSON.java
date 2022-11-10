package configuracion;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

public class lectorJSON {
    private String llavesJSON[];
    private JSONObject objetosPrincipalenJSON;
    private String configuracionMVC[][];

    public lectorJSON(){
        obtenerContenidoTXT();
        guardarLlaves();
    }

    private void guardarLlaves(){
        Set<String> keys = objetosPrincipalenJSON.keySet();
        llavesJSON = new String[keys.size()];
        int i = 0;
        for ( String key : keys) {
            llavesJSON[0] = (String) key;
            i++;
        }
    }

    private void obtenerContenidoTXT(){
        JSONParser jsonParser = new JSONParser();
        try(FileReader reader = new FileReader("json2.txt")){
            Object obj = jsonParser.parse(reader);
            objetosPrincipalenJSON = new JSONObject((Map) obj);
        }catch(FileNotFoundException e){
            System.out.println("No se encontró el archivo");
        }catch(IOException e){
            System.out.println(" ");
        }
        catch(ParseException e){
            System.out.println("Algún error en el formato JSON");
        }
    }

    private void obtenerConfiguracionesMVC(String llave){
        inicializarMatrizConfiguracionMVC(llave);
        JSONArray configuraciones = (JSONArray) objetosPrincipalenJSON.get(llave);
        int i = 0;
        for(Object configuracionN: configuraciones){
            JSONObject auxAtributosObjectoJSON = (JSONObject) configuracionN;
            configuracionMVC[i][0] = (String) auxAtributosObjectoJSON.get("TransaccionName");

            String orderString = (String) auxAtributosObjectoJSON.get("order");
            //int order = Integer.parseInt(orderString);
            //datos[order-1][0]= orderString;
            // datos[order-1][1]=(String) auxAtributosObjectoJSON.get("class");
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
}
