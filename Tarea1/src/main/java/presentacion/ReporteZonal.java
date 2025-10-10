package presentacion;

import datatypes.DtPrestamo;
import datatypes.Zonas;

import java.text.SimpleDateFormat;
import java.util.List;
import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.table.DefaultTableModel;

import interfaces.IPrestamoController;

public class ReporteZonal extends JInternalFrame{
    private static final long serialVersionUID = 1L;
    private IPrestamoController IpreCont;

    public ReporteZonal(IPrestamoController IpreCont) {
        this.IpreCont = IpreCont;
        setResizable(true);
        setIconifiable(true);
        setMaximizable(true);
        setClosable(true);
        setTitle("Reporte Zonal de Préstamos");
        setBounds(100, 100, 600, 300);
        getContentPane().setLayout(new BorderLayout());

        String[] columnas = { "Zona", "Cantidad de Préstamos", "Detalles" };
        DefaultTableModel model = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 2; // Solo el botón de detalles es editable
            }
        };

        for (Zonas zona : Zonas.values()) {
            List<DtPrestamo> prestamos = IpreCont.obtenerDtPrestamosPorZona(zona);
            model.addRow(new Object[] { zona, prestamos.size(), "Detalles" });
        }

        JTable table = new JTable(model);
        table.getColumn("Detalles").setCellRenderer(new ButtonRenderer());
        table.getColumn("Detalles").setCellEditor(new ButtonEditor(new JCheckBox(), (row) -> mostrarDetallesZona((Zonas) model.getValueAt(row, 0))));

        JScrollPane scroll = new JScrollPane(table);
        getContentPane().add(scroll, BorderLayout.CENTER);
    }

    private void mostrarDetallesZona(Zonas zona) {
        List<DtPrestamo> prestamos = IpreCont.obtenerDtPrestamosPorZona(zona);
        JDialog dialog = new JDialog();
        dialog.setTitle("Detalles de Préstamos - " + zona);
        dialog.setModal(true);
        dialog.setSize(700, 400);
        dialog.setLayout(new BorderLayout());

        JPanel panelTabla = new JPanel(new BorderLayout());
        String[] columnas = { "ID", "Fecha Solicitud", "Estado", "Fecha Devolución", "Lector", "Bibliotecario", "Material" };
        DefaultTableModel model = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        for (DtPrestamo p : prestamos) {
            model.addRow(new Object[] {
            p.getIdPrestamo(),
            sdf.format(p.getFechaSoli()),
            p.getEstadoPres(),
            sdf.format(p.getFechaDev()),
            p.getLector(),
            p.getBibliotecario(),
            p.getMaterial()
            });
        }
        JTable table = new JTable(model);
        table.setRowSelectionAllowed(false);
        table.setColumnSelectionAllowed(false);
        panelTabla.add(new JScrollPane(table), BorderLayout.CENTER);


        dialog.add(panelTabla, BorderLayout.CENTER);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    // Renderizador de botón para la tabla
    class ButtonRenderer extends JButton implements javax.swing.table.TableCellRenderer {
        public ButtonRenderer() { setOpaque(true); }
        public java.awt.Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            setText((value == null) ? "" : value.toString());
            return this;
        }
    }

    // Editor de botón para la tabla
    class ButtonEditor extends DefaultCellEditor {
        private JButton button;
        private java.util.function.IntConsumer onClick;
        private int row;
        public ButtonEditor(JCheckBox checkBox, java.util.function.IntConsumer onClick) {
            super(checkBox);
            this.onClick = onClick;
            button = new JButton();
            button.setOpaque(true);
            button.addActionListener(e -> fireEditingStopped());
        }
        public java.awt.Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            this.row = row;
            button.setText((value == null) ? "" : value.toString());
            return button;
        }
        public Object getCellEditorValue() {
            onClick.accept(row);
            return "Detalles";
        }
    }
}
