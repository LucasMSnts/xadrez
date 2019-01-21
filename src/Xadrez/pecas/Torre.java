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

public class Torre extends PecaXadrez{
    
    public Torre(Cor cor, Tabuleiro tabuleiro) {
        super(cor, tabuleiro);
    }

    @Override
    public String toString() {
        return "T";
    }
    
    @Override
    public boolean[][] possivelMovimentos() {
       boolean[][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];
       
       Posicao p = new Posicao(0, 0);
       
       //cima
       p.setValores(posicao.getLinha() - 1, posicao.getColuna());
       while(getTabuleiro().posicaoExistente(p) && !getTabuleiro().temPeca(p)){
           mat[p.getLinha()][p.getColuna()] = true;
           p.setLinha(p.getLinha() - 1);
       }
       if(getTabuleiro().posicaoExistente(p) && temPecaAdversaria(p)){
           mat[p.getLinha()][p.getColuna()] = true;
       }
       
       //esquerda
       p.setValores(posicao.getLinha(), posicao.getColuna() - 1);
       while(getTabuleiro().posicaoExistente(p) && !getTabuleiro().temPeca(p)){
           mat[p.getLinha()][p.getColuna()] = true;
           p.setColuna(p.getColuna() - 1);
       }
       if(getTabuleiro().posicaoExistente(p) && temPecaAdversaria(p)){
           mat[p.getLinha()][p.getColuna()] = true;
       }
       
       //direita
       p.setValores(posicao.getLinha(), posicao.getColuna() + 1);
       while(getTabuleiro().posicaoExistente(p) && !getTabuleiro().temPeca(p)){
           mat[p.getLinha()][p.getColuna()] = true;
           p.setColuna(p.getColuna() + 1);
       }
       if(getTabuleiro().posicaoExistente(p) && temPecaAdversaria(p)){
           mat[p.getLinha()][p.getColuna()] = true;
       }
       
       //baixo
       p.setValores(posicao.getLinha() + 1, posicao.getColuna());
       while(getTabuleiro().posicaoExistente(p) && !getTabuleiro().temPeca(p)){
           mat[p.getLinha()][p.getColuna()] = true;
           p.setLinha(p.getLinha() + 1);
       }
       if(getTabuleiro().posicaoExistente(p) && temPecaAdversaria(p)){
           mat[p.getLinha()][p.getColuna()] = true;
       }
       
       
       
       return mat;
    }    
}
