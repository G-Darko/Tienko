/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tienda.Catalogos;

import DAO.Conexion;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.*;
import tienda.IDS;
import tienda.MenuEs;

/**
 *
 * @author mps35
 */
public class Sucursal {

    JFrame fr;
    JPanel pan1, pan2, pan3, pan4;

    JLabel lbl1, lbl2, lbl3;
    JTextField txt1, txt2, txt3;
    JButton btn1, btn6;

    Connection con;
    PreparedStatement stmt;
    ResultSet tabla;
    int sw = 0;
    String sql;
    Conexion mycon = new Conexion();

    public Sucursal() {
        con = mycon.conecta();

        fr = new JFrame("Informacio de la Sucursal - Tienko");

        lbl1 = new JLabel("Nombre");
        lbl2 = new JLabel("Direccion");
        lbl3 = new JLabel("Telefono");

        txt1 = new JTextField(25);
        txt2 = new JTextField(25);
        txt3 = new JTextField(25);

        pan1 = new JPanel();
        pan2 = new JPanel();
        pan3 = new JPanel();
        pan4 = new JPanel();

        btn1 = new JButton("Guardar", new ImageIcon(getClass().getResource("/icons/grabar.png")));
        btn6 = new JButton("Volver", new ImageIcon(getClass().getResource("/icons/salir.png")));

    }

    public void usar() {
        Image icon = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/img/tienda.png"));
        fr.setIconImage(icon);
        
        pan1.setBackground(Color.WHITE);
        pan2.setBackground(Color.WHITE);
        pan3.setBackground(Color.WHITE);
        pan4.setBackground(Color.WHITE);
        
        pan1.add(lbl1);
        pan1.add(txt1);
        
        pan2.add(lbl2);
        pan2.add(txt2);
        
        pan3.add(lbl3);
        pan3.add(txt3);
        
        pan4.add(btn1);
        pan4.add(btn6);
        
        fr.add(pan1);
        fr.add(pan2);
        fr.add(pan3);
        fr.add(pan4);
        
        
        fr.setLayout(new GridLayout(4, 1));
        fr.setResizable(false);
        fr.pack();
        fr.setVisible(true);
        fr.setLocationRelativeTo(null);

        fr.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int opc = JOptionPane.showConfirmDialog(null, "¿Deseas regresar al menu?", "Salir", JOptionPane.YES_NO_OPTION);
                if (opc == 0) {
                    fr.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    MenuEs myMenu = new MenuEs();
                    myMenu.usar();
                } else {
                    fr.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                }
            }
        });
        
        btn1.addActionListener((ActionEvent ae) -> {
            guardar();
        });
        btn6.addActionListener((ActionEvent ae) -> {
            salir();
        });
        cargar();
    }
    
    public void cargar(){
        try {
            sql = "SELECT * FROM sucursal WHERE id_su = " + IDS.id_su;
            stmt = con.prepareStatement(sql);
            tabla = stmt.executeQuery();
            if (tabla.next()){
                txt1.setText(tabla.getString(2));
                txt2.setText(tabla.getString(3));
                txt3.setText(tabla.getString(4));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }
    
    public void guardar(){
         try {
            sql = "UPDATE sucursal SET "
                    + "nombre = \"" + txt1.getText() + "\", "
                    + "direccion = \"" + txt2.getText() + "\", "
                    + "telefo = \"" + txt3.getText() + "\" "
                    + "WHERE id_su = " + IDS.id_su;
            stmt = con.prepareStatement(sql);
            sw = stmt.executeUpdate();
            if (sw == 0){
                JOptionPane.showMessageDialog(null, "Error al guardar");
            }else{
                JOptionPane.showMessageDialog(null, "Informacion guardada con exito");
                cargar();
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }
    
    public void salir() {
        int opc = JOptionPane.showConfirmDialog(null, "¿Deseas regresar al menu?", "Salir", JOptionPane.YES_NO_OPTION);
        if (opc == 0) {
            fr.dispose();
            MenuEs myMenu = new MenuEs();
            myMenu.usar();
        }
    }

}
