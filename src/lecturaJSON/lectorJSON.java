package lecturaJSON;
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

    public lectorJSON(){
        obtenerContenidoTXT();
        guardarLlaves();
    }

    public JSONObject getObjetosPrincipalenJSON(){
        return objetosPrincipalenJSON;
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
}
