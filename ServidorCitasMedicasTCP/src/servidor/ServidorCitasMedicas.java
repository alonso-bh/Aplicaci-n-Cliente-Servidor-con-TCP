package servidor;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author alonso
 */


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;



public class ServidorCitasMedicas {
    public static BaseDatos almacenDatos;  //= new BaseDatos();  		// base de datos (para las pruebas)

    public static void main(String[] args) {
        almacenDatos = new BaseDatos(); 

        // Puerto de escucha
        int port=8989;
        // array de bytes auxiliar para recibir o enviar datos.
        byte[ ]buffer=new byte[256];
        // Número de bytes leídos
        int bytesLeidos=0;

        ServerSocket socketServidor;
        Socket socketServicio = null;


        try {
            // Abrimos el socket en modo pasivo, escuchando el en puerto indicado por "port"
            socketServidor = new ServerSocket (port);

            System.out.println("------------------------------------------------------------"); 
            System.out.println("   SERVIDOR DE CITAS MÉDICAS: Solicitar o anular una cita");
            System.out.println("------------------------------------------------------------"); 

            // Mientras ... siempre!
            do {

                // Aceptamos una nueva conexión con accept()
                socketServicio = socketServidor.accept();

                ProcesadorCitasMedicas procesador = new ProcesadorCitasMedicas(socketServicio, almacenDatos);
                procesador.procesa();

                // tras el procesamiento del mensaje ...
                // actualizamos la base de datos 
                almacenDatos = new BaseDatos(procesador.getDatosActualizados());

            } while (true) ;

        } catch (IOException e) {
                System.err.println("Error al escuchar en el puerto "+port);
        }

    }

}



