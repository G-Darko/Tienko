/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tienda;

/**
 *
 * @author mps35
 */
import Ayuda.About;
import GenerarRep.*;
import tienda.Catalogos.*;
import tienda.Consultas.*;
import tienda.Procesos.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MenuEs {

    JFrame fr1;

    JMenuBar mb1;

    JMenu menu1, menu2, menu3, menu4, menu5;

    JMenuItem item11, item12, item13, item14, item15;
    JMenuItem item21, item22, item23, item24;
    JMenuItem item31, item32, item33, item34;
    JMenuItem item41, item42, item43, item44;
    JMenuItem item51, item52;

    JLabel lbl1;

    public MenuEs() {
        fr1 = new JFrame("Menu Principal - Tienko");

        mb1 = new JMenuBar();

        menu1 = new JMenu("Catalogos");
        menu2 = new JMenu("Procesos");
        menu3 = new JMenu("Reportes");
        menu4 = new JMenu("Consultas");
        menu5 = new JMenu("Ayuda");

        item11 = new JMenuItem("Administradores");
        item12 = new JMenuItem("Productos");
        item13 = new JMenuItem("Ventas");
        item14 = new JMenuItem("Marcas");
        item15 = new JMenuItem("Informacion de la Sucursal");

        item21 = new JMenuItem("IVA Productos");
        item22 = new JMenuItem("Ganancia Proyecta");
        item23 = new JMenuItem("Ganancia Generada");
        item24 = new JMenuItem("Realizar Venta");

        item31 = new JMenuItem("Administradores");
        item32 = new JMenuItem("Productos");
        item33 = new JMenuItem("Ventas");
        item34 = new JMenuItem("Ticket por ID");

        item41 = new JMenuItem("Administradores");
        item42 = new JMenuItem("Marcas");
        item43 = new JMenuItem("Productos");
        item44 = new JMenuItem("Ventas");

        item51 = new JMenuItem("Acerca De...");
        item52 = new JMenuItem("Manual de Uso (F1)");

        lbl1 = new JLabel();
        lbl1.setIcon(new ImageIcon(getClass().getResource("/img/tienda.png")));

        fr1.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int opc = JOptionPane.showConfirmDialog(null, "¿Deseas salir?", "Salir", JOptionPane.YES_NO_OPTION);
                if (opc == 0) {
                    fr1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                } else {
                    fr1.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                }
            }
        });
    }

    public void usar() {
        Image icon = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/img/tienda.png"));
        fr1.setIconImage(icon);
        
        fr1.setExtendedState(JFrame.MAXIMIZED_BOTH);
        fr1.setResizable(false);

        menu1.add(item11);
        menu1.add(item14);
        menu1.add(item12);
        menu1.add(item13);
        menu1.add(item15);

        menu2.add(item21);
        menu2.add(item22);
        menu2.add(item23);
        menu2.add(item24);

        menu3.add(item31);
        menu3.add(item32);
        menu3.add(item33);
        menu3.add(item34);

        menu4.add(item41);
        menu4.add(item42);
        menu4.add(item43);
        menu4.add(item44);

        menu5.add(item51);
        menu5.add(item52);

        mb1.add(menu1);
        mb1.add(menu2);
        mb1.add(menu3);
        mb1.add(menu4);
        mb1.add(menu5);

        fr1.setJMenuBar(mb1);
        fr1.add(lbl1);

        mb1.setBackground(Color.white);
        fr1.getContentPane().setBackground(Color.WHITE);

        fr1.pack();
        fr1.setVisible(true);
        fr1.setLocationRelativeTo(null);

        item11.addActionListener((ActionEvent ae) -> {
            fr1.dispose();
            Admins my = new Admins();
            my.usar();
        });
        item14.addActionListener((ActionEvent ae) -> {
            fr1.dispose();
            Marcas my = new Marcas();
            my.usar();
        });
        item12.addActionListener((ActionEvent ae) -> {
            fr1.dispose();
            Productos my = new Productos();
            my.usar();
        });
        item13.addActionListener((ActionEvent ae) -> {
            fr1.dispose();
            Ventas my = new Ventas();
            my.usar();
        });
        item15.addActionListener((ActionEvent ae) -> {
            fr1.dispose();
            Sucursal my = new Sucursal();
            my.usar();
        });

        item41.addActionListener((ActionEvent ae) -> {
            fr1.dispose();
            AdminsC my = new AdminsC();
            my.usar();
        });
        item42.addActionListener((ActionEvent ae) -> {
            fr1.dispose();
            MarcasC my = new MarcasC();
            my.usar();
        });
        item43.addActionListener((ActionEvent ae) -> {
            fr1.dispose();
            ProductosC my = new ProductosC();
            my.usar();
        });
        item44.addActionListener((ActionEvent ae) -> {
            fr1.dispose();
            VentasC my = new VentasC();
            my.usar();
        });

        item21.addActionListener((ActionEvent ae) -> {
            fr1.dispose();
            IVA my = new IVA();
            my.usar();
        });
        item22.addActionListener((ActionEvent ae) -> {
            fr1.dispose();
            Ganancia my = new Ganancia();
            my.usar();
        });
        item23.addActionListener((ActionEvent ae) -> {
            fr1.dispose();
            GananciaGenerada my = new GananciaGenerada();
            my.usar();
        });
        item24.addActionListener((ActionEvent ae) -> {
            fr1.dispose();
            NuevaVenta my = new NuevaVenta();
            my.usar();
        });

        item31.addActionListener((ActionEvent ae) -> {
            fr1.dispose();
            AdminsR my = new AdminsR();
            my.reporte();
        });
        item32.addActionListener((ActionEvent ae) -> {
            fr1.dispose();
            ProductosR my = new ProductosR();
            my.reporte();
        });
        item33.addActionListener((ActionEvent ae) -> {
            fr1.dispose();
            VentasR my = new VentasR();
            my.reporte();
        });
        item34.addActionListener((ActionEvent ae) -> {
            String ID = JOptionPane.showInputDialog(null, "Ingresa el ID Venta del Ticket a Imprimir", "Imprimir Ticket", JOptionPane.QUESTION_MESSAGE);
            if (ID == null || ID.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "El ID no existe", "ID Invalido", JOptionPane.ERROR_MESSAGE);
            } else {
                fr1.dispose();
                Ticket my = new Ticket();
                my.reporte(ID);
            }
        });

        item51.addActionListener((ActionEvent ae) -> {
            //fr1.dispose();
            About my = new About();
            my.usar();
        });
        item52.addActionListener((ActionEvent ae) -> {
            //fr1.dispose();
            Manual my = new Manual();
            my.showManual();
        });
        
        Manual my = new Manual();
        fr1.getRootPane().registerKeyboardAction(
                e -> my.showManual(),
                KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0),
                JComponent.WHEN_IN_FOCUSED_WINDOW
        );
    }
    /*
    public static void main(String[] args) {
        MenuEs myMenu = new MenuEs();
        myMenu.usar();
    }
     */
}
