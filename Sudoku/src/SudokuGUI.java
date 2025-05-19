import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//Creamos la clase principal para la interfaz grafica
public class SudokuGUI {

    //Declaramos Sudoku que tiene guardado toda la logica del juego
    private Sudoku sudoku;

    //Para la ventana de la aplicacion
    private JFrame frame;

    //Ponemos una matriz de botones que representaran las celdas del tablero
    private JButton[][] botones;

    //Utilizamos ComboBox para poder seleccionar la dificultad del tablero
    private JComboBox<String> dificultadComboBox;

    //Hacemos un constructor que crea un nuevo sudoku y hace la interfaz grafica
    public SudokuGUI() {
        sudoku = new Sudoku();
        inicializarGUI();
    }

    //Hacemos un metodo que construya y muestre todos los elementos de la GUI
    private void inicializarGUI() {

        //Creamos la ventana con el titulo Sudoku
        frame = new JFrame("Sudoku");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//Para que cierre la aplicacion al cerrar la ventana
        frame.setSize(600, 650);//Ponemos tamaño a la ventana
        frame.setLayout(new BorderLayout());//Para organizar el contenido de la ventana

        // Panel superior para controles del tablero(dificultad, nuevo juego, verificar solucion
        JPanel controlPanel = new JPanel();

        //Para el menu desplegable de dificultad
        dificultadComboBox = new JComboBox<>(new String[]{"Facil", "Medio", "Dificil"});

        //Para el boton de nuevo juego
        JButton nuevoJuegoBtn = new JButton("Nuevo Juego");

        //Para el boton de verificar solucion
        JButton verificarBtn = new JButton("Verificar Solución");

        //Para cuando pulsamos nuevo juego
        nuevoJuegoBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //Obtenemos la dificultad que haya sido seleccionada
                String dificultad = (String) dificultadComboBox.getSelectedItem();

                //Generamos el tablero segun la dificultad seleccionada
                sudoku.generarTablero(dificultad.toLowerCase());

                //Actualizamos el tablero
                actualizarTablero();
            }
        });

        //Para cuando pulsamos en verificar solucion
        verificarBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (sudoku.estaResuelto()) {
                    JOptionPane.showMessageDialog(frame, "¡Felicidades! Has resuelto el Sudoku correctamente.");
                } else {
                    JOptionPane.showMessageDialog(frame, "El tablero no está completo o tiene errores.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        //Agregamos todos los componentes al panel de controles
        controlPanel.add(new JLabel("Dificultad:"));
        controlPanel.add(dificultadComboBox);
        controlPanel.add(nuevoJuegoBtn);
        controlPanel.add(verificarBtn);

        //Añadimos el panel de controles a la parte de arriba de la ventana
        frame.add(controlPanel, BorderLayout.NORTH);

        // Panel central con el tablero de botones
        JPanel tableroPanel = new JPanel(new GridLayout(9, 9));//9x9 botones
        tableroPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        //Creamos la matriz de botones
        botones = new JButton[9][9];

        //Creamos cada boton de cada celda
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                botones[i][j] = new JButton();
                botones[i][j].setFont(new Font("Arial", Font.BOLD, 20));

                // Cambiamos los colores para distinguir bien las subcuadrículas 3x3
                if ((i / 3 + j / 3) % 2 == 0) {
                    botones[i][j].setBackground(new Color(240, 240, 240));
                } else {
                    botones[i][j].setBackground(Color.WHITE);
                }

                //Guardamos la posicion actual para usarla en el evento del boton
                final int fila = i;
                final int col = j;

                //Evento al hacer clic en una celda
                botones[i][j].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        manejarClicCelda(fila, col);
                    }
                });

                //Añadimos el boton al panel del tablero
                tableroPanel.add(botones[i][j]);
            }
        }

        //Ponemos el panel del tablero en el centro de la ventana
        frame.add(tableroPanel, BorderLayout.CENTER);

        // Generar un tablero inicial al arrancar el programa
        sudoku.generarTablero("facil");
        actualizarTablero();//Mostramos los numeros en los botones

        //Mostramos la ventana
        frame.setVisible(true);
    }

    //Hacemos un metodo que se ejecuta al hacer clic sobre una celda del tablero
    private void manejarClicCelda(int fila, int col) {

        //Si la celda es fija (que no sea editable), mostramos un mensaje de advertencia
        if (sudoku.getCeldasFijas()[fila][col]) {
            JOptionPane.showMessageDialog(frame, "Esta celda es fija y no se puede modificar.", "Celda fija", JOptionPane.WARNING_MESSAGE);
            return;
        }

        //Mostramos un cuadro para introducir un numero
        String input = JOptionPane.showInputDialog(frame, "Ingrese un número del 1 al 9 (0 para borrar):", "Ingresar número", JOptionPane.PLAIN_MESSAGE);

        //Si no se escribe nada o cancela, se sale
        if (input == null || input.isEmpty()) {
            return;
        }

        try {
            int valor = Integer.parseInt(input);//Convertimos el imput en numero

            if (valor == 0) {//Si se pone 0 se borra la celda
                sudoku.getTablero()[fila][col] = 0;
                actualizarTablero();
                return;
            }

            if (valor < 1 || valor > 9) {
                JOptionPane.showMessageDialog(frame, "Por favor ingrese un número entre 1 y 9.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            //Para que intente colocar el numero en el tablero
            if (sudoku.colocarNumero(fila, col, valor)) {
                actualizarTablero();
            } else {
                JOptionPane.showMessageDialog(frame, "Movimiento inválido. El número ya está en la fila, columna o subcuadrícula.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "Por favor ingrese un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    //Para actualizar los textos y estilos de los botones segun el estado del tablero
    void actualizarTablero() {
        int[][] tablero = sudoku.getTablero();
        boolean[][] celdasFijas = sudoku.getCeldasFijas();

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {

                //Si la ecelda esta vacia, es un boton sin texto
                if (tablero[i][j] == 0) {
                    botones[i][j].setText("");
                } else {
                    botones[i][j].setText(String.valueOf(tablero[i][j]));
                }

                //Le ponemos el estilo distinto a las celdas que son fijas
                if (celdasFijas[i][j]) {
                    botones[i][j].setForeground(Color.BLUE);
                    botones[i][j].setFont(botones[i][j].getFont().deriveFont(Font.BOLD));
                } else {
                    botones[i][j].setForeground(Color.BLACK);
                    botones[i][j].setFont(botones[i][j].getFont().deriveFont(Font.PLAIN));
                }
            }
        }
    }
}
