package ejemplo.test;


import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.helpers.Loader;

import java.net.URL;


public class MainP {
    static Logger log;

    public static void main(String[] args) {
        log= Logger.getLogger(MainP.class);
        log.info("Hola");
        /*vistaEjemplo1 vistaPrueba = new vistaEjemplo1();
        controlVistaEjemplo1 controlador = new controlVistaEjemplo1(vistaPrueba);

        vistaPrueba.setVisible(true);*/
    }
}
