package com.ies;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.springframework.test.util.AssertionErrors.assertEquals;

@SpringBootTest
class PruebaRspApplicationTests {

	@Test
	public void texasHoldemTest() {
		Test("Escalera de color mas alta gana", "hand2", "2H 3H 4H 5H 6H", "KS AS TS QS JS");
		Test("Escalera de color le gana a cuatro de un tipo", "hand1", "2H 3H 4H 5H 6H", "AS AD AC AH JD");
	}

	private void Test(String descripcion, String valorEsperado, String jugador1, String jugador2) {
		TexasHoldemJuego jug_1 = new TexasHoldemJuego(jugador1);
		TexasHoldemJuego jug_2 = new TexasHoldemJuego(jugador2);
		assertEquals(descripcion + ":", valorEsperado, jug_1.compararCon(jug_2));
	}
}
