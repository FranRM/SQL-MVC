/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proxectosql;

import java.util.ArrayList;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author femio23
 */
public class ProxectoSQL {

    /**
     * Esta clase actuará como controlador.
     * Aparte das funcións de Main, posue o control de todos os metodos de tratamento dos datos da BD,
     * os cales, sonutilizados polo modelo e son aplicados en Ventá(vista).
     * @param args the command line arguments
     */
    //Orixinalmente, a unica función deste metodo era inicializar a Interface, 
    //pero agora alberga todos os metodos que o fan digno dunha clase controlador.
    public static void main(String[] args) {
        Conector con1=new Conector();
        Venta v1=new Venta();

    }
    /**
     * Metodo que controla a acción de borrado.
     * @param c1 Obxeto de clase Conector, necesaria para a realización dos procesos.
     * @param nome Parámetro de busca "nome", provinte do JTextField do mesmo nome.
     * @param idade Parámetro de busca "idade", provinte do JTextField do mesmo nome.
     * @param codigo Parámetro de busca "codigo", provinte do JTextField do mesmo nome.
     */
    public void borrado(Conector c1,JTextField nome,JTextField idade,JTextField codigo){
    
        c1.conectar();
        c1.borrar(c1, nome, idade, codigo);
        c1.pechar();
    }
    /**
     * Metodo que se encarga do control do metodo de actualizacion de datos da base de datos.
     * @param c1 Obxeto de clase Conector, necesaria para a realización dos procesos.
     * @param nome Parámetro de busca "nome", provinte do JTextField do mesmo nome.
     * @param idade Parámetro de busca "idade", provinte do JTextField do mesmo nome.
     * @param codigo Parámetro de busca "codigo", provinte do JTextField do mesmo nome.
     */
    public void actualizado(Conector c1,JTextField nome,JTextField idade,JTextField codigo){
            c1.conectar();
            c1.actualizar(c1, nome, idade, codigo);
            c1.pechar();
    }
    /**Metodo de control de consulta, que se encarga de realizar a consulta e volcado de 
     * datos da mesma nunha taboa
     * @param c1 Obxeto de clase Conector, necesaria para a realización dos procesos.
     * @param nome Parámetro de busca "nome", provinte do JTextField do mesmo nome.
     * @param idade Parámetro de busca "idade", provinte do JTextField do mesmo nome.
     * @param codigo Parámetro de busca "codigo", provinte do JTextField do mesmo nome.
     * @param modelo Parametro de tipo DefaultTableModel que contén a configuración previa
     * do modelo da taboa, ó cal lle engadiremos filas neste método.
     * @param taboa JTable que recibe as filas de datos da consulta.
     */
    public void consultado(Conector c1,JTextField nome,JTextField idade,JTextField codigo,DefaultTableModel modelo, JTable taboa){
            c1.conectar();
            modelo.setRowCount(0);
            taboa.setModel(modelo);
            ArrayList<Programador> l;
            l=c1.consultar(c1, nome, idade, codigo);
            for(Programador l1:l){
                modelo.addRow(new Object[]{l1.nome,l1.idade,l1.codigo});
            }
            taboa.setModel(modelo);
            c1.pechar();
    }
    /**Método de control do engadido de datos.
     * @param c1 Obxeto de clase Conector, necesaria para a realización dos procesos.
     * @param nome Parámetro de busca "nome", provinte do JTextField do mesmo nome.
     * @param idade Parámetro de busca "idade", provinte do JTextField do mesmo nome.
     * @param codigo Parámetro de busca "codigo", provinte do JTextField do mesmo nome.
     */
    public void engadido(Conector c1,JTextField nome,JTextField idade,JTextField codigo){
            c1.conectar();
            c1.insertar(nome.getText(),Integer.parseInt(idade.getText()),codigo.getText());
            c1.pechar();
    }
    /**
     * Este metodo actua como intermediario entre o controlador e a vista, no 
     * referente a o envio de mensaxes, sexan sout`s, ou JOptions.
     * @param signo Criterio que discrimina o tipo do mensaxe.
     * @param mensaxe 
     */
    static public void chamada(String signo, String mensaxe){
        switch (signo){
            case "j": Venta.mensaxeJ(mensaxe);
            break;
            case "r": Venta.mensaxeR(mensaxe);
            break;
            case "s": Venta.mensaxeS(mensaxe);
        }
        
    }
    
    
}
