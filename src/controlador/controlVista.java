package controlador;

import vistas.vista2;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class controlVista implements ActionListener {
    private vista2 prueba;

    public controlVista(vista2 prueba) {
        this.prueba = prueba;

        this.prueba.getEnviarButton().addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(prueba.getEnviarButton()==e.getSource()){
            System.out.println(prueba.getTextField1().getText());
        }
    }
}
