package configuracion;

public class pruebas {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
       // lector lector = new lector();
       // lector.obtenerContenidoJSON();
       // lector.imprimir();
        lectorJSON lec = new lectorJSON();
        lec.obtenerConfiguracionesMVC("confis");
        lec.imprimirConfiguraciones();
//        construccionJSON aa = new construccionJSON();
//        aa.obtenerContenidoJSON();
    }
}
