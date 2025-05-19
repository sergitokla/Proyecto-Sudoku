import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import javax.swing.*;

import static org.junit.jupiter.api.Assertions.*;

//Hacemos la clase para las pruebas
class SudokuGUITest {
    @Test
    //Esta prueba verifica que se crea la interfaz grafica sin que lance ningun error
    void testCreacionGUI() {
        //assertDoesNotThrow se asegura que el codigo dentro del bloque no lanza excepciones
        assertDoesNotThrow(() -> {

            //Creamos una nueva GUI
            SudokuGUI gui = new SudokuGUI();

            //Se verifica que no sea null(Que se haya creado bien)
            assertNotNull(gui);
        });
    }

    @Test
    //Prueba para comproabr si el metodo actualizaTablero funciona sin errores
    void testActualizarTablero() {

        //Se crea la interfaz
        SudokuGUI gui = new SudokuGUI();

        //Sudoku para utilizar la logica del juego
        Sudoku sudoku = new Sudoku();

        //Creamos un tablero vacio (matriz de 9x9 con todos 0)
        int[][] tablero = new int[9][9];
        tablero[0][0] = 5; //Ponemos un 5 en la primera celda
        sudoku.setTablero(tablero);//Le decimos al Objeto Sudoku que use este tablero

        //Creamos una matriz para las celdas fijas (de 9x9 tambien)
        boolean[][] celdasFijas = new boolean[9][9];
        celdasFijas[0][0] = true; //Le indico que la primera celda es fija
        sudoku.setCeldasFijas(celdasFijas);//Aplicamos la configuracion de las celdas fijas

        //LLamamos a actualizar tablero para asegurarnos de que no lanza errores al ejecutarse
        gui.actualizarTablero();

        //Comprobamos que el metodo se ejecuta sin lanzar excepciones
        assertDoesNotThrow(gui::actualizarTablero);
    }
}