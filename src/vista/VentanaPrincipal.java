package vista;

import java.awt.EventQueue;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

public class VentanaPrincipal {

	private JFrame frame;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaPrincipal window = new VentanaPrincipal();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public VentanaPrincipal() {
		initialize();
	}

	private void initialize() {
		frame = new JFrame();
		configurarVentana();

		JPanel panelContenido = crearPanelContenido();

		panelContenido.add(crearBotonCargar());

	}

	private void configurarVentana() {
		frame.setTitle("Bienvenido - Conjunto Dominante Mínimo");
		frame.setSize(1024, 768);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	private JPanel crearPanelContenido() {
		JPanel panelContenido = new JPanel();
		panelContenido.setLayout(null);

		frame.setContentPane(panelContenido);
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

		if (chooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
			File archivo = chooser.getSelectedFile();
			// acá se lo pasás al controlador / capa de negocio
		}
	}

}
