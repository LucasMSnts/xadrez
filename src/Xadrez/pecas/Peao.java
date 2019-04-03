/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Xadrez.pecas;

import Xadrez.Cor;
import Xadrez.PartidaXadrez;
import Xadrez.PecaXadrez;
import jogoTabuleiro.Posicao;
import jogoTabuleiro.Tabuleiro;

public class Peao extends PecaXadrez{
    
    private PartidaXadrez partidaXadrez;

    public Peao(Cor cor, Tabuleiro tabuleiro, PartidaXadrez partida) {
        super(cor, tabuleiro);
        this.partidaXadrez = partida;
    }

    @Override
    public boolean[][] possivelMovimentos() {
       boolean[][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];
       
       Posicao p = new Posicao(0, 0);
       
       if(getCor() == Cor.BRANCO) {
           p.setValores(posicao.getLinha()- 1, posicao.getColuna());
           if (getTabuleiro().posicaoExistente(p) && !getTabuleiro().temPeca(p)) {
               mat[p.getLinha()][p.getColuna()] = true;
           }
           p.setValores(posicao.getLinha()- 2, posicao.getColuna());
           Posicao p2 = new Posicao(posicao.getLinha()- 1, posicao.getColuna());
           if (getTabuleiro().posicaoExistente(p) && !getTabuleiro().temPeca(p) && 
                   getTabuleiro().posicaoExistente(p2) && !getTabuleiro().temPeca(p2) && getMovimentoConta() == 0) {
               mat[p.getLinha()][p.getColuna()] = true;
           }
           p.setValores(posicao.getLinha()- 1, posicao.getColuna() - 1);
           if (getTabuleiro().posicaoExistente(p) && temPecaAdversaria(p)) {
               mat[p.getLinha()][p.getColuna()] = true;
           }
           p.setValores(posicao.getLinha()- 1, posicao.getColuna() + 1);
           if (getTabuleiro().posicaoExistente(p) && temPecaAdversaria(p)) {
               mat[p.getLinha()][p.getColuna()] = true;
           }
           
           //MOVIMENTO ESPECIAL - EN PASSANT BRANCO
           if (posicao.getLinha() == 3){
               Posicao esquerda = new Posicao(posicao.getLinha(), posicao.getColuna() - 1);
               if (getTabuleiro().posicaoExistente(esquerda) && temPecaAdversaria(esquerda) && getTabuleiro().peca(esquerda) == partidaXadrez.getEnPassantVuneravel()){
                   mat[esquerda.getLinha() - 1][esquerda.getColuna()] = true;
               }
               Posicao direita = new Posicao(posicao.getLinha(), posicao.getColuna() + 1);
               if (getTabuleiro().posicaoExistente(direita) && temPecaAdversaria(direita) && getTabuleiro().peca(direita) == partidaXadrez.getEnPassantVuneravel()){
                   mat[direita.getLinha() - 1][direita.getColuna()] = true;
               }
           }           
       }
       else {
           p.setValores(posicao.getLinha()+ 1, posicao.getColuna());
           if (getTabuleiro().posicaoExistente(p) && !getTabuleiro().temPeca(p)) {
               mat[p.getLinha()][p.getColuna()] = true;
           }
           p.setValores(posicao.getLinha()+ 2, posicao.getColuna());
           Posicao p2 = new Posicao(posicao.getLinha()+ 1, posicao.getColuna());
           if (getTabuleiro().posicaoExistente(p) && !getTabuleiro().temPeca(p) && 
                   getTabuleiro().posicaoExistente(p2) && !getTabuleiro().temPeca(p2) && getMovimentoConta() == 0) {
               mat[p.getLinha()][p.getColuna()] = true;
           }
           p.setValores(posicao.getLinha()+ 1, posicao.getColuna() - 1);
           if (getTabuleiro().posicaoExistente(p) && temPecaAdversaria(p)) {
               mat[p.getLinha()][p.getColuna()] = true;
           }
           p.setValores(posicao.getLinha()+ 1, posicao.getColuna() + 1);
           if (getTabuleiro().posicaoExistente(p) && temPecaAdversaria(p)) {
               mat[p.getLinha()][p.getColuna()] = true;
           }
           
           //MOVIMENTO ESPECIAL - EN PASSANT PRETO
            if (posicao.getLinha() == 4){
               Posicao esquerda = new Posicao(posicao.getLinha(), posicao.getColuna() - 1);
               if (getTabuleiro().posicaoExistente(esquerda) && temPecaAdversaria(esquerda) && getTabuleiro().peca(esquerda) == partidaXadrez.getEnPassantVuneravel()){
                   mat[esquerda.getLinha() + 1][esquerda.getColuna()] = true;
               }
               Posicao direita = new Posicao(posicao.getLinha(), posicao.getColuna() + 1);
               if (getTabuleiro().posicaoExistente(direita) && temPecaAdversaria(direita) && getTabuleiro().peca(direita) == partidaXadrez.getEnPassantVuneravel()){
                   mat[direita.getLinha() + 1][direita.getColuna()] = true;
               }
           }            
       }
       return mat;
    }
    
    @Override
    public String toString() {
        return "P";
    }
    
}
