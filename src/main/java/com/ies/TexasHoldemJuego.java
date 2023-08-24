package com.ies;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;

public class TexasHoldemJuego {
    private List<carta> cartas;
    private Map<Integer, List<carta>> mapaCartas;
    private Respuesta respuesta;

    TexasHoldemJuego(String valoresCartas) {
        cartas = new ArrayList<>();
        String[] stringValores = valoresCartas.split(" ");
        for (String valor : stringValores) {
            cartas.add(new carta(valor));
        }
        cartas.sort(Comparator.comparingInt(o -> o.valorFinal));
        mapaCartas = Helpers.getMapaValores(cartas);

        respuesta = new Respuesta();
    }

    Map<Integer, List<carta>> getMapaCartas() {
        return mapaCartas;
    }

    List<carta> getCartas() {
        return cartas;
    }

    Respuesta compararCon(TexasHoldemJuego otro) {
        Valores actual = escalaValores();
        Valores otros = otro.escalaValores();

        if (actual.getPonderado() > otros.getPonderado()) {
            respuesta.setWinnerHand("hand1");
            return respuesta;
        } else{
            respuesta.setWinnerHand("hand2");
            return respuesta;
        }
    }

    Valores escalaValores() {
        if (esEscaleraDeColor()) {
        	respuesta.setWinnerHandType("StraightFlush");
            return Valores.ESCALERA_COLOR;
        } else if (sonCuatroDeUnTipo()) {
        	respuesta.setWinnerHandType("FourOfAKind");
            return Valores.POKER;
        } else if (esFullHouse()) {
        	respuesta.setWinnerHandType("FullHouse");
            return Valores.FULL_HOUSE;
        } else if (esColor()) {
        	respuesta.setWinnerHandType("Flush");
            return Valores.COLOR;
        } else if (esEscalera()) {
        	respuesta.setWinnerHandType("Straight");
            return Valores.ESCALERA;
        } else if (sonTresDeUnTipo()) {
        	respuesta.setWinnerHandType("ThreeOfAKind");
            return Valores.TRES_DE_UNA_CLASE;
        } else if (sonDosPares()) {
        	respuesta.setWinnerHandType("TwoPair");
            respuesta.setCompositionWinnerHand(new String[]{"King", "3"});
            return Valores.DOS_PARES;
        } else if (esUnPar()) {
        	respuesta.setWinnerHandType("OnePair");
            respuesta.setCompositionWinnerHand(new String[]{"King"});
            return Valores.UN_PAR;
        } else {
        	respuesta.setWinnerHandType("HighCard");
            respuesta.setCompositionWinnerHand(new String[]{"As"});
            return Valores.CARTA_ALTA;
        }
    }

    private boolean esUnPar() {
        return Helpers.getCountOfGroupOfASize(this.mapaCartas, 2) == 1;
    }

    private boolean sonDosPares() {
        return Helpers.getCountOfGroupOfASize(this.mapaCartas, 2) == 2;
    }

    private boolean sonTresDeUnTipo() {
        return Helpers.getCountOfGroupOfASize(this.mapaCartas, 3) == 1;
    }

    private boolean esFullHouse() {
        return sonTresDeUnTipo() && esUnPar();
    }

    private boolean sonCuatroDeUnTipo() {
        return Helpers.getCountOfGroupOfASize(this.mapaCartas, 4) == 1;
    }

    private boolean esEscaleraDeColor() {
    	
        return esEscalera() && esColor();
    }


    private boolean esEscalera() {
        boolean esIncremental = true;
        for (int index = 0; index < this.getCartas().size() - 1; index++) {
            if (Math.abs(this.getCartas().get(index).getValorFinal() - this.getCartas().get(index + 1).getValorFinal()) != 1) {
                esIncremental = false;
            }
        }
        return esIncremental;
    }

    private boolean esColor() {
        return this.getCartas().stream().collect(groupingBy(carta::getFigura)).size() == 1;
    }
    public enum Valores {

        CARTA_ALTA {
            @Override
            public int getPonderado() {
                return 1;
            }
        },
        UN_PAR {
            @Override
            public int getPonderado() {
                return 2;
            }
        },
        DOS_PARES {
            @Override
            public int getPonderado() {
                return 3;
            }
        },
        TRES_DE_UNA_CLASE {
            @Override
            public int getPonderado() {
                return 4;
            }
        },
        ESCALERA {
            @Override
            public int getPonderado() {
                
                return 5;
            }
        },
        COLOR {
            @Override
            public int getPonderado() {
                return 6;
            }
        },
        FULL_HOUSE {
            @Override
            public int getPonderado() {
                return 7;
            }
        },
        POKER {
            @Override
            public int getPonderado() {
                return 8;
            }
        },
        ESCALERA_COLOR {
            @Override
            public int getPonderado() {
                return 9;
            }
        };
        public abstract int getPonderado();
    }

    static class Helpers {
        static Map<Integer, List<carta>> getMapaValores(List<carta> cartas) {
            return cartas.stream().collect(groupingBy(carta::getValorFinal));
        }

        static int getCountOfGroupOfASize(Map<Integer, List<carta>> map, int groupSize) {
            return (int) map.entrySet().stream().filter(x -> x.getValue().size() == groupSize).count();
        }

    }

    public class carta {
        private int valorFinal;
        private char figura;

        carta(String representacion) {
           String valor;
            if(representacion.length() == 2){
                valor = String.valueOf(representacion.charAt(0));
                figura = representacion.charAt(1);
            }
            else{
                valor =  representacion.substring(0,2);
                figura = representacion.charAt(2);
            }

            valorFinal = calcularValor(valor);
        }

        int getValorFinal() {
            return valorFinal;
        }

        char getFigura() {
            return figura;
        }

        private int calcularValor(String valor) {
            int valorCarta;
            switch (valor) {
                case "2":
                case "3":
                case "4":
                case "5":
                case "6":
                case "7":
                case "8":
                case "9":
                    valorCarta = Integer.parseInt(valor + "");
                    break;
                case "10":
                    valorCarta = 10;
                    break;
                case "J":
                    valorCarta = 11;
                    break;
                case "Q":
                    valorCarta = 12;
                    break;
                case "K":
                    valorCarta = 13;
                    break;
                case "A":
                    valorCarta = 14;
                    break;
                default:
                    throw new IllegalArgumentException("Valor no valido para una carta : " + valor);
            }
            return valorCarta;
        }
    }

}