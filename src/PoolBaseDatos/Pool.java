package PoolBaseDatos;

import java.util.ArrayList;

public class Pool {
    private int numeroConexiones, numeroConecionesActivas, numeroConecionesNoActivas;
    private String urlBD, usuarioBD, contrasenaBD;
    private final ArrayList<ConexionBaseDatos> conexiones = new ArrayList<>();

    public void getConexion(){}

    public void cerrarConexion(int indice){}

    public void cerrarConexion(){}


}
