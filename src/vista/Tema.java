package vista;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Window;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JToggleButton;
import javax.swing.JViewport;
import javax.swing.plaf.basic.BasicToggleButtonUI;

// Modo claro / oscuro de la aplicación.
public final class Tema {

	private static final String PROPIEDAD_BOTON_TEMA = "botonTema";

	private static final Dimension TAMANIO_BOTON_TEMA = new Dimension(60, 30);

	private static final Color TEXTO_CLARO = new Color(30, 35, 40);
	private static final Color CONTROL_CLARO = Color.WHITE;
	private static final Color BORDE_BOTON_CLARO = new Color(150, 160, 170);

	private static final Color TEXTO_OSCURO = new Color(235, 237, 240);
	private static final Color CONTROL_OSCURO = new Color(55, 58, 64);
	private static final Color BORDE_BOTON_OSCURO = new Color(115, 125, 135);

	private static boolean _oscuro;

	private Tema() {
	}

	public static JToggleButton crearBotonTema() {
		JToggleButton boton = new JToggleButton();

		configurarAspectoBoton(boton);
		boton.putClientProperty(PROPIEDAD_BOTON_TEMA, Boolean.TRUE);
		actualizarBoton(boton);

		boton.addActionListener(e -> cambiarTema(boton.isSelected()));

		return boton;
	}

	private static void configurarAspectoBoton(JToggleButton boton) {
		boton.setUI(new BasicToggleButtonUI());
		boton.setPreferredSize(TAMANIO_BOTON_TEMA);
		boton.setFocusPainted(false);
		boton.setOpaque(true);
		boton.setContentAreaFilled(true);
		boton.setBorderPainted(true);
	}

	private static void actualizarBoton(JToggleButton boton) {
		boton.setSelected(_oscuro);
		boton.setText(_oscuro ? "☀️  Modo claro" : "🌙  Modo oscuro");
		boton.setBackground(fondoControl());
		boton.setForeground(texto());

		Color colorBorde = _oscuro ? BORDE_BOTON_OSCURO : BORDE_BOTON_CLARO;

		boton.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(colorBorde),
				BorderFactory.createEmptyBorder(5, 10, 5, 10)));
	}

	private static Color fondoControl() {
		return _oscuro ? CONTROL_OSCURO : CONTROL_CLARO;
	}

	private static Color texto() {
		return _oscuro ? TEXTO_OSCURO : TEXTO_CLARO;
	}

	private static void cambiarTema(boolean activarOscuro) {
		_oscuro = activarOscuro;

		for (Window ventana : Window.getWindows()) {
			if (ventana.isDisplayable()) {
				aplicar(ventana);
			}
		}
	}

	public static void aplicar(Component componente) {
		aplicarColores(componente);
		aplicarAHijos(componente);

		if (esBotonTema(componente)) {
			actualizarBoton((JToggleButton) componente);
		}

		componente.repaint();
	}

	private static void aplicarColores(Component componente) {
		if (componente instanceof JLabel) {
			componente.setForeground(texto());
		}

		if (usaFondoDeControl(componente)) {
			componente.setBackground(fondoControl());
			componente.setForeground(texto());
		}
	}

	private static boolean usaFondoDeControl(Component componente) {
		return componente instanceof JPanel || componente instanceof JScrollPane || componente instanceof JViewport
				|| componente instanceof JTextArea || componente instanceof AbstractButton;
	}

	private static void aplicarAHijos(Component componente) {
		if (componente instanceof Container contenedor) {
			for (Component hijo : contenedor.getComponents()) {
				aplicar(hijo);
			}
		}
	}

	private static boolean esBotonTema(Component componente) {
		return componente instanceof JToggleButton boton
				&& Boolean.TRUE.equals(boton.getClientProperty(PROPIEDAD_BOTON_TEMA));
	}

	public static boolean esOscuro() {
		return _oscuro;
	}
}
