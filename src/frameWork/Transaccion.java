package frameWork;

import MVC.Modelo;
import ejemplo.modelo.modeloVistaEjemplo1;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Transaccion {

    private Object controlador;
    private Object modelo;
    private String funcion;
    private String modeloNombre;

    public Transaccion(String controlador, String modelo, String funcion){
        crearModelo(modelo);
        crearControlador(controlador);
        this.funcion = funcion;
        this.modeloNombre = modelo;
    }

    private void crearControlador(String control){
        try {
            Class controlador = Class.forName(control);
            Class parametros[] = new Class[1];
            parametros[0] = Modelo.class;
            Constructor contructorControlador = controlador.getConstructor(parametros);

            Object parametrosDos[] = new Object[1];
            parametrosDos[0] = (Modelo)modelo;
            this.controlador = contructorControlador.newInstance(parametrosDos);

        } catch (ClassNotFoundException e) {
            System.out.println("La clase controladora no fue encontrada");
        } catch (NoSuchMethodException e) {
            System.out.println("La función del controlador no fue encontrada");
        } catch (InvocationTargetException e) {
            System.out.println("Error en la función constructora: "+e.getCause());
        } catch (InstantiationException e) {
            System.out.println("No se pudo crear una instancia de la clase controladora");
        } catch (IllegalAccessException e) {
            System.out.println("No se pudo acceder a la clase o a la función");
        }
    }

    private void crearModelo(String modelo){
        try {
            Class modeloDemo = Class.forName(modelo);
            Constructor modeloContructor = modeloDemo.getConstructor();
            this.modelo = modeloContructor.newInstance();
        } catch (ClassNotFoundException e) {
            System.out.println("La clase modelo no fue encontrada");
        } catch (NoSuchMethodException e) {
            System.out.println("La función del modelo no fue encontrada");
        } catch (InvocationTargetException e) {
            System.out.println("Error en la función constructora: "+e.getCause());
        } catch (InstantiationException e) {
            System.out.println("No se pudo crear una instancia de la clase modelo");
        } catch (IllegalAccessException e) {
            System.out.println("No se pudo acceder a la clase o a la función");
        }
    }

    public void execute(Object texto) {
        Class modeloDemo = null;
        try {
            modeloDemo = Class.forName(modeloNombre);
            Class parametros[] = new Class[1];
            parametros[0] = String.class;
            Method meth = modeloDemo.getMethod(funcion,parametros);

            Object para[] = new Object[1];
            para[0] = texto;
            modeloVistaEjemplo1 mo = new modeloVistaEjemplo1();
            meth.invoke(this.modelo,para);
        } catch (ClassNotFoundException e) {
            System.out.println("No se pudo ejecutar la transacción:  No se encontró la clase");
        } catch (NoSuchMethodException e) {
            System.out.println("No se pudo ejecutar la transacción:  No se encontró la función");
        } catch (InvocationTargetException e) {
            System.out.println("No se pudo ejecutar la transacción: Error en la función: "+e.getCause());
        } catch (IllegalAccessException e) {
            System.out.println("No se pudo ejecutar la transacción: No se pudo acceder a la clase o a la función");
        }
    }

}
