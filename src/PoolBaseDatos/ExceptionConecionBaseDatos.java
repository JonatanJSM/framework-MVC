package PoolBaseDatos;

import javax.swing.*;

public class ExceptionConecionBaseDatos extends RuntimeException{
    public ExceptionConecionBaseDatos(String message){
        super(message);
        JOptionPane.showMessageDialog(null, "Error: "+message, "ERROR", JOptionPane.WARNING_MESSAGE);
    }
}

