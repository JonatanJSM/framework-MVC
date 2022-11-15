package parseos;

import javax.swing.*;

public class MissiongAttributeException extends Exception{

    public MissiongAttributeException(int i){
        super("Error en algún atributo de la transacción "+ i);
        JOptionPane.showMessageDialog(null, "Error en transaccion", "Tran", JOptionPane.WARNING_MESSAGE);
        System.exit(0);
    }
}
