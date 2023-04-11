/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.batalla.naval.Recursos;

import com.mycompany.batalla.naval.archivos.Lectura;
import com.mycompany.batalla.naval.interfaz.ListaTablero;
import com.mycompany.batalla.naval.interfaz.MenuPrincipal;
import com.mycompany.batalla.naval.interfaz.NuevaPartida;

/**
 *
 * @author HP
 */
public class ManejarVentana {
   
    private MenuPrincipal menu = new MenuPrincipal(this);
    
    public void RunMenuPrincipal(){
      MenuPrincipal menu = new MenuPrincipal(this);
      this.menu=menu;
      menu.setVisible(true);
      menu.setLocationRelativeTo(null);
      menu.getjButton2().setVisible(false);
      
    }
    
    public void crearPartida(){
        menu.setVisible(false);
        NuevaPartida partida = new NuevaPartida(this);
        partida.setVisible(true);
        partida.setLocationRelativeTo(null);
        
    }
    
    public void menuPrincipal(){
         menu.setVisible(true);
         menu.getjButton2().setVisible(true);
         
    }
    
    public void LeerArchivoEntrada(NuevaPartida ventana){
        Lectura lectura = new Lectura();
        lectura.LeerArchivo(ventana);
        ListaTablero nuevo = new ListaTablero(this);
        ventana.setVisible(false);
        nuevo.setVisible(true);
        nuevo.setLocationRelativeTo(null);
        
    }
    
}
