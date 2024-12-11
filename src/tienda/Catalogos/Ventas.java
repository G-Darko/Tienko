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
import java.text.SimpleDateFormat;
import javax.swing.table.DefaultTableModel;
import tienda.IDS;
import tienda.IDS;
import tienda.JCalendarFull;
import tienda.JCalendarFull;
import tienda.MenuEs;
import tienda.MenuEs;

/**
 *
 * @author mps35
 */
public class Ventas {

    JPanel panIz, panD, pan1, pan2, panMain, pan3, pan4, pan5, pan6, panBtn, panID, pan7, pan8, panMod;
    JFrame fr;

    JTable tab;
    JScrollPane scrollPane;
    DefaultTableModel model;

    String[] titulos = new String[7];
    String[] datos = new String[7];

    JLabel lbl1, lbl2, lbl3, lbl4, lblID, lbl5, lbl6;

    JTextField txt1, txt2, txt3, txt4, txtID, txtSKU, txtVenta, txtFecha;

    JComboBox<IDS> combo = new JComboBox<>();
    JComboBox<IDS> combo2 = new JComboBox<>();
    JComboBox<IDS> combo3 = new JComboBox<>();

    JButton btn1, btn2, btn3, btn4, btn5, btn6, btnFecha;

    Connection con;
    PreparedStatement stmt;
    ResultSet tabla;
    String sql = "";
    int sw = 0;
    Conexion mycon = new Conexion();

    public void datosTab() {
        con = mycon.conecta();
        titulos[0] = "ID ";
        titulos[1] = "SKU";
        titulos[2] = "Sucursal";
        titulos[3] = "Administrador";
        titulos[4] = "Cantidad";
        titulos[5] = "Total";
        titulos[6] = "Fecha";

        model = new DefaultTableModel(null, titulos);
        try {
            sql = ""
                    + "SELECT * FROM ventas "
                    + "INNER JOIN productos, admins, sucursal "
                    + "WHERE ventas.id_prod = productos.id_prod "
                    + "AND ventas.id_admin = admins.id_admin "
                    + "AND ventas.id_su = sucursal.id_su";
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

    public void consultarTabla() {
        int ind = tab.getSelectedRow();
        txtID.setText((String) tab.getValueAt(ind, 0));
        txt1.setText((String) tab.getValueAt(ind, 1));
        selectComboBoxValueById((String) tab.getValueAt(ind, 2), combo);
        selectComboBoxValueById((String) tab.getValueAt(ind, 3), combo2);
        txt2.setText((String) tab.getValueAt(ind, 4));
        txt3.setText((String) tab.getValueAt(ind, 5));
        txt4.setText((String) tab.getValueAt(ind, 6));

        txtVenta.setText((String) tab.getValueAt(ind, 0));
        txtSKU.setText((String) tab.getValueAt(ind, 1));
        txtFecha.setText((String) tab.getValueAt(ind, 6));
    }

    public Ventas() {
        panIz = new JPanel();
        panD = new JPanel(new BorderLayout(1, 1));
        pan1 = new JPanel();
        pan2 = new JPanel();
        pan3 = new JPanel();
        pan4 = new JPanel();
        pan5 = new JPanel();
        pan6 = new JPanel();
        pan7 = new JPanel();
        pan8 = new JPanel();
        panMod = new JPanel();
        panBtn = new JPanel();
        panID = new JPanel();
        panMain = new JPanel(new BorderLayout(1, 1));

        lbl1 = new JLabel("SKU ");
        lbl2 = new JLabel("Sucursal ");
        lbl3 = new JLabel("Administrador ");
        lbl4 = new JLabel("Cantidad ");
        lbl5 = new JLabel("Total ");
        lbl6 = new JLabel("Fecha ");
        lblID = new JLabel("ID ");

        txt1 = new JTextField(30);
        txt2 = new JTextField(30);
        txt3 = new JTextField(30);
        txt4 = new JTextField(25);
        txtID = new JTextField(30);

        txtSKU = new JTextField(5);
        txtFecha = new JTextField(5);
        txtVenta = new JTextField(5);

        btn1 = new JButton("Limpiar", new ImageIcon(getClass().getResource("/icons/limpiar.png")));
        btn2 = new JButton("Consultar", new ImageIcon(getClass().getResource("/icons/consultar.png")));
        btn3 = new JButton("Modificar", new ImageIcon(getClass().getResource("/icons/modificar.png")));
        btn4 = new JButton("Borrar", new ImageIcon(getClass().getResource("/icons/borrar.png")));
        btn5 = new JButton("Insertar", new ImageIcon(getClass().getResource("/icons/nuevo.png")));
        btn6 = new JButton("Volver", new ImageIcon(getClass().getResource("/icons/salir.png")));
        btnFecha = new JButton("", new ImageIcon(getClass().getResource("/img/calendar-icon.png")));

        fr = new JFrame("Ventas - Tienko");

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
        
        txt4.setEditable(false);
        panMod.setVisible(false);

        tab.setDragEnabled(false);
        tab.getTableHeader().setReorderingAllowed(false);
        tab.getTableHeader().setBackground(Color.WHITE);
        tab.setGridColor(Color.BLACK);

        datosTab();
        loadDataCombo(combo, "SELECT id_su, nombre FROM sucursal ORDER BY nombre");
        loadDataCombo(combo2, "SELECT id_admin, usuario FROM admins ORDER BY usuario");
        pan1.setLayout(new GridLayout(8, 2, -100, 25));
        panBtn.setLayout(new GridLayout(2, 1, 10, 10));

        //txtID.setEnabled(false);
        panID.setBackground(Color.WHITE);
        pan1.setBackground(Color.WHITE);
        pan2.setBackground(Color.WHITE);
        pan3.setBackground(Color.WHITE);
        pan4.setBackground(Color.WHITE);
        pan5.setBackground(Color.WHITE);
        pan6.setBackground(Color.WHITE);
        pan7.setBackground(Color.WHITE);
        pan8.setBackground(Color.WHITE);
        panBtn.setBackground(Color.WHITE);
        panIz.setBackground(Color.WHITE);
        panD.setBackground(Color.WHITE);
        tab.setBackground(Color.WHITE);

        panID.add(lblID);
        panID.add(txtID);
        pan3.add(lbl1);
        pan3.add(txt1);
        pan4.add(lbl2);
        pan4.add(combo);
        pan5.add(lbl3);
        pan5.add(combo2);
        pan6.add(lbl4);
        pan6.add(txt2);
        pan7.add(lbl5);
        pan7.add(txt3);
        pan8.add(lbl6);
        pan8.add(txt4);
        pan8.add(btnFecha);

        panMod.add(txtVenta);
        panMod.add(txtSKU);
        panMod.add(txtFecha);

        pan1.add(panID);
        pan1.add(pan3);
        pan1.add(pan4);
        pan1.add(pan5);
        pan1.add(pan6);
        pan1.add(pan7);
        pan1.add(pan8);
        pan1.add(panMod);

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
        btnFecha.addActionListener((ActionEvent e) -> {
            // Crear y mostrar el JCalendarFull
            JCalendarFull calendarDialog = new JCalendarFull(fr);
            calendarDialog.setVisible(true);

            // Obtener la fecha y hora seleccionadas
            if (calendarDialog.selecciono) {
                String fecha = calendarDialog.getFechaCompleta(2);
                String hora = calendarDialog.getHoraCompleta();

                String fechaHora = fecha + " " + hora;
                txt4.setText(fechaHora);
            }
        });
    }

    public int selectedID(IDS o) {
        int id = 1;
        if (o != null) {
            id = o.getID();
        }
        return id;
    }

    void loadDataCombo(JComboBox<IDS> com, String sql) {
        try {
            stmt = con.prepareStatement(sql);
            tabla = stmt.executeQuery();
            while (tabla.next()) {
                int id = tabla.getInt(1);
                String name = tabla.getString(2);
                com.addItem(new IDS(id, name));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }

    public void consultar() {
        try {
            sql = ""
                    + "SELECT * FROM ventas "
                    + "INNER JOIN productos, admins, sucursal "
                    + "WHERE ventas.id_prod = productos.id_prod "
                    + "AND ventas.id_admin = admins.id_admin "
                    + "AND ventas.id_su = sucursal.id_su "
                    + "AND ventas.id_venta = \"" + txtID.getText() + "\" "
                    + "AND productos.sku = \"" + txt1.getText() + "\" "
                    + "AND ventas.fecha = \"" + txt4.getText() + "\" ";
            stmt = con.prepareStatement(sql);
            tabla = stmt.executeQuery();
            if (tabla.next()) {
                txt2.setText(tabla.getString(5));
                txt3.setText(tabla.getString(6));
                txt4.setText(tabla.getString(7));

                txtVenta.setText(tabla.getString(1));
                txtSKU.setText(tabla.getString(5));
                txtFecha.setText(tabla.getString(7));
                if (combo.getItemCount() > 1) {
                    selectComboBoxValueById(tabla.getString(21), combo);
                } else if (combo2.getItemCount() > 1) {
                    selectComboBoxValueById(tabla.getString(16), combo2);
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }

    public void modificar() {
        IDS selectedCombo = (IDS) combo.getSelectedItem();
        IDS selectedCombo2 = (IDS) combo2.getSelectedItem();
        int sucursal = selectedID(selectedCombo);
        int admin = selectedID(selectedCombo2);
        try {
            sql = "UPDATE ventas SET "
                    + "id_venta = \"" + txtID.getText() + "\", "
                    + "id_prod = (SELECT id_prod FROM productos WHERE sku = \"" + txt1.getText() + "\"), "
                    + "id_su = \"" + sucursal + "\", "
                    + "id_admin = \"" + admin + "\", "
                    + "cantidad = \"" + txt2.getText() + "\", "
                    + "total = \"" + txt3.getText() + "\", "
                    + "fecha = \"" + txt4.getText() + "\" "
                    + "WHERE id_venta = \"" + txtVenta.getText() + "\" "
                    + "AND id_prod = (SELECT id_prod FROM productos WHERE sku = \"" + txtSKU.getText() + "\") "
                    + "AND fecha = \"" + txtFecha.getText() + "\"";
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

    }

    public void borrar() {
        try {
            sql = "DELETE FROM ventas "
                    + "WHERE id_venta = \"" + txtID.getText() + "\" "
                    + "AND id_prod = (SELECT id_prod FROM productos WHERE sku = \"" + txt1.getText() + "\") "
                    + "AND fecha = \"" + txt4.getText() + "\"";
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
        IDS selectedCombo = (IDS) combo.getSelectedItem();
        IDS selectedCombo2 = (IDS) combo2.getSelectedItem();
        int sucursal = selectedID(selectedCombo);
        int admin = selectedID(selectedCombo2);
        String id, fecha, total;
        if ("".equals(txtID.getText())) {
            id = "null";
        } else {
            id = txtID.getText();
        }

        if ("".equals(txt4.getText())) {
            fecha = "CURRENT_TIMESTAMP";
        } else {
            fecha = "\"" + txt4.getText() + "\"";
        }

        if ("".equals(txt3.getText())) {
            total = "(SELECT (precioVenta * " + txt2.getText() + ") FROM productos WHERE sku = \"" + txt1.getText() + "\")";
        } else {
            total = txt3.getText();
        }

        try {
            sql = "INSERT INTO ventas VALUES "
                    + "(" + id + ", "
                    + "(SELECT id_prod FROM productos WHERE sku = " + "\"" + txt1.getText() + "\"), "
                    + "\"" + sucursal + "\", "
                    + "\"" + admin + "\", "
                    + "\"" + txt2.getText() + "\","
                    + " " + total + ", "
                    + "" + fecha + ""
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

    }

    public void limpiar() {
        txtID.setText("");
        txt1.setText("");
        txt2.setText("");
        txt3.setText("");
        txt4.setText("");

        txtVenta.setText("");
        txtFecha.setText("");
        txtSKU.setText("");

        txt1.requestFocusInWindow();
        if (combo.getItemCount() > 1) {
            combo.setSelectedIndex(0);
        } else if (combo2.getItemCount() > 1) {
            combo2.setSelectedIndex(0);
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

    void selectComboBoxValueById(String name, JComboBox<IDS> com) {
        ComboBoxModel<IDS> model = com.getModel();
        for (int i = 0; i < model.getSize(); i++) {
            IDS item = model.getElementAt(i);
            if (item.getNombre().equals(name)) {
                com.setSelectedIndex(i);
                break;
            }
        }
    }
/*
    public static void main(String[] args) {
        Ventas a = new Ventas();
        a.usar();
    }
*/

}
