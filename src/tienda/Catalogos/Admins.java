/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tienda.Catalogos;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
public class Admins {

    JPanel panIz, panD, pan1, pan2, panMain, pan3, pan4, pan5, pan6, panBtn, panID;
    JFrame fr;

    JTable tab;
    JScrollPane scrollPane;
    DefaultTableModel model;

    String[] titulos = new String[5];
    String[] datos = new String[5];

    JLabel lbl1, lbl2, lbl3, lbl4, lblID;

    JTextField txt1, txt3, txtID;
    JPasswordField txt2;

    JComboBox<IDS> combo = new JComboBox<>();

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
        titulos[1] = "Usuario";
        titulos[2] = "Correo";
        titulos[3] = "Password";
        titulos[4] = "Sucursal";

        model = new DefaultTableModel(null, titulos);
        try {
            sql = "SELECT * FROM admins INNER JOIN sucursal WHERE admins.id_su = sucursal.id_su";
            stmt = con.prepareStatement(sql);
            tabla = stmt.executeQuery();

            while (tabla.next()) {
                datos[0] = tabla.getString(1);
                datos[1] = tabla.getString(2);
                datos[2] = tabla.getString(3);
                datos[3] = tabla.getString(4);
                datos[4] = tabla.getString(7);
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
        txt3.setText((String) tab.getValueAt(ind, 2));
        txt2.setText((String) tab.getValueAt(ind, 3));
        if (combo.getItemCount() > 1) {
            combo.setSelectedIndex(Integer.parseInt(txtID.getText()));
        }
    }

    public Admins() {
        panIz = new JPanel();
        panD = new JPanel(new BorderLayout(1, 1));
        pan1 = new JPanel();
        pan2 = new JPanel();
        pan3 = new JPanel();
        pan4 = new JPanel();
        pan5 = new JPanel();
        pan6 = new JPanel();
        panBtn = new JPanel();
        panID = new JPanel();
        panMain = new JPanel(new BorderLayout(1, 1));

        lbl1 = new JLabel("Usuario    ");
        lbl3 = new JLabel("Correo     ");
        lbl2 = new JLabel("Password ");
        lbl4 = new JLabel("Sucursal ");
        lblID = new JLabel("ID          ");

        txt1 = new JTextField(30);
        txt2 = new JPasswordField(30);
        txt3 = new JTextField(30);
        txtID = new JTextField(30);

        btn1 = new JButton("Limpiar", new ImageIcon(getClass().getResource("/icons/limpiar.png")));
        btn2 = new JButton("Consultar", new ImageIcon(getClass().getResource("/icons/consultar.png")));
        btn3 = new JButton("Modificar", new ImageIcon(getClass().getResource("/icons/modificar.png")));
        btn4 = new JButton("Borrar", new ImageIcon(getClass().getResource("/icons/borrar.png")));
        btn5 = new JButton("Insertar", new ImageIcon(getClass().getResource("/icons/nuevo.png")));
        btn6 = new JButton("Volver", new ImageIcon(getClass().getResource("/icons/salir.png")));

        fr = new JFrame("Administradores - Tienko");

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
                }else{
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
        loadDataCombo(combo);
        pan1.setLayout(new GridLayout(5, 2, -100, 25));
        panBtn.setLayout(new GridLayout(2, 1, 10, 10));

        //txtID.setEnabled(false);
        panID.setBackground(Color.WHITE);
        pan1.setBackground(Color.WHITE);
        pan2.setBackground(Color.WHITE);
        pan3.setBackground(Color.WHITE);
        pan4.setBackground(Color.WHITE);
        pan5.setBackground(Color.WHITE);
        pan6.setBackground(Color.WHITE);
        panBtn.setBackground(Color.WHITE);
        panIz.setBackground(Color.WHITE);
        panD.setBackground(Color.WHITE);
        tab.setBackground(Color.WHITE);

        panID.add(lblID);
        panID.add(txtID);
        pan3.add(lbl1);
        pan3.add(txt1);
        pan4.add(lbl2);
        pan4.add(txt2);
        pan5.add(lbl3);
        pan5.add(txt3);
        pan6.add(lbl4);
        pan6.add(combo);

        pan1.add(panID);
        pan1.add(pan3);
        pan1.add(pan4);
        pan1.add(pan5);
        pan1.add(pan6);

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

    public int selectedID(IDS o) {
        int id = 0;
        if (o != null) {
            id = o.getID();
        }
        return id;
    }

    void loadDataCombo(JComboBox<IDS> com) {
        try {
            sql = "SELECT id_su, nombre FROM sucursal ORDER BY nombre";
            stmt = con.prepareStatement(sql);
            tabla = stmt.executeQuery();
            while (tabla.next()) {
                int id = tabla.getInt(1);
                String name = tabla.getString(2);
                combo.addItem(new IDS(id, name));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }

    public void consultar() {
        try {
            sql = "SELECT * FROM admins WHERE id_admin = \"" + txtID.getText() + "\"";
            stmt = con.prepareStatement(sql);
            tabla = stmt.executeQuery();
            if (tabla.next()) {
                txt1.setText(tabla.getString(2));
                txt2.setText(tabla.getString(4));
                txt3.setText(tabla.getString(3));
                if (combo.getItemCount() > 1) {
                    combo.setSelectedIndex(Integer.parseInt(txtID.getText()));
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }

    public void modificar() {
        IDS selectedCombo = (IDS) combo.getSelectedItem();
        int sucur = selectedID(selectedCombo);
        try {
            sql = "UPDATE admins SET "
                    + "usuario = \"" + txt1.getText() + "\", "
                    + "correo = \"" + txt3.getText() + "\", "
                    + "pass = \"" + txt2.getText() + "\", "
                    + "id_su = \"" + sucur + "\" "
                    + "WHERE id_admin = \"" + txtID.getText() + "\"";
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
        IDS ids = new IDS();
        if (IDS.id_admin == Integer.parseInt(txtID.getText())) {
            JOptionPane.showMessageDialog(null, "No puedes eliminar el Administrador actual");
        } else {
            try {
                sql = "DELETE FROM admins "
                        + "WHERE id_admin = \"" + txtID.getText() + "\"";
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
    }

    public void insertar() {
        IDS selectedCombo = (IDS) combo.getSelectedItem();
        int sucur = selectedID(selectedCombo);
        try {
            sql = "INSERT INTO admins VALUES "
                    + "(null,"
                    + "\"" + txt1.getText() + "\","
                    + "\"" + txt3.getText() + "\","
                    + "\"" + txt2.getText() + "\","
                    + "\"" + sucur + "\""
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
        txt2.setText("");
        txt3.setText("");
        txt1.requestFocusInWindow();
        if (combo.getItemCount() > 1) {
            combo.setSelectedIndex(0);
            System.out.println(combo.getItemCount());
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
/*
    public static void main(String[] args) {
        Admins a = new Admins();
        a.usar();
    }
*/
}
