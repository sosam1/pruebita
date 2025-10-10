package presentacion;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.swing.table.DefaultTableModel;

import datatypes.*;
import interfaces.IMaterialController;

public class ListarMaterialPorRango extends JInternalFrame {

    private static final long serialVersionUID = 1L;

    private IMaterialController matCont;
    private JTable table;
    private DefaultTableModel model;
    private JComboBox<Integer> comboDiaInicio, comboAnioInicio, comboDiaFin, comboAnioFin;
    private JComboBox<String> comboMesInicio, comboMesFin;
    private String[] columnas = { "ID", "Fecha Registro", "Titulo", "Cantidad Pag", "Descripcion", "Peso", "DimFisica" };
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    public ListarMaterialPorRango(IMaterialController matCont) {
        this.matCont = matCont;
        setResizable(true);
        setIconifiable(true);
        setMaximizable(true);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setClosable(true);
        setTitle("Listar Materiales por Rango");
        setBounds(100, 100, 700, 500);
        setLayout(new BorderLayout());

        JPanel panelForm = new JPanel(new GridLayout(3, 1, 5, 5));

        // Fecha inicio
        comboDiaInicio = new JComboBox<>();
        comboMesInicio = new JComboBox<>();
        comboAnioInicio = new JComboBox<>();
        for (int i = 1; i <= 31; i++) comboDiaInicio.addItem(i);
        for (String mes : new String[] {
                "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
                "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"
        }) comboMesInicio.addItem(mes);
        int anioActual = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = anioActual; i >= 1900; i--) comboAnioInicio.addItem(i);

        JPanel panelFechaInicio = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelFechaInicio.add(new JLabel("Fecha Inicio:"));
        panelFechaInicio.add(comboDiaInicio);
        panelFechaInicio.add(comboMesInicio);
        panelFechaInicio.add(comboAnioInicio);
        panelForm.add(panelFechaInicio);

        // Fecha fin
        comboDiaFin = new JComboBox<>();
        comboMesFin = new JComboBox<>();
        comboAnioFin = new JComboBox<>();
        for (int i = 1; i <= 31; i++) comboDiaFin.addItem(i);
        for (String mes : new String[] {
                "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
                "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"
        }) comboMesFin.addItem(mes);
        for (int i = anioActual; i >= 1900; i--) comboAnioFin.addItem(i);

        JPanel panelFechaFin = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelFechaFin.add(new JLabel("Fecha Fin:"));
        panelFechaFin.add(comboDiaFin);
        panelFechaFin.add(comboMesFin);
        panelFechaFin.add(comboAnioFin);
        panelForm.add(panelFechaFin);

        // Botón buscar
        JButton btnBuscar = new JButton("Buscar");
        btnBuscar.addActionListener(this::eliminarInscripcionAceptarActionPerformed);
        JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelBoton.add(btnBuscar);
        panelForm.add(panelBoton);

        add(panelForm, BorderLayout.NORTH);

        // Tabla vacía al inicio
        Object[][] data = new Object[0][columnas.length];
        model = new DefaultTableModel(data, columnas) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(model);
        JScrollPane scroll = new JScrollPane(table);
        add(scroll, BorderLayout.CENTER);

        // Botón cerrar
        JButton btnCerrar = new JButton("Cancelar");
        btnCerrar.addActionListener(this::eliminarInscripcionCancelarActionPerformed);
        JPanel panelBotones = new JPanel();
        panelBotones.add(btnCerrar);
        add(panelBotones, BorderLayout.SOUTH);

        iniciarlizarComboBoxes();
    }

    public void iniciarlizarComboBoxes() {
        // Ya inicializados en el constructor, pero aquí podrías resetear valores si lo necesitas
        comboDiaInicio.setSelectedIndex(0);
        comboMesInicio.setSelectedIndex(0);
        comboAnioInicio.setSelectedIndex(0);
        comboDiaFin.setSelectedIndex(0);
        comboMesFin.setSelectedIndex(0);
        comboAnioFin.setSelectedIndex(0);
    }

    protected void eliminarInscripcionCancelarActionPerformed(ActionEvent arg0) {
        setVisible(false);
        // Limpia la tabla
        model.setRowCount(0);
    }

    protected void eliminarInscripcionAceptarActionPerformed(ActionEvent arg0) {
        if (!checkFormulario()) return;
        try {
            int diaInicio = comboDiaInicio.getSelectedIndex() + 1;
            int mesInicio = comboMesInicio.getSelectedIndex();
            int anioInicio = (int) comboAnioInicio.getSelectedItem();

            int diaFin = comboDiaFin.getSelectedIndex() + 1;
            int mesFin = comboMesFin.getSelectedIndex();
            int anioFin = (int) comboAnioFin.getSelectedItem();

            Calendar calInicio = Calendar.getInstance();
            calInicio.set(anioInicio, mesInicio, diaInicio, 0, 0, 0);
            calInicio.set(Calendar.MILLISECOND, 0);
            Date fechaInicio = calInicio.getTime();

            Calendar calFin = Calendar.getInstance();
            calFin.set(anioFin, mesFin, diaFin, 23, 59, 59);
            calFin.set(Calendar.MILLISECOND, 999);
            Date fechaFin = calFin.getTime();

            List<DtMaterial> materiales = matCont.obtenerMaterialesPorRango(fechaInicio, fechaFin);

            if (materiales.isEmpty()) {
                model.setRowCount(0); // Limpia la tabla
                JOptionPane.showMessageDialog(this, "No hay materiales en ese rango de fechas.", "Sin resultados", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            Object[][] nuevaData = new Object[materiales.size()][columnas.length];

            for (int i = 0; i < materiales.size(); i++) {
                DtMaterial m = materiales.get(i);
                nuevaData[i][0] = m.getIdMaterial();
                nuevaData[i][1] = sdf.format(m.getFechaRegistro());
                if (m instanceof DtLibro) {
                    DtLibro libro = (DtLibro) m;
                    nuevaData[i][2] = libro.getTitulo();
                    nuevaData[i][3] = libro.getCantPag();
                    nuevaData[i][4] = "N/A";
                    nuevaData[i][5] = "N/A";
                    nuevaData[i][6] = "N/A";
                } else if (m instanceof DtArticuloEspecial) {
                    DtArticuloEspecial art = (DtArticuloEspecial) m;
                    nuevaData[i][2] = "N/A";
                    nuevaData[i][3] = "N/A";
                    nuevaData[i][4] = art.getDescripcion();
                    nuevaData[i][5] = art.getPeso();
                    nuevaData[i][6] = art.getDimFisica();
                } else {
                    nuevaData[i][2] = "N/A";
                    nuevaData[i][3] = "N/A";
                    nuevaData[i][4] = "N/A";
                    nuevaData[i][5] = "N/A";
                    nuevaData[i][6] = "N/A";
                }
            }
            model.setDataVector(nuevaData, columnas);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al buscar materiales: " + ex.getMessage());
        }
    }

    private boolean checkFormulario() {
        if (comboDiaInicio.getSelectedItem() == null ||
        comboMesInicio.getSelectedItem() == null ||
        comboAnioInicio.getSelectedItem() == null ||
        comboDiaFin.getSelectedItem() == null ||
        comboMesFin.getSelectedItem() == null ||
        comboAnioFin.getSelectedItem() == null) {
        JOptionPane.showMessageDialog(this, "No puede haber campos vacíos", "Listar Materiales por Rango",
                JOptionPane.ERROR_MESSAGE);
        return false;
        }
        try {
            int diaInicio = (int) comboDiaInicio.getSelectedItem();
            int mesInicio = comboMesInicio.getSelectedIndex();
            int anioInicio = (int) comboAnioInicio.getSelectedItem();

            int diaFin = (int) comboDiaFin.getSelectedItem();
            int mesFin = comboMesFin.getSelectedIndex();
            int anioFin = (int) comboAnioFin.getSelectedItem();

            java.util.Calendar calInicio = java.util.Calendar.getInstance();
            calInicio.set(anioInicio, mesInicio, diaInicio, 0, 0, 0);
            calInicio.set(java.util.Calendar.MILLISECOND, 0);

            java.util.Calendar calFin = java.util.Calendar.getInstance();
            calFin.set(anioFin, mesFin, diaFin, 23, 59, 59);
            calFin.set(java.util.Calendar.MILLISECOND, 999);

            if (calInicio.getTime().after(calFin.getTime())) {
                JOptionPane.showMessageDialog(this, "La fecha de inicio no puede ser posterior a la fecha de fin", "Listar Materiales por Rango",
                        JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error en las fechas seleccionadas", "Listar Materiales por Rango",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }
            return true;
        }
}