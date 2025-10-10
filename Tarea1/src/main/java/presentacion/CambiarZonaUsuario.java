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

public class CambiarZonaUsuario extends JFrame {
    private static final long serialVersionUID = 1L;

    private DtLector lector;
    private IUsuarioController IusCont;

    private JComboBox<Zonas> comboZona;
    private JButton btnAceptar;
    private JButton btnCancelar;

    public CambiarZonaUsuario(Window parent, DtLector lector, IUsuarioController IusCont) {
        this.lector = lector;
        this.IusCont = IusCont;

        setTitle("Cambiar Zona de Usuario");
        setSize(400, 200);
        setLocationRelativeTo(parent);
        setAlwaysOnTop(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridLayout(1, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panel.add(new JLabel("Nueva Zona:"));
        comboZona = new JComboBox<>(Zonas.values());
        comboZona.setSelectedItem(lector.getZona());
        panel.add(comboZona);

        add(panel, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel();
        btnAceptar = new JButton("Aceptar");
        btnAceptar.addActionListener(e -> cambiarZonaAceptarActionPerformed());

        btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(e -> cambiarZonaCancelarActionPerformed());

        panelBotones.add(btnAceptar);
        panelBotones.add(btnCancelar);

        add(panelBotones, BorderLayout.SOUTH);
    }

    protected void cambiarZonaAceptarActionPerformed() {
        try {
            Zonas nuevaZona = (Zonas) comboZona.getSelectedItem();
            if (nuevaZona != null && !nuevaZona.equals(lector.getZona())) {
                IusCont.cambiarZonaLector(lector, nuevaZona);
                JOptionPane.showMessageDialog(this,
                        "La zona del usuario ha sido actualizada a " + nuevaZona,
                        "Éxito", JOptionPane.INFORMATION_MESSAGE);
                setVisible(false);
            } else {
                JOptionPane.showMessageDialog(this,
                        "Seleccione una zona diferente a la actual.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Error al cambiar la zona: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    protected void cambiarZonaCancelarActionPerformed() {
        setVisible(false);
    }
}
