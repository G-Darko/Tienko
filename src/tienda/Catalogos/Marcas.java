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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.*;
import javax.swing.table.DefaultTableModel;
import tienda.MenuEs;

/**
 *
 * @author mps35
 */
public class Marcas {

    JPanel panIz, panD, pan1, pan2, panMain, pan3, panBtn, panID;
    JFrame fr;

    JTable tab;
    JScrollPane scrollPane;
    DefaultTableModel model;

    String[] titulos = new String[2];
    String[] datos = new String[2];

    JLabel lbl1, lblID;

    JTextField txt1, txtID;

    JButton btn1, btn2, btn3, btn4, btn5, btn6;

    Connection con;
    PreparedStatement stmt;
    ResultSet tabla;
    String sql = "";
    int sw = 0;
    Conexion mycon = new Conexion();

    public void datosTab() {
        con = mycon.conecta();
        titulos[0] = "ID";
        titulos[1] = "Nombre";

        model = new DefaultTableModel(null, titulos);
        try {
            sql = "SELECT * FROM marcas";
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

    public void consultarTabla() {
        int ind = tab.getSelectedRow();
        txtID.setText((String) tab.getValueAt(ind, 0));
        txt1.setText((String) tab.getValueAt(ind, 1));
    }

    public Marcas() {
        panIz = new JPanel();
        panD = new JPanel(new BorderLayout(1, 1));
        pan1 = new JPanel();
        pan2 = new JPanel();
        pan3 = new JPanel();
        panBtn = new JPanel();
        panID = new JPanel();
        panMain = new JPanel(new BorderLayout(1, 1));

        lbl1 = new JLabel("Marca    ");
        lblID = new JLabel("ID          ");

        txt1 = new JTextField(30);
        txtID = new JTextField(30);

        btn1 = new JButton("Limpiar", new ImageIcon(getClass().getResource("/icons/limpiar.png")));
        btn2 = new JButton("Consultar", new ImageIcon(getClass().getResource("/icons/consultar.png")));
        btn3 = new JButton("Modificar", new ImageIcon(getClass().getResource("/icons/modificar.png")));
        btn4 = new JButton("Borrar", new ImageIcon(getClass().getResource("/icons/borrar.png")));
        btn5 = new JButton("Insertar", new ImageIcon(getClass().getResource("/icons/nuevo.png")));
        btn6 = new JButton("Volver", new ImageIcon(getClass().getResource("/icons/salir.png")));

        fr = new JFrame("Marcas - Tienko");

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
        
        tab.setDragEnabled(false);
        tab.getTableHeader().setReorderingAllowed(false);
        tab.getTableHeader().setBackground(Color.WHITE);
        tab.setGridColor(Color.BLACK);
        
        datosTab();
        pan1.setLayout(new GridLayout(2, 2, -100, 25));
        panBtn.setLayout(new GridLayout(2, 1, 10, 10));

        //txtID.setEnabled(false);
        panID.setBackground(Color.WHITE);
        pan1.setBackground(Color.WHITE);
        pan2.setBackground(Color.WHITE);
        pan3.setBackground(Color.WHITE);
        panBtn.setBackground(Color.WHITE);
        panIz.setBackground(Color.WHITE);
        panD.setBackground(Color.WHITE);
        tab.setBackground(Color.WHITE);

        panID.add(lblID);
        panID.add(txtID);
        pan3.add(lbl1);
        pan3.add(txt1);

        pan1.add(panID);
        pan1.add(pan3);

        panBtn.add(btn1);
        panBtn.add(btn2);
        panBtn.add(btn3);
        panBtn.add(btn4);
        panBtn.add(btn5);
        panBtn.add(btn6);

        pan2.add(panBtn);

        panD.add(pan1, BorderLayout.NORTH);
        panD.add(pan2, BorderLayout.SOUTH);

        panIz.add(scrollPane);

        panMain.add(panIz, BorderLayout.EAST);
        panMain.add(panD, BorderLayout.WEST);

        fr.add(panIz);
        fr.add(panD);

        fr.setLayout(new GridLayout(1, 2, 10, 10));
        //fr.setResizable(false);
        fr.pack();
        fr.setVisible(true);
        fr.setLocationRelativeTo(null);

        tab.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                consultarTabla();
            }
        });

        btn1.addActionListener((ActionEvent ae) -> {
            limpiar();
        });
        btn2.addActionListener((ActionEvent ae) -> {
            consultar();
        });
        btn3.addActionListener((ActionEvent ae) -> {
            modificar();
        });
        btn4.addActionListener((ActionEvent ae) -> {
            borrar();
        });
        btn5.addActionListener((ActionEvent ae) -> {
            insertar();
        });
        btn6.addActionListener((ActionEvent ae) -> {
            salir();
        });
    }

    public void consultar() {
        try {
            sql = "SELECT * FROM marcas WHERE id_mar = \"" + txtID.getText() + "\"";
            stmt = con.prepareStatement(sql);
            tabla = stmt.executeQuery();
            if (tabla.next()) {
                txt1.setText(tabla.getString(2));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }

    public void modificar() {
        try {
            sql = "UPDATE marcas SET "
                    + "nombre = \"" + txt1.getText() + "\" "
                    + "WHERE id_mar= \"" + txtID.getText() + "\"";
            stmt = con.prepareStatement(sql);
            sw = stmt.executeUpdate();
            tab.removeAll();
            datosTab();
            limpiar();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }

    public void borrar() {
        try {
            sql = "DELETE FROM marcas "
                    + "WHERE id_mar = \"" + txtID.getText() + "\"";
            stmt = con.prepareStatement(sql);
            sw = stmt.executeUpdate();
            if (sw != 0) {
                tab.removeAll();
                datosTab();
                limpiar();
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }

    }

    public void insertar() {
        try {
            sql = "INSERT INTO marcas VALUES "
                    + "(null,"
                    + "\"" + txt1.getText() + "\""
                    + ")";
            stmt = con.prepareStatement(sql);
            sw = stmt.executeUpdate();
            if (sw != 0) {
                tab.removeAll();
                datosTab();
                limpiar();
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }

    }

    public void limpiar() {
        txtID.setText("");
        txt1.setText("");
        txt1.requestFocusInWindow();
    }

    public void salir() {
        int opc = JOptionPane.showConfirmDialog(null, "¿Deseas regresar al menu?", "Salir", JOptionPane.YES_NO_OPTION);
        if (opc == 0) {
            fr.dispose();
            MenuEs myMenu = new MenuEs();
            myMenu.usar();
        }
    }
    /*
    public static void main(String[] args) {
        Admins a = new Admins();
        a.usar();
    }
     */
}
