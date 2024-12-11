/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tienda;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import javax.swing.*;

/**
 *
 * @author mps35
 */
public class Manual {

    public Manual() {

    }

    public void showManual() {
        try {
            URL urlPdf = getClass().getResource("/Ayuda/Tienko Manual.pdf");
            if (urlPdf == null) {
                JOptionPane.showMessageDialog(null, "Archivo no encontrado");
                return;
            }

            File tempFile = File.createTempFile("Manual de Uso", ".pdf");
            try (InputStream input = urlPdf.openStream()) {
                Files.copy(input, tempFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            }
            
            if (Desktop.isDesktopSupported()){
                Desktop.getDesktop().open(tempFile);
            }else{
                JOptionPane.showMessageDialog(null, "No se puede abrir el archivo automaticamente");
            }

            tempFile.deleteOnExit();
            
        } catch (HeadlessException | IOException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }

}
