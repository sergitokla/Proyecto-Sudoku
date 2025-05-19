import javax.swing.*;

public class JuegoSudoku {
    //Hacemos el metodo principal para ejecutar el sudoku
    public static void main(String[] args) {

        //Hacemos que se asegure que la interfaz grafica se cree y ejecute en el hilo correcto
        SwingUtilities.invokeLater(new Runnable() {

            //Este metodo se ejecuta cuando arranca el Sudoku
            @Override
            public void run() {

                //Crea la nueva ventana del sudoku
                new SudokuGUI();
            }
        });
    }
}
