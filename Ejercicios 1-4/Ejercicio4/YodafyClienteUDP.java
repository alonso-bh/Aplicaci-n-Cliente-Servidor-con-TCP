//
// YodafyServidorIterativo
// (CC) jjramos, 2012
//
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class YodafyClienteUDP {

	public static void main(String[] args) {
		int bytesLeidos=0;
		
		// Nombre del host donde se ejecuta el servidor:
		String host="localhost";
		// Puerto en el que espera el servidor:
		int port=8989;
		
		// Socket para la conexión UDP
		Socket socketServicio=null;
		
		String anfitrion = "localhost";

		InetAddress direccion;
		DatagramPacket paquete;

		byte[] buferEnvio = new byte[256];
		byte[] buferRecepcion = new byte[256]; 
		DatagramSocket socket;

		try {
			// Creamos un socket que se conecte a "hist" y "port":
			//////////////////////////////////////////////////////
			// socketServicio= ... (Completar)
			//socketServicio = new Socket (anfitrion, port); 
			socket = new DatagramSocket();
			//////////////////////////////////////////////////////			
			
			direccion = InetAddress.getByName("localhost");

			//InputStream inputStream = socketServicio.getInputStream();
			//OutputStream outputStream = socketServicio.getOutputStream();
			
			//PrintWriter outPrinter = new PrintWriter(socketServicio.getOutputStream(), true);
			//BufferedReader inReader = new BufferedReader (new InputStreamReader (socketServicio.getInputStream()));

			// Si queremos enviar una cadena de caracteres por un OutputStream, hay que pasarla primero
			// a un array de bytes:
			buferEnvio="Al monte del volcán debes ir sin demora".getBytes();
			
			// Enviamos el array por el outputStream;
			//////////////////////////////////////////////////////
			// ... .write ... (Completar)
			//outputStream.write(buferEnvio, 0, buferEnvio.length);
			//outPrinter.println(buferEnvio);
			//////////////////////////////////////////////////////
			
			paquete = new DatagramPacket(buferEnvio, buferEnvio.length, direccion, port);


			socket.send(paquete);

			
			//////////////////////////////////////////////////////
			// ... .flush(); (Completar)
			//outPrinter.flush();
			//////////////////////////////////////////////////////
			
			// Leemos la respuesta del servidor. Para ello le pasamos un array de bytes, que intentará
			// rellenar. El método "read(...)" devolverá el número de bytes leídos.
			//////////////////////////////////////////////////////
			// bytesLeidos ... .read... buferRecepcion ; (Completar)
			//buferRecepcion = inReader.readLine();

			paquete = new DatagramPacket(buferRecepcion, buferRecepcion.length);
			socket.receive(paquete);
			paquete.getData();
			paquete.getAddress();
			paquete.getPort();

			
			//////////////////////////////////////////////////////
			
			// Mostremos la cadena de caracteres recibidos:
			System.out.println("Recibido: ");
			for(int i=0;i<paquete.getData().length;i++){
				System.out.print((char)paquete.getData()[i]);
			}
			
			System.out.println(""); 


			//System.out.println(buferRecepcion);

		
			// Una vez terminado el servicio, cerramos el socket (automáticamente se cierran
			// el inpuStream  y el outputStream)
			//////////////////////////////////////////////////////
			// ... close(); (Completar)
			socket.close();
			//////////////////////////////////////////////////////

			// Excepciones:
		} catch (UnknownHostException e) {
			System.err.println("Error: Nombre de host no encontrado.");
		} catch (IOException e) {
			System.err.println("Error de entrada/salida al abrir el socket.");
		}
	}
}
