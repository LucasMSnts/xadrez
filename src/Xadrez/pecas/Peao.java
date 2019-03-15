/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Xadrez.pecas;

import Xadrez.Cor;
import Xadrez.PecaXadrez;
import jogoTabuleiro.Posicao;
import jogoTabuleiro.Tabuleiro;

public class Peao extends PecaXadrez{

    public Peao(Cor cor, Tabuleiro tabuleiro) {
        super(cor, tabuleiro);
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
       }
       return mat;
    }
    
    @Override
    public String toString() {
        return "P";
    }
    
}
