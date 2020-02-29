/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cliente;

import java.awt.Container;
import java.awt.event.ActionListener;
import java.io.* ; 
import java.net.Socket;
import java.net.UnknownHostException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class ClienteCitasMedicasTCP extends Thread  {  //implements ActionListener{
    
    String buferRecepcion = new String() ;

    public String getBuferRecepcion() {
        return buferRecepcion;
    }

    public void setBuferRecepcion(String buferRecepcion) {
        this.buferRecepcion = buferRecepcion;
    }

    public static BufferedReader getEntrada() {
        return entrada;
    }

    public static void setEntrada(BufferedReader entrada) {
        ClienteCitasMedicasTCP.entrada = entrada;
    }
    
    // creamos un objeto buffer para leer desde teclado a un String 
    public static BufferedReader entrada = new BufferedReader(new InputStreamReader(System.in)); 

    public ClienteCitasMedicasTCP ()
    {
        System.out.println("Constructor... que no hace nada");
    }
    
    
    //public static void main(String[] args) {
    public  void procesar (String buferEnvio) {    
        
        // String buferEnvio = new String();
        
        int bytesLeidos=0;

        // Nombre del host donde se ejecuta el servidor:
        String host="localhost";
        // Puerto en el que espera el servidor:
        int port=8989;

        // Socket para la conexión TCP
        Socket socketServicio=null;

        Socket socket;
        String anfitrion = "localhost";

        try {
            // Creamos un socket que se conecte a "host" y "port":
            socketServicio = new Socket (anfitrion, port); 			

            // para flujos de R/W del socket (conexión con el servidor)
            PrintWriter outPrinter  = new PrintWriter(socketServicio.getOutputStream(), true);
            BufferedReader inReader = new BufferedReader (new InputStreamReader (socketServicio.getInputStream()));

            // Si queremos enviar una cadena de caracteres por un OutputStream, hay que pasarla primero
            // a un array de bytes:

            System.out.println("VA A SOLICITAR UNA CITA MÉDICA AL SISTEMA. ");


            // ----------------------------------------------
            // 1. LECTURA DE DATOS POR TECLADO 
            // ----------------------------------------------

            // leer FECHA 
            /*String cadDia 			= new String(); 
            String cadMes 			= new String();
            String cadAnio 			= new String();
            String DNI 				= new String(); 
            String cadEspecialidad  = new String(); 
            String opcionMenu 		= new String();


            System.out.println("¿Que desea hacer?: ");
            System.out.println("  1) Pedir cita");
            System.out.println("  2) Anular cita");
            System.out.println("  3) Salir del programa incondicionalmente"); 


            boolean valida = false;

            while (!valida)
            {			
                    System.out.println("Introduzca una opcion valida: 1, 2 o 3");
                    opcionMenu  = new String(entrada.readLine()); 
                    if ("1".equals(opcionMenu) || "2".equals(opcionMenu) || "3".equals(opcionMenu))
                    {
                            valida = true;
                    }
            }	

            if ("1".equals(opcionMenu))
            {
                    System.out.println("Seleccionada opcion PEDIR CITA");
            }
            else if ("2".equals(opcionMenu))
            {
                    System.out.println("Seleccionada opcion PEDIR CITA");
            }
            else if  ("3".equals(opcionMenu))
            {
                    System.out.println("Ha decidido salir del programa"); 
                    System.exit(0); 
            }
            else{
                    System.out.println("Opcion incorrecta "); 
                    System.exit(0); 
            }


            System.out.println("Introduzca día: ");
            cadDia  = new String(entrada.readLine()); 

            System.out.println("Introduzca el mes: ");
            cadMes  = new String(entrada.readLine());  

            System.out.println("Introduzca el anio (cuatro digitos): ");
            cadAnio = new String(entrada.readLine()); 

            buferEnvio = buferEnvio + cadDia + "/" + cadMes + "/" + cadAnio + "#";

            // leer especialidad
            System.out.println("\nIntroduzca la especialidad en mayúsculas y sin acentos");

            cadEspecialidad = new String(entrada.readLine()); 
            buferEnvio = buferEnvio + cadEspecialidad+ "#";

            // leer DNI
            System.out.println("Escriba su DNI (numeros seguidos de la letra sin separadores): ");

            do{
                    DNI = new String(entrada.readLine()); 
            } while (DNI.length() != 9);

            buferEnvio = buferEnvio+ DNI + "#";		

            buferEnvio = buferEnvio + opcionMenu + "#";

            String fecha = new String();

            if ("2".equals(opcionMenu)){
                    System.out.println("Introduzca la hora a anular en esa fecha, dos digitos separados por el simbolo ':' ");
                    fecha = new String(entrada.readLine()); 
                    buferEnvio = buferEnvio + fecha; 
            }
            
            */ 


            // ----------------------------------------------
            // 2. MANEJO DEL SOCKET Y LOS FLUJOS DE DATOS 
            // ----------------------------------------------


            // Enviamos el array por el socket;
            outPrinter.println(buferEnvio);

            // usar "flush()" para obligar a TCP a que no espere para hacer el envío:
            outPrinter.flush();

            // Leemos la respuesta del servidor. 
            buferRecepcion = inReader.readLine();

            // MOstremos la cadena de caracteres recibidos:
            System.out.println("Recibido: ");
            System.out.println(buferRecepcion);

            // Una vez terminado el servicio, cerramos el socket 
            // (automáticamente se cierran el inpuStream  y el outputStream)
            socketServicio.close();


                // Excepciones:
        } catch (UnknownHostException e) {
                System.err.println("Error: Nombre de host no encontrado.");
        } catch (IOException e) {
                System.err.println("Error de entrada/salida al abrir el socket.");
        }
    }
}

