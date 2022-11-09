import controlador.controlVista;
import vistas.vista2;

public class MainP {
    public static void main(String[] args) {
        vista2 vistaPrueba = new vista2();
        controlVista controlador = new controlVista(vistaPrueba);

        vistaPrueba.setVisible(true);
    }
}
