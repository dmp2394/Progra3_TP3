package vista;

import java.awt.EventQueue;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

import controlador.ControladorPrincipal;
import modelo.Orquestador;

public class VentanaPrincipal {

	private JFrame _frame;
	private ControladorPrincipal _controlador;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaPrincipal window = new VentanaPrincipal();
					window._frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public VentanaPrincipal() {
		_controlador = new ControladorPrincipal(new Orquestador());
		initialize();
	}

	private void initialize() {
		_frame = new JFrame();
		configurarVentana();

		JPanel panelContenido = crearPanelContenido();

		panelContenido.add(crearBotonCargar());

	}

	private void configurarVentana() {
		_frame.setTitle("Bienvenido - Conjunto Dominante Mínimo");
		_frame.setSize(1024, 768);
		_frame.setResizable(false);
		_frame.setLocationRelativeTo(null);
		_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	private JPanel crearPanelContenido() {
		JPanel panelContenido = new JPanel();
		panelContenido.setLayout(null);

		_frame.setContentPane(panelContenido);
		return panelContenido;
	}

	private JButton crearBotonCargar() {
		JButton btnCargar = new JButton("Cargar grafo (.json)");
		btnCargar.setBounds(10, 10, 180, 30);
		btnCargar.addActionListener(e -> seleccionarArchivo());
		return btnCargar;
	}

	private void seleccionarArchivo() {
		JFileChooser chooser = new JFileChooser();
		chooser.setFileFilter(new FileNameExtensionFilter("Archivos JSON (*.json)", "json"));
		chooser.setAcceptAllFileFilterUsed(false);

		if (chooser.showOpenDialog(_frame) == JFileChooser.APPROVE_OPTION) {
			File archivo = chooser.getSelectedFile();
			_controlador.cargarGrafoDesdeJSON(archivo.getAbsolutePath());
		}
	}

}
