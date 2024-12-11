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
public class GananciaGenerada {

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

    public GananciaGenerada() {
        pan1 = new JPanel();
        pan2 = new JPanel();
        panMain = new JPanel(new BorderLayout(1, 1));

        lbl = new JLabel("Ganancia de Productos (Generada)");

        fr = new JFrame("Ganancia (Generada) - Tienko");

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
        titulos[4] = "Cantidad";
        titulos[5] = "Ganancia Generada";
        titulos[6] = "Total";

        datosTab();
    }

    public void datosTab() {
        con = mycon.conecta();
        model = new DefaultTableModel(null, titulos);
        try {
            sql = ""
                    + "SELECT * FROM ventas "
                    + "INNER JOIN productos "
                    + "WHERE ventas.id_prod = productos.id_prod ";
            stmt = con.prepareStatement(sql);
            tabla = stmt.executeQuery();
            double gT = 0, tT = 0;
            while (tabla.next()) {
                datos[0] = tabla.getString(9);
                datos[1] = tabla.getString(10);
                datos[2] = tabla.getString(11);
                datos[3] = tabla.getString(12);
                datos[4] = tabla.getString(5);
                
                int cantidad = tabla.getInt(5);
                
                double precioC = tabla.getDouble(11);
                double precioV = tabla.getDouble(12);
                double gananciaU = precioV - precioC;
                double gananciaG = (precioV - precioC) * cantidad;
                datos[5] = String.format("%.2f", gananciaG);
                datos[6] = tabla.getString(6);
                model.addRow(datos);
                gT += gananciaU;
                tT += gananciaG;
            }
            datos[0] = "";
            datos[1] = "";
            datos[2] = "";
            datos[3] = "";
            datos[4] = "Total:";
            datos[5] = String.format("%.2f", gT);
            datos[6] = String.format("%.2f", tT);
            model.addRow(datos);
            tab.setModel(model);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }
}
