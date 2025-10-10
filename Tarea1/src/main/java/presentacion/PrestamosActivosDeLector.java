package presentacion;

import java.awt.BorderLayout;
import java.util.*;
import java.text.SimpleDateFormat;

import javax.swing.JInternalFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import java.awt.FlowLayout;

import interfaces.IPrestamoController;
import datatypes.DtPrestamo;

public class PrestamosActivosDeLector extends JInternalFrame{

    private static final long serialVersionUID = 1L;

    private IPrestamoController IpreCont;
    private JTextField txtCorreo;
    private DefaultTableModel model;
    private String[] columnas = { "ID", "Fecha Solicitud", "Fecha Devolución", "Estado", "Material", "Bibliotecario" };

    public PrestamosActivosDeLector(IPrestamoController IpreCont){
        this.IpreCont = IpreCont;
        setTitle("Préstamos Activos de Lector");
        setSize(650,350);
        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setLayout(new BorderLayout());

        JPanel panelForm = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelForm.add(new JLabel("Correo del lector:"));
        txtCorreo = new JTextField(20);
        panelForm.add(txtCorreo);

        JButton btnBuscar = new JButton("Buscar");
        panelForm.add(btnBuscar);

        add(panelForm, BorderLayout.NORTH);

        model = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable table = new JTable(model);
        JScrollPane scroll = new JScrollPane(table);
        add(scroll, BorderLayout.CENTER);

        btnBuscar.addActionListener(e -> buscarPrestamos());
    }

    private void buscarPrestamos() {
        String correo = txtCorreo.getText().trim();
        if (correo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor ingrese un correo.");
            return;
        }

        try {
            List<DtPrestamo> prestamos = this.IpreCont.obtenerPrestamosActivosLector(correo);

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Object[][] nuevaData = new Object[prestamos.size()][columnas.length];

            for (int i = 0; i < prestamos.size(); i++) {
                DtPrestamo p = prestamos.get(i);
                nuevaData[i][0] = p.getIdPrestamo();
                nuevaData[i][1] = sdf.format(p.getFechaSoli());
                nuevaData[i][2] = sdf.format(p.getFechaDev());
                nuevaData[i][3] = p.getEstadoPres().toString();
                nuevaData[i][4] = p.getMaterial();
                nuevaData[i][5] = p.getBibliotecario();
            }

            model.setDataVector(nuevaData, columnas);

        }catch(Exception ex){
           JOptionPane.showMessageDialog(this, "Error al buscar el préstamo: " + ex.getMessage(), "Prestamo Activo De Lector", JOptionPane.ERROR_MESSAGE);
            return;
        }
    }
}