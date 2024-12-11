/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GenerarRep;

import DAO.Conexion;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import tienda.MenuEs;

/**
 *
 * @author mps35
 */
public class Ticket {

    Connection con;
    PreparedStatement stmt;
    ResultSet tabla;
    String sql = "";
    int sw = 0;
    Conexion mycon = new Conexion();

    public Ticket() {
        con = mycon.conecta();
    }

    public void reporte(String ID) {

        try {
            URL reportUrl = getClass().getResource("/Reportes/Ticket.jasper");
            URL imgUrl = getClass().getResource("/img/tienda.png");
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("ID", ID);
            parametros.put("img", imgUrl.toString());
            JasperReport jR = (JasperReport) JRLoader.loadObject(reportUrl);
            //JRDataSource dS = new JREmptyDataSource();

            JasperPrint jP = JasperFillManager.fillReport(jR, parametros, con);

            //JasperViewer.viewReport(jP, false);
            JasperViewer jV = new JasperViewer(jP, false);

            jV.setTitle("Ticket de Venta (ID: " + ID + ")");
            jV.setZoomRatio((float) 0.75);
            Image icon = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/img/tienda.png"));
            jV.setIconImage(icon);

            // Añadir listener para el cierre del reporte
            jV.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    // Abrir el nuevo frame al cerrar el reporte
                    MenuEs my = new MenuEs();
                    my.usar();
                }
            });

            jV.setVisible(true);

        } catch (JRException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }

    }

}
