/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package truco.util;

import truco.baralho.Carta;

/**
 *
 * @author PC
 */
public class Constantes {
    
    public enum TipoErro{JOGADOR((byte)1),
        TIPO((byte)2),PONTOS((byte)3),EMPATE((byte)4),ORDEM((byte)5),INTELIGENCIA((byte)6);
    
        byte codErro;
    
        TipoErro(byte codErro)  {this.codErro=codErro;}
    
        @Override
        public String toString()    {return String.valueOf(codErro);}
    };
    
        
    public static final byte AS=1;
    public static final byte DOIS=2;
    public static final byte TRES=3;
    public static final byte QUATRO=4;
    public static final byte CINCO=5;
    public static final byte SEIS=6;
    public static final byte SETE=7;
    public static final byte DAMA=8;
    public static final byte VALETE=9;
    public static final byte REI=0;
    
    public static final byte PAUS =10;
    public static final byte ESPADAS=20;
    public static final byte COPAS=30;
    public static final byte OUROS=40;
    
    //Valores das cartas para serem usados pela IA 
    //e pelo sistema do jogo para definir os vencedores de cada rodada
    public static final byte VCOBERTA=0;
    public static final byte VQUATRO=1;
    public static final byte VCINCO=2;
    public static final byte VSEIS=3;
    public static final byte VSETE=4;
    public static final byte VDAMA=5;
    public static final byte VVALETE=6;
    public static final byte VREI=7;
    public static final byte VAS=8;
    public static final byte VDOIS=9;
    public static final byte VTRES=10;
    public static final byte VOUROS=11;
    public static final byte VESPADAS=12;
    public static final byte VCOPAS=13;
    public static final byte VPAUS=14;
    
    //Constantes para identificar o tipo de partida a ser jogada
    public static final byte MINEIRO=2;
    public static final byte GOIANO=1;
    public static final byte PAULISTA=0;
    
    //Constantes para delimitar o número de IAs
    public static final byte IA=1;
    public static final byte HUMANO=0;
    
    //Constantes que delimitam os tipos de exceções

    
/*    public static final byte JOGADOR=0;
    public static final byte TIPO=1;
    public static final byte PONTOS=2;
    public static final byte EMPATE=3;
    public static final byte ORDEM=4;
    public static final byte INTELIGENCIA=5;
    */
    //Constantes para delimitar as jogadas possíveis para a IA. 
    //Lembrando que o vetor de cartas está em ordem crescente, logo, jogar a primeira significa jogar a mais fraca e assim por diante.
    public static final byte PRIMEIRA=0;
    public static final byte SEGUNDA=1;
    public static final byte TERCEIRA=2;
    public static final byte COBERTA=-1;
    
    
    
    
    public enum Ordenacao{
        
    }
    
    public static Carta[] append(Carta[]array,Carta elemento){
        Carta[] vetor=new Carta[array.length+1];
        System.arraycopy(array, 0, vetor, 0, array.length);
        vetor[vetor.length-1]=elemento;
        return vetor;
    }
    
    
    /**
     * Função estática que recebe um vetor e devolve outro sem os valores nulos.
     * @param array
     * @return 
     */
    public static Carta[] removeNull(Carta[]array){
        byte nill=0;
        
        for(byte i=0;i<array.length;i++)
            if(array[i] == null)
                ++nill;
        Carta[] notNull= new Carta[array.length-nill];
        
        for(byte i=0,j=0;i<array.length&&j<notNull.length;i++,j++){
            if(array[i]==null)
                i++;
            notNull[j]=array[i];
        }
        return notNull;
    }

    
}
