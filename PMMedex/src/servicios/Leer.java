/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicios;

/**
 *
 * @author Mauricio
 */
/*Clase que permite leer un archivo de texto*/
//Importamos clases a usar
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;

public class Leer {

    public String leer() {
//Creamos un String que va a contener todo el texto del archivo
        String texto = "";

        try {
//Creamos un archivo FileReader que obtiene lo que tenga el archivo
            FileReader lector = new FileReader("src/servicios/id_program.txt");

//El contenido de lector se guarda en un BufferedReader
            BufferedReader contenido = new BufferedReader(lector);

//Con el siguiente ciclo extraemos todo el contenido del objeto "contenido" y lo mostramos
            if ((texto = contenido.readLine()) != null) {
                return texto;
            }

        } //Si se causa un error al leer cae aqui
        catch (IOException e) {
            texto = "Error al leer";
        }
        return texto;
    }
}
