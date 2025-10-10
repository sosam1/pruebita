package presentacion;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.SwingConstants;

import interfaces.IMaterialController;
import interfaces.IPrestamoController;
import interfaces.IUsuarioController;
import publicadores.*;
import interfaces.Fabrica;



public class Principal {
    private JFrame frame;

    private RegistrarUsuario registrarUsuarioInternalFrame;
    private ListadoUsuarios listarUsuariosInternalFrame;

    private RegistrarMaterial registrarMaterialInternalFrame;
    private ListadoMateriales listadoMaterialesInternalFrame;
    private ListarMaterialPorRango listarMaterialPorRangoInternalFrame;

    private RegistrarPrestamo registrarPrestamoInternalFrame;
    private ListarPrestamos listarPrestamosInternalFrame;
    private ReporteZonal reporteZonalInternalFrame;
    private PrestamosPorBiblio prestamosPorBiblioInternalFrame;
   
    private MaterialesConMasPrestamos materialesConMasPrestamosInternalFrame;
    private PrestamosActivosDeLector prestamosActivosDeLectorInternalFrame;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                Principal window = new Principal();
                window.frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public Principal(){
        initialize();

        MaterialPublishController mpc = new MaterialPublishController();
        mpc.publicar();

        PrestamoPublishController ppc = new PrestamoPublishController();
        ppc.publicar();

        UsuarioPublishController upc = new UsuarioPublishController();
        upc.publicar();

        Fabrica fabrica = Fabrica.getInstancia();
        IMaterialController ImatCont = fabrica.getIControladorMaterial();
        IPrestamoController IpresCont = fabrica.getIControladorPrestamo();
        IUsuarioController  IusCont = fabrica.getIControladorUsuario();

        Dimension desktopSize = frame.getSize();
		Dimension jInternalFrameSize;

        //Funciones de Usuario
        registrarUsuarioInternalFrame = new RegistrarUsuario(IusCont);
        jInternalFrameSize = registrarUsuarioInternalFrame.getSize();
        registrarUsuarioInternalFrame.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                (desktopSize.height - jInternalFrameSize.height) / 2);
        registrarUsuarioInternalFrame.setVisible(false);
        frame.getContentPane().add(registrarUsuarioInternalFrame);

        listarUsuariosInternalFrame = new ListadoUsuarios(IusCont);
        jInternalFrameSize = listarUsuariosInternalFrame.getSize();
        listarUsuariosInternalFrame.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                (desktopSize.height - jInternalFrameSize.height) / 2);
        listarUsuariosInternalFrame.setVisible(false);
        frame.getContentPane().add(listarUsuariosInternalFrame);

        //Funciones de Material
        registrarMaterialInternalFrame = new RegistrarMaterial(ImatCont);
        jInternalFrameSize = registrarMaterialInternalFrame.getSize();
        registrarMaterialInternalFrame.setLocation((desktopSize.width - jInternalFrameSize.width)/2,
            (desktopSize.height- jInternalFrameSize.height)/2);
        registrarMaterialInternalFrame.setVisible(false);
        frame.getContentPane().add(registrarMaterialInternalFrame);

        listadoMaterialesInternalFrame = new ListadoMateriales(ImatCont);
        jInternalFrameSize = listadoMaterialesInternalFrame.getSize();
        listadoMaterialesInternalFrame.setLocation((desktopSize.width - jInternalFrameSize.width)/2,
            (desktopSize.height- jInternalFrameSize.height)/2);
        listadoMaterialesInternalFrame.setVisible(false);
        frame.getContentPane().add(listadoMaterialesInternalFrame);

        listarMaterialPorRangoInternalFrame = new ListarMaterialPorRango(ImatCont);
        jInternalFrameSize = listarMaterialPorRangoInternalFrame.getSize();
        listarMaterialPorRangoInternalFrame.setLocation((desktopSize.width - jInternalFrameSize.width)/2,
            (desktopSize.height- jInternalFrameSize.height)/2);
        listarMaterialPorRangoInternalFrame.setVisible(false);
        frame.getContentPane().add(listarMaterialPorRangoInternalFrame);

        //Funciones de Prestamo
        registrarPrestamoInternalFrame = new RegistrarPrestamo(IpresCont, IusCont);
        jInternalFrameSize = registrarPrestamoInternalFrame.getSize();
        registrarPrestamoInternalFrame.setLocation((desktopSize.width - jInternalFrameSize.width)/2,
            (desktopSize.height- jInternalFrameSize.height)/2);
        registrarPrestamoInternalFrame.setVisible(false);
        frame.getContentPane().add(registrarPrestamoInternalFrame);

        listarPrestamosInternalFrame = new ListarPrestamos(IpresCont);
        jInternalFrameSize = listarPrestamosInternalFrame.getSize();
        listarPrestamosInternalFrame.setLocation((desktopSize.width - jInternalFrameSize.width)/2,
            (desktopSize.height- jInternalFrameSize.height)/2);
        listarPrestamosInternalFrame.setVisible(false);
        frame.getContentPane().add(listarPrestamosInternalFrame);

        reporteZonalInternalFrame = new ReporteZonal(IpresCont);
        jInternalFrameSize = reporteZonalInternalFrame.getSize();
        reporteZonalInternalFrame.setLocation((desktopSize.width - jInternalFrameSize.width)/2,
            (desktopSize.height- jInternalFrameSize.height)/2);
        reporteZonalInternalFrame.setVisible(false);
        frame.getContentPane().add(reporteZonalInternalFrame);

        prestamosPorBiblioInternalFrame = new PrestamosPorBiblio(IpresCont);
        jInternalFrameSize = prestamosPorBiblioInternalFrame.getSize();
        prestamosPorBiblioInternalFrame.setLocation((desktopSize.width - jInternalFrameSize.width)/2,
            (desktopSize.height- jInternalFrameSize.height)/2);
        prestamosPorBiblioInternalFrame.setVisible(false);
        frame.getContentPane().add(prestamosPorBiblioInternalFrame);
        
        materialesConMasPrestamosInternalFrame = new MaterialesConMasPrestamos(IpresCont);
        jInternalFrameSize = materialesConMasPrestamosInternalFrame.getSize();
        materialesConMasPrestamosInternalFrame.setLocation((desktopSize.width - jInternalFrameSize.width)/2,
            (desktopSize.height- jInternalFrameSize.height)/2);
        materialesConMasPrestamosInternalFrame.setVisible(false);
        frame.getContentPane().add(materialesConMasPrestamosInternalFrame);

        prestamosActivosDeLectorInternalFrame = new PrestamosActivosDeLector(IpresCont);
        jInternalFrameSize = prestamosActivosDeLectorInternalFrame.getSize();
        prestamosActivosDeLectorInternalFrame.setLocation((desktopSize.width - jInternalFrameSize.width)/2,
            (desktopSize.height- jInternalFrameSize.height)/2);
        prestamosActivosDeLectorInternalFrame.setVisible(false);
        frame.getContentPane().add(prestamosActivosDeLectorInternalFrame);
    }

    private void initialize(){
        int windowWidth = 1000;
        frame = new JFrame();
        frame.setBounds(100, 100, windowWidth, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        //Timido mensaje de bienvenida
        JLabel bienvenidaLabel = new JLabel("Bienvenido al Sistema de Gestión de Biblioteca");
        bienvenidaLabel.setHorizontalAlignment(SwingConstants.CENTER);
        bienvenidaLabel.setVerticalAlignment(SwingConstants.CENTER);
        bienvenidaLabel.setBounds(0, 0, frame.getWidth(), 100);
        bienvenidaLabel.setFont(new Font("Tahoma", Font.BOLD, 25));
        frame.getContentPane().add(bienvenidaLabel);


        //Barra de menu
        JMenuBar menuBar = new JMenuBar();
        menuBar.setBounds(0, 0, windowWidth, 21);
        frame.getContentPane().add(menuBar);


        //Opciones de menu
        JMenu mnUsuario = new JMenu("Usuarios");
        menuBar.add(mnUsuario);
        JMenu mnMaterial = new JMenu("Material");
        menuBar.add(mnMaterial);
        JMenu mnPrestamo = new JMenu("Préstamo");
        menuBar.add(mnPrestamo);
        JMenu mnControl = new JMenu("Control y Seguimiento");
        menuBar.add(mnControl);


        //Items de menu 'Usuario'
        JMenuItem mntmRegistrarUsuario = new JMenuItem("Registrar Usuario");
        mntmRegistrarUsuario.addActionListener(e -> registrarUsuarioInternalFrame.setVisible(true));
        mnUsuario.add(mntmRegistrarUsuario);

        JMenuItem mntmListarUsuarios = new JMenuItem("Listar Usuarios");
        mntmListarUsuarios.addActionListener(e -> listarUsuariosInternalFrame.setVisible(true));
        mnUsuario.add(mntmListarUsuarios);


        //Items de menu 'Material'
         JMenuItem mntmRegistrarMaterial = new JMenuItem("Registrar Material");
        mntmRegistrarMaterial.addActionListener(e -> registrarMaterialInternalFrame.setVisible(true));
        mnMaterial.add(mntmRegistrarMaterial);

        JMenuItem mntmListadoMateriales = new JMenuItem("Listado de Materiales");
        mntmListadoMateriales.addActionListener(e -> listadoMaterialesInternalFrame.setVisible(true));    
        mnMaterial.add(mntmListadoMateriales);

        JMenuItem mntmListarMaterialPor = new JMenuItem("Listar Material por Rango de Fechas");
        mntmListarMaterialPor.addActionListener(e -> listarMaterialPorRangoInternalFrame.setVisible(true));
        mnMaterial.add(mntmListarMaterialPor);


        //Items de menu 'Prestamo'
        JMenuItem mntmRegistrarPrestamo = new JMenuItem("Registrar Préstamo");
        mntmRegistrarPrestamo.addActionListener(e-> registrarPrestamoInternalFrame.setVisible(true));
        mnPrestamo.add(mntmRegistrarPrestamo);

        JMenuItem mntmListarPrestamos = new JMenuItem("Listar Préstamos");
        mntmListarPrestamos.addActionListener(e-> listarPrestamosInternalFrame.setVisible(true));
        mnPrestamo.add(mntmListarPrestamos);


        //Items de menu 'Control'
        JMenuItem mntmReporteZonal = new JMenuItem("Reporte zonal");
        mntmReporteZonal.addActionListener(e-> reporteZonalInternalFrame.setVisible(true));
        mnControl.add(mntmReporteZonal);

        JMenuItem mntmPrestamosPorBiblio = new JMenuItem("Préstamos por Bibliotecario");
        mntmPrestamosPorBiblio.addActionListener(e-> prestamosPorBiblioInternalFrame.setVisible(true));
        mnControl.add(mntmPrestamosPorBiblio);

        JMenuItem mntmMaterialesConMasPrestamos = new JMenuItem("Materiales con más Préstamos Pendientes");
        mntmMaterialesConMasPrestamos.addActionListener(e-> materialesConMasPrestamosInternalFrame.setVisible(true));
        mnControl.add(mntmMaterialesConMasPrestamos);

        JMenuItem mntmPrestamosActivosDeLector = new JMenuItem("Préstamos Activos de Lector");
        mntmPrestamosActivosDeLector.addActionListener(e-> prestamosActivosDeLectorInternalFrame.setVisible(true));
        mnControl.add(mntmPrestamosActivosDeLector);
    }
}