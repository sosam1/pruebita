package presentacion;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableCellEditor;
import java.util.function.IntConsumer;

import datatypes.*;
import interfaces.IPrestamoController;
import interfaces.IUsuarioController;

public class ListarPrestamos extends JInternalFrame {
    
    private static final long serialVersionUID = 1L;
    
    private IPrestamoController IpreCont;
    private JTable table;
    private DefaultTableModel model;
    private List<DtPrestamo> prestamos;
    private SimpleDateFormat sdf;
    private JComboBox<String> comboEstadoFiltro;
    private JLabel lblEstadoFiltro;

    public ListarPrestamos(IPrestamoController IpreCont) {
        this.IpreCont = IpreCont;
        setResizable(true);
        setIconifiable(true);
        setMaximizable(true);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setClosable(true);
        setTitle("Listado de Préstamos");
        setBounds(100, 100, 800, 500);
        setLayout(new BorderLayout());

        // Inicializar formato de fecha
        sdf = new SimpleDateFormat("dd/MM/yyyy");

        // Panel superior para filtros (opcional)
        JPanel panelFiltros = crearPanelFiltros();
        add(panelFiltros, BorderLayout.NORTH);

        // Crear tabla
        crearTabla();
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Panel inferior para botones
        JPanel panelBotones = crearPanelBotones();
        add(panelBotones, BorderLayout.SOUTH);

        // Cargar datos
        cargarPrestamos();

        // Actualizar datos cada vez que se muestra la ventana
        addInternalFrameListener(new javax.swing.event.InternalFrameAdapter() {
            @Override
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent e) {
                cargarPrestamos();
            }
        });
    }
    
    private JPanel crearPanelFiltros() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        
        lblEstadoFiltro = new JLabel("Filtrar por Estado:");
        comboEstadoFiltro = new JComboBox<>(new String[]{"TODOS", "PENDIENTE", "EN_CURSO", "DEVUELTO"});

        JButton btnFiltrar = new JButton("Filtrar");
        btnFiltrar.addActionListener(e -> {
            String estadoSeleccionado = (String)comboEstadoFiltro.getSelectedItem();

            lblEstadoFiltro.setText("Filtrar por Estado:");

            if ("TODOS".equalsIgnoreCase(estadoSeleccionado)) {
                cargarPrestamos();
            } else {
                filtrarPorEstado(EstadosP.valueOf(estadoSeleccionado));
            }
        });

        panel.add(lblEstadoFiltro);
        panel.add(comboEstadoFiltro);
        panel.add(btnFiltrar);

        return panel;
    }
    
    private void crearTabla() {
        String[] columnas = {"ID", "Fecha Solicitud", "Estado", "Fecha Devolución", 
                            "Lector", "Bibliotecario", "Material", "Acciones"};
        model = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 7; // Solo la columna de acciones es editable
            }
        };
        
        table = new JTable(model);
        table.getColumn("Acciones").setCellRenderer(new ButtonRenderer());
        table.getColumn("Acciones").setCellEditor(
            new ButtonEditor(new JCheckBox(), this::mostrarDialogoEdicion)
        );
    }
    
    private JPanel crearPanelBotones() {
        JPanel panel = new JPanel();
        
        JButton btnRefrescar = new JButton("Refrescar");
        btnRefrescar.addActionListener(e -> {
            cargarPrestamos();
            // Restablecer la etiqueta y el ComboBox al refrescar
            if (lblEstadoFiltro != null) lblEstadoFiltro.setText("Filtrar por Estado:");
            if (comboEstadoFiltro != null) comboEstadoFiltro.setSelectedItem("TODOS");
        });
        

        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.addActionListener(e -> setVisible(false));
        
        panel.add(btnRefrescar);
        panel.add(btnCerrar);
        
        return panel;
    }
    
    private void cargarPrestamos() {
        model.setRowCount(0); // Limpiar tabla
        prestamos = IpreCont.obtenerDtPrestamos();
        
        for (DtPrestamo p : prestamos) {
            model.addRow(new Object[]{
                p.getIdPrestamo(),
                sdf.format(p.getFechaSoli()),
                p.getEstadoPres(),
                sdf.format(p.getFechaDev()),
                p.getLector(),
                p.getBibliotecario(),
                p.getMaterial(),
                "Editar"
            });
        }
    }
    
    private void filtrarPorEstado(EstadosP estado) {
        model.setRowCount(0);
        for (DtPrestamo p : prestamos) {
            if (p.getEstadoPres() == estado) {
                model.addRow(new Object[]{
                    p.getIdPrestamo(),
                    sdf.format(p.getFechaSoli()),
                    p.getEstadoPres(),
                    sdf.format(p.getFechaDev()),
                    p.getLector(),
                    p.getBibliotecario(),
                    p.getMaterial(),
                    "Editar"
                });
            }
        }
    }
    
    private void mostrarDialogoEdicion(int row) {
        DtPrestamo prestamo = prestamos.get(row);
        
        JDialog dialog = new JDialog();
        dialog.setTitle("Editar Préstamo ID: " + prestamo.getIdPrestamo());
        dialog.setModal(true);
        dialog.setLayout(new BorderLayout());
        dialog.setSize(500, 300);
        
        JPanel panel = new JPanel(new GridLayout(6, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Material
        panel.add(new JLabel("ID Material:"));
        JTextField txtMaterial = new JTextField(String.valueOf(prestamo.getMaterial()));
        panel.add(txtMaterial);
        
        // Lector
        panel.add(new JLabel("Correo Lector:"));
        JTextField txtLector = new JTextField(prestamo.getLector());
        panel.add(txtLector);
        
        // Bibliotecario
        panel.add(new JLabel("Correo Bibliotecario:"));
        JTextField txtBibliotecario = new JTextField(prestamo.getBibliotecario());
        panel.add(txtBibliotecario);
        
        // Estado
        panel.add(new JLabel("Estado:"));
        JComboBox<EstadosP> comboEstado = new JComboBox<>(EstadosP.values());
        comboEstado.setSelectedItem(prestamo.getEstadoPres());
        panel.add(comboEstado);
        
        // Fechas de Solicitud
        panel.add(new JLabel("Fecha Solicitud:"));
        JPanel panelFechaSoli = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        
        // ComboBox para fecha de solicitud
        JComboBox<Integer> comboDiaInicio = new JComboBox<>();
        JComboBox<String> comboMesInicio = new JComboBox<>();
        JComboBox<Integer> comboAnioInicio = new JComboBox<>();
        
        // Llenar los ComboBox
        for (int i = 1; i <= 31; i++) comboDiaInicio.addItem(i);
        for (String mes : new String[] {
                "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
                "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"
        }) comboMesInicio.addItem(mes);
        
        Calendar cal = Calendar.getInstance();
        int anioActual = cal.get(Calendar.YEAR);
        for (int i = anioActual; i >= 2000; i--) comboAnioInicio.addItem(i);
        
        // Establecer valores actuales del préstamo
        cal.setTime(prestamo.getFechaSoli());
        comboDiaInicio.setSelectedItem(cal.get(Calendar.DAY_OF_MONTH));
        comboMesInicio.setSelectedIndex(cal.get(Calendar.MONTH));
        comboAnioInicio.setSelectedItem(cal.get(Calendar.YEAR));
        
        panelFechaSoli.add(comboDiaInicio);
        panelFechaSoli.add(comboMesInicio);
        panelFechaSoli.add(comboAnioInicio);
        panel.add(panelFechaSoli);
        
        // Fechas de Devolución
        panel.add(new JLabel("Fecha Devolución:"));
        JPanel panelFechaDev = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        
        // ComboBox para fecha de devolución
        JComboBox<Integer> comboDiaFin = new JComboBox<>();
        JComboBox<String> comboMesFin = new JComboBox<>();
        JComboBox<Integer> comboAnioFin = new JComboBox<>();
        
        // Llenar los ComboBox
        for (int i = 1; i <= 31; i++) comboDiaFin.addItem(i);
        for (String mes : new String[] {
                "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
                "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"
        }) comboMesFin.addItem(mes);
        for (int i = anioActual; i >= 2000; i--) comboAnioFin.addItem(i);
        
        // Establecer valores actuales del préstamo
        cal.setTime(prestamo.getFechaDev());
        comboDiaFin.setSelectedItem(cal.get(Calendar.DAY_OF_MONTH));
        comboMesFin.setSelectedIndex(cal.get(Calendar.MONTH));
        comboAnioFin.setSelectedItem(cal.get(Calendar.YEAR));
        
        panelFechaDev.add(comboDiaFin);
        panelFechaDev.add(comboMesFin);
        panelFechaDev.add(comboAnioFin);
        panel.add(panelFechaDev);
        
        JPanel panelBotones = new JPanel();
        JButton btnGuardar = new JButton("Guardar");
        JButton btnCancelar = new JButton("Cancelar");
        
        btnGuardar.addActionListener(e -> {
            try {
                // Actualizar el préstamo
                IpreCont.cambiarMaterialPrestamo(prestamo, Integer.parseInt(txtMaterial.getText()));
                IpreCont.cambiarCorreoLectorPrestamo(prestamo, txtLector.getText());
                IpreCont.cambiarCorreoBibliotecarioPrestamo(prestamo, txtBibliotecario.getText());
                IpreCont.cambiarEstadoPrestamo(prestamo, (EstadosP)comboEstado.getSelectedItem());
                
                // Crear fecha de solicitud
                Calendar calInicio = Calendar.getInstance();
                calInicio.set(
                    (int)comboAnioInicio.getSelectedItem(),
                    comboMesInicio.getSelectedIndex(),
                    (int)comboDiaInicio.getSelectedItem(),
                    0, 0, 0
                );
                calInicio.set(Calendar.MILLISECOND, 0);
                
                // Crear fecha de devolución
                Calendar calFin = Calendar.getInstance();
                calFin.set(
                    (int)comboAnioFin.getSelectedItem(),
                    comboMesFin.getSelectedIndex(),
                    (int)comboDiaFin.getSelectedItem(),
                    23, 59, 59
                );
                calFin.set(Calendar.MILLISECOND, 999);
                
                // Validar que la fecha de devolución no sea anterior a la de solicitud
                if (calFin.getTime().before(calInicio.getTime())) {
                    throw new Exception("La fecha de devolución no puede ser anterior a la fecha de solicitud");
                }
                
                IpreCont.cambiarFechaSolicitudPrestamo(prestamo, calInicio.getTime());
                IpreCont.cambiarFechaDevolucionPrestamo(prestamo, calFin.getTime());
                
                JOptionPane.showMessageDialog(dialog, "Préstamo actualizado correctamente");
                dialog.dispose();
                cargarPrestamos(); // Recargar la tabla
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, "Error al actualizar: " + ex.getMessage(),
                                           "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        btnCancelar.addActionListener(e -> dialog.dispose());
        
        panelBotones.add(btnGuardar);
        panelBotones.add(btnCancelar);
        
        dialog.add(panel, BorderLayout.CENTER);
        dialog.add(panelBotones, BorderLayout.SOUTH);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }
    
    // Clase interna para renderizar botones en la tabla
    class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
        }
        
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            setText("Editar");
            return this;
        }
    }
    
    // Clase interna para manejar la edición de celdas con botones
    class ButtonEditor extends DefaultCellEditor {
        private JButton button;
        private IntConsumer onClick;
        private int row;
        
        public ButtonEditor(JCheckBox checkBox, IntConsumer onClick) {
            super(checkBox);
            this.onClick = onClick;
            button = new JButton();
            button.setOpaque(true);
            button.addActionListener(e -> fireEditingStopped());
        }
        
        @Override
        public Component getTableCellEditorComponent(JTable table, Object value,
                boolean isSelected, int row, int column) {
            this.row = row;
            button.setText("Editar");
            return button;
        }
        
        @Override
        public Object getCellEditorValue() {
            onClick.accept(row);
            return "Editar";
        }
    }
}