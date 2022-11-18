package ejemplo.controlador;

import ejemplo.vista.vistaEjemplo1;
import frameWork.Framework;
import frameWork.Transaccion;
import org.apache.log4j.Logger;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class controlVistaEjemplo1 implements ActionListener {
    private vistaEjemplo1 prueba;
    private Logger log;

    public controlVistaEjemplo1(vistaEjemplo1 prueba) {
        this.prueba = prueba;

        this.prueba.getEnviarButton().addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try{
            if(prueba.getEnviarButton() == e.getSource()){
                System.out.println(prueba.getTextField1().getText());
                Framework frame = new Framework();
                log=frame.getLogger();
                log.info("A");
                Transaccion t = frame.getTransaccion("Login");
                t.execute(prueba.getTextField1().getText());
            }
        }catch (NullPointerException exc){
            System.out.println("La transacción es inexistente"+exc.getMessage());
        }

    }
}