/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tienda.Consultas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
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
public class MarcasC {

    JFrame fr;
    JPanel pan1, pan2, panMain;

    JLabel lbl;
    JTextField txt;
    JButton btn;

    JTable tab;
    JScrollPane scrollPane;
    DefaultTableModel model;

    String[] titulos = new String[2];
    String[] datos = new String[2];

    Connection con;
    PreparedStatement stmt;
    ResultSet tabla;
    String sql = "";
    int sw = 0;
    Conexion mycon = new Conexion();

    public MarcasC() {
        pan1 = new JPanel();
        pan2 = new JPanel();
        panMain = new JPanel(new BorderLayout(1, 1));

        lbl = new JLabel("Buscar");
        txt = new JTextField(30);
        btn = new JButton("", new ImageIcon(getClass().getResource("/icons/buscar.png")));

        fr = new JFrame("Marcas - Consultas - Tienko");

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

        pan2.setPreferredSize(new Dimension(0, 400));

        pan1.setBackground(Color.WHITE);
        pan2.setBackground(Color.WHITE);

        pan1.add(lbl);
        pan1.add(txt);
        pan1.add(btn);

        pan2.add(scrollPane);

        panMain.add(pan1, BorderLayout.NORTH);
        panMain.add(pan2, BorderLayout.CENTER);

        fr.add(panMain);

        fr.setLayout(new GridLayout(1, 1));
        //fr.setResizable(false);
        fr.pack();
        fr.setVisible(true);
        fr.setLocationRelativeTo(null);
        
        btn.addActionListener((ActionEvent ae) -> {
            datosTab();
        });
        
        titulos[0] = "ID";
        titulos[1] = "Marca";

        model = new DefaultTableModel(null, titulos);
        tab.setModel(model);
    }

    public void datosTab() {
        con = mycon.conecta();
        model = new DefaultTableModel(null, titulos);
        try {
            sql = "SELECT * FROM marcas "
                    + "WHERE (nombre LIKE \"%"+ txt.getText() +"%\" "
                    + "OR id_mar LIKE \"%"+ txt.getText() +"%\" )"
                    ;
            stmt = con.prepareStatement(sql);
            tabla = stmt.executeQuery();
            while (tabla.next()) {
                datos[0] = tabla.getString(1);
                datos[1] = tabla.getString(2);
                model.addRow(datos);
            }
            tab.setModel(model);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }
}
