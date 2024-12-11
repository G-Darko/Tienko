/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tienda;

import DAO.Conexion;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;

/**
 *
 * @author mps35
 */
public class Login {

    int id_su = 0, id_admin = 0;

    JLabel lbl1, lbl2, lbl3, lbl4, lbl5, lbl6;
    JTextField txt1;
    JPasswordField txt2;
    JButton btn1;

    JFrame fr1;
    JPanel pan1, pan2, pan3, pan4, pan5, pan6, pan7;

    String nulo = "", correo = "", pass = "";

    Connection con;
    PreparedStatement stmt;
    ResultSet tabla;
    int sw = 0;
    String sql;
    Conexion mycon = new Conexion();

    public Login() {

        con = mycon.conecta();

        fr1 = new JFrame("Login - Tienko");

        lbl1 = new JLabel("Usuario    ");
        lbl2 = new JLabel("Password");
        lbl3 = new JLabel("¿Olvidaste tú contraseña?                            ");
        lbl5 = new JLabel("Bienvenid@,  Inicie Sesión");

        txt1 = new JTextField(25);
        txt2 = new JPasswordField(25);

        pan1 = new JPanel();
        pan2 = new JPanel();
        pan3 = new JPanel();
        pan4 = new JPanel();
        pan5 = new JPanel();
        pan6 = new JPanel();
        pan7 = new JPanel();

        btn1 = new JButton("Acceder");

        lbl4 = new JLabel();
        lbl4.setIcon(new ImageIcon(getClass().getResource("/img/login.png")));

    }

    public void usar() {
        Image icon = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/img/tienda.png"));
        fr1.setIconImage(icon);
        
        fr1.setLayout(new GridLayout(1, 1));
        fr1.setResizable(false);

        pan7.setPreferredSize(new Dimension(400, 0));
        pan6.setPreferredSize(new Dimension(300, 60));

        lbl3.setVisible(false);

        pan1.add(lbl4);

        pan5.add(lbl5);

        pan2.add(lbl1);
        pan2.add(txt1);

        pan3.add(lbl2);
        pan3.add(txt2);

        pan4.add(lbl3);
        pan4.add(btn1);

        pan7.add(pan6);
        pan7.add(pan5);
        pan7.add(pan2);
        pan7.add(pan3);
        pan7.add(pan4);

        fr1.add(pan1);
        fr1.add(pan7);

        fr1.pack();
        fr1.setVisible(true);
        fr1.setLocationRelativeTo(null);
        fr1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        txt1.addActionListener((ActionEvent ae) -> {
            if (txt1.getText().equals(nulo)) {
                JOptionPane.showMessageDialog(pan2, "El id del usuario es requerido");
            } else {
                txt2.requestFocusInWindow();
            }
        });

        txt2.addActionListener((ActionEvent ae) -> {
            if (txt2.getText().equals(nulo)) {
                JOptionPane.showMessageDialog(pan3, "La password del usuario es requerida");
            } else {
                btn1.requestFocusInWindow();
            }
        });

        btn1.addActionListener((ActionEvent ae) -> {
            accesar();
        });

        btn1.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                btn1.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }
        });

        lbl3.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                lbl3.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                fr1.dispose();
                CambioPass frameCambioPass = new CambioPass();
                frameCambioPass.setCorreo(correo);
                frameCambioPass.setPass(pass);
                frameCambioPass.setUser(txt1.getText());
                frameCambioPass.usar();
            }
        });
    }

    public void accesar() {
        sql = "SELECT * FROM admins WHERE usuario = " + "\"" + txt1.getText() + "\" ";
        //sql += "AND pass = \"" + txt2.getText() + "\"";
        sw = 0;
        try {
            stmt = con.prepareStatement(sql);
            tabla = stmt.executeQuery();
            if (tabla.next()) {
                sw = 1;
                if (sw == 1) {
                    correo = tabla.getString(3);
                    pass = tabla.getString(4);
                    id_su = tabla.getInt(5);
                    id_admin = tabla.getInt(1);
                    if (tabla.getString(4).equals(txt2.getText())) {
                        sw = 2;
                    } else {
                        JOptionPane.showMessageDialog(null, "El password es incorrecto");
                        lbl3.setVisible(true);
                    }
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        if (sw == 0) {
            JOptionPane.showMessageDialog(null, "El usuario no existe");
        } else if (sw == 2) {
            //JOptionPane.showMessageDialog(null, "Accediendo al sistema");
            IDS myids = new IDS();
            myids.setId_admin(id_admin);
            myids.setId_su(id_su);

            fr1.dispose();
            MenuEs frameMenu = new MenuEs();
            frameMenu.usar();
        }
    }
}
