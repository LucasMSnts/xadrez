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


public class Cavalo extends PecaXadrez {
    
    public Cavalo(Cor cor, Tabuleiro tabuleiro) {
        super(cor, tabuleiro);
    }

    @Override
    public String toString() {
        return "C";
    }   

    private boolean podeMover(Posicao pos){
        PecaXadrez p = (PecaXadrez) getTabuleiro().peca(pos);
        return p == null || p.getCor() != getCor();
    }
    
    @Override
    public boolean[][] possivelMovimentos() {
       boolean[][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];
       
       Posicao p = new Posicao(0, 0);
       
       
       p.setValores(posicao.getLinha() - 1, posicao.getColuna() - 2);
       if(getTabuleiro().posicaoExistente(p) && podeMover(p)){
           mat[p.getLinha()][p.getColuna()] = true;
       }
       
       p.setValores(posicao.getLinha() - 2, posicao.getColuna() - 1);
       if(getTabuleiro().posicaoExistente(p) && podeMover(p)){
           mat[p.getLinha()][p.getColuna()] = true;
       }
       
       p.setValores(posicao.getLinha() - 2, posicao.getColuna() + 1);
       if(getTabuleiro().posicaoExistente(p) && podeMover(p)){
           mat[p.getLinha()][p.getColuna()] = true;
       }
       
       p.setValores(posicao.getLinha() - 1, posicao.getColuna() + 2);
       if(getTabuleiro().posicaoExistente(p) && podeMover(p)){
           mat[p.getLinha()][p.getColuna()] = true;
       }
       
       p.setValores(posicao.getLinha() + 1, posicao.getColuna() + 2);
       if(getTabuleiro().posicaoExistente(p) && podeMover(p)){
           mat[p.getLinha()][p.getColuna()] = true;
       }
       
       p.setValores(posicao.getLinha() + 2, posicao.getColuna() + 1);
       if(getTabuleiro().posicaoExistente(p) && podeMover(p)){
           mat[p.getLinha()][p.getColuna()] = true;
       }
       
       p.setValores(posicao.getLinha() + 2, posicao.getColuna() - 1);
       if(getTabuleiro().posicaoExistente(p) && podeMover(p)){
           mat[p.getLinha()][p.getColuna()] = true;
       }
       
       p.setValores(posicao.getLinha() + 1, posicao.getColuna() - 2);
       if(getTabuleiro().posicaoExistente(p) && podeMover(p)){
           mat[p.getLinha()][p.getColuna()] = true;
       }
       
       return mat;
    }
    
}

