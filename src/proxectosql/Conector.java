/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proxectosql;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
/**
 *
 * @author femio23
 */
public class Conector {
    /**Esta clase actuará como modelo.
     * Posúe os metodos necesarios para o procesado de datos da BD.
     * A variable dirección almacena a ruta de acceso ó arquivo da base de datos.
     * O obxeto connect de clase Connection permitenos realizar as conexions e accions coa BD.
     */
    String direccion="Base de datos/creador.sqlite3";
    Connection connect;
    /**
     * Este metodo encargase de realizar a conexión coa BD.
     */
    public void conectar(){
        try{
        connect=DriverManager.getConnection("jdbc:sqlite:"+direccion);
        if(connect!=null){
            ProxectoSQL.chamada("s", "Base conectada.");
        }
        }catch(SQLException sqe1){
            ProxectoSQL.chamada("r","Erro:"+sqe1.getMessage());
        }
    }
    /**
     * O metodo pechar realiza a misión contraria ó anterior, pecha a conexión coa BD.
     */
    public void pechar(){
    try {
            connect.close();
        } catch (SQLException ex) {
            Logger.getLogger(Conector.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /**
     * O metodo consultar realiza as accións de consulta, sexa xeral ou por parámetros.
     * @param c1 Obxeto de clase Conector, necesaria para a realización dos procesos.
     * @param nome Parámetro de busca "nome", provinte do JTextField do mesmo nome.
     * @param idade Parámetro de busca "idade", provinte do JTextField do mesmo nome.
     * @param codigo Parámetro de busca "codigo", provinte do JTextField do mesmo nome.
     * @return Devolve un ArrayList de tipo Programador, cos resultados da consulta.
     */
    //Esta clase apenas recibiu cambios, únicamente se lle eliminou as chamadas 
    //a Sout e JOptionPane,para adecualas a o modelo MVC.
public ArrayList consultar(Conector c1,JTextField nome,JTextField idade,JTextField codigo){        
    ResultSet result = null;
    ArrayList <Programador>lista=new ArrayList();
    PreparedStatement st;
        try {
            
            if(idade.getText().equals("")&& codigo.getText().equals("")&& nome.getText().equals("")){
                st = connect.prepareStatement("select * from EquipoProg;");
            }else{
            if(idade.getText().equals("")&& codigo.getText().equals("")){
                st = connect.prepareStatement("select * from EquipoProg where nome='"+nome.getText()+"';");
            }else{
            st = connect.prepareStatement("select * from EquipoProg;");
            }
            if(nome.getText().equals("")&& codigo.getText().equals("")){
                st = connect.prepareStatement("select * from EquipoProg where idade="+idade.getText()+";");
            }
            if(idade.getText().equals("")&& nome.getText().equals("")){
                st = connect.prepareStatement("select * from EquipoProg where codigo='"+codigo.getText()+"';");
            }
            }
            result = st.executeQuery();
            lista.clear();
            while (result.next()) {
                 lista.add(new Programador(result.getString("nome"),result.getInt("idade"),result.getString("codigo")));
            }
        } catch (SQLException ex) {
            ProxectoSQL.chamada("r",ex.getMessage());
        }
        return lista;
        
    }
    /**
     * O metodo insertar encargase de inserir novos datos na BD.
     * @param nome Parámetro de busca "nome", provinte do JTextField do mesmo nome.
     * @param idade Parámetro de busca "idade", provinte do JTextField do mesmo nome.
     * @param codigo Parámetro de busca "codigo", provinte do JTextField do mesmo nome.
     */
    public void insertar(String nome,int idade,String codigo){
     try {
            PreparedStatement st = connect.prepareStatement("insert into EquipoProg (nome,idade,codigo) values (?,?,?)");
            st.setString(1,nome);
            st.setInt(2,idade);
            st.setString(3,codigo);
            st.execute();
            
        } catch (SQLException ex) {
            ProxectoSQL.chamada("r",ex.getMessage());
        }
    }
    /**
     * O metodo actualizar encargase de renovar datos mediante os parametros de busca inseridos.
      * @param c1 Obxeto de clase Conector, necesaria para a realización dos procesos.
     * @param nome Parámetro de busca "nome", provinte do JTextField do mesmo nome.
     * @param idade Parámetro de busca "idade", provinte do JTextField do mesmo nome.
     * @param codigo Parámetro de busca "codigo", provinte do JTextField do mesmo nome.
     */
    public void actualizar(Conector c1,JTextField nome,JTextField idade,JTextField codigo){
        PreparedStatement st;
    try {
        
            if(idade.getText().equals("")&& codigo.getText().equals("")){
                String var=JOptionPane.showInputDialog("Introduza o novo nome:");
                st = connect.prepareStatement("update EquipoProg set nome='"+var+"' where nome='"+nome.getText()+"';");
                st.executeUpdate();
            }
            if(nome.getText().equals("")&& codigo.getText().equals("")){
                String var=JOptionPane.showInputDialog("Introduza a nova idade:");
                st = connect.prepareStatement("update EquipoProg set idade="+Integer.parseInt(var)+" where idade="+idade.getText()+";");
                st.executeUpdate();
            }
            if(idade.getText().equals("")&& nome.getText().equals("")){
                String var=JOptionPane.showInputDialog("Introduza o novo codigo:");
                st = connect.prepareStatement("update EquipoProg set codigo='"+var+"' where codigo='"+codigo.getText()+"';");
                st.executeUpdate();
            }else{
                ProxectoSQL.chamada("j","Datos insuficientes, intenteo de novo");
            }
            
        } catch (SQLException ex) {
            ProxectoSQL.chamada("r",ex.getMessage());
        }
    }
    /**
     * O método borrar ocupase de eliminar as filas que coincidan cos parámetros que se lle suministran.
     * @param c1 Obxeto de clase Conector, necesaria para a realización dos procesos.
     * @param nome Parámetro de busca "nome", provinte do JTextField do mesmo nome.
     * @param idade Parámetro de busca "idade", provinte do JTextField do mesmo nome.
     * @param codigo Parámetro de busca "codigo", provinte do JTextField do mesmo nome.
     */
    public void borrar(Conector c1,JTextField nome,JTextField idade,JTextField codigo){
        PreparedStatement st;
    try {
        
        if(idade.getText().equals("")&& codigo.getText().equals("")){
            st= connect.prepareStatement("delete from EquipoProg where nome='"+nome.getText()+"';");
            st.execute();
            ProxectoSQL.chamada("s","Borrado.");
        }
        if(nome.getText().equals("")&& codigo.getText().equals("")){
            st= connect.prepareStatement("delete from EquipoProg where idade='"+idade.getText()+"';");
            st.execute();
            ProxectoSQL.chamada("s","Borrado.");
        }
        if(idade.getText().equals("")&& nome.getText().equals("")){
            st= connect.prepareStatement("delete from EquipoProg where codigo='"+codigo.getText()+"';");
            st.execute();
            ProxectoSQL.chamada("s","Borrado.");
        }

    } catch (SQLException ex) {
        ProxectoSQL.chamada("r",ex.getMessage());
        }
    }
}
