/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.batalla.naval.archivos;

import com.mycompany.batalla.naval.interfaz.NuevaPartida;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 *
 * @author dell
 */
public class Lectura {
    //extension th
    //solo para deficion de tablero 
   
     public void  LeerArchivo(NuevaPartida ventana) {
        // Abre el diálogo de selección de archivo
        JFileChooser fileChooser = new JFileChooser();
        int seleccion = fileChooser.showOpenDialog(ventana);
        
        if (seleccion == JFileChooser.APPROVE_OPTION) {
            // Obtiene el archivo seleccionado
            File archivo = fileChooser.getSelectedFile();
            
            try {
                // Lee el archivo y muestra su contenido en un diálogo
                BufferedReader lector = new BufferedReader(new FileReader(archivo));
                String linea;
                StringBuilder contenido = new StringBuilder();
                while ((linea = lector.readLine()) != null) {
                    limpiar(separarEnditad(linea));
                    contenido.append(linea);
                    contenido.append("\n");
                }
                lector.close();
                JOptionPane.showMessageDialog(ventana, contenido.toString());
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(ventana, "Error al leer el archivo");
            }
        }
    }
     
     
    public String separarEnditad(String linea) {
        String limpio = linea.replaceAll("[< | > | X]", ",");
        return limpio;
    }
  
    public ArrayList<String> limpiar(String linea) {
        ArrayList<String> lista = new ArrayList<>();
        String subCampo[] = null;
        String cadena = linea.substring(0, linea.length());
        subCampo = cadena.split(",");
        for (int i = 0; i < subCampo.length; i++) {
            String lineaLimp = subCampo[i].replace("\"", "");
            if (lineaLimp != "") {
                System.out.println(lineaLimp);
                lista.add(lineaLimp);
            }
        }

        return lista;
    }
}
