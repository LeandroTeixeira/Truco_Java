/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package truco.jogo;

import java.util.Scanner;
import truco.baralho.Carta;
import truco.regras.RuleException;
import truco.util.Constantes;

/**
 *
 * @author Leandro Teixeira <https://leandroteixeira.github.io/home>
 */
public class Main {

    /*
    Bugs conhecidos:
    1 - Dois jogadores do mesmo time podem pedir truco de maneira consecutiva
     */
 /*
    Coisas a serem implementadas:
    1 - Possibilidade de recusar truco
    2 - Mão de 10 funcional
    
    3 - Inteligência artificial básica (joga as cartas na ordem que foram dadas)
    4 - Interface Gráfica simples
    5 - Salvamento de dados em JDBC.
    
    6 - Inteligência artificial complexa, usando-se dos dados salvos.
    7 - Interface gráfica complexa e refinada.
    
    8 - Explicação via label de como foi programado, para fins de aprendizado.
        ---Se der tempo---
        9 - Dificuldade de IAs.
        10 - Afinidade entre jogadores.
        11 - Possibilidade de jogar com apenas dois jogadores
        12 - Mudança do estilo das cartas/fundo/tabuleiro.
     */
    //Converte o vetor de cartas na mão em uma única string
    public static String cartinhas(Carta[] carta) {
        String aux = "";
        for (Carta carta1 : carta) {
            aux += carta1.getIMAGEM() + "\t" + "\n";
        }
        return aux;
    }

    public static void jogo(Jogo game, int atual, int cont, Scanner scan, Carta[][] mão) throws RuleException, GameException {
        int index;
        /* Índice que determina qual carta da mão será jogada. 
        Ele recebe o valor digitado pelo jogador -1*/

        /*Exibe a mão e as opções ao jogador. Se for a primeira rodada,
        a opção mandar de coberta não aparece */
        System.out.println("Jogador " + (atual + 1) + ":\n" + cartinhas(mão[atual]));
        if (cont == 0) {
            System.out.println("Qual carta deseja jogar? (aperte 4 para trucar) \n");
        } else {
            System.out.println("Qual carta deseja jogar? (aperte 4 para trucar e 5 para jogar de coberta) \n");
        }

        index = scan.nextInt() - 1;

        /*Se o jogador digita 4, isso significa que ele pediu truco. Nesse caso,
        a opção de jogar a carta reaparece, dessa vez sem a opção de trucar*/
        if (index == 3) {
            if (!(atual % 2 == game.getTruco())) {
                if (atual == 0) {
                    System.out.println("Jogador " + 4 + " deseja aceitar o truco? (1 para aceitar, 5 para recusar)");
                } else {
                    System.out.println("Jogador " + atual + " deseja aceitar o truco? (1 para aceitar, 5 para recusar)");
                }
                index = scan.nextInt();
                if (index == 5) {
                    game.setCorreu(atual);
                }
                if (index == 1) {
                    game.truco(atual % 2);
                    System.out.println("Jogador " + (atual + 1) + "\n" + cartinhas(mão[atual]));
                    if (cont == 0) {
                        System.out.println("Qual carta deseja jogar? \n");
                    } else {
                        System.out.println("Qual carta deseja jogar? (aperte 5 para jogar de coberta) \n");
                    }
                    index = scan.nextInt() - 1;
                }
            }
        }
        //     System.out.println(atual +"\t" + index);
        if (index >= 0 && index < 3) {
            game.setCartaJogada(atual, mão[atual][index]);
        }
        /*Se o jogador digita 5 numa situação em que é possível jogar de coberta, 
        ele recebe a opção de qual carta jogar. Independente da escolhida, 
        ela terá o valor 0*/
        if (index == 4 && cont > 0) {
            System.out.println("Jogador " + (atual + 1) + "\n" + cartinhas(mão[atual]));
            if (cont == 0) {
                System.out.println("Qual carta deseja jogar de coberta? \n");
            } else {
                System.out.println("Qual carta deseja jogar de coberta? \n");
            }
            index = scan.nextInt() - 1;
            game.setCartaCoberta(atual, mão[atual][index]);
        }

    }

    /**
     * @param args the command line arguments
     * @throws truco.regras.RuleException
     */
    public static void main(String[] args) throws RuleException{
        byte []dif ={1,2,1};
        Jogo game = new Jogo(Constantes.PAULISTA,(byte)4,dif);
        Carta[][] mão = new Carta[4][];
        Scanner scan = new Scanner(System.in);
        int sair = 1;
        while (sair != 0) {
            int atual;
            /*
            A diferença entre atual e que jogador determina qual posição do 
            vetor está sendo acessada enquanto atual determina qual é o jogador 
            nessa posição.
             */
            int cont = 0; // determina qual é a rodada jogada. Se for a primeira, cartas de coberta não podem ser jogadas.
            if (game.getRegras().getTipo() == Constantes.PAULISTA) 
                System.out.println("A vira é: " + game.getVira().getIMAGEM());
            
            while (true) {
                //Vetor mão recebe a mão que o jogo distribuiu aos jogadores
                for (int i = 0; i < game.getPlayers().length; i++) {
                    mão[i] = game.getMão(i);
                }

                //Os 4 revezam-se nos turnos
                for (int jogador = 0; jogador < 4; jogador++) {
                    atual = Integer.parseInt(String.valueOf(game.getOrdem().charAt(jogador)));
                    jogo(game, atual, cont, scan, mão);
                    if (game.isTruco()) {
                        break;
                    }
                }

                cont++;
                if (game.determinarVencedor()) {
                    if (game.getVencedor()[0].getCartaJogada() == null) {
                        System.out.println("Jogador " + (game.getCorreu() + 1) + " venceu a rodada.");
                    } else {
                        for (int i = 0; i < game.getVencedor().length; i++) {
                            System.out.println("carta " + game.getVencedor()[i].getCartaJogada().getIMAGEM() + " do jogador " + (game.getVencedor()[i].getIndice() + 1) + " venceu");
                        }
                    }
                    System.out.println("Time 1 (1-3): " + game.getPlayer(0).getPontos() + "\tTime 2 (2-4): " + game.getPlayer(1).getPontos());
                    System.out.println("\nDigite 0 para sair: ");
                    sair = scan.nextInt();
                    System.out.println("\n");
                    break;
                }
                if (game.getVencedor()[0].getCartaJogada() == null) {
                    System.out.println("Jogador " + (game.getCorreu() + 1) + " venceu a rodada.");
                } else {
                    for (int i = 0; i < game.getVencedor().length; i++) {
                        System.out.println("carta " + game.getVencedor()[i].getCartaJogada().getIMAGEM() + " do jogador " + (game.getVencedor()[i].getIndice() + 1) + " venceu");
                    }
                }
                System.out.println();
            }
        }
    }

}
