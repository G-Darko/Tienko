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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.*;

/**
 *
 * @author mps35
 */
public final class CambioPass {

    String correo, user, pass;

    JLabel lbl1, lbl2, lbl3, lblImg, lbl4;
    JTextField txt1;
    JPasswordField txt2, txt3;
    JButton btn1;

    JFrame fr1;
    JPanel pan1, pan2, pan3, pan4, pan5, pan6, pan7, pan8;

    String nulo = "";

    Connection con;
    PreparedStatement stmt;
    ResultSet tabla;
    int sw = 0;
    String sql;
    Conexion mycon = new Conexion();

    public CambioPass() {

        con = mycon.conecta();

        fr1 = new JFrame("Recuperar Password - Tienko");

        lbl1 = new JLabel("Ingresa tu correo     ");
        lbl2 = new JLabel("Password Nuevo       ");
        lbl3 = new JLabel("Confirmar Password");
        lbl4 = new JLabel();

        txt1 = new JTextField(25);
        txt2 = new JPasswordField(25);
        txt3 = new JPasswordField(25);

        txt2.setEnabled(false);
        txt3.setEnabled(false);

        pan1 = new JPanel();
        pan2 = new JPanel();
        pan3 = new JPanel();
        pan4 = new JPanel();
        pan5 = new JPanel();
        pan6 = new JPanel();
        pan7 = new JPanel();
        pan8 = new JPanel();

        btn1 = new JButton("Recupera");

        lblImg = new JLabel();
        lblImg.setIcon(new ImageIcon(getClass().getResource("/img/recuperar.png")));
        
        fr1.addWindowListener(new WindowAdapter() {
            
            @Override
            public void windowClosing(WindowEvent e) {
                // Cambiar al frame nuevo
                Login log = new Login();
                log.usar();
                fr1.dispose();
            }
        });
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getUser() {
        return user;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void usar() {
        Image icon = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/img/tienda.png"));
        fr1.setIconImage(icon);
        
        System.out.println("Cambio, correo: " + correo + " user: " + user + " pass: " + pass);

        fr1.setLayout(new GridLayout(1, 1));
        fr1.setResizable(false);

        lbl4.setText("Bienvenid@ " + user);

        pan2.setPreferredSize(new Dimension(450, 0));
        pan8.setPreferredSize(new Dimension(350, 50));

        txt2.setEnabled(false);
        txt3.setEnabled(false);
        btn1.setEnabled(false);

        pan3.add(lbl1);
        pan3.add(txt1);

        pan4.add(lbl2);
        pan4.add(txt2);

        pan5.add(lbl3);
        pan5.add(txt3);

        pan6.add(btn1);
        pan7.add(lbl4);

        pan1.add(lblImg);
        pan2.add(pan8);
        pan2.add(pan7);
        pan2.add(pan3);
        pan2.add(pan4);
        pan2.add(pan5);
        pan2.add(pan6);

        fr1.add(pan1);
        fr1.add(pan2);

        fr1.pack();
        fr1.setVisible(true);
        fr1.setLocationRelativeTo(null);
        
        txt1.addActionListener((ActionEvent ae) -> {
            if (!(txt1.getText().equals(correo))) {
                JOptionPane.showMessageDialog(pan1, "El correo no coincide con el del usuario");
            } else {
                txt2.setEnabled(true);
                txt3.setEnabled(true);
                txt2.requestFocusInWindow();
            }
        });

        txt2.addActionListener((ActionEvent ae) -> {
            if (txt2.getText().equals(nulo)) {
                JOptionPane.showMessageDialog(pan1, "El password nuevo es requerido");
            } else if (txt2.getText().equals(pass)) {
                JOptionPane.showMessageDialog(pan1, "El password nuevo debe ser diferente al actual");
            } else {
                btn1.setEnabled(true);
                txt3.requestFocusInWindow();
            }
        });

        txt3.addActionListener((ActionEvent ae) -> {
            if (txt3.getText().equals(nulo)) {
                JOptionPane.showMessageDialog(pan1, "El password nuevo es requerido");
            } else if (!(txt2.getText().equals(txt3.getText()))) {
                JOptionPane.showMessageDialog(pan1, "El password a confirmar debe ser igual al nuevo");
            } else {
                btn1.requestFocusInWindow();
            }
        });

        btn1.addActionListener((ActionEvent ae) -> {
            cambia();
        });
    }

    public void cambia() {

        sql = "UPDATE admins SET pass =";
        sql += "\"" + txt2.getText() + "\" " + " where usuario =\"" + user + "\"";
        try {
            stmt = con.prepareStatement(sql);
            sw = stmt.executeUpdate();
            if (sw != 0) {
                JOptionPane.showMessageDialog(null, "Contraseña cambiada");
                fr1.dispose();
                Login myLogin = new Login();
                myLogin.usar();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    /*
    public static void main(String[] args) {
        CambioPass myCambio = new CambioPass();
        myCambio.usar();
    }
     */
}
