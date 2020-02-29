/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/*
Esta clase debería de hacer la conexión a la BD, extraer la información 
y devolverla al servidor
Podemos considerar esta aplicación como una arquitectura por capas
*/

package servidor;

/**
 *
 * @author alonso
 */


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Random;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;


////////////////////////////////////////////////////////////////////////////////
public class ProcesadorCitasMedicas extends Thread
{ 

    // Referencia a un socket para enviar/recibir las peticiones/respuestas
    private Socket socketServicio;
    // stream de lectura (por aquí se recibe lo que envía el cliente)
    private InputStream inputStream;
    // stream de escritura (por aquí se envía los datos al cliente)
    private OutputStream outputStream;

    // Para que la respuesta sea siempre diferente, usamos un generador de números aleatorios.
    private Random random;

    // almacen de datos de prueba (mini-BD) para este procesador 
    protected BaseDatos almacenDatos; 


    /**************************************************************************/
    // Constructor que tiene como parámetro una referencia al socket abierto en por otra clase
    public ProcesadorCitasMedicas(Socket socketServicio, BaseDatos almacenDatosDado) {
        this.socketServicio = socketServicio;
        random = new Random();
        almacenDatos = almacenDatosDado; 	// trabajar con la mini-BD actualizada
    }
    
    
    /**************************************************************************/
    // Aquí es donde se realiza el procesamiento realmente:
    public void procesa(){

        // Como máximo leeremos un bloque de 1024 bytes. Esto se puede modificar.
        String datosRecibidos;
        int bytesRecibidos=0;

        // Array de bytes para enviar la respuesta. Podemos reservar memoria cuando vayamos a enviarka:
        String datosEnviar;		

        try {
            // Obtiene los flujos de escritura/lectura
            PrintWriter outPrinter  = new PrintWriter(socketServicio.getOutputStream(), true);
            BufferedReader inReader = new BufferedReader (new InputStreamReader (socketServicio.getInputStream()));

            // Lee la frase a Yodaficar:
            datosRecibidos = inReader.readLine();

            // PROCESAMIENTO DE LA CITA con el método citasDo
            // le devolverá una fecha de cita al cliente, la más temprana 
            //   que haya 
            String respuesta=citasDo(datosRecibidos);

            // Convertimos el String de respuesta en una array de bytes:
            datosEnviar=respuesta;

            // Enviamos la fecha de la cita: 
            outPrinter.println(datosEnviar);

        } catch (IOException e) {
                System.err.println("Error al obtener los flujos de entrada/salida.");
        }

    }


    /**************************************************************************/
    // El procesador interpreta las diferentes peticiones de citas médicas.
    private String  citasDo (String informacion)
    { 

            // 1.Descomponermos la información
            String info = informacion;
            String[] partes = info.split("#");
            String fecha = partes[0];
            String especialidad = partes[1];
            String DNI = partes[2];
            String opcionMenu = partes[3]; 

            // 2. Tratamiento de las citas
            String horaAsignada;

            if (almacenDatos.compruebaDNI(DNI)){	

                    if ("1".equals(opcionMenu))
                    {
                            System.out.println("OPCION 1"); 

                            if (almacenDatos.comprobarEspecialidad(especialidad)){	 

                                    if(almacenDatos.disponibilidadFecha(fecha, especialidad)){ 

                                            // 4. Comprobamos la hora
                                            horaAsignada = almacenDatos.obtenerHoraCita(fecha, especialidad);

                                            return ("Su cita es a las: " + horaAsignada); 
                                    } 
                                    else{
                                            return ("No hay citas disponibles en esta fecha.");

                                    } 

                            } // if (especialidad correcta)
                            else{
                                    return ("Para la especialidad " + especialidad + " no hay citas disponibles.");
                            }
                    } // fin opcion de pedir cita

                    else if ("2".equals (opcionMenu)  )
                    {
                            System.out.println("OPCION 2"); 

                            String horaAInvalidar = new String(); 

                            horaAInvalidar = partes[4]; 

                            if (almacenDatos.comprobarEspecialidad(especialidad) )
                            {
                                    if (almacenDatos.anularHora(fecha, especialidad, horaAInvalidar)){
                                            return ("Hora anulada");
                                    }
                                    else{
                                            return ("No se ha podido anular esa cita (hora)"); 
                                    }

                            }
                            else 
                            {
                                    return ("La especialidad introducida" + especialidad + " no es correcta.");					
                            }
                    }

            }
            //else{
                    return ("El DNI introducido no se encuentra en la base de datos.");
            //}

    }

    /**************************************************************************/
    public BaseDatos getDatosActualizados()
    {
            return (almacenDatos); 
    }
    /**************************************************************************/

}

