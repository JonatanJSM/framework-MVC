package ejemplo.modelo;

import MVC.Modelo;
import javax.swing.*;

public class modeloVistaEjemplo1 extends Modelo {
    public modeloVistaEjemplo1(){

    }
    public void mostrarTex(String texto){
        JOptionPane.showMessageDialog(null, texto, "Hola mundo", JOptionPane.WARNING_MESSAGE);
    }
}
