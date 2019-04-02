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


public class Rei extends PecaXadrez {
    
    private PartidaXadrez partidaXadrez;
    
    public Rei(Cor cor, Tabuleiro tabuleiro, PartidaXadrez partidaXadrez) {
        super(cor, tabuleiro);
        this.partidaXadrez = partidaXadrez;
    }

    @Override
    public String toString() {
        return "K";
    }   

    private boolean podeMover(Posicao pos){
        PecaXadrez p = (PecaXadrez) getTabuleiro().peca(pos);
        return p == null || p.getCor() != getCor();
    }
    
    private boolean testeTorreRoque(Posicao pos) {
        PecaXadrez p = (PecaXadrez)getTabuleiro().peca(pos);
        return p != null && p instanceof Torre && p.getCor() == getCor() && p.getMovimentoConta() == 0;
    }
    
    @Override
    public boolean[][] possivelMovimentos() {
       boolean[][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];
       
       Posicao p = new Posicao(0, 0);
       
       //cima
       p.setValores(posicao.getLinha() - 1, posicao.getColuna());
       if(getTabuleiro().posicaoExistente(p) && podeMover(p)){
           mat[p.getLinha()][p.getColuna()] = true;
       }
       
       //abaixo
       p.setValores(posicao.getLinha() + 1, posicao.getColuna());
       if(getTabuleiro().posicaoExistente(p) && podeMover(p)){
           mat[p.getLinha()][p.getColuna()] = true;
       }
       
       //esquerda
       p.setValores(posicao.getLinha(), posicao.getColuna() - 1);
       if(getTabuleiro().posicaoExistente(p) && podeMover(p)){
           mat[p.getLinha()][p.getColuna()] = true;
       }
       
       //direita
       p.setValores(posicao.getLinha(), posicao.getColuna() + 1);
       if(getTabuleiro().posicaoExistente(p) && podeMover(p)){
           mat[p.getLinha()][p.getColuna()] = true;
       }
       
       //noroeste 
       p.setValores(posicao.getLinha() - 1, posicao.getColuna() - 1);
       if(getTabuleiro().posicaoExistente(p) && podeMover(p)){
           mat[p.getLinha()][p.getColuna()] = true;
       }
       
       //nordeste
       p.setValores(posicao.getLinha() - 1, posicao.getColuna() + 1);
       if(getTabuleiro().posicaoExistente(p) && podeMover(p)){
           mat[p.getLinha()][p.getColuna()] = true;
       }
       
       //sudoeste 
       p.setValores(posicao.getLinha() + 1, posicao.getColuna() - 1);
       if(getTabuleiro().posicaoExistente(p) && podeMover(p)){
           mat[p.getLinha()][p.getColuna()] = true;
       }
       
       //sudeste 
       p.setValores(posicao.getLinha() + 1, posicao.getColuna() + 1);
       if(getTabuleiro().posicaoExistente(p) && podeMover(p)){
           mat[p.getLinha()][p.getColuna()] = true;
       }
       
       // MOVIMENTO ESPECIAL - ROQUE
       if (getMovimentoConta() == 0 && !partidaXadrez.getXeque()){
           // MOVIMENTO ESPECIAL - ROQUE PEQUENO (VERIFICANDO a TORRE no LADO do REI)  
           Posicao posT1 = new Posicao(posicao.getLinha(), posicao.getColuna() + 3);
           if (testeTorreRoque(posT1)){
               Posicao p1 = new Posicao(posicao.getLinha(), posicao.getColuna() + 1);
               Posicao p2 = new Posicao(posicao.getLinha(), posicao.getColuna() + 2);
               if (getTabuleiro().peca(p1) == null && getTabuleiro().peca(p2) == null) {
                   mat[posicao.getLinha()][posicao.getColuna() + 2] = true;
               }
           }
           // MOVIMENTO ESPECIAL - ROQUE GRANDE (VERIFICANDO a TORRE no LADO da RAINHA)  
           Posicao posT2 = new Posicao(posicao.getLinha(), posicao.getColuna() - 4);
           if (testeTorreRoque(posT2)){
               Posicao p1 = new Posicao(posicao.getLinha(), posicao.getColuna() - 1);
               Posicao p2 = new Posicao(posicao.getLinha(), posicao.getColuna() - 2);
               Posicao p3 = new Posicao(posicao.getLinha(), posicao.getColuna() - 3);
               if (getTabuleiro().peca(p1) == null && getTabuleiro().peca(p2) == null && getTabuleiro().peca(p3) == null) {
                   mat[posicao.getLinha()][posicao.getColuna() - 2] = true;
               }
           }
       }
       
       return mat;
    }
    
}
