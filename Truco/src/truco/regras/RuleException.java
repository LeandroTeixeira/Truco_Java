/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package truco.regras;

import truco.util.Constantes.TipoErro;


/**
 *
 * @author PC
 * Classe que cuida das exceções das regras (por exemplo, uma mão ter mais do que 3 cartas)
 */
public class RuleException extends Exception {
    /**
     * Creates a new instance of <code>RuleException</code> without detail
     * message.
     */
    private static String um="Erro " + TipoErro.JOGADOR + " : Há algo errado com o número de jogadores. Certifique-se de que o jogo está definido para 2(dois) ou 4(quatro) "
            + "jogadores e que o número de jogadores humanos somados aos artificiais totalizem esse mesmo número.";
    private static String dois="Erro " + TipoErro.TIPO + " : Tipo inválido de jogo. Tenha certeza que o jogo está configurado corretamente para mineiro, paulista ou goiano.";
    private static String três="Erro " + TipoErro.PONTOS + " : Há algo de errado com a pontuação. Certifique-se de que a pontuação em determinado momento não exceda 10";
    private static String quatro="Erro " + TipoErro.EMPATE + " : Empate entre dois jogadores do mesmo time. Potencial erro de verificação de vencedor";
    private static String cinco="Erro " + TipoErro.ORDEM + " : Ordem inválida. Potencial erro na hora de definir as ordens posteriores de jogo";
    private static String seis="Erro " + TipoErro.INTELIGENCIA + ": Jogada inválida da inteligência artificial. Verifique se o algoritmo de decisão "
            + "culmina necessariamente em uma carta da mão ser jogada";
    public RuleException() {
    }

    /**
     * Constructs an instance of <code>RuleException</code> with the specified
     * detail message.
     *
     * @param msg the detail message.
     */
    
    public RuleException(String msg) {
        super(msg);
    }
    /**
     * 
     * @param type Tipo do erro, possui a seguinte correspondência:
     * 1 - Número inválido de jogadores - Deve ser sempre 2 ou 4
     */
    public RuleException(TipoErro type){
        super(getRule(type));
    }
    /**
     * Devolve o que causa o erro em questão. Se o erro não estiver listado, 
     * retorna todos os erros ao invés
     * @param type - Código do erro que deseja retornar
     * @return Erro selecionado ou todos erros caso este não esteja listado
     */
    public static String getRule (TipoErro type){
        switch (type){
            case JOGADOR:
                return um;
            case TIPO:
                return dois;
            case PONTOS:
                return três;
            case EMPATE:
                return quatro;
            case ORDEM:
                return cinco;
            case INTELIGENCIA:
                return seis;
            default:
                return getAllRules();
        }
    }
    
    /**
     * @return Lista com todas as regras intrínsecas ao jogo.
     */
    public static String getAllRules(){
        return um+"\n"+dois+"\n"+três+"\n"+quatro+"\n"+cinco+"\n"+seis;
    }
}

/*
1 Suco de maracujá Tang
1 Creme de leite
1 Iogurte natural
Bater no liquidificador
*/