package tienda;

import com.toedter.calendar.JCalendar;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class JCalendarFull extends JDialog {

    public JCalendar jcFecha;
    public JButton jbAceptar, jbLimpiar, jbAhora;
    public JComboBox<String> jcHora, jcMinutos, jcSegundos;
    public int dia, mes, year, diaSemana, hora, minutos, segundos;
    String nombreDia, nombreMes, horaCadena, minutosCadena, segundosCadena, fechaCompleta, horaCompleta;
    public boolean selecciono;

    // constructor para crear un JCalendar con la fecha actual del sistema
    public JCalendarFull(JFrame jf) {
        super(jf, "Selector de Fecha", true);
        set(); // asignar atributos con valores por defecto
        crearGUI();
    }

    /* 
    Constructor para crear un JCalendar con una fecha establecida
    La fecha que se desea establecer debe estar en formato "dia;mes;anio;hora;minutos"
    dia, mes, hora, minutos con 2 digitos. hora en formato militar
    anio con 4 digitos    
    Por ej: para establecer la fecha de 7 de marzo de 1979 a las 3:30 pm, 
    la cadena debe ser: "07;03;1979;15;30"
     */
    public JCalendarFull(JFrame jf, String fecha) {
        super(jf, "Selector de Fecha", true);
        set(); // asignar atributos con valores por defecto
        crearGUI();

        selecciono = true;
        String tokens[] = fecha.split(";");
        Calendar calendario = new GregorianCalendar(
                Integer.parseInt(tokens[2]), //convertir el año en entero
                Integer.parseInt(tokens[1]) - 1, //convertir el mes en entero
                Integer.parseInt(tokens[0]));       //convertir el dia en entero
        TimeZone timeZone = TimeZone.getDefault();
        calendario.setTimeZone(timeZone);
        jcFecha.setDate(calendario.getTime());  //asignar la fecha al jcFecha

        jcHora.setSelectedItem(tokens[3]);      //asignar la hora al combobox
        jcMinutos.setSelectedItem(tokens[4]);   //asignar los minutos al combobox
        jcSegundos.setSelectedItem(tokens[5]);  //asignar los segundos al combobox

        dia = calendario.get(Calendar.DATE);
        mes = calendario.get(Calendar.MONTH) + 1;
        year = calendario.get(Calendar.YEAR);
        diaSemana = calendario.get(Calendar.DAY_OF_WEEK);
        hora = Integer.parseInt((String) jcHora.getSelectedItem());
        minutos = Integer.parseInt((String) jcMinutos.getSelectedItem());
        segundos = Integer.parseInt((String) jcSegundos.getSelectedItem());
        horaCadena = (String) jcHora.getSelectedItem();
        minutosCadena = (String) jcMinutos.getSelectedItem();
        segundosCadena = (String) jcSegundos.getSelectedItem();

        setNombreDia();
        setNombreMes();
    }

    public void set() {
        dia = mes = year = diaSemana = hora = minutos = segundos = 0;
        nombreDia = nombreMes = horaCadena = minutosCadena = segundosCadena = fechaCompleta = horaCompleta = "";
        selecciono = false;
    }

    public void crearGUI() {
        // crear el JDialog
        setSize(350, 330);
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        // asignar icono en la barra de titulo del JDialog
        Image icon = new ImageIcon(getClass().getResource("/img/calendar-icon.png")).getImage();
        setIconImage(icon);

        // crear los componentes para ser adicionados el JDialog
        jcFecha = new JCalendar();// crear el JCalendar con la fecha actual del sistema
        jcFecha.setBounds(30, 10, 240, 200);// Ubicar y agregar al panel             
        add(jcFecha);

        jcHora = new JComboBox<String>();
        for (int i = 0; i < 24; i++) {
            if (i < 10) {
                jcHora.addItem("0" + i);
            } else {
                jcHora.addItem("" + i);
            }
        }
        jcHora.setBounds(87, 215, 48, 25);
        add(jcHora);

        JLabel jl = new JLabel(":");
        jl.setBounds(140, 215, 20, 25);
        add(jl);

        jcMinutos = new JComboBox<String>();
        for (int i = 0; i < 60; i++) {
            if (i < 10) {
                jcMinutos.addItem("0" + i);
            } else {
                jcMinutos.addItem("" + i);
            }
        }
        jcMinutos.setBounds(147, 215, 48, 25);
        add(jcMinutos);

        JLabel jl2 = new JLabel(":");
        jl2.setBounds(200, 215, 10, 25);
        add(jl2);

        jcSegundos = new JComboBox<String>();
        for (int i = 0; i < 60; i++) {
            if (i < 10) {
                jcSegundos.addItem("0" + i);
            } else {
                jcSegundos.addItem("" + i);
            }
        }
        jcSegundos.setBounds(207, 215, 48, 25);
        add(jcSegundos);

        jbAceptar = new JButton("Aceptar");
        jbAceptar.setBounds(20, 248, 85, 25);// Ubicar y agregar al panel
        jbAceptar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                evento_jbAceptar();
            }
        });
        add(jbAceptar);

        jbAhora = new JButton("Ahora");
        jbAhora.setBounds(110, 248, 80, 25);// Ubicar y agregar al panel
        jbAhora.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                evento_jbAhora();
            }
        });
        add(jbAhora);

        jbLimpiar = new JButton("Cancelar");
        jbLimpiar.setBounds(195, 248, 85, 25);// Ubicar y agregar al panel
        jbLimpiar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                evento_jbLimpiar();
            }
        });
        add(jbLimpiar);
    }

    public void evento_jbAceptar() {
        selecciono = true;
        Calendar calendario = jcFecha.getCalendar();
        TimeZone timeZone = TimeZone.getDefault();
        calendario.setTimeZone(timeZone);
        dia = calendario.get(Calendar.DATE);
        mes = calendario.get(Calendar.MONTH) + 1;
        year = calendario.get(Calendar.YEAR);
        diaSemana = calendario.get(Calendar.DAY_OF_WEEK);
        hora = Integer.parseInt((String) jcHora.getSelectedItem());
        minutos = Integer.parseInt((String) jcMinutos.getSelectedItem());
        segundos = Integer.parseInt((String) jcSegundos.getSelectedItem());
        horaCadena = (String) jcHora.getSelectedItem();
        minutosCadena = (String) jcMinutos.getSelectedItem();
        segundosCadena = (String) jcSegundos.getSelectedItem();

        setNombreDia();
        setNombreMes();

        setVisible(false);
    }

    public void evento_jbAhora() {
        jcFecha.setDate(new Date());// asignar la fecha actual del sistema al JCalendar

        // obtener la fecha actual del sistema
        Calendar actual = Calendar.getInstance();
        TimeZone timeZone = TimeZone.getDefault();
        actual.setTimeZone(timeZone);
        int hora = actual.get(Calendar.HOUR_OF_DAY); // obtener la hora del dia
        int min = actual.get(Calendar.MINUTE); // obtener los minutos del dia
        int sec = actual.get(Calendar.SECOND); // obtener los segundos del dia

        // asignar la hora al combobox
        if (hora < 10) {
            jcHora.setSelectedItem("0" + hora);
        } else {
            jcHora.setSelectedItem(hora + "");
        }

        // asignar los minutos al combobox
        if (min < 10) {
            jcMinutos.setSelectedItem("0" + min);
        } else {
            jcMinutos.setSelectedItem(min + "");
        }

        // asignar los segundos al combobox
        if (sec < 10) {
            jcSegundos.setSelectedItem("0" + sec);
        } else {
            jcSegundos.setSelectedItem(sec + "");
        }
    }

    public void evento_jbLimpiar() {
        set(); // asignar atributos con valores por defecto
        evento_jbAhora();
        setVisible(false);
    }

    public void setNombreDia() {
        switch (diaSemana) {
            case 1:
                nombreDia = "Domingo";
                break;
            case 2:
                nombreDia = "Lunes";
                break;
            case 3:
                nombreDia = "Martes";
                break;
            case 4:
                nombreDia = "Miércoles";
                break;
            case 5:
                nombreDia = "Jueves";
                break;
            case 6:
                nombreDia = "Viernes";
                break;
            case 7:
                nombreDia = "Sábado";
                break;
        }
    }

    public void setNombreMes() {
        switch (mes) {
            case 1:
                nombreMes = "Enero";
                break;
            case 2:
                nombreMes = "Febrero";
                break;
            case 3:
                nombreMes = "Marzo";
                break;
            case 4:
                nombreMes = "Abril";
                break;
            case 5:
                nombreMes = "Mayo";
                break;
            case 6:
                nombreMes = "Junio";
                break;
            case 7:
                nombreMes = "Julio";
                break;
            case 8:
                nombreMes = "Agosto";
                break;
            case 9:
                nombreMes = "Septiembre";
                break;
            case 10:
                nombreMes = "Octubre";
                break;
            case 11:
                nombreMes = "Noviembre";
                break;
            case 12:
                nombreMes = "Diciembre";
                break;
        }
    }

    public void setFecha(int tipo) {
        fechaCompleta = "";
        switch (tipo) {
            case 1: // fecha en formato DD/MM/YYYY (2 digitos para el dia y el mes. 4 digitos para el anio)
                if (dia < 10) {
                    fechaCompleta += "0" + dia + "/";
                } else {
                    fechaCompleta += dia + "/";
                }

                if (mes < 10) {
                    fechaCompleta += "0" + mes + "/";
                } else {
                    fechaCompleta += mes + "/";
                }

                fechaCompleta += year;
                break;
            case 2: // fecha en formato YYYY/MM/DD (4 digitos para el anio. 2 digitos para el mes y el dia)
                fechaCompleta += year + "-";

                if (mes < 10) {
                    fechaCompleta += "0" + mes + "-";
                } else {
                    fechaCompleta += mes + "-";
                }

                if (dia < 10) {
                    fechaCompleta += "0" + dia;
                } else {
                    fechaCompleta += dia;
                }
                break;
            case 3: // fecha en formato MM/DD/YYYY (2 digitos para el mes y el dia. 4 digitos para el anio)
                if (mes < 10) {
                    fechaCompleta += "0" + mes + "-";
                } else {
                    fechaCompleta += mes + "-";
                }

                if (dia < 10) {
                    fechaCompleta += "0" + dia + "-";
                } else {
                    fechaCompleta += dia + "-";
                }

                fechaCompleta += year;
                break;
            default:
                fechaCompleta = "FORMATO INVALIDO";
        }
    }

    public int getDia() {
        return dia;
    }

    public int getMes() {
        return mes;
    }

    public int getYear() {
        return year;
    }

    public int getHora() {
        return hora;
    }

    public int getMinutos() {
        return minutos;
    }

    public int getDiaSemana() {
        return diaSemana;
    }

    public String getNombreDia() {
        return nombreDia;
    }

    public String getNombreMes() {
        return nombreMes;
    }

    public String getHoraCadena() {
        return horaCadena;
    }

    public String getMinutosCadena() {
        return minutosCadena;
    }

    public String getHoraCompleta() {
        return horaCadena + ":" + minutosCadena + ":" + segundosCadena;
    }

    public String getFechaCompleta(int tipo) {
        if (selecciono) {
            setFecha(tipo);
        } else {
            fechaCompleta = "";
        }
        return fechaCompleta;
    }

}
