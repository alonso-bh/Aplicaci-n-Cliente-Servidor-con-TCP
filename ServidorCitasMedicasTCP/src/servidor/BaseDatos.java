/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidor;

/**
 *
 * @author alonso
 */


import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
//import  javafx.util.Pair;
import java.util.concurrent.PriorityBlockingQueue;


////////////////////////////////////////////////////////////////////////////////
public class BaseDatos
{
    
    String[] DNI;
    String[] especialidades;
    String[][] diasDisponibles;
    boolean [][] diasDisponiblesActivos;
    String[][] horasDisponibles;
    boolean[][] horasDisponiblesActivas;

    // datos fijos sobre el número de datos de la "base de datos"
    private  int NUM_PACIENTES = 5;         // tamaño del array de DNI
    private  int NUM_FECHAS_POR_ESPECIALIDAD = 20 ; 
    private  int NUM_HORAS_POR_FECHA = 3;   
    private  int NUM_ESPECIALIDADES = 4;


    /**************************************************************************/
    public BaseDatos ()
    {
        rellenarBD();
    } 

    
    // constructor de copia 
    public BaseDatos (BaseDatos otra)
    {
        DNI = otra.DNI;
        especialidades = otra.especialidades;
        diasDisponibles = otra.diasDisponibles;
        horasDisponibles = otra.horasDisponibles;
        horasDisponiblesActivas = otra.horasDisponiblesActivas;
        diasDisponiblesActivos = otra.diasDisponiblesActivos; 
    }

    public boolean comprobarEspecialidad (String especialidad )
    {
        boolean apta = false; 
        for (int i=0; i< especialidades.length; i++)
        {
            if (especialidad.equals(especialidades[i]))
                apta= true; 
        }

        return apta; 
    }



    public boolean disponibilidadFecha(String fecha, String especialidad){
        boolean fechaDisponible = false; 
        int indice_fecha = -1; 

        // buscar el índice (columna) de la fecha dada 
        // para poder ver en la tabla de horas si esa fecha tiene horas
        //   disponibles 
        for (int i=0; i < NUM_ESPECIALIDADES; i++)
        {
            for (int j=0; j < NUM_FECHAS_POR_ESPECIALIDAD; j++)
            {
                if ( (fecha.equals(diasDisponibles[i][j])) && (especialidad.equals(especialidades[i]) ) )
                {    
                    indice_fecha = j;  // la columna de esa fecha 
                }
            }
        }

        boolean salir = false; 

        if (indice_fecha != -1)     // true: hemos encontrado la fecha
        { 
            for (int i=0; i < NUM_HORAS_POR_FECHA && !salir; i++){ 
                // buscar si quedan HORAS LIBRES Para la fecha "indice_fecha"
                if (  horasDisponiblesActivas[indice_fecha][i] == true ){ 
                    fechaDisponible= true;
                    salir = true; 
                }
            }
        }
        else 
            fechaDisponible= false; 


        return fechaDisponible; 
    
    }
    
    
    // precondición: se sabe ya que hay disponibilidad de esa fecha 
    public String obtenerHoraCita(String fecha, String especialidad)
    {        
        String hora_rastreada = new String();
        int indice_fecha = -1; 

        // buscamos la fecha pedida, que sabemos que es correcta por la precond.
	for (int i=0; i < NUM_ESPECIALIDADES; i++)
        {
            for (int j=0; j < NUM_FECHAS_POR_ESPECIALIDAD; j++)
            {
                if ( (fecha.equals(diasDisponibles[i][j]))   && 
                     (especialidad.equals(especialidades[i]) )  
                   )
                {
                    indice_fecha = j;  // la columna de esa fecha 
                }
            }
        }

        boolean salir = false; 

        // asignar cita (hora) y marcar en la matriz de horas como NO DISPONIBLE
        for (int i=0; i < NUM_HORAS_POR_FECHA && !salir; i++){ 
            // buscar si quedan HORAS LIBRES Para la fecha "indice_fecha"
            if (horasDisponiblesActivas[indice_fecha][i] == true )
            { 
                hora_rastreada = horasDisponibles[indice_fecha][i];
                horasDisponiblesActivas[indice_fecha][i] = false;
                salir = true; 
            }
        }
        return hora_rastreada; 

    }

    public boolean anularHora(String fecha, String especialidad, String hora){
        int indice_fecha=-1; 
        

        for (int i=0; i < NUM_ESPECIALIDADES; i++)
        {
            for (int j=0; j < NUM_FECHAS_POR_ESPECIALIDAD; j++)
            {
                if ( (fecha.equals(diasDisponibles[i][j]))   && 
                     (especialidad.equals(especialidades[i]) )  )
                {
                    indice_fecha = j;  // la columna de esa fecha 
                }
            }
        }

        boolean salir = false; 
        boolean encontrada_y_anulada = false; 

        // poner a true la hora 

        for (int i=0; i < NUM_HORAS_POR_FECHA && !salir; i++){ 
            // buscar si quedan HORAS LIBRES Para la fecha "indice_fecha"
            if (horasDisponiblesActivas[indice_fecha][i] == false && hora.equals(horasDisponibles[indice_fecha][i]) )
            { 
                horasDisponiblesActivas[indice_fecha][i] = true;
                salir = true; 
                encontrada_y_anulada = true; 
            }
        }

        return (encontrada_y_anulada); 
    }

    public boolean compruebaDNI(String DNI_entrada)
    {
        boolean encontrado=false;
        boolean estaDNI = false;

        for (int i=0 ; i<NUM_PACIENTES && !encontrado; i++){
            if(DNI_entrada.equals(DNI[i]))
            {  
                estaDNI = true;
                encontrado = true;
            }
            else
            {
                estaDNI = false;
            }
        }

        return estaDNI;
    }

    // método privado para rellenar con datos de prueba la mini-BD
    private void rellenarBD()
    {
        // rellenar lista de DNI
        DNI = new String[NUM_PACIENTES];
        DNI[0] = "12121212A";
        DNI[1] = "22223333B";
        DNI[2] = "29293939C";
        DNI[3] = "33332333F";
        DNI[4] = "90909090E";


        // rellenar lista de especialidades
        especialidades = new String [NUM_ESPECIALIDADES];
        especialidades[0] = "TRAUMATOLOGIA";
        especialidades[1] = "CARDIOLOGIA";
        especialidades[2] = "MEDICINA GENERAL";
        especialidades[3] = "NEUROLOGIA"; 

        // rellenar lista de FECHAS DISPONIBLES
        //Primera especialidad 
        diasDisponibles = new String[NUM_ESPECIALIDADES][NUM_FECHAS_POR_ESPECIALIDAD];

        diasDisponibles[0][0] = "15/01/2019";
        diasDisponibles[0][1] = "15/05/2019";
        diasDisponibles[0][2] = "20/10/2020";
        diasDisponibles[0][3] = "12/12/2019";
        diasDisponibles[0][4] = "04/02/2020";
        diasDisponibles[0][5] = "18/11/2019";
        diasDisponibles[0][6] = "19/09/2020";
        diasDisponibles[0][7] = "03/04/2019";
        diasDisponibles[0][8] = "05/07/2019";
        diasDisponibles[0][9] = "30/11/2020";
        //Segunda especialidad
        diasDisponibles[1][0] = "15/06/2019";
        diasDisponibles[1][1] = "03/04/2020";
        diasDisponibles[1][2] = "10/03/2020";
        diasDisponibles[1][3] = "20/12/2019";
        diasDisponibles[1][4] = "16/11/2020";
        diasDisponibles[1][5] = "03/08/2019";
        diasDisponibles[1][6] = "13/11/2019";
        diasDisponibles[1][7] = "03/04/2019";
        diasDisponibles[1][8] = "15/10/2020";
        diasDisponibles[1][9] = "03/04/2020";
        //Tercera especialidad
        diasDisponibles[2][0] = "30/10/2019";
        diasDisponibles[2][1] = "03/04/2020";
        diasDisponibles[2][2] = "14/06/2020";
        diasDisponibles[2][3] = "21/04/2019";
        diasDisponibles[2][4] = "16/11/2019";
        diasDisponibles[2][5] = "16/10/2019";
        diasDisponibles[2][6] = "28/09/2019";
        diasDisponibles[2][7] = "21/07/2019";
        diasDisponibles[2][8] = "27/11/2020";
        diasDisponibles[2][9] = "02/04/2019";
        //Cuarta especialidad
        diasDisponibles[3][0] = "05/06/2019";
        diasDisponibles[3][1] = "13/04/2020";
        diasDisponibles[3][2] = "10/11/2019";
        diasDisponibles[3][3] = "03/09/2019";
        diasDisponibles[3][4] = "12/11/2020";
        diasDisponibles[3][5] = "06/03/2019";
        diasDisponibles[3][6] = "26/09/2020";
        diasDisponibles[3][7] = "24/07/2019";
        diasDisponibles[3][8] = "22/10/2020";
        diasDisponibles[3][9] = "03/04/2020";

        // rellenar matriz de booleanos para la tabla de fechas
        diasDisponiblesActivos = new boolean[NUM_ESPECIALIDADES][NUM_FECHAS_POR_ESPECIALIDAD];

        for(int i=0; i < NUM_ESPECIALIDADES; i++ )
        {
            for (int j=0; j< NUM_FECHAS_POR_ESPECIALIDAD; j++ )
            {
                diasDisponiblesActivos[i][j] = true;
            }
        } 

        // rellenar lista de HORAS disponibles 
        horasDisponibles = new String[NUM_FECHAS_POR_ESPECIALIDAD][NUM_HORAS_POR_FECHA];
        horasDisponibles[0][0] = "12:40";
        horasDisponibles[0][1] = "13:30";
        horasDisponibles[0][2] = "17:40";

        horasDisponibles[1][0] = "09:05";
        horasDisponibles[1][1] = "11:40";
        horasDisponibles[1][2] = "13:20";

        horasDisponibles[2][0] = "13:40";
        horasDisponibles[2][1] = "16:30";
        horasDisponibles[2][2] = "17:20";

        horasDisponibles[3][0] = "08:10";
        horasDisponibles[3][1] = "18:40";
        horasDisponibles[3][2] = "19:00";

        horasDisponibles[4][0] = "16:40";
        horasDisponibles[4][1] = "17:30";
        horasDisponibles[4][2] = "18:20";

        horasDisponibles[5][0] = "12:00";
        horasDisponibles[5][1] = "17:40";
        horasDisponibles[5][2] = "20:10";

        horasDisponibles[6][0] = "09:40";
        horasDisponibles[6][0] = "10:20";
        horasDisponibles[6][0] = "11:35";

        horasDisponibles[7][0] = "09:05";
        horasDisponibles[7][1] = "11:20";
        horasDisponibles[7][2] = "13:35";

        horasDisponibles[8][0] = "11:40";
        horasDisponibles[8][1] = "15:30";
        horasDisponibles[8][2] = "16:00";

        horasDisponibles[9][0] = "12:40";
        horasDisponibles[9][1] = "13:25";
        horasDisponibles[9][1] = "13:45";

        // rellenar matriz de booleanos para la tabla de horas por fecha
        horasDisponiblesActivas = new boolean [NUM_FECHAS_POR_ESPECIALIDAD][NUM_HORAS_POR_FECHA]; 
        for(int i=0; i < NUM_FECHAS_POR_ESPECIALIDAD; i++)
        {
            for (int j=0; j < NUM_HORAS_POR_FECHA; j++)
                horasDisponiblesActivas[i][j] = true; 
        }
    }
    ////////////////////////////////////////////////////////////////////////////
}

