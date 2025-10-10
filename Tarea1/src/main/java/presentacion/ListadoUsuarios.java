package presentacion;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.swing.table.*;

import datatypes.*;
import interfaces.IUsuarioController;
// import presentacion.BibliotecaGUI.ButtonEditor;
// import presentacion.BibliotecaGUI.ButtonRenderer;

import presentacion.CambiarZonaUsuario;
import presentacion.ModificarEstadoUsuario;

public class ListadoUsuarios extends JInternalFrame {
    private static final long serialVersionUID = 1L;
    private IUsuarioController IusCont;
    private JTable table;
    private DefaultTableModel model;
    private String[] columnas = { "Nombre", "Correo", "Tipo", "Mas Info" };
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    private JPanel panelDetalle;
    private JTextField txtNombre, txtCorreo, txtZona, txtDireccion, txtEstado;
    private JButton btnZona, btnEstado;

    public ListadoUsuarios(IUsuarioController IusCont) {
        this.IusCont = IusCont;
        setResizable(true);
        setIconifiable(true);
        setMaximizable(true);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setClosable(true);
        setTitle("Listado de Usuarios");
        setBounds(100, 100, 600, 400);
        setLayout(new BorderLayout());

        Object[][] data = cargarDatosUsuarios();

        model = new DefaultTableModel(data, columnas) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 3;
            }
        };

        table = new JTable(model);
        // table.setRowHeight(30);
        table.getColumn("Mas Info").setCellRenderer(new ButtonRenderer());
        table.getColumn("Mas Info").setCellEditor(new ButtonEditor(new JCheckBox(), IusCont, this));

        JScrollPane scroll = new JScrollPane(table);
        add(scroll, BorderLayout.CENTER);

        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.addActionListener(e -> setVisible(false));

        JButton btnRefrescar = new JButton("Refrescar");
        btnRefrescar.addActionListener(e -> refrescarUsuarios());
        


        JPanel panelBotones = new JPanel();
        panelBotones.add(btnRefrescar);
        panelBotones.add(btnCerrar);
        add(panelBotones, BorderLayout.SOUTH);
    }

    private Object[][] cargarDatosUsuarios() {
        List<DtUsuario> usuarios = IusCont.obtenerUsuarios();
        Object[][] data = new Object[usuarios.size()][4];

        for (int i = 0; i < usuarios.size(); i++) {
            DtUsuario u = usuarios.get(i);
            data[i][0] = u.getNombre();
            data[i][1] = u.getCorreo();
            if (u instanceof DtLector) {
                data[i][2] = "Lector";
            } else if (u instanceof DtBibliotecario) {
                data[i][2] = "Bibliotecario";
            }
            data[i][3] = "Mas Info";
        }
        return data;
    }

    class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() {
            setText("Mas info");
        }

        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            return this;
        }
    }

    class ButtonEditor extends DefaultCellEditor {
        private JButton button;
        private String label;
        private boolean isPushed;
        private IUsuarioController IusCont;
        private JInternalFrame parentFrame;
        private int selectedRow;

        public ButtonEditor(JCheckBox checkBox, IUsuarioController IusCont, ListadoUsuarios parentFrame) {
            super(checkBox);
            this.IusCont = IusCont;
            this.parentFrame = parentFrame;
            button = new JButton();
            button.setOpaque(true);
            button.addActionListener(e -> masInfo());
        }

        public Component getTableCellEditorComponent(JTable table, Object value,
                boolean isSelected, int row, int column) {
            selectedRow = row;
            return button;
        }

        private void masInfo() {
            String correo = (String) model.getValueAt(selectedRow, 1);
            DtUsuario usuario = null;
            for (DtUsuario u : IusCont.obtenerUsuarios()) {
                if (u.getCorreo() == correo) {
                    usuario = u;
                    break;
                }
            }

            if (usuario == null) {
                JOptionPane.showMessageDialog(parentFrame, "Usuario no encontrado", "Error", JOptionPane.ERROR_MESSAGE);
                fireEditingStopped();
                return;
            }

            InfoUsuario detalleUsuario = new InfoUsuario(SwingUtilities.getWindowAncestor(parentFrame), usuario);
            detalleUsuario.setVisible(true);

            fireEditingStopped();
        }
    }

    class InfoUsuario extends JDialog {
        public InfoUsuario(Window parent, DtUsuario usuario) {
            setTitle("Detalle del Usuario");
            setSize(400, 300);
            setLocationRelativeTo(parent);
            setLayout(new BorderLayout());

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

            JPanel panelDatos = new JPanel(new GridLayout(0, 2, 5, 5));
            panelDatos.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            panelDatos.add(new JLabel("Nombre:"));
            panelDatos.add(new JLabel(usuario.getNombre()));
            panelDatos.add(new JLabel("Correo:"));
            panelDatos.add(new JLabel(usuario.getCorreo()));

            JPanel panelBotones = new JPanel();

            if (usuario instanceof DtBibliotecario bibliotecario) {
                panelDatos.add(new JLabel("ID Empleado:"));
                panelDatos.add(new JLabel(String.valueOf(bibliotecario.getIdEmp())));
            } else {
                DtLector lector = (DtLector) usuario;
                panelDatos.add(new JLabel("Fecha de Ingreso:"));
                panelDatos.add(new JLabel(dateFormat.format(lector.getFechaIngreso())));
                panelDatos.add(new JLabel("Zona:"));
                panelDatos.add(new JLabel(lector.getZona().toString()));
                panelDatos.add(new JLabel("Dirección:"));
                panelDatos.add(new JLabel(lector.getDireccion()));
                panelDatos.add(new JLabel("Estado:"));
                panelDatos.add(new JLabel(lector.getEstadoUsuario().toString()));

                JButton btnZona = new JButton("Cambiar Zona");
                JButton btnEstado = new JButton("Modificar Estado");

                btnZona.addActionListener(e -> {
                    CambiarZonaUsuario cambiarZona = new CambiarZonaUsuario(this, lector, IusCont);
                    cambiarZona.setVisible(true);
                });

                btnEstado.addActionListener(e -> {
                    ModificarEstadoUsuario modificarEstado = new ModificarEstadoUsuario(this, lector,
                            IusCont);
                    modificarEstado.setVisible(true);
                });

                panelBotones.add(btnZona);
                panelBotones.add(btnEstado);
            }

            JButton btnSalir = new JButton("Salir");
            btnSalir.addActionListener(e -> dispose());
            panelBotones.add(btnSalir);

            add(panelDatos, BorderLayout.CENTER);
            add(panelBotones, BorderLayout.SOUTH);
        }
    }

    public void refrescarUsuarios() {
        Object[][] data = cargarDatosUsuarios();
        model.setRowCount(0);
        for (Object[] row : data) {
            model.addRow(row);
        }
    }

    @Override
    public void setVisible(boolean b) {
        super.setVisible(b);
        if (b) {
            refrescarUsuarios();
        }
    }
}
