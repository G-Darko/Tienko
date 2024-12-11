/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ayuda;

import java.awt.*;
import javax.swing.*;

/**
 *
 * @author mps35
 */
public class About {
    JDialog fr;
    JPanel pan1, pan2, panMain;
    JLabel logo, lbl1;
    
    String l1 = ""
            + "<html>"
            + "<p>Version: <i>1.0</i></p>"
            + "<p>Descripcion: <i>Sistema de Gestion de Ventas</i></p>"
            + "<p>Desarrollador: <i>Isaac Gael Uribe Ortiz (Darkø)</i></p>"
            + "<p>Año: <i>&copy; 2024</i></p>"
            + "<p>Contacto: <i> <a href=\"mailto:gdarko.uribe@gmail.com\"></a>gdarko.uribe@gmail.com</i></p>"
            + "<p><i></i></p>"
            + "<p><i></i></p>"
            + "<p><i></i></p>"
            + "</html>";

    public About() {
        lbl1 = new JLabel(l1);
        
        logo = new JLabel();
        logo.setIcon(new ImageIcon(getClass().getResource("/img/TIENKO_logo.png")));
        
        pan1 = new JPanel();
        pan2 = new JPanel();
        panMain = new JPanel(new BorderLayout(1, 1));
        
        fr = new JDialog();
    }
    
    public void usar(){
        Image icon = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/img/tienda.png"));
        fr.setIconImage(icon);
        
        fr.setTitle("Acerca de - Tienko");
        fr.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        
        pan1.setBackground(Color.WHITE);
        pan2.setBackground(Color.WHITE);
        
        pan1.add(logo);
        pan2.add(lbl1);
        
        panMain.add(pan1, BorderLayout.NORTH);
        panMain.add(pan2, BorderLayout.CENTER);
        
        fr.add(panMain);
        
        fr.setLayout(new GridLayout(1, 1, 10, 10));
        fr.setResizable(false);
        fr.pack();
        fr.setVisible(true);
        fr.setLocationRelativeTo(null);
    }
    
}
