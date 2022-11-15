package parseos;

import javax.swing.*;

public class MissingAttributeException extends Exception{

    public MissingAttributeException(String message){
        super(message);
        JOptionPane.showMessageDialog(null, "Error: "+message, "ERROR", JOptionPane.WARNING_MESSAGE);
        System.exit(0);
    }
}
