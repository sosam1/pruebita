package presentacion;

import java.awt.BorderLayout;
import java.util.*;
import javax.swing.JInternalFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import interfaces.IPrestamoController;
import datatypes.DtPrestamo;

public class MaterialesConMasPrestamos extends JInternalFrame {

    private static final long serialVersionUID = 1L;
    private IPrestamoController IpreCont;

    public MaterialesConMasPrestamos(IPrestamoController IpreCont) {
        this.IpreCont = IpreCont;
        setResizable(true);
        setIconifiable(true);
        setMaximizable(true);
        setDefaultCloseOperation(JInternalFrame.HIDE_ON_CLOSE);
        setClosable(true);
        setTitle("Materiales con más Préstamos Pendientes");
        setBounds(100, 100, 600, 400);
        getContentPane().setLayout(new BorderLayout());

        String[] columnas = { "Índice", "ID Material", "Cantidad de Préstamos Pendientes" };
        DefaultTableModel model = new DefaultTableModel(columnas, 0);

        // Llenar el modelo
        buscarPrestamosPendientes(model);

        JTable table = new JTable(model);
        JScrollPane scroll = new JScrollPane(table);
        getContentPane().add(scroll, BorderLayout.CENTER);
    }

    private void buscarPrestamosPendientes(DefaultTableModel model){
        List<DtPrestamo> prestamosPendientes = this.IpreCont.obtenerDtPrestamosPendientes();

        // Agrupar por idMaterial y contar
        Map<Integer, Integer> conteoPorMaterial = new HashMap<>();
        for (DtPrestamo p : prestamosPendientes) {
            int idMaterial = p.getMaterial();
            conteoPorMaterial.put(idMaterial, conteoPorMaterial.getOrDefault(idMaterial, 0) + 1);
        }

        // Ordenar por cantidad descendente
        List<Map.Entry<Integer, Integer>> listaOrdenada = new ArrayList<>(conteoPorMaterial.entrySet());
        listaOrdenada.sort((a, b) -> b.getValue().compareTo(a.getValue()));

        int indice = 1;
        for (Map.Entry<Integer, Integer> entry : listaOrdenada) {
            model.addRow(new Object[] { indice++, entry.getKey(), entry.getValue() });
        }
    }
}