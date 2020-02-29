//
// YodafyServidorIterativo
// (CC) jjramos, 2012
//
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Random;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

//
// Nota: si esta clase extendiera la clase Thread, y el procesamiento lo hiciera el método "run()",
// ¡Podríamos realizar un procesado concurrente! 
//
public class ProcesadorYodafy extends Thread{
	// Referencia a un socket para enviar/recibir las peticiones/respuestas
	//private Socket socketServicio;
	// stream de lectura (por aquí se recibe lo que envía el cliente)
	//private InputStream inputStream;
	// stream de escritura (por aquí se envía los datos al cliente)
	//private OutputStream outputStream;
	
	// Para que la respuesta sea siempre diferente, usamos un generador de números aleatorios.
	private Random random;
	
	private int Id;

	private DatagramPacket paquete;
	private DatagramSocket socketServicio;

	// Constructor que tiene como parámetro una referencia al socket abierto en por otra clase
	public ProcesadorYodafy(DatagramPacket packet) {
		this.paquete = packet;
		random = new Random();
		//this.Id = id;
	}
	
	// Aquí es donde se realiza el procesamiento realmente:
	@Override
	public void run(){
		
		// Como máximo leeremos un bloque de 1024 bytes. Esto se puede modificar.
		byte [] datosRecibidos = new byte[1024];
		byte [] datosEnviar;
		
		// Array de bytes para enviar la respuesta. Podemos reservar memoria cuando vayamos a enviarka:
		//String datosEnviar;
		
		
		try {
			// Obtiene los flujos de escritura/lectura
//			inputStream=socketServicio.getInputStream();
//			outputStream=socketServicio.getOutputStream();

			//PrintWriter outPrinter = new PrintWriter(socketServicio.getOutputStream(), true);
			//BufferedReader inReader = new BufferedReader (new InputStreamReader (socketServicio.getInputStream()));
			
			// Lee la frase a Yodaficar:
			////////////////////////////////////////////////////////
			// read ... datosRecibidos.. (Completar)
			datosRecibidos = paquete.getData();
			////////////////////////////////////////////////////////
			
			String peticion = new String(datosRecibidos, 0, datosRecibidos.length);

			// Yoda hace su magia:
			// Creamos un String a partir de un array de bytes de tamaño "bytesRecibidos":
			//String peticion=new String(datosRecibidos);
			// Yoda reinterpreta el mensaje:
			String respuesta=yodaDo(peticion);
			// Convertimos el String de respuesta en una array de bytes:
			datosEnviar=respuesta.getBytes();
			
			// Enviamos la traducción de Yoda:
			////////////////////////////////////////////////////////
			// ... write ... datosEnviar... datosEnviar.length ... (Completar)
			//outPrinter.println(datosEnviar);
			////////////////////////////////////////////////////////
			
			socketServicio = new DatagramSocket();
			paquete = new DatagramPacket(datosEnviar, datosEnviar.length, paquete.getAddress(), paquete.getPort());
			socketServicio.send(paquete);

			socketServicio.close();
			
			
		} catch (IOException e) {
			System.err.println("Error al obtener los flujso de entrada/salida.");
		}

	}

	// Yoda interpreta una frase y la devuelve en su "dialecto":
	private String yodaDo(String peticion) {
		// Desordenamos las palabras:
		String[] s = peticion.split(" ");
		String resultado="";
		
		for(int i=0;i<s.length;i++){
			int j=random.nextInt(s.length);
			int k=random.nextInt(s.length);
			String tmp=s[j];
			
			s[j]=s[k];
			s[k]=tmp;
		}
		
		resultado=s[0];
		for(int i=1;i<s.length;i++){
		  resultado+=" "+s[i];
		}
		
		return resultado;
	}
}
