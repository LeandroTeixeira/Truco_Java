/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package truco.baralho;

import java.util.Random;
import truco.util.Constantes;
import truco.util.Constantes.Ordenacao;

/**
 *
 * @author PC
 */
public class Baralho {

    private final Carta[] baralho;
    private byte cont;//Define quantas cartas ainda restam no baralho

    public Baralho() {
        this.cont = 40;
        baralho = new Carta[cont];
        inicializar();
    }

    public Baralho(Ordenacao ord) {
        this.cont = 40;
        baralho = new Carta[cont];
        inicializar();
    }

    /**
     * 1 - Cria todas as cartas do baralho de truco e coloca uma em cada posição
     * no vetor Carta. 2 - Como definido em truco.util.Constantes, toda carta
     * possui seu simbolo dado por um número de 2 algarismos. O primeiro se
     * refere a seu naipe enquanto o segundo à carta em si. Esses valores são
     * utilizados para definir o ícone de cada uma
     */
    private void inicializar() {
        byte valor = 0;
        String naipe = "";
        String carta = "";
        //cria todas as cartas do baralho de truco e coloca uma em cada posição no vetor Carta
        for (byte i = 10; i < 50; i++) {
            switch (i / 10) {
                case 1:
                    naipe = "Paus";
                    break;
                case 2:
                    naipe = "Espadas";
                    break;
                case 3:
                    naipe = "Copas";
                    break;
                case 4:
                    naipe = "Ouros";
                    break;
            }
            switch (i % 10) {
                case 0:
                    carta = "Rei";
                    valor = Constantes.VREI;
                    break;
                case 1:
                    carta = "Ás";
                    valor = Constantes.VAS;
                    break;
                case 2:
                    carta = "Dois";
                    valor = Constantes.VDOIS;
                    break;
                case 3:
                    carta = "Três";
                    valor = Constantes.VTRES;
                    break;
                case 4:
                    carta = "Quatro";
                    valor = Constantes.VQUATRO;
                    break;
                case 5:
                    carta = "Cinco";
                    valor = Constantes.VCINCO;
                    break;
                case 6:
                    carta = "Seis";
                    valor = Constantes.VSEIS;
                    break;
                case 7:
                    carta = "Sete";
                    valor = Constantes.VSETE;
                    break;
                case 8:
                    carta = "Dama";
                    valor = Constantes.VDAMA;
                    break;
                case 9:
                    carta = "Valete";
                    valor = Constantes.VVALETE;
                    break;

            }

            /*Como definido em truco.util.Constantes, toda carta possui seu 
            simbolo dado por um número de 2 algarismos. O primeiro se refere
            a seu naipe enquanto o segundo à carta em si. Esses valores são 
            utilizados para definir o ícone de cada uma
             */
            baralho[i - 10] = new Carta(valor, i, /*String.valueOf(i),*/ carta + " de " + naipe);

        }

    }

    /**
     * Troca cada carta com outra aleatória dentro do vetor
     */
    public void embaralhar() {
        resetContador();
        Random random = new Random();
        for (int i = 0; i < baralho.length; i++) {
            int troca = random.nextInt(baralho.length);
            swap(i, troca);
        }
    }

    private void swap(int p1, int p2) {
        Carta aux = baralho[p1];
        baralho[p1] = baralho[p2];
        baralho[p2] = aux;
    }

    /*Entrega a carta na posição baralho[cont-1]. Função designada para ser
    utilizada por uma classe feita para controlar o jogo.
     */
    public Carta entregarCarta() {
        Carta entregue = null;
        try {
            entregue = baralho[--cont];
        } catch (ArrayIndexOutOfBoundsException e) {
            System.err.println("Não há mais cartas a serem distribuídas!");
        }
        return entregue;
    }

    public Carta[] getBaralho() {return baralho;}

    /**
     * Função designada para distribuir uma quantidade de cartas, geralmente
     * múltiplo de 3, entre os jogadores.
     *
     * @param qtd - Quantidade de cartas a serem distribuídas
     * @return Carta[] Distribuída
     */
    public Carta[] distribuir(int qtd) {
        Carta[] distribuidas = new Carta[qtd];
        for (int i = 0; i < qtd; i++) {
            distribuidas[i] = entregarCarta();
        }
        return distribuidas;
    }

    public void resetContador() {cont = 40;}

}
