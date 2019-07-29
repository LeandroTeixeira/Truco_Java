/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package truco.jogo;

import java.util.logging.Level;
import java.util.logging.Logger;
import truco.baralho.Carta;
import truco.jogador.IA;
import truco.jogador.Jogador;

import truco.regras.RuleException;
import truco.util.Constantes;
import truco.DataBase.Database;
import truco.DataBase.Perfil;

/**
 *
 * @author PC
 */
public class Mesa{ //Equivalente a um "juiz"
/*
    1 - Resetar a pontuação quando um jogador chegar a 12 pontos
    */
    private Jogo jogo;
    private Perfil ativo;
//  private final Database DB;
    
    public Mesa(){}
    
    
    public Perfil[] listarPerfis(){
       Perfil[] profiles = null;
     //profiles=Database.getPerfis();
        return profiles;
    }
    
    public void carregarPerfil(Perfil profile){
        /*
        if(profile==null)
            this.ativo=Database.carregarVisitante();
        else
        */
            //this.perfil=Database.carregarPerfil(profile);
    }

    public void setJogo(byte tipo, byte jogadores, byte IA, byte[] dificuldade) {
        try {
            this.jogo = new Jogo(tipo,jogadores,dificuldade);
        } catch (RuleException ex) {
            Logger.getLogger(Mesa.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
