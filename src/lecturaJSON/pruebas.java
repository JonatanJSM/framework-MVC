package lecturaJSON;

public class pruebas {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        lectorJSON lec = new lectorJSON();
        lec.obtenerConfiguracionesMVC("confis");
        lec.imprimirConfiguraciones();
        lec.getTransacciones();
    }
}
