package ejemplo.test;

import ejemplo.vista.vistaEjemplo1;
import frameWork.Framework;
import frameWork.Transaccion;
import org.apache.log4j.Logger;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;

public class controlVistaEjemplo1 implements ActionListener {
    private vistaEjemplo1 prueba;
    private Logger log;
    private Framework frame;

    public controlVistaEjemplo1(vistaEjemplo1 prueba)  {
        this.prueba = prueba;
        this.prueba.getEnviarButton().addActionListener(this);
        frame = new Framework();

        Connection uno= frame.getConexion();
        Connection dos= frame.getConexion();
        Connection tres= frame.getConexion();
        Connection cuatro= frame.getConexion();
        frame.execute();
        //.-----------
        try{
            Thread.sleep(10000);
        }catch (InterruptedException e){}
        frame.dejarConexion(uno);
        frame.dejarConexion(tres);

        try{
            Thread.sleep(10000);
        }catch (InterruptedException e){}
        System.out.println("Ya se dio la 5");
        Connection cinco = frame.getConexion();

        //frame.cancarlar(dos);
        //frame.cancarlar(cuatro);
        //frame.cancarlar(tres);
        //frame.cancarlar(uno);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try{
            if(prueba.getEnviarButton() == e.getSource()){
                System.out.println(prueba.getTextField1().getText());
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