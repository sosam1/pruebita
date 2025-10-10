package presentacion;

import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.util.Date;
import java.awt.*;

import datatypes.DtMaterial;
import datatypes.DtArticuloEspecial;
import datatypes.DtLibro;
import interfaces.IMaterialController;
import excepciones.MaterialRepetidoException;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class RegistrarMaterial extends JInternalFrame {

    private static final long serialVersionUID = 1L;

    private IMaterialController ImatCont;

    private JTextField txtTitulo;
    private JTextField txtCantPag;
    private JTextField txtDescripcion;
    private JTextField txtPeso;
    private JTextField txtDimensiones;
	private JComboBox<String> comboTipo;
    private JPanel panelDinamico;
    private JPanel panelLibro;
    private JPanel panelArticulo;

    public RegistrarMaterial(IMaterialController ImatCont) {
        this.ImatCont = ImatCont;
        setResizable(true);
        setIconifiable(true);
        setMaximizable(true);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setClosable(true);
        setTitle("Registro de Material");
        setBounds(100, 100, 500, 300);
        setLayout(new BorderLayout());

        JPanel panelForm = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(2, 8, 2, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;
        gbc.weighty = 1;

        gbc.gridx = 0;
        gbc.gridy = 0;
        panelForm.add(new JLabel("Tipo de material:"), gbc);
        gbc.gridx = 1;
        comboTipo = new JComboBox<>(new String[] { "Libro", "Articulo Especial" });
        panelForm.add(comboTipo, gbc);

        // Panel para libros
        panelLibro = new JPanel(new GridLayout(2, 2, 5, 5));
        txtTitulo = new JTextField();
        panelLibro.add(new JLabel("Título:"));
        panelLibro.add(txtTitulo);

        txtCantPag = new JTextField();
        panelLibro.add(new JLabel("Cantidad de Páginas:"));
        panelLibro.add(txtCantPag);

        // Panel para articulos
        panelArticulo = new JPanel(new GridLayout(3, 2, 5, 5));
        txtDescripcion = new JTextField();
        panelArticulo.add(new JLabel("Descripcion:"));
        panelArticulo.add(txtDescripcion);

        txtPeso = new JTextField();
        panelArticulo.add(new JLabel("Peso (kg):"));
        panelArticulo.add(txtPeso);

        txtDimensiones = new JTextField();
        panelArticulo.add(new JLabel("Dimensiones Físicas:"));
        panelArticulo.add(txtDimensiones);

        // Panel dinamico
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        panelDinamico = new JPanel(new CardLayout());
        panelDinamico.add(panelLibro, "Libro");
        panelDinamico.add(panelArticulo, "Articulo Especial");
        panelForm.add(panelDinamico, gbc);

        comboTipo.addActionListener(e -> {
            CardLayout cl = (CardLayout) (panelDinamico.getLayout());
            cl.show(panelDinamico, (String) comboTipo.getSelectedItem());
        });

        ((CardLayout) panelDinamico.getLayout()).show(panelDinamico, "Libro");

        add(panelForm, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel();
        JButton btnAceptar = new JButton("Aceptar");
        JButton btnCancelar = new JButton("Cancelar");

        btnAceptar.addActionListener(e -> agregarMaterialAceptarActionPerformed());
        btnCancelar.addActionListener(e -> agregarMaterialCancelarActionPerformed());

        panelBotones.add(btnAceptar);
        panelBotones.add(btnCancelar);

        add(panelBotones, BorderLayout.SOUTH);
    }

    protected void agregarMaterialAceptarActionPerformed() {
        try {
            Date fechaRegistro = new Date();

            if (comboTipo.getSelectedItem().equals("Libro")) {
                String titulo = txtTitulo.getText();
                int cantPag = Integer.parseInt(txtCantPag.getText());

                DtLibro libro = new DtLibro(fechaRegistro, titulo, cantPag);
                this.ImatCont.agregarMaterial(libro);
                JOptionPane.showMessageDialog(this, "Libro registrado correctamente.");
            } else {
                String descripcion = txtDescripcion.getText();
                float peso = Float.parseFloat(txtPeso.getText());
                float dimensiones = Float.parseFloat(txtDimensiones.getText());

                DtArticuloEspecial articulo = new DtArticuloEspecial(fechaRegistro, descripcion, peso, dimensiones);
                this.ImatCont.agregarMaterial(articulo);
                JOptionPane.showMessageDialog(this, "Artículo Especial registrado correctamente.");
            }
            limpiarFormularioMaterial();
            setVisible(false);
        } catch (MaterialRepetidoException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Registrar Material", JOptionPane.ERROR_MESSAGE);
        }
    }

    protected void agregarMaterialCancelarActionPerformed() {
        limpiarFormularioMaterial();
        setVisible(false);
    }

    private void limpiarFormularioMaterial() {
        txtTitulo.setText("");
        txtCantPag.setText("");
        txtDescripcion.setText("");
        txtPeso.setText("");
        txtDimensiones.setText("");
    }
}

