package ejemplo.controlador;

import ejemplo.vista.vistaEjemplo1;
import realizarConfiguracion.Framework;
import realizarConfiguracion.Transaccion;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class controlVistaEjemplo1 implements ActionListener {
    private vistaEjemplo1 prueba;

    public controlVistaEjemplo1(vistaEjemplo1 prueba) {
        this.prueba = prueba;

        this.prueba.getEnviarButton().addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(prueba.getEnviarButton() == e.getSource()){
            System.out.println(prueba.getTextField1().getText());
            Framework frame = new Framework();
            Transaccion t = frame.getTransaccion("Login");
            t.execute(prueba.getTextField1().getText());
        }
    }
}