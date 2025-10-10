package presentacion;

import datatypes.DtPrestamo;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.table.DefaultTableModel;
import interfaces.IPrestamoController;

public class PrestamosPorBiblio extends JInternalFrame {
	private static final long serialVersionUID = 1L;
	private IPrestamoController IpreCont;

	public PrestamosPorBiblio(IPrestamoController IpreCont) {
		this.IpreCont = IpreCont;
		setResizable(true);
		setIconifiable(true);
		setMaximizable(true);
		setClosable(true);
		setTitle("Préstamos por Bibliotecario");
		setBounds(100, 100, 700, 400);
		getContentPane().setLayout(new BorderLayout());

		JPanel panelBusqueda = new JPanel();
		JLabel label = new JLabel("ID Bibliotecario:");
		JTextField textField = new JTextField(10);
		JButton button = new JButton("Buscar");
		panelBusqueda.add(label);
		panelBusqueda.add(textField);
		panelBusqueda.add(button);
		getContentPane().add(panelBusqueda, BorderLayout.NORTH);

		String[] columnas = { "ID", "Fecha Solicitud", "Estado", "Fecha Devolución", "Lector", "Bibliotecario", "Material" };
		DefaultTableModel model = new DefaultTableModel(columnas, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		JTable table = new JTable(model);
		JScrollPane scroll = new JScrollPane(table);
		getContentPane().add(scroll, BorderLayout.CENTER);

		button.addActionListener(e -> {
			try {
				int idEmp = Integer.parseInt(textField.getText());
				List<DtPrestamo> prestamos = IpreCont.obtenerDtPrestamoBibliotecario(idEmp);
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				model.setRowCount(0);
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
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
			}
		});
	}
}
