package presentacion;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.swing.table.*;

import datatypes.*;
import interfaces.IMaterialController;

public class ListadoMateriales extends JInternalFrame {

    private static final long serialVersionUID = 1L;

    private IMaterialController matCont;
    private JTable table;
    private DefaultTableModel model;
    private String[] columnas = { "ID", "FECHA", "Tipo de material", "Más info" };
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    public ListadoMateriales(IMaterialController matCont) {
        this.matCont = matCont;
        setResizable(true);
        setIconifiable(true);
        setMaximizable(true);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setClosable(true);
        setTitle("Listado de Materiales");
        setBounds(100, 100, 600, 400);
        setLayout(new BorderLayout());

        // Cargar datos
        Object[][] data = cargarDatosMateriales();

        model = new DefaultTableModel(data, columnas) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 3; // Solo la columna del botón es editable
            }
        };

        table = new JTable(model);
        table.getColumn("Más info").setCellRenderer(new ButtonRenderer());
        table.getColumn("Más info").setCellEditor(new ButtonEditor(new JCheckBox(), matCont, this));

        JScrollPane scroll = new JScrollPane(table);
        add(scroll, BorderLayout.CENTER);

        JButton btnCerrar = new JButton("Cerrar");
        JButton btnRefrescar = new JButton("Refrescar");
        btnCerrar.addActionListener(e -> setVisible(false));
        btnRefrescar.addActionListener(e -> {
            model.setRowCount(0); // Limpiar la tabla
            Object[][] nuevosDatos = cargarDatosMateriales();
            for (Object[] fila : nuevosDatos) {
                model.addRow(fila);
            }
        });
        JPanel panelBotones = new JPanel();
        panelBotones.add(btnRefrescar);
        panelBotones.add(btnCerrar);
        add(panelBotones, BorderLayout.SOUTH);
    }

    private Object[][] cargarDatosMateriales() {
        List<DtMaterial> materiales = matCont.obtenerMateriales();
        Object[][] data = new Object[materiales.size()][4];

        for (int i = 0; i < materiales.size(); i++) {
            DtMaterial m = materiales.get(i);
            data[i][0] = m.getIdMaterial();
            data[i][1] = sdf.format(m.getFechaRegistro());
            if (m instanceof DtLibro) {
                data[i][2] = "Libro";
            } else if (m instanceof DtArticuloEspecial) {
                data[i][2] = "Artículo Especial";
            } 
            data[i][3] = "Más info";
        }
        return data;
    }

    // Renderizador para el botón
    class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() {
            setText("Más info");
        }
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            return this;
        }
    }

    // Editor para el botón
    class ButtonEditor extends DefaultCellEditor {
        private JButton button;
        private int selectedRow;
        private IMaterialController matCont;
        private JInternalFrame parentFrame;

        public ButtonEditor(JCheckBox checkBox, IMaterialController matCont, JInternalFrame parentFrame) {
            super(checkBox);
            this.matCont = matCont;
            this.parentFrame = parentFrame;
            button = new JButton("Más info");
            button.addActionListener(e -> masInfo());
        }

        public Component getTableCellEditorComponent(JTable table, Object value,
                boolean isSelected, int row, int column) {
            selectedRow = row;
            return button;
        }

        private void masInfo() {
            int id = (int) model.getValueAt(selectedRow, 0);
            DtMaterial mat = null;
            // Buscar el material por ID en la lista
            for (DtMaterial m : matCont.obtenerMateriales()) {
                if (m.getIdMaterial() == id) {
                    mat = m;
                    break;
                }
            }
            if (mat == null) {
                JOptionPane.showMessageDialog(parentFrame, "No se encontró el material.", "Error", JOptionPane.ERROR_MESSAGE);
                fireEditingStopped();
                return;
            }
            StringBuilder info = new StringBuilder();
            info.append("ID: ").append(mat.getIdMaterial()).append("\n");
            info.append("Fecha: ").append(sdf.format(mat.getFechaRegistro())).append("\n");
            if (mat instanceof DtLibro) {
                DtLibro libro = (DtLibro) mat;
                info.append("Tipo: Libro\n");
                info.append("Título: ").append(libro.getTitulo()).append("\n");
                info.append("Cantidad de páginas: ").append(libro.getCantPag()).append("\n");
            } else if (mat instanceof DtArticuloEspecial) {
                DtArticuloEspecial art = (DtArticuloEspecial) mat;
                info.append("Tipo: Artículo Especial\n");
                info.append("Descripción: ").append(art.getDescripcion()).append("\n");
                info.append("Peso: ").append(art.getPeso()).append("\n");
                info.append("Dimensiones físicas: ").append(art.getDimFisica()).append("\n");
            }
            JOptionPane.showMessageDialog(parentFrame, info.toString(), "Información del material", JOptionPane.INFORMATION_MESSAGE);
            fireEditingStopped();
        }
    }
}