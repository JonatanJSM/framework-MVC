package realizarConfiguracion;

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
            parametros[0] = Object.class;
            Constructor contructorControlador = controlador.getConstructor(parametros);

            Object parametrosDos[] = new Object[1];
            parametrosDos[0] = modelo;
            this.controlador = contructorControlador.newInstance(parametrosDos);

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private void crearModelo(String modelo){
        try {
            Class modeloDemo = Class.forName(modelo);
            Constructor modeloContructor = modeloDemo.getConstructor();
            this.modelo = modeloContructor.newInstance();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
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
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

}
