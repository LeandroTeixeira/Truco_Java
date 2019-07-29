/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package truco.regras;

import truco.baralho.Baralho;
import truco.baralho.Carta;
import truco.util.Constantes;
import truco.util.Constantes.TipoErro;

/**
 *
 * @author PC
 */
public class RuleSet {

    /*Variáveis de um típico jogo de truco. Esse software permite a presença
    de 2 ou 4 jogadores por partida nos modos de jogo mineiro (manilha fixa,
    2 pontos por rodada), paulista (manilha variável, 1 ponto por rodada) ou 
    goiano (manilha fixa, 1 ponto por rodada). Essa classe é a 'juíza', responsável
    por distribuir as cartas, definir a pontuação e os vencedores de cada rodada.
     */
    private final byte jogadores;
    private final byte tipo;
    private final Baralho deck;
    private byte cartasPorRodada;
    private byte pontosPorRodada;
    private byte maofinal;//Representa a mão em que os jogadores podem definir se querem ou não as cartas
    private Carta vira;
    private byte Smanilha;//Variável usada exclusivamente no truco paulista para fazer as manilhas perderem seu status de manilhas entre um jogo e outro

    /**
     * Esse software permite a presença
    de 2 ou 4 jogadores por partida nos modos de jogo mineiro (manilha fixa,
    2 pontos por rodada), paulista (manilha variável, 1 ponto por rodada) ou 
    goiano (manilha fixa, 1 ponto por rodada). Essa classe é a 'juíza', responsável
    por distribuir as cartas, definir a pontuação e os vencedores de cada rodada.
     * 
     * A classe precisa ser instanciada já com as informações relevantes a ela,
       que são número de jogadores e modo de jogo.
     * @param tipo - Usada para definir o número de jogadores e o modo de jogo
     * @param jogadores
     * @throws RuleException 
     */
    public RuleSet(byte tipo, byte jogadores) throws RuleException {
        if (tipo <= Constantes.MINEIRO && tipo >= Constantes.PAULISTA) {
            this.tipo = tipo;
        } else {
            throw new RuleException(TipoErro.TIPO);
        }
        
      //Variáveis finais
        this.deck = new Baralho();
        if(jogadores==2 || jogadores==4)
            this.jogadores=jogadores;
        else
            throw new RuleException(TipoErro.JOGADOR);
        Smanilha=-1;
        this.setCartasPorRodada();
        this.setPontosPorRodada();
        this.setMaofinal();
    }

    private void setCartasPorRodada() {
        cartasPorRodada = (byte) (3 * this.jogadores);

        //No caso do truco paulista, além das cartas distribuídas aos jogadores, há também a carta virada. 
        if (this.tipo == Constantes.PAULISTA) {
            ++cartasPorRodada;
        }
    }

    /**
     * Define os pontos por rodada, sendo 1 no caso de paulista ou goiano e 
     * 2 no caso de mineiro.
     */
    public final void setPontosPorRodada(){
        pontosPorRodada=1;
        if (this.tipo == Constantes.MINEIRO) {
            pontosPorRodada *= 2;
        }
    }

    
    private void setMaofinal() {
        maofinal=11;
        if (this.tipo == Constantes.MINEIRO) {
            maofinal-=1;
        }
    }
    
    public byte getMaofinal() {
        return maofinal;
    }

    public void setMaofinal(byte maofinal) {
        this.maofinal = maofinal;
    }

    /**
     * 
     * @param cartas
     * @return 
     */
    private Carta[] setManilhas(Carta[] cartas) {
        Carta[] carta = cartas;
        //Se o numero de cartas for par, o truco é mineiro ou goiano, logo, manilhas fixas
        if (cartasPorRodada % 2 == 0) {
            this.manilhaFixa(cartas);
        } //Se o numero for impar, o truco é paulista, logo, manilhas variáveis
        else {
            this.manilhaVariavel(cartas);
        }

        return carta;
    }

    /**
     * Define as manilhas fixas, que são o caso do truco mineiro e do goiano.
     * A função recebe um vetor de cartas e procura pelas cartas mais fortes,
     * respectivamente, quatro de paus, sete de copas, ás de espadas e sete de ouros.
     * 
     * Eles são identificados através do código do símbolo e recebem o valor da manilha.
     * @param carta Baralho a ser recebido
     * 
     * Nota-se que a passagem por referência e não por valor dispensa o retorno do deck organizado
     */
    private void manilhaFixa(Carta[] carta){
        for (Carta carta1 : carta) {
                switch (carta1.getSIMBOLO()) {
                    case 14:
                        carta1.setValor(Constantes.VPAUS);
                        break;
                    case 21:
                        carta1.setValor(Constantes.VESPADAS);
                        break;
                    case 37:
                        carta1.setValor(Constantes.VCOPAS);
                        break;
                    case 47:
                        carta1.setValor(Constantes.VOUROS);
                        break;
                }
            }
    }
    
    /**
     * Define as manilhas variáveis, usadas no truco paulista. As cartas geralmente 
     * são distribuídas em ordem crescente no vetor, fazendo a vira ser a última. 
     * Dela extraímos o símbolo pelo resto da divisão por 10
     * 
     * @param carta 
     */
    
    private void manilhaVariavel(Carta[] carta){
            resetManilhas(deck.getBaralho());//faz as manilhas da última rodada voltarem a ser cartas comuns
            vira = carta[carta.length - 1];
            //As manilhas são as do número seguinte às da vira
            byte valor = (byte) (vira.getSIMBOLO() % 10);
            byte valorCarta;
            Smanilha=(byte) (valor+1);
            for (Carta carta1 : carta) {
                //Sabemos que uma carta é manilha se seu número das unidades for maior que o da vira em 1
                valorCarta = (byte) (carta1.getSIMBOLO() % 10);
                 if (valorCarta == (valor + 1)%10) {
                    /* Obtemos o naipe da carta através da substração de seu valor com o resto de sua divisão por 10.
                    Nota que se a vira for a dama (Simbolo terminado em 9), a manilha é o rei, símbolo terminado em 0,
                    logo, é necessário pegar o resto por 10 duas vezes.
                    */
                    switch (carta1.getSIMBOLO() - valorCarta) {
                        case 10:
                            carta1.setValor(Constantes.VPAUS);
                            
                            break;
                        case 20:
                            carta1.setValor(Constantes.VESPADAS);
                            
                            break;
                        case 30:
                            carta1.setValor(Constantes.VCOPAS);
                            
                            break;
                        case 40:
                            carta1.setValor(Constantes.VOUROS);
                            break;
                    }
                }
            }
    }
    
    
    
    
    
    /**
     * Embaralha o deck, distribui as cartas e define as manilhas (se houverem)
     * @return Cartas - Vetor com 3 cartas para cada jogador e a vira se for truco paulista.
     */
    public Carta[] distribuirMão() {
        deck.embaralhar();
        Carta cartas[]= deck.distribuir(cartasPorRodada);
        cartas = setManilhas(cartas);
        return cartas;
    }

    /**
     * Com 1 ou 2 pontos, o jogo passa a valer 2 a mais. Com 3 ou 4, passa a 
        valer o dobro do anterior. Com 9 ou 10, passa a valer 12. Caso o valor 
        seja diferente, lança exceção.
      
     * @throws RuleException - Caso a pontuação não seja um dos valores pré-definidos
     */
    public void truco() throws RuleException {
        /*
         */
        switch (pontosPorRodada) {
            case 1:

            case 2:
                pontosPorRodada += 2;
                break;

            case 3:

            case 4:
                pontosPorRodada *= 2;
                break;

            case 6:
                pontosPorRodada += 3;
                break;

            case 8:
                pontosPorRodada += 2;
                break;
            case 9:

            case 10:
                pontosPorRodada = 12;
                break;

            default:
                throw new RuleException(TipoErro.PONTOS);
        }
    }

    /**
     * Confiro o vetor do baralho. Se a carta possui valor de manilha (11,12,13 ou 14) 
     * ela volta a ter o valor original determinado pela variável Smanilha
     * @param cartas Baralho de onde as manilhas serão retiradas
     * 
     */
    private void resetManilhas(Carta[] cartas) {
        //
        Carta[] carta = cartas;
        for (byte i=0;i<cartas.length;i++){
            switch(carta[i].getValor()-10){
                case 1:
                case 2:
                case 3:
                case 4:
                carta[i].setValor(Smanilha);
            }
        }
    }

    
    public Carta getVira() {
        return vira;
    }

    public void setVira(Carta vira) {
        this.vira = vira;
    }


    public byte getCartasPorRodada() {
        return cartasPorRodada;
    }

    public void setCartasPorRodada(byte cartasPorRodada) {
        this.cartasPorRodada = cartasPorRodada;
    }

    public byte getPontosPorRodada() {
        return pontosPorRodada;
    }

    public void setPontosPorRodada(byte pontosPorRodada) {
        this.pontosPorRodada = pontosPorRodada;
    }

    public byte getJogadores() {
        return jogadores;
    }

    public byte getTipo() {
        return tipo;
    }

    public Baralho getDeck() {
        return deck;
    }

    public byte getSmanilha() {
        return Smanilha;
    }

    public void setSmanilha(byte Smanilha) {
        this.Smanilha = Smanilha;
    }

}
