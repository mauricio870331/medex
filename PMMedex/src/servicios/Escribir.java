package servicios;

/**
 *
 * @author Mauricio
 */
/*Clase que permite escribir en un archivo de texto*/
//Importamos clases que se usaran
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import javax.swing.JOptionPane;

public class Escribir {
    Date fecha;
    public boolean escribir() {
//Un texto cualquiera guardado en una variable
        fecha = new Date();
        long currentTimeMillis = System.currentTimeMillis();
        try {
//Crear un objeto File se encarga de crear o abrir acceso a un archivo que se especifica en su constructor
            File archivo = new File("src/servicios/id_program.txt");
//Escribimos en el archivo con el metodo write
            try (//Crear objeto FileWriter que sera el que nos ayude a escribir sobre archivo
                    FileWriter escribir = new FileWriter(archivo, true)) {
                //Escribimos en el archivo con el metodo write
                escribir.write(Long.toString(currentTimeMillis));
                JOptionPane.showMessageDialog(null, "Se creo la llave del programa");
                return true;
            }
        } //Si existe un problema al escribir cae aqui
        catch (IOException e) {
            System.out.println("Error al escribir");
            return false;
        }
    }
}
