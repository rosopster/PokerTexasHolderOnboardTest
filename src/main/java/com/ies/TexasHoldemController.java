package com.ies;

import org.springframework.web.bind.annotation.*;

@RestController
public class TexasHoldemController {


    @RequestMapping(value = "/poker/validation", method = RequestMethod.POST)
    public Respuesta checkGame(@RequestBody Peticion request){
        TexasHoldemJuego juego1 = new TexasHoldemJuego(request.getHand1());
        TexasHoldemJuego juego2 = new TexasHoldemJuego(request.getHand2());
        return juego1.compararCon(juego2);
    }
}
