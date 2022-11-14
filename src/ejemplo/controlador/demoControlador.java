package ejemplo.controlador;

import ejemplo.modelo.modeloVistaEjemplo1;

public class demoControlador extends Controlador {
    public Modelo modelo;


    //funsion concreta
    public demoControlador(Object modelo){
        this.modelo = modelo;
    }
    public void execute(String nomFunction, Object params){
        ;
        //Aqui utilizaremos reflection
    }

    }
