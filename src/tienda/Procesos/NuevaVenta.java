/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tienda.Procesos;

import DAO.Conexion;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.*;
import java.text.SimpleDateFormat;
import javax.swing.table.DefaultTableModel;

import tienda.*;
import GenerarRep.Ticket;

/**
 *
 * @author mps35
 */
public class NuevaVenta {

    int id_venta = 0;
    String fecha = "";

    JPanel panIz, panD, pan1, pan2, panMain, pan3, pan4, panBtn, panMod;
    JFrame fr;

    JTable tab;
    JScrollPane scrollPane;
    DefaultTableModel model;

    String[] titulos = new String[6];
    String[] datos = new String[6];

    JLabel lbl1, lbl2;

    JTextField txt1, txt2, txtSKU;

    JButton btn1, btn2, btn3, btn4, btn5, btn6, btn7;

    Connection con;
    PreparedStatement stmt;
    ResultSet tabla;
    String sql = "";
    int sw = 0;
    Conexion mycon = new Conexion();

    public void datosTab() {
        titulos[0] = "SKU";
        titulos[1] = "Nombre";
        titulos[2] = "Precio";
        titulos[3] = "Cantidad";
        titulos[4] = "Total";
        titulos[5] = "Fecha";

        model = new DefaultTableModel(null, titulos);
        try {
            sql = ""
                    + "SELECT * FROM ventas "
                    + "INNER JOIN productos "
                    + "WHERE ventas.id_prod = productos.id_prod "
                    + "AND id_venta = " + id_venta;
            stmt = con.prepareStatement(sql);
            tabla = stmt.executeQuery();

            while (tabla.next()) {
                datos[0] = tabla.getString(9);
                datos[1] = tabla.getString(10);
                datos[2] = tabla.getString(12);
                datos[3] = tabla.getString(5);
                datos[4] = tabla.getString(6);
                Timestamp fechaT = tabla.getTimestamp(7);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String fechaFormateada = sdf.format(fechaT);
                datos[5] = fechaFormateada;
                model.addRow(datos);
            }
            tab.setModel(model);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }

    public void consultarTabla() {
        int ind = tab.getSelectedRow();
        txt1.setText((String) tab.getValueAt(ind, 0));
        txt2.setText((String) tab.getValueAt(ind, 3));

        txtSKU.setText((String) tab.getValueAt(ind, 0));
    }

    public NuevaVenta() {
        panIz = new JPanel();
        panD = new JPanel(new BorderLayout(1, 1));
        pan1 = new JPanel();
        pan2 = new JPanel();
        pan3 = new JPanel();
        pan4 = new JPanel();
        panMod = new JPanel();
        panMain = new JPanel(new BorderLayout(1, 1));
        panBtn = new JPanel();

        lbl1 = new JLabel("SKU ");
        lbl2 = new JLabel("Cantidad ");

        txt1 = new JTextField(30);
        txt2 = new JTextField(30);

        txtSKU = new JTextField(5);

        //btn1 = new JButton("Limpiar", new ImageIcon(getClass().getResource("/icons/limpiar.png")));
        //btn2 = new JButton("Consultar", new ImageIcon(getClass().getResource("/icons/consultar.png")));
        btn5 = new JButton("Agregar", new ImageIcon(getClass().getResource("/icons/nuevo.png")));
        btn3 = new JButton("Modificar", new ImageIcon(getClass().getResource("/icons/modificar.png")));
        btn4 = new JButton("Borrar", new ImageIcon(getClass().getResource("/icons/borrar.png")));
        btn6 = new JButton("Cancelar", new ImageIcon(getClass().getResource("/icons/salir.png")));
        btn7 = new JButton("Cobrar", new ImageIcon(getClass().getResource("/icons/bill.png")));

        fr = new JFrame("Nueva Venta - Tienko");

        tab = new JTable();
        scrollPane = new JScrollPane(tab);

        fr.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int opc = JOptionPane.showConfirmDialog(null, "¿Deseas cancelar la venta y regresar?", "Cancelar venta " + id_venta, JOptionPane.YES_NO_OPTION);
                if (tab.getRowCount() == 0) {
                    if (opc == 0) {
                        fr.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                        MenuEs myMenu = new MenuEs();
                        myMenu.usar();
                    } else {
                        fr.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                    }

                } else {
                    if (opc == 0) {
                        try {
                            sql = "DELETE FROM ventas "
                                    + "WHERE id_venta = \"" + id_venta + "\" ";
                            stmt = con.prepareStatement(sql);
                            sw = stmt.executeUpdate();
                            if (sw != 0) {
                                fr.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                                MenuEs myMenu = new MenuEs();
                                myMenu.usar();
                            } else {
                                JOptionPane.showMessageDialog(null, "Error al borrar");
                            }

                        } catch (SQLException ex) {
                            JOptionPane.showMessageDialog(null, ex.getMessage());
                        }
                    }else{
                        fr.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                    }
                }
            }
        });
    }

    public void usar() {
        Image icon = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/img/tienda.png"));
        fr.setIconImage(icon);
        
        con = mycon.conecta();
        id_venta = obtenerID();
        fecha = obtenerFecha();

        panMod.setVisible(false);

        tab.setDragEnabled(false);
        tab.getTableHeader().setReorderingAllowed(false);
        tab.getTableHeader().setBackground(Color.WHITE);
        tab.setGridColor(Color.BLACK);

        datosTab();

        pan1.setLayout(new GridLayout(3, 2, -100, 25));
        panBtn.setLayout(new GridLayout(2, 1, 10, 10));

        pan1.setBackground(Color.WHITE);
        pan2.setBackground(Color.WHITE);
        pan3.setBackground(Color.WHITE);
        pan4.setBackground(Color.WHITE);
        panBtn.setBackground(Color.WHITE);
        panIz.setBackground(Color.WHITE);
        panD.setBackground(Color.WHITE);
        tab.setBackground(Color.WHITE);

        pan3.add(lbl1);
        pan3.add(txt1);
        pan4.add(lbl2);
        pan4.add(txt2);

        panMod.add(txtSKU);

        pan1.add(pan3);
        pan1.add(pan4);
        pan1.add(panMod);

        //panBtn.add(btn1);
        //panBtn.add(btn2);
        panBtn.add(btn5);
        panBtn.add(btn3);
        panBtn.add(btn4);
        panBtn.add(btn7);
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
        /*
        btn1.addActionListener((ActionEvent ae) -> {
            limpiar();
        });
        btn2.addActionListener((ActionEvent ae) -> {
            consultar();
        });
         */
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
            cancelar();
        });
        btn7.addActionListener((ActionEvent ae) -> {
            cobrar();
        });

    }

    public void modificar() {
        if ("".equals(txt1.getText()) || "".equals(txt2.getText())) {
            JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios");
        } else {
            if (isEnStock()) {
                try {
                    sql = "UPDATE ventas SET "
                            + "id_prod = (SELECT id_prod FROM productos WHERE sku = \"" + txt1.getText() + "\"), "
                            + "cantidad = \"" + txt2.getText() + "\", "
                            + "total = " + "(SELECT (precioVenta * " + txt2.getText() + ") FROM productos WHERE sku = \"" + txt1.getText() + "\") "
                            + "WHERE id_venta = \"" + id_venta + "\" "
                            + "AND id_prod = (SELECT id_prod FROM productos WHERE sku = \"" + txtSKU.getText() + "\") "
                            + "AND fecha = \"" + fecha + "\"";
                    stmt = con.prepareStatement(sql);
                    sw = stmt.executeUpdate();
                    if (sw == 0) {
                        JOptionPane.showMessageDialog(null, "Error al modificar");
                    } else {
                        tab.removeAll();
                        datosTab();
                        limpiar();
                    }

                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }
            } else {
                JOptionPane.showMessageDialog(null, "Stock insuficiente");
            }
        }

    }

    public void borrar() {
        try {
            sql = "DELETE FROM ventas "
                    + "WHERE id_venta = \"" + id_venta + "\" "
                    + "AND id_prod = (SELECT id_prod FROM productos WHERE sku = \"" + txt1.getText() + "\") "
                    + "AND fecha = \"" + fecha + "\"";
            stmt = con.prepareStatement(sql);
            sw = stmt.executeUpdate();
            if (sw != 0) {
                tab.removeAll();
                datosTab();
                limpiar();
            } else {
                JOptionPane.showMessageDialog(null, "Error al borrar");
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }

    }

    public void insertar() {
        String total;

        total = "(SELECT (precioVenta * " + txt2.getText() + ") FROM productos WHERE sku = \"" + txt1.getText() + "\")";

        if ("".equals(txt1.getText()) || "".equals(txt2.getText())) {
            JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios");
        } else {
            if (isEnStock()) {

                try {
                    sql = "INSERT INTO ventas VALUES "
                            + "(" + id_venta + ", "
                            + "(SELECT id_prod FROM productos WHERE sku = " + "\"" + txt1.getText() + "\"), "
                            + "\"" + IDS.id_su + "\", "
                            + "\"" + IDS.id_admin + "\", "
                            + "\"" + txt2.getText() + "\","
                            + " " + total + ", "
                            + "\"" + fecha + "\""
                            + ")";
                    stmt = con.prepareStatement(sql);
                    sw = stmt.executeUpdate();
                    if (sw != 0) {
                        tab.removeAll();
                        datosTab();
                        limpiar();
                    } else {
                        JOptionPane.showMessageDialog(null, "Error al insertar");
                    }

                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }
            } else {
                JOptionPane.showMessageDialog(null, "Stock insuficiente");
            }
        }

    }

    public void limpiar() {
        txt1.setText("");
        txt2.setText("");
        txtSKU.setText("");
        txt1.requestFocusInWindow();
    }

    public void salir() {
        int opc = JOptionPane.showConfirmDialog(null, "¿Deseas cancelar la venta y regresar?", "Cancelar venta", JOptionPane.YES_NO_OPTION);
        if (opc == 0) {
            fr.dispose();
            MenuEs myMenu = new MenuEs();
            myMenu.usar();
        }
    }

    public int obtenerID() {
        int id_ = 0;
        try {
            sql = "SELECT max(id_venta)+1 from ventas";
            stmt = con.prepareStatement(sql);
            tabla = stmt.executeQuery();
            if (tabla.next()) {
                id_ = tabla.getInt(1);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        return id_;
    }

    public String obtenerFecha() {
        String fecha_ = "";
        try {
            sql = "SELECT CURRENT_TIMESTAMP";
            stmt = con.prepareStatement(sql);
            tabla = stmt.executeQuery();
            if (tabla.next()) {
                Timestamp fechaT = tabla.getTimestamp(1);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                fecha_ = sdf.format(fechaT);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        return fecha_;
    }

    public boolean isEnStock() {
        boolean is;
        int stock = obtenerStock(txt1.getText());
        is = stock >= Integer.parseInt(txt2.getText());
        return is;
    }

    public void cancelar() {
        if (tab.getRowCount() == 0) {
            salir();
        } else {
            int opc = JOptionPane.showConfirmDialog(null, "¿Deseas cancelar la venta y regresar?", "Cancelar venta", JOptionPane.YES_NO_OPTION);
            if (opc == 0) {
                try {
                    sql = "DELETE FROM ventas "
                            + "WHERE id_venta = \"" + id_venta + "\" ";
                    stmt = con.prepareStatement(sql);
                    sw = stmt.executeUpdate();
                    if (sw != 0) {
                        tab.removeAll();
                        datosTab();
                        limpiar();
                        fr.dispose();
                        MenuEs myMenu = new MenuEs();
                        myMenu.usar();
                    } else {
                        JOptionPane.showMessageDialog(null, "Error al borrar");
                    }

                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }
            }

        }
    }

    public void cobrar() {
        if (tab.getRowCount() > 0) {
            // Recorrer las filas de la tabla
            for (int i = 0; i < tab.getRowCount(); i++) {
                // Obtener el valor de la columna SKU (suponiendo que es la columna 0)
                Object sku = tab.getValueAt(i, 0);
                // Obtener el valor de la columna Cantidad (suponiendo que es la columna 3)
                Object cantidad = tab.getValueAt(i, 3);

                int stock = obtenerStock((String) sku);
                stock = stock - Integer.parseInt((String) cantidad);

                try {
                    sql = "UPDATE productos p1 "
                            + "JOIN (SELECT id_prod FROM productos WHERE sku = " + sku + ") p2 "
                            + "ON p1.id_prod = p2.id_prod "
                            + "SET p1.stock = " + stock;
                    stmt = con.prepareStatement(sql);
                    sw = stmt.executeUpdate();
                    if (sw == 0) {
                        JOptionPane.showMessageDialog(null, "Error al modificar");
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }
            }
            if (sw == 1) {
                JOptionPane.showMessageDialog(null, "Venta realizada con exito");
                fr.dispose();
                Ticket my = new Ticket();
                my.reporte(String.valueOf(id_venta));
            }
        }
    }

    public int obtenerStock(String sku) {
        int stock = 0;
        try {
            sql = "SELECT stock FROM productos WHERE id_prod = (SELECT id_prod FROM productos WHERE sku = " + "\"" + sku + "\")";
            stmt = con.prepareStatement(sql);
            tabla = stmt.executeQuery();
            if (tabla.next()) {
                stock = tabla.getInt(1);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        return stock;
    }
}
