package retroquest.service;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class VendaServiceTest {

    @Test
    public void testCalcularTotal() {
        VendaService vendaService = new VendaService();

        double resultado = vendaService.calcularTotal(2, 50.00);

        assertEquals(100.00, resultado, 0.001);
    }
}
