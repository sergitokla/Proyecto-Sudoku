import java.util.Random;

public class GeneradorSudoku {
    private Random random = new Random();

    //Hcemos el metodo principal para hacer un tablero segun la dificultad que se elija
    public void generarTablero(Sudoku sudoku, String dificultad) {
        sudoku.limpiarTablero();

        // Para generar un tablero completo y resuelto
        generarTableroResuelto(sudoku, 0, 0);

        // Determinamos cuantas celdas se eliminan dependiendo de la dificultad
        int celdasAEliminar;
        switch (dificultad.toLowerCase()) {
            case "dificil":
                celdasAEliminar = 50;
                break;
            case "medio":
                celdasAEliminar = 40;
                break;
            case "facil":
            default:
                celdasAEliminar = 30;
                break;
        }

        //Se elimina las celdas del tablero para hacer el rompecabezas
        eliminarCeldas(sudoku, celdasAEliminar);
    }

    //Hacemos el metodo recursivo para llenar el tablero con una solucion valida
    private boolean generarTableroResuelto(Sudoku sudoku, int fila, int col) {
        if (col == 9) { //Si se llega al final de la fila
            col = 0;
            fila++;
            if (fila == 9) { //Si se terminan todas las filas
                return true; //Tablero completo
            }
        }

        int[][] tablero = sudoku.getTablero();

        // Si la celda ya tiene un numero, se pasa a la siguiente
        if (tablero[fila][col] != 0) {
            return generarTableroResuelto(sudoku, fila, col + 1);
        }

        // Se intenta poner numeros del 1 al 9 aleatoriamente
        for (int num = 1; num <= 9; num++) {
            int randomNum = random.nextInt(9) + 1; //Numero entre el 1 y el 9
            if (sudoku.esMovimientoValido(fila, col, randomNum)) {
                tablero[fila][col] = randomNum; //Coloca el numero

                //Pasa a la siguiente celda
                if (generarTableroResuelto(sudoku, fila, col + 1)) {
                    return true;
                }

                //Si no funciona retrocede
                tablero[fila][col] = 0;
            }
        }

        return false; //Si ninguno de los numeros es valido pues nos devuelve falso
    }

    //Se eliminan las celdas aleatoriamente segun la cantidad indicada
    private void eliminarCeldas(Sudoku sudoku, int celdasAEliminar) {
        int[][] tablero = sudoku.getTablero();
        boolean[][] celdasFijas = sudoku.getCeldasFijas();
        Random random = new Random();

        int eliminadas = 0;
        while (eliminadas < celdasAEliminar) {
            int fila = random.nextInt(9);
            int col = random.nextInt(9);

            //Solo se elimina si la celda no esta vacia
            if (tablero[fila][col] != 0) {
                tablero[fila][col] = 0; //Elimina el numero
                celdasFijas[fila][col] = false;//Se marca que ya no es fija
                eliminadas++;
            } else {
                //Si ya estaba vacia, nos aseguramos de que no sea celda fija
                celdasFijas[fila][col] = true;
            }
        }

        // Marcamos las celdas restantes que no se eliminaron como fijas
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (tablero[i][j] != 0) {
                    celdasFijas[i][j] = true;
                }
            }
        }
    }
}
