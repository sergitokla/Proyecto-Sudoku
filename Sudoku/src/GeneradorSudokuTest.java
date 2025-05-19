import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

//Clase para ver que generador sudoku funciona bien
class GeneradorSudokuTest {

    //Prueba para ver que genera bien el tablero en el nivel facil
    @Test
    void testGenerarTableroFacil() {
        testGenerarTableroConDificultad("facil", 30);
    }

    //Prueba para ver que genera bien el tablero en el nivel medio
    @Test
    void testGenerarTableroMedio() {
        testGenerarTableroConDificultad("medio", 40);
    }

    //Prueba para ver que genera bien el tablero en el nivel dificil
    @Test
    void testGenerarTableroDificil() {
        testGenerarTableroConDificultad("dificil", 50);
    }

    //Hacemos un metodo auxiliar para usarlo en los tres test anteriores y no tener que repetir el mismo codigo
    private void testGenerarTableroConDificultad(String dificultad, int maxCeldasVacias) {

        //Hacemos un nuevo objeto Sudoku, que es el tablero
        Sudoku sudoku = new Sudoku();

        //Hacemos el generador de sudokus
        GeneradorSudoku generador = new GeneradorSudoku();

        //Hacemos que genere el tablero segun la dificultad indicada
        generador.generarTablero(sudoku, dificultad);

        //Se cuentan cuantas celdas estan vacias
        int celdasVacias = contarCeldasVacias(sudoku);

        //  Se comprueba que el numero de celdas vacias no supere el limite de la dificultad
        assertTrue(celdasVacias <= maxCeldasVacias,
                "Dificultad " + dificultad + " debería tener hasta " + maxCeldasVacias + " celdas vacías, pero tiene " + celdasVacias);

        // Verificamos que el tablero generado es valido
        assertTrue(esTableroValido(sudoku), "El tablero generado no es válido");
    }

    //Metodo que hace el conteo de cuentas celdas del tablero estan vacias
    private int contarCeldasVacias(Sudoku sudoku) {
        int vacias = 0;

        //Hacemos que recorra todas las filas y columnas del tablero
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {

                //Si la celta tiene un 0 quiere decir que esta vacia
                if (sudoku.getTablero()[i][j] == 0) {
                    vacias++;
                }
            }
        }
        return vacias;
    }

    //Hacemos un metodo para verificar que el tablero sea valido
    private boolean esTableroValido(Sudoku sudoku) {
        // Obtenemos el tablero y las celdas fijas
        int[][] tablero = sudoku.getTablero();
        boolean[][] celdasFijas = sudoku.getCeldasFijas();

        //Recorremos todas las celdas del tablero
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {

                //Verificamos las celdas fijas que tienen un numero
                if (celdasFijas[i][j] && tablero[i][j] != 0) {

                    // Guardamos el valor temporalmente
                    int temp = tablero[i][j];

                    //Se borra la celda pada poder ver si seria valido colocar ese numero ahi
                    tablero[i][j] = 0;

                    //Se verifica que el numero es valido en esa posicion
                    boolean valido = sudoku.esMovimientoValido(i, j, temp);

                    // Restauramos el valor original de la celda
                    tablero[i][j] = temp;

                    //Si el numero no es valido el tablero no es valido
                    if (!valido) {
                        return false;
                    }
                }
            }
        }
        //Si todas las celdas fijas esta bien colocadas el tablero es valido
        return true;
    }
}