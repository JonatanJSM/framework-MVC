package lecturaJSON;

import javax.swing.*;

public class MissingKeyException extends Exception{
    public MissingKeyException(){
        super("El archivo de configración no tiene todas las configuraciones");
        JOptionPane.showMessageDialog(null, "Error: El archivo JSON no cuenta con todas las configuraciones \n" +
                "Asegúrese de que los encabezados 'configTransac', 'configLOG4j', 'configPool','configBD' se encuentren en el archivo",
                "ERROR", JOptionPane.WARNING_MESSAGE);
        System.exit(0);
    }
}
