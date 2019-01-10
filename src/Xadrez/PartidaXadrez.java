/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Xadrez;

import Xadrez.pecas.Rei;
import Xadrez.pecas.Torre;
import jogoTabuleiro.Posicao;
import jogoTabuleiro.Tabuleiro;

public class PartidaXadrez {
    private Tabuleiro tabuleiro;

    public PartidaXadrez() {
        tabuleiro = new Tabuleiro(8, 8);
        iniciarSetup();
    }
    
    public PecaXadrez[][] getPecas() {
        PecaXadrez[][] mat = new PecaXadrez[tabuleiro.getLinhas()][tabuleiro.getColunas()];
        for (int i=0; i < tabuleiro.getLinhas(); i++){
            for (int j=0; j < tabuleiro.getColunas(); j++){
                mat[i][j] = (PecaXadrez) tabuleiro.peca(i, j);
            }
        }
        return mat;
    } 
    
    private void iniciarSetup(){
        tabuleiro.lugarPeca(new Torre(Cor.PRETO, tabuleiro), new Posicao(2, 1));
        tabuleiro.lugarPeca(new Rei(Cor.BRANCO, tabuleiro), new Posicao(7, 4));
        tabuleiro.lugarPeca(new Torre(Cor.BRANCO, tabuleiro), new Posicao(6, 6));
        tabuleiro.lugarPeca(new Rei(Cor.PRETO, tabuleiro), new Posicao(0, 4));
    }
}
