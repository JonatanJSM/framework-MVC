package lecturaJSON;

import PoolBaseDatos.Pool;
import parseos.PoolDBParser;

import javax.swing.*;

public class File extends SwingWorker<Void, Void> {
    private boolean modificacionPendienteJSON = false; private Pool pool;
    String[] configuracionPool;

    public File(Pool pool){
        PoolDBParser poolDBParser = new PoolDBParser();
        configuracionPool = poolDBParser.getConfiguracionPoolDB();
        int tamanio = Integer.parseInt(configuracionPool[0]);
        int baseDatos = Integer.parseInt(configuracionPool[1]);
        this.pool = pool;

    }

    protected Void doInBackground() {
        modificacionPendienteJSON = true;

        while(modificacionPendienteJSON){
            PoolDBParser poolDBParser = new PoolDBParser();
            String[] configuracionPool2 = poolDBParser.getConfiguracionPoolDB();

            if(!configuracionPool[0].equals(configuracionPool2[0])){
                if(Integer.parseInt(configuracionPool[0])<Integer.parseInt(configuracionPool2[0])){
                    int agregardas = Integer.parseInt(configuracionPool2[0])- Integer.parseInt(configuracionPool[0]);
                    this.pool.agregarNuevasConexiones(agregardas);
                    configuracionPool[0] = configuracionPool2[0];
                }else {
                    this.pool.setNuevoNumeroConexiones(Integer.parseInt(configuracionPool2[0]));
                    this.pool.execute();
                }
            }
            if (this.pool.isDone()){
                configuracionPool[0] = configuracionPool2[0];
            }
            try{
                Thread.sleep(500);
            }catch (InterruptedException e){}

        }
        if(isCancelled())
            System.out.println("Se detuvo por alguna razón desconocida la revision documenro");
        return null;
    }
}
