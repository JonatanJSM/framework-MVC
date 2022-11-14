package ejemplo.modelo;

import javax.swing.*;

public class modeloVistaEjemplo1 extends Modelo{
    public modeloVistaEjemplo1(){

    }
    public void mostrarTexto(String texto){
        JOptionPane.showMessageDialog(null, texto, "Hola mundo", JOptionPane.WARNING_MESSAGE);
    }
}
