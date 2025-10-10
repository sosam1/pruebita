package presentacion;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import java.util.function.IntConsumer;

import datatypes.*;
import interfaces.IPrestamoController;
import interfaces.IUsuarioController;
import excepciones.PrestamoRepetidoException;
import excepciones.CorreoInvalidoException;

public class RegistrarPrestamo extends JInternalFrame {

    private static final long serialVersionUID = 1L;

    private IPrestamoController IpreCont;
    private IUsuarioController IusCont;

    private JTextField textFieldIdMaterial;
    private JTextField textFieldCorreoLector;
    private JTextField textFieldCorreoBiblio;
    private JComboBox<Integer> comboDiaInicio, comboAnioInicio, comboDiaFin, comboAnioFin;
    private JComboBox<String> comboMesInicio, comboMesFin;
    private JComboBox<String> comboBoxEstado;

    public RegistrarPrestamo(IPrestamoController IpreCont, IUsuarioController IusCont) {
        this.IpreCont = IpreCont;
        this.IusCont = IusCont;
        setResizable(true);
        setIconifiable(true);
        setMaximizable(true);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setClosable(true);
        setTitle("Registro de Préstamo");
        setBounds(100, 100, 430, 400);
        getContentPane().setLayout(null);

        JLabel lblIdMaterial = new JLabel("ID Material:");
        lblIdMaterial.setBounds(30, 30, 120, 20);
        getContentPane().add(lblIdMaterial);

        textFieldIdMaterial = new JTextField();
        textFieldIdMaterial.setBounds(180, 30, 200, 25);
        getContentPane().add(textFieldIdMaterial);

        JLabel lblCorreoLector = new JLabel("Correo Lector:");
        lblCorreoLector.setBounds(30, 70, 120, 20);
        getContentPane().add(lblCorreoLector);

        textFieldCorreoLector = new JTextField();
        textFieldCorreoLector.setBounds(180, 70, 200, 25);
        getContentPane().add(textFieldCorreoLector);

        JLabel lblCorreoBiblio = new JLabel("Correo Bibliotecario:");
        lblCorreoBiblio.setBounds(30, 110, 140, 20);
        getContentPane().add(lblCorreoBiblio);

        textFieldCorreoBiblio = new JTextField();
        textFieldCorreoBiblio.setBounds(180, 110, 200, 25);
        getContentPane().add(textFieldCorreoBiblio);

        // Fecha solicitud
        JLabel lblFechaSoli = new JLabel("Fecha Solicitud:");
        lblFechaSoli.setBounds(30, 160, 120, 20);
        getContentPane().add(lblFechaSoli);

        comboDiaInicio = new JComboBox<>();
        comboMesInicio = new JComboBox<>();
        comboAnioInicio = new JComboBox<>();
        for (int i = 1; i <= 31; i++) comboDiaInicio.addItem(i);
        for (String mes : new String[] {
                "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
                "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"
        }) comboMesInicio.addItem(mes);
        int anioActual = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = anioActual+1; i >= 2000; i--) comboAnioInicio.addItem(i);
        comboAnioInicio.setSelectedItem(anioActual);

        comboDiaInicio.setBounds(180, 160, 50, 25);
        comboMesInicio.setBounds(240, 160, 90, 25);
        comboAnioInicio.setBounds(340, 160, 60, 25);
        getContentPane().add(comboDiaInicio);
        getContentPane().add(comboMesInicio);
        getContentPane().add(comboAnioInicio);

        // Fecha devolución
        JLabel lblFechaDev = new JLabel("Fecha Devolución:");
        lblFechaDev.setBounds(30, 210, 140, 20);
        getContentPane().add(lblFechaDev);

        comboDiaFin = new JComboBox<>();
        comboMesFin = new JComboBox<>();
        comboAnioFin = new JComboBox<>();
        for (int i = 1; i <= 31; i++) comboDiaFin.addItem(i);
        for (String mes : new String[] {
                "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
                "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"
        }) comboMesFin.addItem(mes);
        for (int i = anioActual+1; i >= 2000; i--) comboAnioFin.addItem(i);
        comboAnioFin.setSelectedItem(anioActual);

        comboDiaFin.setBounds(180, 210, 50, 25);
        comboMesFin.setBounds(240, 210, 90, 25);
        comboAnioFin.setBounds(340, 210, 60, 25);
        getContentPane().add(comboDiaFin);
        getContentPane().add(comboMesFin);
        getContentPane().add(comboAnioFin);

        JLabel lblEstado = new JLabel("Estado:");
        lblEstado.setBounds(30, 260, 120, 20);
        getContentPane().add(lblEstado);

        comboBoxEstado = new JComboBox<>(new String[] { "PENDIENTE", "EN_CURSO", "DEVUELTO" });
        comboBoxEstado.setBounds(180, 260, 200, 25);
        comboBoxEstado.setSelectedItem("PENDIENTE");
        getContentPane().add(comboBoxEstado);

        JButton btnAceptar = new JButton("Aceptar");
        btnAceptar.setBounds(90, 320, 100, 30);
        btnAceptar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                agregarClaseAceptarActionPerformed(arg0);
            }
        });
        getContentPane().add(btnAceptar);

        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setBounds(230, 320, 100, 30);
        btnCancelar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                agregarClaseCancelarActionPerformed(arg0);
            }
        });
        getContentPane().add(btnCancelar);
    }

    protected void agregarClaseCancelarActionPerformed(ActionEvent arg0) {
        limpiarFormulario();
        setVisible(false);
    }

    protected void agregarClaseAceptarActionPerformed(ActionEvent arg0) {
        String strIdMaterial = textFieldIdMaterial.getText();
        String correoLector = textFieldCorreoLector.getText();
        String correoBiblio = textFieldCorreoBiblio.getText();
        String estadoStr = (String) comboBoxEstado.getSelectedItem();

        try {
            int idMaterial = Integer.parseInt(strIdMaterial);
            EstadosP estado = EstadosP.valueOf(estadoStr);
            int diaInicio = comboDiaInicio.getSelectedIndex() + 1;
            int mesInicio = comboMesInicio.getSelectedIndex();
            int anioInicio = (int) comboAnioInicio.getSelectedItem();

            int diaFin = comboDiaFin.getSelectedIndex() + 1;
            int mesFin = comboMesFin.getSelectedIndex();
            int anioFin = (int) comboAnioFin.getSelectedItem();

            Calendar calInicio = Calendar.getInstance();
            calInicio.set(anioInicio, mesInicio, diaInicio, 0, 0, 0);
            calInicio.set(Calendar.MILLISECOND, 0);
            Date fechaSoli = calInicio.getTime();

            Calendar calFin = Calendar.getInstance();
            calFin.set(anioFin, mesFin, diaFin, 23, 59, 59);
            calFin.set(Calendar.MILLISECOND, 999);
            Date fechaDev = calFin.getTime();

            if (fechaDev.before(fechaSoli)) {
                JOptionPane.showMessageDialog(this, "La fecha de devolución no puede ser anterior a la fecha de solicitud", "Registro de Préstamo", JOptionPane.ERROR_MESSAGE);
                return;
            }

            IpreCont.agregarPrestamo(fechaSoli, fechaDev, estado, correoLector, correoBiblio, idMaterial);

            JOptionPane.showMessageDialog(this, "El préstamo se ha registrado con éxito", "Registro de Préstamo", JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "El ID Material debe ser un número", "Registro de Préstamo", JOptionPane.ERROR_MESSAGE);
            return;
        } catch (PrestamoRepetidoException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Registro de Préstamo", JOptionPane.ERROR_MESSAGE);
            return;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al registrar el préstamo: " + e.getMessage(), "Registro de Préstamo", JOptionPane.ERROR_MESSAGE);
            return;
        }

        limpiarFormulario();
        setVisible(false);
    }

    private void limpiarFormulario() {
        textFieldIdMaterial.setText("");
        textFieldCorreoLector.setText("");
        textFieldCorreoBiblio.setText("");
        comboDiaInicio.setSelectedIndex(0);
        comboMesInicio.setSelectedIndex(0);
        comboAnioInicio.setSelectedIndex(0);
        comboDiaFin.setSelectedIndex(0);
        comboMesFin.setSelectedIndex(0);
        comboAnioFin.setSelectedIndex(0);
        comboBoxEstado.setSelectedItem("PENDIENTE");
    }
}

