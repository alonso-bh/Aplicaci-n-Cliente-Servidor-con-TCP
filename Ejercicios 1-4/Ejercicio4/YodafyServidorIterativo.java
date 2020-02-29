import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.DatagramSocket;
import java.net.DatagramPacket;

//
// YodafyServidorIterativo
// (CC) jjramos, 2012
//
public class YodafyServidorIterativo {

	public static void main(String[] args) {
	
		// Abrimos el DatagramSocket en modo pasivo
		DatagramSocket socketServidor;
		DatagramPacket respuesta;

		// Puerto de escucha
		int port=8989;
		// array de bytes auxiliar para recibir o enviar datos.
		byte []buffer=new byte[256];
		// Número de bytes leídos
		int bytesLeidos=0;

		//ServerSocket socketServidor;
		//Socket socketServicio = null;

		//int numHebras = 0;

		//ProcesadorYodafy hebra = new ProcesadorYodafy(socketServicio, 22);

		try {
			// Abrimos el socket en modo pasivo, escuchando el en puerto indicado por "port"
			//////////////////////////////////////////////////
			// ...serverSocket=... (completar)
			socketServidor = new DatagramSocket (port);
			//////////////////////////////////////////////////
			
			// Mientras ... siempre!
			do {

				// Aceptamos una nueva conexión con accept()
				/////////////////////////////////////////////////
				// socketServicio=... (completar)
				respuesta = new DatagramPacket(buffer, buffer.length);
				socketServidor.receive(respuesta);
				//////////////////////////////////////////////////
				
				// Creamos un objeto de la clase ProcesadorYodafy, pasándole como 
				// argumento el nuevo socket, para que realice el procesamiento
				// Este esquema permite que se puedan usar hebras más fácilmente.
				//ProcesadorYodafy procesador=new ProcesadorYodafy(socketServicio);
				//procesador.procesa();

				//numHebras ++;
				ProcesadorYodafy procesador = new ProcesadorYodafy(respuesta);
				procesador.start();
				

			} while (true);
			
			
		} catch (IOException e) {
			System.err.println("Error al escuchar en el puerto " + port);
		}

	}

}
