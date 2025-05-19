import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SudokuTest {

    //Declaramos Sudoku para utilizar la logica de los juegos en los test
    private Sudoku sudoku;

    //Metodo que se va a ejecutar antes de cada test, es para preparar un tablero inicial
    @BeforeEach
    void setUp() {
        sudoku = new Sudoku(); //Creamos un nuevo juego

        //Hacemos un tablero inicial con algunos numeros colocados
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
        sudoku.setTablero(tableroInicial);//Asignamos este tablero al juego

        //Hacemos un array para marcar que celdas son fijas
        boolean[][] celdasFijas = new boolean[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (tableroInicial[i][j] != 0) {
                    celdasFijas[i][j] = true;//Si hay algun numero se marca como fija
                }
            }
        }
        sudoku.setCeldasFijas(celdasFijas);//Asignamos las celdas fijas al juego
    }

    //Test para ver si los movimientos son validos segun las reglas
    @Test
    void testEsMovimientoValido() {

        //Movimiento valido
        assertTrue(sudoku.esMovimientoValido(0, 2, 4));

        //Movimiento invalido (numero 5 repetido en fila)
        assertFalse(sudoku.esMovimientoValido(0, 2, 5));

        //Movimiento invalido (numero 6 repetido en columna)
        assertFalse(sudoku.esMovimientoValido(0, 2, 6));

        //Movimiento invalido (número 8 repetido en subcuadricula)
        assertFalse(sudoku.esMovimientoValido(0, 2, 8));
    }

    //Test para ver si se puede colocar un numero en una celda
    @Test
    void testColocarNumero() {
        assertTrue(sudoku.colocarNumero(0, 2, 4)); //Movimiento valido
        assertEquals(4, sudoku.getTablero()[0][2]); //Confirmamos que se haya colocado el 4

        assertFalse(sudoku.colocarNumero(0, 0, 1)); //Celda fija, no se puede modificar
        assertEquals(5, sudoku.getTablero()[0][0]); //El numero deberia seguir siendo 5

        assertFalse(sudoku.colocarNumero(0, 2, 5)); // 5 ya esta en la fila, movimiento invalido

        assertFalse(sudoku.colocarNumero(0, 2, 10)); //Numero fuera del rango(1-9)
        assertFalse(sudoku.colocarNumero(0, 2, 0)); //0 No es valido como numero
    }

    //Test para ver si el tablero esta bien resuelto
    @Test
    void testEstaResuelto() {
        assertFalse(sudoku.estaResuelto()); //El tablero inicial no esta resuelto

        //Creamos un tablero completamente resuelto (solucion valida)
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
        sudoku.setTablero(tableroResuelto); //Ponemos ese tablero en el juego
        assertTrue(sudoku.estaResuelto()); //ahora si deberia estar resuelto

        sudoku.getTablero()[0][0] = 1; //Cambiamos un numero para hacer que el tablero este incorrecto
        assertFalse(sudoku.estaResuelto()); //Ya no esta resuelto correctamente
    }

    //Test para ver que el metodo limpiarTablero deja todo vacio
    @Test
    void testLimpiarTablero() {
        sudoku.limpiarTablero(); //Limpiamos el tablero y las celdas fijas
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                assertEquals(0, sudoku.getTablero()[i][j]); //Todas las celdas deben quedar en 0
                assertFalse(sudoku.getCeldasFijas()[i][j]); //Ninguna celda debe estar marcada como fija
            }
        }
    }
}