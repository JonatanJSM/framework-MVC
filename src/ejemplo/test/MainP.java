package ejemplo.test;

import controlador.controlVista;
import ejemplo.controlador.controlVistaEjemplo1;
import ejemplo.vista.vistaEjemplo1;
import vistas.vista2;

public class MainP {
    public static void main(String[] args) {
        vistaEjemplo1 vistaPrueba = new vistaEjemplo1();
        controlVistaEjemplo1 controlador = new controlVistaEjemplo1(vistaPrueba);

        vistaPrueba.setVisible(true);
    }
}
