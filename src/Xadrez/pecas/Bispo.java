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

public class Bispo extends PecaXadrez{
    
    public Bispo(Cor cor, Tabuleiro tabuleiro) {
        super(cor, tabuleiro);
    }

    @Override
    public String toString() {
        return "B";
    }
    
    @Override
    public boolean[][] possivelMovimentos() {
       boolean[][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];
       
       Posicao p = new Posicao(0, 0);
       
       //Noroeste
       p.setValores(posicao.getLinha() - 1, posicao.getColuna() - 1);
       while(getTabuleiro().posicaoExistente(p) && !getTabuleiro().temPeca(p)){
           mat[p.getLinha()][p.getColuna()] = true;
           p.setValores(p.getLinha() - 1, p.getColuna() - 1);
       }
       if(getTabuleiro().posicaoExistente(p) && temPecaAdversaria(p)){
           mat[p.getLinha()][p.getColuna()] = true;
       }
       
       //nordeste
       p.setValores(posicao.getLinha() - 1, posicao.getColuna() + 1);
       while(getTabuleiro().posicaoExistente(p) && !getTabuleiro().temPeca(p)){
           mat[p.getLinha()][p.getColuna()] = true;
           p.setValores(p.getLinha() - 1, p.getColuna() + 1);
       }
       if(getTabuleiro().posicaoExistente(p) && temPecaAdversaria(p)){
           mat[p.getLinha()][p.getColuna()] = true;
       }
       
       //sudeste
       p.setValores(posicao.getLinha() + 1, posicao.getColuna() + 1);
       while(getTabuleiro().posicaoExistente(p) && !getTabuleiro().temPeca(p)){
           mat[p.getLinha()][p.getColuna()] = true;
           p.setValores(p.getLinha() + 1, p.getColuna() + 1);
       }
       if(getTabuleiro().posicaoExistente(p) && temPecaAdversaria(p)){
           mat[p.getLinha()][p.getColuna()] = true;
       }
       
       //sudoeste
       p.setValores(posicao.getLinha() + 1, posicao.getColuna() - 1);
       while(getTabuleiro().posicaoExistente(p) && !getTabuleiro().temPeca(p)){
           mat[p.getLinha()][p.getColuna()] = true;
           p.setValores(p.getLinha() + 1, p.getColuna() - 1);
       }
       if(getTabuleiro().posicaoExistente(p) && temPecaAdversaria(p)){
           mat[p.getLinha()][p.getColuna()] = true;
       }
       
       return mat;
    }    
}
