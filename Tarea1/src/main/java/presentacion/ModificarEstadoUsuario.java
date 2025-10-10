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

public class ModificarEstadoUsuario extends JFrame {
    private static final long serialVersionUID = 1L;

    private DtLector lector;
    private IUsuarioController IusCont;

    private JComboBox<EstadosU> comboEstado;
    private JButton btnAceptar;
    private JButton btnCancelar;

    public ModificarEstadoUsuario(Window parent, DtLector lector, IUsuarioController IusCont) {
        this.lector = lector;
        this.IusCont = IusCont;

        setTitle("Modificar Estado de Usuario");
        setSize(400, 200);
        setLocationRelativeTo(parent);
        setResizable(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridLayout(1, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panel.add(new JLabel("Nuevo Estado:"));
        comboEstado = new JComboBox<>(EstadosU.values());
        comboEstado.setSelectedItem(lector.getEstadoUsuario());
        panel.add(comboEstado);

        add(panel, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel();
        btnAceptar = new JButton("Aceptar");
        btnAceptar.addActionListener(e -> modificarEstadoAceptarActionPerformed());

        btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(e -> modificarEstadoCancelarActionPerformed());

        panelBotones.add(btnAceptar);
        panelBotones.add(btnCancelar);

        add(panelBotones, BorderLayout.SOUTH);
    }

    private void modificarEstadoAceptarActionPerformed() {
        EstadosU nuevoEstado = (EstadosU) comboEstado.getSelectedItem();
        if (nuevoEstado != null && !nuevoEstado.equals(lector.getEstadoUsuario())) {
            try {
                IusCont.cambiarEstadoLector(lector, nuevoEstado);
                JOptionPane.showMessageDialog(this, "Estado del usuario modificado correctamente.", "Éxito",
                        JOptionPane.INFORMATION_MESSAGE);
                setVisible(false);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al modificar el estado del usuario: " + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione un estado diferente al actual.", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void modificarEstadoCancelarActionPerformed() {
        setVisible(false);
    }
}
