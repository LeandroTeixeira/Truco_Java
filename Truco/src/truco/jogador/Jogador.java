/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package truco.jogador;

import truco.baralho.Carta;
import truco.util.Constantes;

/**
 *
 * @author PC
 */
public class Jogador {

    /**
     * Mão do jogador. Subclasses podem reordená-la em ordem crescente ou decrescente de força
     */
    protected Carta[] mão;
    protected Carta[] mãojogada;//Oposto da mão; Cartas jogadas saem da mão e vão para a mão jogada.
    private Carta cartaJogada;
    private byte pontos; // Pontuaçao da partida
    private final byte INDICE;
    
    private Jogador[]oponentes;
    private Jogador parceiro;
    
    //Padrões dos jogadores. Usado pela IA para se adaptar aos adversários
    private PadraoJogador[] padroes;
    
    public Jogador(byte indice /*, PadraoJogador[] padroes*/){
    //    this.padroes=padroes;
        this.INDICE=indice;
        this.pontos=0;
        this.oponentes=new Jogador[2];
        this.mão=new Carta[3];
  //    this.cartaJogada=null;
    }
    
    /**
     * Recebe o estado do jogo e determina sua jogada a partir desses fatores. Usado pela IA mas também presente no jogador, onde sempre retornará uma jogada inválida.
     * 
     * Vale lembrar que esses não são as únicas coisas levadas em consideração ao decidir uma jogada. 
     * A IA (e, por consequência, o jogador) também mantém gravados os padrões de jogada dos outros jogadores.
     * 
     * 
     * @param valorRodada Valor atual da rodada. Possui valores discretos que incluem alguns (mas não todos) os valores de 1 a 12.
     * Usada em conjunto com pontJogo para determinar possíveis resultados de blefe.
     * 
     * @param qtdRestanteOponentes Quantidade restante de cartas nas mãos dos oponentes. 
     * Usada para calcular a probabilidade de eles possuírem algo mais forte
     * 
     * @param pontMao Placar atual da mão; pode ter os valores de 0-0, 1-1, 2-2, 1-0 e 0-1.
     * Usada em conjunto com outros fatores para determinar a melhor carta a ser jogada. 
     * Na dúvida entre uma forte e uma fraca, a tendência é jogar uma fraca quando o jogo está em vantagem e a forte quando o jogo está empatado ou em desvantagem.
     * 
     * @param pontJogo Pontuação atual do jogo. Pode ter todos os valores de 0-0 a 11-11.
     * Usado em conjunto com o valor rodada para determinar os possíveis resultados de blefe.
     * 
     * qtdRestanteAmigos Quantidade restante de cartas na mão dos amigos. É a soma das cartas da própria mão com as da mão do parceiro.
     * Usada em conjunto com os padroes de jogo para determinar a possibilidade de o parceiro ter cartas que possam vir a alterar a melhor jogada.
     * 
     * @return acao, valor inteiro que determina a melhor carta a ser jogada a partir de um vetor ordenado de forma crescente.
     * Na classe Jogador sempre retornará 3 (valor inválido) enquanto na IA retornará a posição correspondente do vetor.
     * 
     */
    public byte acao(byte valorRodada, byte[]pontMao){
        byte amigos=this.getCartasRestantesTime();
        byte adversarios=oponentes[0].getCartasRestantesTime();
        byte[] mao=pontMao;
        byte[] jogo={pontos,oponentes[0].getPontos()};
        byte valor=valorRodada;
        Carta[][]jogadasTime={this.getMãojogada(),parceiro.getMãojogada()};
        Carta[][]jogadasOponente={oponentes[0].getMãojogada(),oponentes[1].getMãojogada()};
        
        
        return 3;
    }
    /**
     * Remove a carta com o mesmo nome da carta jogada. 
     * Nota-se que não pode-se comparar as duas cartas diretamente devido a cartas jogadas de coberta apresentarem valor diferente mesmo sendo iguais.
     */
    
    public void removeCartaJogada() {
        mãojogada=Constantes.append(mãojogada, cartaJogada);
        for(int i=0;i<mão.length;i++)
            if(mão[i].getIMAGEM().equals(cartaJogada.getIMAGEM()))
                mão[i]=null;
        
        mão=Constantes.removeNull(mão);
    }

    public void novaMão(){
        mão=null;
        mão=new Carta[3];
        System.gc();
    }
    
    public void setOponentes(Jogador oponente1, Jogador oponente2) {
        oponentes[0]=oponente1;
        oponentes[1]=oponente2;
    }

    public Carta[] getMãojogada() {return mãojogada;}
        
    public Jogador[] getOponentes() {return oponentes;}

    public void setOponentes(Jogador[] oponentes) {this.oponentes = oponentes;}
    
    public Jogador getParceiro() {return parceiro;}

    public void setParceiro(Jogador parceiro) {this.parceiro = parceiro;}

    public PadraoJogador[] getPadroes() {return padroes;}

    public void setPadroes(PadraoJogador[] padroes) {this.padroes = padroes;}
    
    public Carta[] getMão() {return mão;}

    public void setMão(Carta[] mão) {this.mão = mão;}

    public Carta getCartaJogada() {return cartaJogada;}
    
    public byte getCartasRestantesTime(){return (byte) (this.mão.length+parceiro.getMão().length);}

    public void setCartaJogada(Carta cartaJogada) {this.cartaJogada = cartaJogada;}
    
    public void setCartaJogada(int cartaJogada) {this.cartaJogada = mão[cartaJogada];}
    
    public Carta getCarta(int index){return mão[index];}
    
    public void setCarta(int index, Carta carta){mão[index]=carta;}
    
    public Carta getMaisForte(){return null;}
    
    public Carta getIntermediaria(){return null;}
    
    public Carta getMaisFraca(){return null;}

    public byte getINDICE() {return INDICE;}
    
    public byte getPontos() {return pontos;}

    public void setPontos(byte pontos) {this.pontos = pontos;}
    
    public void addPontos(int pontos) {this.pontos += pontos;}
}
