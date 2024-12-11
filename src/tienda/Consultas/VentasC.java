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
import java.text.SimpleDateFormat;
import tienda.*;

/**
 *
 * @author mps35
 */
public class VentasC {

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

    public VentasC() {
        pan1 = new JPanel();
        pan2 = new JPanel();
        panMain = new JPanel(new BorderLayout(1, 1));

        lbl = new JLabel("Buscar");
        txt = new JTextField(30);
        btn = new JButton("", new ImageIcon(getClass().getResource("/icons/buscar.png")));

        fr = new JFrame("Ventas - Consultas - Tienko");

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
        
        titulos[0] = "ID ";
        titulos[1] = "SKU";
        titulos[2] = "Sucursal";
        titulos[3] = "Administrador";
        titulos[4] = "Cantidad";
        titulos[5] = "Total";
        titulos[6] = "Fecha";

        model = new DefaultTableModel(null, titulos);
        tab.setModel(model);
    }

    public void datosTab() {
        con = mycon.conecta();
        model = new DefaultTableModel(null, titulos);
        try {
            sql = "SELECT * FROM ventas "
                    + "INNER JOIN productos, admins, sucursal "
                    + "WHERE ventas.id_prod = productos.id_prod "
                    + "AND ventas.id_admin = admins.id_admin "
                    + "AND ventas.id_su = sucursal.id_su "
                    + "AND (id_venta LIKE \"%"+ txt.getText() +"%\" "
                    + "OR ventas.id_prod LIKE \"%"+ txt.getText() +"%\" "
                    + "OR ventas.id_su LIKE \"%"+ txt.getText() +"%\" "
                    + "OR ventas.id_admin LIKE \"%"+ txt.getText() +"%\" "
                    + "OR total LIKE \"%"+ txt.getText() +"%\" "
                    + "OR fecha LIKE \"%"+ txt.getText() +"%\" "
                    + "OR sku LIKE \"%"+ txt.getText() +"%\" "
                    + "OR productos.nombre LIKE \"%"+ txt.getText() +"%\" "
                    + "OR precioCompra LIKE \"%"+ txt.getText() +"%\" "
                    + "OR precioVenta LIKE \"%"+ txt.getText() +"%\" "
                    + "OR stock LIKE \"%"+ txt.getText() +"%\" "
                    + "OR usuario LIKE \"%"+ txt.getText() +"%\" "
                    + "OR correo LIKE \"%"+ txt.getText() +"%\" "
                    + "OR sucursal.nombre LIKE \"%"+ txt.getText() +"%\" "
                    + "OR direccion LIKE \"%"+ txt.getText() +"%\" "
                    + "OR telefo LIKE \"%"+ txt.getText() +"%\" "
                    + "OR productos.id_mar LIKE \"%"+ txt.getText() +"%\")"
                    ;
            stmt = con.prepareStatement(sql);
            tabla = stmt.executeQuery();
            while (tabla.next()) {
                datos[0] = tabla.getString(1);
                datos[1] = tabla.getString(9);
                datos[2] = tabla.getString(21);
                datos[3] = tabla.getString(16);
                datos[4] = tabla.getString(5);
                datos[5] = tabla.getString(6);
                Timestamp fecha = tabla.getTimestamp(7);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String fechaFormateada = sdf.format(fecha);
                datos[6] = fechaFormateada;
                model.addRow(datos);
            }
            tab.setModel(model);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }
}
