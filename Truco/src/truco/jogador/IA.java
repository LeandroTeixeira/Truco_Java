/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package truco.jogador;

import truco.baralho.Carta;
/**
 *
 * @author Leandro Teixeira <https://leandroteixeira.github.io/home>
 */
public class IA extends Jogador{
/* - Dificuldade será determinada pelo número de variáveis que serão levadas
     em consideração na hora de tomar a decisão
*/
    private final byte dificuldade;
    public IA(byte indice, byte dificuldade) {
        super(indice);
        this.dificuldade=dificuldade;
    }
    
    
    /**
     * Recebe uma mão e a ordena de modo crescente para que a primeira carta seja sempre a mais fraca das restantes
     * @param mão - Mão do jogador 
     */
    @Override
    public void setMão(Carta[] mão) {
        this.mão = mão;
      //  ordenarMão();
    }

    /**
     * Obtém a carta mais fraca que estará sempre na primeira posição do vetor. 
     * 
     * @return Carta mais fraca.Nota-se que, quando as cartas restantes são 
     * idênticas ou só existe uma no vetor, essa função retornará o mesmo que a getMaisForte()
     */
    @Override
    public Carta getMaisFraca() {return mão[0];}

    /**
     * Devolve a carta mais forte que está na última posição do vetor. 
     *      * @return 
     * @return 
     */
    @Override
    public Carta getMaisForte() {return mão[mão.length-1];}
    /**
     * Retorna a carta intermediária na mão
     * @return carta na segunda posição do vetor. Em alguns contextos retornará o mesmo que getMaisForte(), outros, retornará null
     */
    @Override
    public Carta getIntermediaria() {
        if(mão.length>1)
            return mão[1];
        else
            return mão[0];
    }

    //private void ordenarMão() {mão=(Carta[]) Sorting.InsertionSort(mão);}
    
}
