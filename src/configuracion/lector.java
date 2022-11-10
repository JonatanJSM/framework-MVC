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


/**
 *
 * @author jonat
 */
public class lector {
    private JSONObject objetoPrincipalenJSON;
    private String contenidoJSOn[][];

    public String[][] getContenidoJSOn() {
        return contenidoJSOn;
    }

    public void obtenerContenidoJSON(){
        JSONParser jsonParser = new JSONParser();
        try(FileReader reader = new FileReader("json2.txt")){
            Object obj = jsonParser.parse(reader);
            objetoPrincipalenJSON = new JSONObject((Map) obj);
            contenidoJSOn = generarMatrizConNumeroElementos();
        }catch(FileNotFoundException e){
            System.out.println("No se encontró el archivo");
        }catch(IOException e){
            System.out.println(" ");
        }
        catch(ParseException e){
            System.out.println("Algún error en el formato JSON");
        }
    }

    private String[][] generarMatrizConNumeroElementos(){
        int i=0;
        JSONArray arr = (JSONArray) objetoPrincipalenJSON.get("phases");
        for(Object arreglo: arr){
            i++;
        }
        contenidoJSOn = new String[i][2];
        String[][] datos = new String[i][2];
        return guardarDatosConfiguracionJSON(datos);
    }

    private String[][] guardarDatosConfiguracionJSON(String[][] datos) {
        //Como hola es un arreglo de 3 elementos
        JSONArray arr = (JSONArray) objetoPrincipalenJSON.get("hola");
        for(Object arreglo: arr){
            //arr es el arreglo principal
            //arreglo es un elemento del arreglo arr
            JSONObject auxAtributosObjectoJSON = (JSONObject) arreglo;
            // en la linea 57 se convierte ese elemento en un objeto JSON para consultar sus llaves

            System.out.print("Se imprimen ");
            Set<String> keys = auxAtributosObjectoJSON.keySet();
            for ( String key : keys ) {
                System.out.println( key +"\n");
                String orderString = (String) auxAtributosObjectoJSON.get(key);
                System.out.println("lo guardado es "+ orderString);
                System.out.println("\n");
            }

            System.out.println( "\n");
            String orderString = (String) auxAtributosObjectoJSON.get("order");
            //int order = Integer.parseInt(orderString);
            //datos[order-1][0]= orderString;
           // datos[order-1][1]=(String) auxAtributosObjectoJSON.get("class");
        }
        return datos;
    }

    public void imprimir(){
        //for(int i=0;i<contenidoJSOn.length;i++){
          //  System.out.println("order"+contenidoJSOn[i][0]);//imprime lo guardado en order: ---
           // System.out.println("order"+contenidoJSOn[i][1]); //imprimer lo otro: ......

       // }

        System.out.print("Se imprimen las llaves phases y hola");
        Set<String> keys = objetoPrincipalenJSON.keySet();
        System.out.println("longitud"+keys.size());
        for ( String key : keys ) {
            System.out.println( key +"\n");
        }

        /*
        {
            "phases": [
                {
                    "order": "1",
                    "class": "org.filter.Tokenizer"
                },
                {
                    "order": "3",
                    "class": "org.filter.Alfabetizador"
                },
                {
                    "order": "2",
                    "class": "org.filter.Combinador"
                }
                ],
            "hola": [
                {
                    "order": "1",
                    "class": "hola"
                },
                {
                    "order": "2",
                    "class": "buenas"
                },
                {
                    "order": "3",
                    "class": "noches"
                }
            ]
        }
        */

         /*
        {
            "confis": [
                {
                    "TransaccionName": "Login",
                    "order": "1",
                    "class": "votos.demo.controlador.LoginCtrl"
                    "order": "3",
                    "class": "votos.demo.modelo.Login"
                    "order": "2",
                    "class": "votos.demo.modelo.Login"
                },
                {
                    "TransaccionName": "DarseDeBaja",
                    "order": "1",
                    "class": "votos.demo.controlador.LoginCtrl"
                    "order": "3",
                    "class": "votos.demo.modelo.Login"
                    "order": "2",
                    "class": "votos.demo.modelo.Login"
                },
                {
                    "TransaccionName": "SerMillonario",
                    "order": "1",
                    "class": "votos.demo.controlador.LoginCtrl"
                    "order": "3",
                    "class": "votos.demo.modelo.Login"
                    "order": "2",
                    "class": "votos.demo.modelo.Login"
                }
                ],
            "configuLOG4j": [
                {
                    "hola": "que tal"
                }
            ]
        }
        */
    }
}