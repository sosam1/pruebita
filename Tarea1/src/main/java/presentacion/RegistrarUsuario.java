package presentacion;

import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.util.Date;
import java.awt.*;

import datatypes.*;
import interfaces.IUsuarioController;
import excepciones.CorreoInvalidoException;
import excepciones.UsuarioRepetidoException;
import excepciones.CorreoInvalidoException;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;

public class RegistrarUsuario extends JInternalFrame {
    private static final long serialVersionUID = 1L;

    private IUsuarioController IusCont;

    private JTextField txtNombre;
    private JTextField txtCorreo;
    private JPasswordField txtPassword;
    private JComboBox<String> comboTipo;

    private JTextField txtDireccion;
    private JComboBox<Zonas> comboZona;
    private JComboBox<String> comboEstado;

    private JPanel panelDinamico;
    private JPanel panelLector;
    private JPanel panelBibliotecario;

    public RegistrarUsuario(IUsuarioController IusCont) {
        this.IusCont = IusCont;
        setResizable(true);
        setIconifiable(true);
        setMaximizable(true);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setClosable(true);
        setTitle("Registro de Usuario");
        setBounds(100, 100, 500, 300);
        setLayout(new BorderLayout());

        JPanel panelForm = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;

        panelForm.add(new JLabel("Nombre:"), gbc);
        gbc.gridx = 1;
        txtNombre = new JTextField();
        panelForm.add(txtNombre, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panelForm.add(new JLabel("Correo:"), gbc);
        gbc.gridx = 1;
        txtCorreo = new JTextField();
        panelForm.add(txtCorreo, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panelForm.add(new JLabel("Contraseña:"), gbc);
        gbc.gridx = 1;
        txtPassword = new JPasswordField();
        panelForm.add(txtPassword, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panelForm.add(new JLabel("Tipo de usuario:"), gbc);
        gbc.gridx = 1;
        comboTipo = new JComboBox<>(new String[] { "Lector", "Bibliotecario" });
        panelForm.add(comboTipo, gbc);

        panelLector = new JPanel(new GridLayout(3, 2, 5, 5));
        panelLector.add(new JLabel("Dirección:"));
        txtDireccion = new JTextField();
        panelLector.add(txtDireccion);

        panelLector.add(new JLabel("Zona:"));
        comboZona = new JComboBox<>(Zonas.values());
        panelLector.add(comboZona);

        panelLector.add(new JLabel("Estado:"));
        comboEstado = new JComboBox<>(new String[] { "ACTIVO", "SUSPENDIDO" });
        panelLector.add(comboEstado);

        panelBibliotecario = new JPanel();

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        panelDinamico = new JPanel(new CardLayout());
        panelDinamico.add(panelLector, "Lector");
        panelDinamico.add(panelBibliotecario, "Bibliotecario");
        panelForm.add(panelDinamico, gbc);

        comboTipo.addActionListener(e -> {
            CardLayout cl = (CardLayout) panelDinamico.getLayout();
            cl.show(panelDinamico, (String) comboTipo.getSelectedItem());
        });
        ((CardLayout) panelDinamico.getLayout()).show(panelDinamico, "Lector");

        add(panelForm, BorderLayout.CENTER);

        // Botones
        JPanel panelBotones = new JPanel();
        JButton btnAceptar = new JButton("Registrar");
        JButton btnCancelar = new JButton("Cancelar");

        btnAceptar.addActionListener(e -> agregarUsuarioAceptarActionPerformed());
        btnCancelar.addActionListener(e -> agregarUsuarioCancelarActionPerformed());

        panelBotones.add(btnAceptar);
        panelBotones.add(btnCancelar);
        add(panelBotones, BorderLayout.SOUTH);
    }

    protected void agregarUsuarioAceptarActionPerformed() {
        try {
            String nombre = txtNombre.getText();
            String correo = txtCorreo.getText();
            String password = new String(txtPassword.getPassword());


            validarCorreo(correo);

            if (comboTipo.getSelectedItem().equals("Lector")) {
                String direccion = txtDireccion.getText();
                Zonas zona = (Zonas) comboZona.getSelectedItem();
                EstadosU estado = EstadosU.valueOf((String) comboEstado.getSelectedItem());

                DtLector lector = new DtLector(nombre, correo, password, new Date(), estado, zona, direccion);
                IusCont.agregarUsuario(lector);
                JOptionPane.showMessageDialog(this, "Lector registrado correctamente.");
            } else {
                DtBibliotecario bibliotecario = new DtBibliotecario(nombre, correo, password);
                IusCont.agregarUsuario(bibliotecario);
                JOptionPane.showMessageDialog(this, "Bibliotecario registrado correctamente.");
            }

            limpiarFormularioUsuario();
            setVisible(false);
        } catch (CorreoInvalidoException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Registrar Usuario", JOptionPane.ERROR_MESSAGE);
        } catch (UsuarioRepetidoException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Registrar Usuario", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al registrar usuario: " + ex.getMessage());
        }

    }

    protected void agregarUsuarioCancelarActionPerformed() {
        limpiarFormularioUsuario();
        this.setVisible(false);
    }

    private void validarCorreo(String correo) throws CorreoInvalidoException {
        if (correo == null || !correo.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,5}$")) {
            throw new CorreoInvalidoException("El correo \"" + correo + "\" no es válido.");
        }
    }

    private void limpiarFormularioUsuario() {
        txtNombre.setText("");
        txtCorreo.setText("");
        comboTipo.setSelectedIndex(0);
        txtDireccion.setText("");
        comboZona.setSelectedIndex(0);
        comboEstado.setSelectedIndex(0);
    }
}
