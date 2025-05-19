import java.util.Arrays;

public class Sudoku {

    //Hacemos la matriz para el tablero del sudoku
    private int[][] tablero;

    //Hacemos una matriz con bolean que dira si una celda es fija (true) o editable (false)
    private boolean[][] celdasFijas;

    //Hacemos el constructor que inicializa el tablero y las celdas fijas como matrices vacias
    public Sudoku() {
        tablero = new int[9][9];
        celdasFijas = new boolean[9][9];
    }

    //Hacemos un metodo para generar un nuevo tablero segun la dificultad indicada
    public void generarTablero(String dificultad) {
        GeneradorSudoku generador = new GeneradorSudoku();//Se crea el generador de tableros
        generador.generarTablero(this, dificultad);//Generamos el tablero dentro de este objeto sudoku
    }

    //Se comprueba si es valido colocar algun numero en una posicion especifica del tablero
    public boolean esMovimientoValido(int fila, int columna, int valor) {
        //Se verifica que el numero no este repetido en la fila
        for (int c = 0; c < 9; c++) {
            if (tablero[fila][c] == valor && c != columna) {
                return false;
            }
        }

        //Se verifica que el numero no esta repetido en la columna
        for (int r = 0; r < 9; r++) {
            if (tablero[r][columna] == valor && r != fila) {
                return false;
            }
        }

        // Se verifica que el numero no este repetido en la subcuadrícula 3x3
        int subgridRowStart = (fila / 3) * 3;
        int subgridColStart = (columna / 3) * 3;

        for (int r = subgridRowStart; r < subgridRowStart + 3; r++) {
            for (int c = subgridColStart; c < subgridColStart + 3; c++) {
                if (tablero[r][c] == valor && r != fila && c != columna) {
                    return false;
                }
            }
        }
        //Si pasa todas las validaciones anteriores el numero es valido
        return true;
    }

    //Para intentar colocar un numero en una celda
    public boolean colocarNumero(int fila, int columna, int valor) {

        //No se puede modificar una celda fija
        if (celdasFijas[fila][columna]) {
            return false;
        }

        //Solo se aceptan numeros del 1 al 9
        if (valor < 1 || valor > 9) {
            return false;
        }

        //Si el numero es valido se coloca en el tablero
        if (esMovimientoValido(fila, columna, valor)) {
            tablero[fila][columna] = valor;
            return true;
        }
        return false;
    }

    //Comprobamos si el tablero esta completamente resuelto de forma correcta
    public boolean estaResuelto() {

        //Se verifica que las filas tengan numeros del 1 al 9 sin repetir
        for (int fila = 0; fila < 9; fila++) {
            boolean[] numeros = new boolean[10];//Indicamos del 1 al 9
            for (int col = 0; col < 9; col++) {
                int num = tablero[fila][col];
                if (num == 0 || numeros[num]) {
                    return false;//Si hay un 0 (que significa que esta vacio) o un numero repetido, esta mal
                }
                numeros[num] = true;
            }
        }

        // Verificamos las columnas
        for (int col = 0; col < 9; col++) {
            boolean[] numeros = new boolean[10];
            for (int fila = 0; fila < 9; fila++) {
                int num = tablero[fila][col];
                if (num == 0 || numeros[num]) {
                    return false;
                }
                numeros[num] = true;
            }
        }

        // Verificamos las subcuadrículas 3x3
        for (int subgrid = 0; subgrid < 9; subgrid++) {
            boolean[] numeros = new boolean[10];
            int subgridRowStart = (subgrid / 3) * 3;
            int subgridColStart = (subgrid % 3) * 3;

            for (int r = subgridRowStart; r < subgridRowStart + 3; r++) {
                for (int c = subgridColStart; c < subgridColStart + 3; c++) {
                    int num = tablero[r][c];
                    if (num == 0 || numeros[num]) {
                        return false;
                    }
                    numeros[num] = true;
                }
            }
        }

        return true; //Si todo esta bien el tablero esta resuelto
    }

    // Mostramos el tablero por consola
    public void mostrarTablero() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                System.out.print(tablero[i][j] + " ");
            }
            System.out.println();
        }
    }

    // Getters y setters
    public int[][] getTablero() {
        return tablero;
    }

    public void setTablero(int[][] tablero) {
        this.tablero = tablero;
    }

    public boolean[][] getCeldasFijas() {
        return celdasFijas;
    }

    public void setCeldasFijas(boolean[][] celdasFijas) {
        this.celdasFijas = celdasFijas;
    }

    //Para limpiar el tablero dejando todo en ceros y sin celdas vacias
    public void limpiarTablero() {
        for (int i = 0; i < 9; i++) {
            Arrays.fill(tablero[i], 0); //Para poner todos los valores en 0
            Arrays.fill(celdasFijas[i], false); //Para marcar todas las celdas como no fijas
        }
    }
}
