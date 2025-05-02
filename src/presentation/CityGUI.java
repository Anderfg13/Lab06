package presentation;
import domain.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

//prueba

/**
 * Clase CityGUI que representa la interfaz gráfica de la simulación de la ciudad.
 * Extiende JFrame para crear una ventana de aplicación.
 */
public class CityGUI extends JFrame {  
    public static final int SIDE = 20; // Tamaño de cada celda en la cuadrícula

    public final int SIZE; // Tamaño de la ciudad (número de celdas)
    private JButton ticTacButton; // Botón para avanzar un turno en la simulación
    private JPanel controlPanel; // Panel de control (no utilizado en este código)
    private PhotoCity photo; // Panel que dibuja la ciudad
    private City theCity; // Instancia de la ciudad que se está simulando

    /**
     * Constructor privado de CityGUI. Inicializa la ciudad y prepara los elementos de la interfaz.
     */
    private CityGUI() {
        theCity = new City();
        SIZE = theCity.getSize();
        prepareElements();
        prepareActions();
    }

    /**
     * Prepara los elementos gráficos de la interfaz, como el título, el botón y el panel de la ciudad.
     */
    private void prepareElements() {
        setTitle("Schelling City");
        photo = new PhotoCity(this);
        ticTacButton = new JButton("Tic-tac");
        setLayout(new BorderLayout());
        add(photo, BorderLayout.NORTH);
        add(ticTacButton, BorderLayout.SOUTH);
        setSize(new Dimension(SIDE * SIZE + 15, SIDE * SIZE + 72)); 
        setResizable(false);
        photo.repaint();
    }

    /**
     * Prepara las acciones de la interfaz, como el cierre de la ventana y el manejo de eventos del botón.
     */
    private void prepareActions() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);       
        ticTacButton.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    ticTacButtonAction();
                }
            });
    }

    /**
     * Acción que se ejecuta al presionar el botón "Tic-tac". Avanza un turno en la simulación y repinta la ciudad.
     */
    private void ticTacButtonAction() {
        theCity.ticTac();
        photo.repaint();
    }

    /**
     * Devuelve la instancia de la ciudad que se está simulando.
     *
     * @return La instancia de la ciudad.
     */
    public City gettheCity() {
        return theCity;
    }

    /**
     * Método principal que inicia la aplicación.
     *
     * @param args Argumentos de la línea de comandos (no utilizados).
     */
    public static void main(String[] args) {
        CityGUI cg = new CityGUI();
        cg.setVisible(true);
    }  
}

/**
 * Clase PhotoCity que representa el panel donde se dibuja la ciudad.
 * Extiende JPanel para permitir el dibujo personalizado.
 */
class PhotoCity extends JPanel {
    private CityGUI gui; // Referencia a la ventana principal de la interfaz

    /**
     * Constructor de PhotoCity.
     *
     * @param gui La instancia de CityGUI que contiene este panel.
     */
    public PhotoCity(CityGUI gui) {
        this.gui = gui;
        setBackground(Color.white);
        setPreferredSize(new Dimension(gui.SIDE * gui.SIZE + 10, gui.SIDE * gui.SIZE + 10));         
    }

    /**
     * Método que dibuja la ciudad en el panel. Se llama automáticamente cuando el panel necesita ser repintado.
     *
     * @param g El objeto Graphics utilizado para dibujar.
     */
    public void paintComponent(Graphics g) {
        City theCity = gui.gettheCity();
        super.paintComponent(g);
         
        // Dibuja las líneas de la cuadrícula
        for (int c = 0; c <= theCity.getSize(); c++) {
            g.drawLine(c * gui.SIDE, 0, c * gui.SIDE, theCity.getSize() * gui.SIDE);
        }
        for (int f = 0; f <= theCity.getSize(); f++) {
            g.drawLine(0, f * gui.SIDE, theCity.getSize() * gui.SIDE, f * gui.SIDE);
        }       

        // Dibuja los ítems de la ciudad
        for (int f = 0; f < theCity.getSize(); f++) {
            for (int c = 0; c < theCity.getSize(); c++) {
                if (theCity.getItem(f, c) != null) {
                    g.setColor(theCity.getItem(f, c).getColor());
                    if (theCity.getItem(f, c).shape() == Item.SQUARE) {                  
                        if (theCity.getItem(f, c).isActive()) {
                            g.fillRoundRect(gui.SIDE * c + 1, gui.SIDE * f + 1, gui.SIDE - 2, gui.SIDE - 2, 2, 2);
                        } else {
                            g.drawRoundRect(gui.SIDE * c + 1, gui.SIDE * f + 1, gui.SIDE - 2, gui.SIDE - 2, 2, 2);    
                        }
                    } else {
                        if (theCity.getItem(f, c).isActive()) {
                            g.fillOval(gui.SIDE * c + 1, gui.SIDE * f + 1, gui.SIDE - 2, gui.SIDE - 2);
                        } else {
                            g.drawOval(gui.SIDE * c + 1, gui.SIDE * f + 1, gui.SIDE - 2, gui.SIDE - 2);
                        }
                    }

                    // Dibuja el estado de los agentes (feliz, indiferente, insatisfecho)
                    if (theCity.getItem(f, c).isAgent()) {
                        g.setColor(Color.red);
                        if (((Agent) theCity.getItem(f, c)).isHappy()) {
                            g.drawString("u", gui.SIDE * c + 6, gui.SIDE * f + 15);
                        } else if (((Agent) theCity.getItem(f, c)).isIndifferent()) { 
                            g.drawString("_", gui.SIDE * c + 7, gui.SIDE * f + 10);
                        } else if (((Agent) theCity.getItem(f, c)).isDissatisfied()) {
                            g.drawString("~", gui.SIDE * c + 6, gui.SIDE * f + 17);
                        }
                    }    
                }
            }
        }
    }  
}