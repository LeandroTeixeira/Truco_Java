package truco.jogo;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.logging.Level;
import java.util.logging.Logger;
import truco.baralho.Carta;
import truco.jogador.IA;
import truco.jogador.Jogador;
import truco.regras.RuleException;
import truco.regras.RuleSet;
import truco.util.Constantes;
import truco.util.Constantes.TipoErro;

/**
 *
 * @author PC
 */
//Classe designada para determinar as consequências das ações tomadas na interface gráfica.
public class Jogo {

    private byte[] rodTime;//Rodadas parciais dos times. Vence o primeiro a chegar a dois
    private Jogador player[];
    private Jogador vencedor[]; // vencedores da rodada. No caso de empate, ambos são considerados vencedores
    private final RuleSet regras;
    private Carta cartas[]; //Cartas que serão distribuídas entre os jogadores
    private byte primeiro, empate;//determina o primeiro a jogar
    private byte ativo;//determina o jogador da vez. Usada para alternar entre jogadores humanos e IAs
    private String ordem;

    
    public Jogo(byte tipo, byte jogadores, /*byte IA,*/ byte[] dificuldade) throws RuleException {
        this.ordem = "";
        this.ativo = 0;
        this.primeiro = empate = 0;
        this.rodTime = new byte[]{0, 0};
        this.regras = new RuleSet(tipo, jogadores);
        this.player = new Jogador[regras.getJogadores()];
        this.cartas = new Carta[regras.getCartasPorRodada()];
        inicializar(dificuldade/*, IA*/);
    }

    private void inicializar(byte[] dificuldade/*, byte IA*/) throws RuleException {
        
        if ((dificuldade.length != regras.getJogadores() - 1) /*|| IA != regras.getJogadores() - 1*/) {
            throw new RuleException(TipoErro.JOGADOR);
        }

     /*   
        byte cont;
        for (cont = 0; cont < IA; cont++) {
            player[cont] = new IA(cont, dificuldade[cont]);
        }

        for (; cont < player.length; cont++) {
            player[cont] = new Jogador(cont);
        }*/
     
        player[0]=new Jogador((byte)0);
        for(byte i=1;i<player.length;i++)
            player[i]=new IA(i,dificuldade[i-1]);
     
        //Cada jogador possui um parceiro e dois oponentes, sendo o primeiro o jogador que joga depois e só então o que joga antes
        
        player[0].setParceiro(player[2]);
        player[0].setOponentes(player[1],player[3]);
        player[2].setParceiro(player[0]);
        player[2].setOponentes(player[3],player[1]);
        

        player[1].setParceiro(player[3]);
        player[1].setOponentes(player[2],player[0]);
        player[3].setParceiro(player[1]);
        player[3].setOponentes(player[0],player[2]);
                
        distribuirMão();
        try {
            setOrdem(0);
        } catch (RuleException ex) {
            Logger.getLogger(Jogo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    /*    public void jogar(Acao A){
        
    }
     */
    private void distribuirMão() {
        rodTime[0] = rodTime[1] = 0;
        regras.setPontosPorRodada();
        empate = 0;
        cartas = regras.distribuirMão();

        for (Jogador player1 : player) {
            player1.novaMão();
        }

        for (int i = 0; i < player.length; i++) {
            for (int j = 0; j < 3; j++) {
                player[i].setCarta(j, cartas[i * 3 + j]);
            }
        }
    }

    /**
     * Determina se a rodada determinou o vencedor da mão; true se sim, false se
     * não. 
     * 
     * @return Verdadeiro caso alguém tenha vencido nessa rodada, falso caso contrário
     */
    public boolean determinarVencedor() {
        byte jogada[] = new byte[player.length];
        byte tv = -1;
        byte v = -1, v1 = -1;
        byte maior = -1;
        boolean emp = false;

        for (int i = 0; i < player.length; i++) {
            jogada[i] = player[i].getCartaJogada().getValor();
            if (jogada[i] == maior && (tv != i % 2)) { 
                //Vê se empatou, levando em consideração que só é empate se jogadores de times diferentes jogaram a mesma carta
                emp = true;
                tv = (byte) (i % 2);
                v1 = v;
                v = (byte) i;
            } else if (jogada[i] > maior) {
                emp = false;
                maior = jogada[i];
                tv = (byte) (i % 2);
                v = (byte) i;
            }
        }

        //determina se houve empate
        if (emp) {
            try {
                empate(v, v1);
                return false;
            } catch (RuleException ex) {
                Logger.getLogger(Jogo.class.getName()).log(Level.SEVERE, null, ex);
            }
            return false;
        } else {
            try {
                vencedor(tv, v);
            } catch (RuleException ex) {
                Logger.getLogger(Jogo.class.getName()).log(Level.SEVERE, null, ex);
            }
            return true;
        }
    }

    /**
     * Aumenta as rodadas vencidas pelo time; se for igual a dois, o time ganhou
     * uma mão e uma nova é distribuída
     *
     * @param Tvencedor
     * @param vencedor
     * @throws RuleException
     */
    protected void vencedor(byte Tvencedor, byte vencedor) throws RuleException {
        ++rodTime[Tvencedor];
        setVencedor(player[vencedor]);
        if (rodTime[Tvencedor] == 2) {
            //**rodTime[0] = rodTime[1] = 0;
            pontuar(Tvencedor, regras.getPontosPorRodada());

            if (++primeiro == 4) {
                primeiro = 0;
            }
            setOrdem(primeiro);
            distribuirMão();
        }
        setOrdem(vencedor);
    }

    /**
     *
     * @param v1
     * @param v2
     * @return
     * @throws RuleException
     * @throws GameException
     */
    private void empate(int v1, int v2) throws RuleException {

        setVencedor(player[v1], player[v2]);//Empate implica em duas cartas vencedoras
        ++rodTime[0];
        ++rodTime[1];
        ++empate;

        if (rodTime[0] != rodTime[1]) {//Se o número de rodadas for diferente 
            //mesmo após o acréscimo, isso implica em vitória do time com mais pontos
            //   rodTime[0] = rodTime[1] = 0;
            if (rodTime[0] > rodTime[1]) {
                pontuar((byte) 0, regras.getPontosPorRodada());
            } else {
                pontuar((byte) 1, regras.getPontosPorRodada());
            }
            if (++primeiro == 4) {
                primeiro = 0;
            }
            setOrdem(primeiro);
            distribuirMão();
            //return false;

        } else if (rodTime[0] == 3 || (rodTime[0] == 2 && empate == 1)) {
            //  rodTime[0] = rodTime[1] = 0;
            pontuar((byte) 0, regras.getPontosPorRodada());
            pontuar((byte) 1, regras.getPontosPorRodada());
            if (++primeiro == 4) {
                primeiro = 0;
            }
            setOrdem(primeiro);
            distribuirMão();
        }
        //Quando empata, quem volta é o último que empatou
        if (v1 == v2) {
            throw new RuleException(TipoErro.EMPATE);
        }

        if (v1 > v2) {
            setOrdem(v1);
        } else {
            setOrdem(v2);
        }
    }

    private void pontuar(byte time, int ponto) {
        player[time].addPontos(ponto);

        if (regras.getJogadores() > 2) {
            player[time + 2].addPontos(ponto);
            if (player[time + 2].getPontos() >= 12) {
                player[time + 2].setPontos((byte) 0);
            }
        }
        if (player[time].getPontos() >= 12) {
            player[time].setPontos((byte) 0);
        }

    }

    /**
     * Função usada para coordenar as atividades das inteligências artificiais.
     * Elas irão jogar sucessivamente até encontrar um jogador humano (que, como
     * definido anteriormente, é o último a ter seu índice instanciado, logo,
     * sempre terá índice igual ao número de jogadores menos um) ou chegar ao
     * fim do vetor.
     *
     * @throws truco.regras.RuleException
     */
    public void jogadaIA() throws RuleException {
        while (ativo < player.length && ordem.charAt(ativo) != regras.getJogadores() - 1) {
            byte acao;
            acao = player[ativo].acao(regras.getPontosPorRodada(), rodTime);

            switch (acao) {
                case Constantes.PRIMEIRA:
                    setCartaJogada(ativo, player[ativo].getMaisFraca());
                    break;
                case Constantes.SEGUNDA:
                    setCartaJogada(ativo, player[ativo].getIntermediaria());
                    break;
                case Constantes.TERCEIRA:
                    setCartaJogada(ativo, player[ativo].getMaisForte());
                    break;
                case Constantes.COBERTA:
                    setCartaCoberta(ativo,player[ativo].getMaisFraca());
                    break;
                default:
                    throw new RuleException(TipoErro.INTELIGENCIA);
            }
        }

    }

    /**
     * Define a pontuação dos jogadores como 0
     */
    public void resetarPontos() {
        for (byte i = 0; i < regras.getJogadores(); i++) {
            player[i].setPontos((byte) 0);
        }
    }

    public void truco() {
        try {
            this.regras.truco();
        } catch (RuleException ex) {
            Logger.getLogger(Jogo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * define a ordem de jogo. Ela é crescente e começa pelo vencedor da rodada
     * anterior
     *
     * @param prim
     * @throws truco.regras.RuleException
     */
    public void setOrdem(int prim) throws RuleException {
        if (prim > 3) {
            throw new RuleException(TipoErro.ORDEM);
        }
        ativo = 0;
        ordem = "";
        int atual = prim;
        for (Jogador player1 : player) {
            if (atual == player.length) {
                atual = 0;
            }
            ordem += atual++;
        }
    }

    /**
     * Quando seta a carta jogada, remove-a imediatamente, a não ser que ela
     * seja vazia.
     *
     * Se o jogador foi o último a jogar, compara as cartas jogadas para determinar o vencedor
     * @param jogador - Índice do jogador
     * @param cartas - Carta que será jogada
     */
    public void setCartaJogada(int jogador, Carta cartas) {
        player[jogador].setCartaJogada(cartas);
        /*    if (cartas == null) {
        } else {*/
        player[jogador].removeCartaJogada();
        //   }
        ++ativo;
        if (ativo == regras.getJogadores()) {
            determinarVencedor();
        }
    }

    /**
     * Joga uma carta de coberta. Jogar de coberta implica em jogá-la virada para baixo. 
     * Cartas de coberta possuem o menor valor possível e são indícios de blefe.
     * 
     * Se o jogador foi o último a jogar, compara as cartas jogadas para determinar o vencedor
     * @param jogador - Índice do jogador
     * @param cartas - Carta que será jogada de coberta.
     */
    public void setCartaCoberta(int jogador, Carta cartas) {
        cartas.setValor((byte) 0);
        player[jogador].setCartaJogada(cartas);
        player[jogador].removeCartaJogada();
        ++ativo;
        if (ativo == regras.getJogadores()) {
            determinarVencedor();
        }
    }

   public Carta[] getCartasJogadas() {
        Carta[] cards = new Carta[player.length];
        for (int i = 0; i < cards.length; i++) {
            cards[i] = player[i].getCartaJogada();
        }
        return cards;
    }
   
    public Carta getCartaJogada(int jogador) {return player[jogador].getCartaJogada();}
    
    public void setVencedor(Jogador... vencedor) {this.vencedor = vencedor;}

    public Carta[] getMão(int jogador) {return player[jogador].getMão();}

    public void setMão(int jogador, Carta[] cartas) {this.player[jogador].setMão(cartas);}

    public void setCartas(Carta[] cartas) {this.cartas = cartas;}

    public Carta getVira() {return regras.getVira();}

    public int getPrimeiro() {return primeiro;}

    public String getOrdem() {return ordem;}

    public int getEmpate() {return empate;}

    public void setEmpate(byte empate) {this.empate = empate;}

    public Jogador[] getVencedor() {return this.vencedor;}
    
    public RuleSet getRegras() {return regras;}

    public Jogador[] getPlayers() {return player;}

    public int getPlayersLength() {return player.length;}

    public Jogador getPlayer(int i) {return player[i];}

    public void setPlayer(Jogador[] player) {this.player = player;}

    public Carta[] getCartas() {return cartas;}
}
