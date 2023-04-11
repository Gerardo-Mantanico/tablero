/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.batalla.naval.clases;

import com.mycompany.batalla.naval.interfaz.Tablero;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author dell
 */
public class Button extends JFrame{
    
    //cantidad de celdas generadas 
    private int CANTIDAD_BOTONES=100;
    private Casia [][] botones;
    public  Button(){
        botones = new Casia[CANTIDAD_BOTONES][CANTIDAD_BOTONES]; 
    }
           
    
        //en este metedo es donde se crean los diferentes tipos de suelos 
    public void pintar(Tablero tablero, int tamaño){
         JPanel panel1= new  JPanel();
          JPanel panel2= new  JPanel();
        for(int i=0;i<=tamaño;i++){
            for(int j=0;j<=tamaño;j++){
         
                    
                    botones[i][j]=new Casia(55*j,55*i,50,50);
                    botones[i][j].posicion(i, j);
                    tablero.getjPanel1().add(botones[i][j]);
                    tablero.getjPanel1().setPreferredSize( new java.awt.Dimension(55*j, 55*i));
                   // tablero.getjScrollPane1().add(panel1.add(botones[i][j]));
                     //tablero.getjScrollPane1().add(panel2.add(botones[i][j]));
                    
                   // tablero.getContentPane().add(panel1.add(botones[i][j]));
                    //tablero.getContentPane().add(panel2.add(botones[i][j]));
                   
                  }}}
                
    
}
