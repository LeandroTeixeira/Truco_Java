/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package truco.baralho;

/**
 *
 * @author PC
 */
public class Carta {
    private byte valor; //Valor da carta em comparação às outras. Varia de 0(coberta) a 14 (zap)
    private final byte SIMBOLO; //Constante do símbolo da carta. Ex: Rei de Paus
    private final String IMAGEM;//Imagem exibida na carta.
    private final String ICONE; // Icone localizado no pacote truco.jogo
    
/**
 * 
 * @param valor Valor da carta em comparação às outras. Varia de 0(coberta) a 14 (zap)
 * @param SIMBOLO Constante do símbolo da carta. Ex: Rei de Paus
 * @param ICONE Imagem exibida na carta.
 * @param IMAGEM Icone localizado no pacote truco.jogo
 */
    public Carta(byte valor, byte SIMBOLO, /*String ICONE,*/ String IMAGEM) {
        this.valor = valor;
        this.SIMBOLO = SIMBOLO;
        this.ICONE =SIMBOLO+".png";
        this.IMAGEM=IMAGEM;
    }

    /**
     * 
     * 
     * @return Constante do símbolo da carta Ex: 11 (ás de paus)
     */
    public byte getSIMBOLO() {return SIMBOLO;}

    /**
     * 
     * 
     * @return Nome do ícone do pacote truco.jogo. Ex 11.png
     */
    public String getICONE() {return ICONE;}

    /**
     * 
     * 
     * @return Imagem da carta. Ex: Ás de Pausa
     */
    public String getIMAGEM() {return IMAGEM;}
    
    /**
     * 
     * @return Valor da carta. De 0(coberta) a 14 (Zap)
     */
    public byte getValor() {return valor;}

    /**
     * 
     * @param valor 
     */
    public void setValor(byte valor) {this.valor = valor;}

    @Override    public String toString() {return String.valueOf(valor);}

}
