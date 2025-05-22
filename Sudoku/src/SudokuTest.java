import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SudokuTest {
    private Sudoku sudoku;

    @BeforeEach
    public void setUp() {

        //Creamos una instancia del juego antes de las pruebas
        sudoku = new Sudoku();

        // Configuramos un tablero incompleto para las pruebas
        int[][] tableroInicial = {
                {5, 3, 0, 0, 7, 0, 0, 0, 0},
                {6, 0, 0, 1, 9, 5, 0, 0, 0},
                {0, 9, 8, 0, 0, 0, 0, 6, 0},
                {8, 0, 0, 0, 6, 0, 0, 0, 3},
                {4, 0, 0, 8, 0, 3, 0, 0, 1},
                {7, 0, 0, 0, 2, 0, 0, 0, 6},
                {0, 6, 0, 0, 0, 0, 2, 8, 0},
                {0, 0, 0, 4, 1, 9, 0, 0, 5},
                {0, 0, 0, 0, 8, 0, 0, 7, 9}
        };
        sudoku.setTablero(tableroInicial);//Asignamos el tablero a la instancia de Sudoku

        // Marcar algunas celdas como fijas
        boolean[][] fijas = new boolean[9][9];//Tablero boleano que indica cuales celdas son fijas
        fijas[0][0] = true; // El 5 en la esquina superior izquierda es fijo
        sudoku.setCeldasFijas(fijas);//Asignamos las celdas fijas al tabblero
    }

    @Test
    public void testEsMovimientoValido() {
        assertTrue(sudoku.esMovimientoValido(0, 2, 4)); // Celda vacia, numero valido
        assertFalse(sudoku.esMovimientoValido(0, 2, 5)); // 5 ya esta en la fila
        assertFalse(sudoku.esMovimientoValido(0, 2, 6)); // 6 ya esta en la columna
        assertFalse(sudoku.esMovimientoValido(0, 2, 8)); // 8 ya esta en la subcuadricula
    }

    @Test
    public void testColocarNumeroValido() throws SudokuException {
        assertTrue(sudoku.colocarNumero(0, 2, 4));//Intentamos colocar el numero 4 en (0,2)
        assertEquals(4, sudoku.getTablero()[0][2]);//Verificamos que se haya colocado
    }

    @Test
    public void testColocarNumeroEnCeldaFija() {
        assertThrows(MovimientoInvalidoException.class, () -> {
            sudoku.colocarNumero(0, 0, 9); //Intenta colocar un numero en una celda fija
        });
    }

    @Test
    public void testColocarNumeroFueraDeRango() {
        assertThrows(EntradaFueraDeRangoException.class, () -> {
            sudoku.colocarNumero(0, 2, 10); //Intenta colocar un numero fuera de rango
        });
    }

    @Test
    public void testEstaResuelto() {
        assertFalse(sudoku.estaResuelto());//Con el tablero inicial no debe de estar completo

        // Creamos un tablero resuelto y correcto
        int[][] tableroResuelto = {
                {5, 3, 4, 6, 7, 8, 9, 1, 2},
                {6, 7, 2, 1, 9, 5, 3, 4, 8},
                {1, 9, 8, 3, 4, 2, 5, 6, 7},
                {8, 5, 9, 7, 6, 1, 4, 2, 3},
                {4, 2, 6, 8, 5, 3, 7, 9, 1},
                {7, 1, 3, 9, 2, 4, 8, 5, 6},
                {9, 6, 1, 5, 3, 7, 2, 8, 4},
                {2, 8, 7, 4, 1, 9, 6, 3, 5},
                {3, 4, 5, 2, 8, 6, 1, 7, 9}
        };
        sudoku.setTablero(tableroResuelto);//Asignamos el tablero completo al juego
        assertTrue(sudoku.estaResuelto());//Verifica que el metodo reconozca que el tablero esta resuelto
    }
}