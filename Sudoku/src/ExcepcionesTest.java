import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ExcepcionesTest {
    @Test
    public void testMovimientoInvalidoException() {

        //Creamos una instancia para la excepcion con un mensaje
        MovimientoInvalidoException exception =
                new MovimientoInvalidoException("Mensaje de prueba");

        //Verificamos que el mensaje de la excepcion sea el que esperamos
        assertEquals("Mensaje de prueba", exception.getMessage());

        //Verificamos que la excepcion hereda de SudokuException
        assertTrue(exception instanceof SudokuException);
    }

    @Test
    public void testEntradaFueraDeRangoException() {

        //Creamos una instancia para la excepcion con un mensaje
        EntradaFueraDeRangoException exception =
                new EntradaFueraDeRangoException("Mensaje de prueba");

        //Verificamos que el mensaje de la excepcion sea el que esperamos
        assertEquals("Mensaje de prueba", exception.getMessage());

        //Verificamos que la excepcion hereda de SudokuException
        assertTrue(exception instanceof SudokuException);
    }
}
