package frameWork;

import PoolBaseDatos.ExceptionConecionBaseDatos;
import PoolBaseDatos.Pool;
import lecturaJSON.FileFramework;
import log4J.log4JManager;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import parseos.MVCparser;
import parseos.PoolDBParser;
import parseos.log4Parser;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

public class Framework {

    private Map<String, Transaccion> listaTransacciones = new HashMap<String, Transaccion>();
    private MVCparser lectorMCV;
    private log4Parser logConfigurator; private boolean modificacionPendienteJSON = false;

    private Pool pool; private FileFramework file;

    String configuracionMVC[][];
    String[] configuracionPool;
    static Logger log;

    public Framework(){
        logConfigurator = new log4Parser();
        iniciarLogger();
        log.info("Se inició el logger");
        lectorMCV = new MVCparser();
        lectorMCV.obtenerConfiguracionesMVC();
        log.info("Se obtuvo la configuración MVC");
        configuracionMVC = lectorMCV.getConfiguracionMVC();
        generarTransacciones();
        log.info("Se generaron las transacciones");

        PoolDBParser poolDBParser = new PoolDBParser();
        configuracionPool = poolDBParser.getConfiguracionPoolDB();

        int tamanio = Integer.parseInt(configuracionPool[0]);
        int baseDatos = Integer.parseInt(configuracionPool[1]);
        this.pool = new Pool(tamanio,baseDatos);
        file = new FileFramework(this.pool);
    }

    private void iniciarLogger(){
        String[] configuracion = logConfigurator.getConfiguracion();
        if(configuracion[0].equals("False")){
            log=Logger.getLogger(Framework.class);
            Logger.getRootLogger().setLevel(Level.OFF);
        }else{
            log = log4JManager.getLogger(Framework.class,configuracion[1]);
        }
    }

    private void generarTransacciones(){
        for (int i= 0; i<configuracionMVC.length;i++){
            listaTransacciones.put(configuracionMVC[i][0],new Transaccion(configuracionMVC[i][1],configuracionMVC[i][2],configuracionMVC[i][3]));
        }
    }

    public void execute(){
        file.execute();
    }

    public Transaccion getTransaccion(String nombreTransaccion){
        return listaTransacciones.get(nombreTransaccion);
    }

    public Logger getLogger(){
        return log;
    }

    public Connection getConexion(){
        try {
            return this.pool.getConexion();
        } catch (ExceptionConecionBaseDatos e) {
            System.out.println("Ya no se pudo retornar una conexion");
        }
        return null;
    }

    public void dejarUsoConexion(Connection con){
        this.pool.dejarUsoConexion(con);
    }

    public void cancelarConexion(Connection con){
        this.pool.cerrarConexion(con);
    }

}
