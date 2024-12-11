/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tienda.Procesos;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.*;
import javax.swing.table.DefaultTableModel;

import DAO.Conexion;
import tienda.*;

/**
 *
 * @author mps35
 */
public class Ganancia {

    JFrame fr;
    JPanel pan1, pan2, panMain;

    JLabel lbl;
    JTextField txt;
    JButton btn;

    JTable tab;
    JScrollPane scrollPane;
    DefaultTableModel model;

    String[] titulos = new String[7];
    String[] datos = new String[7];

    Connection con;
    PreparedStatement stmt;
    ResultSet tabla;
    String sql = "";
    int sw = 0;
    Conexion mycon = new Conexion();

    public Ganancia() {
        pan1 = new JPanel();
        pan2 = new JPanel();
        panMain = new JPanel(new BorderLayout(1, 1));

        lbl = new JLabel("Ganancia de Productos (Proyectada)");

        fr = new JFrame("Ganancia (Proyectada) - Tienko");

        tab = new JTable();
        scrollPane = new JScrollPane(tab);

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
    }

    public void usar() {
        Image icon = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/img/tienda.png"));
        fr.setIconImage(icon);
        
        tab.getTableHeader().setBackground(Color.WHITE);
        tab.setGridColor(Color.BLACK);

        pan1.setPreferredSize(new Dimension(500, 30));

        pan1.setBackground(Color.WHITE);
        pan2.setBackground(Color.WHITE);

        pan1.add(lbl);

        pan2.add(scrollPane);

        panMain.add(pan1, BorderLayout.NORTH);
        panMain.add(pan2, BorderLayout.CENTER);

        fr.add(panMain);

        fr.setLayout(new GridLayout(1, 1));
        //fr.setResizable(false);
        fr.pack();
        fr.setVisible(true);
        fr.setLocationRelativeTo(null);
        
        titulos[0] = "SKU";
        titulos[1] = "Nombre";
        titulos[2] = "Precio Compra";
        titulos[3] = "Precio Venta";
        titulos[4] = "Stock";
        titulos[5] = "Ganancia por Unidad";
        titulos[6] = "Ganancia por Stock";

        datosTab();
    }

    public void datosTab() {
        con = mycon.conecta();
        model = new DefaultTableModel(null, titulos);
        try {
            sql = "SELECT * FROM productos "
                    ;
            stmt = con.prepareStatement(sql);
            tabla = stmt.executeQuery();
            double gUT = 0, gST = 0;
            while (tabla.next()) {
                datos[0] = tabla.getString(2);
                datos[1] = tabla.getString(3);
                datos[2] = tabla.getString(4);
                datos[3] = tabla.getString(5);
                datos[4] = tabla.getString(6);
                
                int stock = tabla.getInt(6);
                
                double precioC = tabla.getDouble(4);
                double precioV = tabla.getDouble(5);
                double gananciaU = precioV - precioC;
                double gananciaS = (precioV - precioC) * stock;
                datos[5] = String.format("%.2f", gananciaU);
                datos[6] = String.format("%.2f", gananciaS);
                model.addRow(datos);
                gUT += gananciaU;
                gST += gananciaS;
            }
            datos[0] = "";
            datos[1] = "";
            datos[2] = "";
            datos[3] = "";
            datos[4] = "Total:";
            datos[5] = String.format("%.2f", gUT);
            datos[6] = String.format("%.2f", gST);
            model.addRow(datos);
            tab.setModel(model);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }
}
