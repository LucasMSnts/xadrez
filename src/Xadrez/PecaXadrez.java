/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Xadrez;

import jogoTabuleiro.Peca;
import jogoTabuleiro.Tabuleiro;
import jogoTabuleiro.Posicao;

public abstract class PecaXadrez extends Peca{
    private Cor cor;

    public PecaXadrez(Cor cor, Tabuleiro tabuleiro) {
        super(tabuleiro);
        this.cor = cor;
    }

    public Cor getCor() {
        return cor;
    }  
    
    protected boolean temPecaAdversaria(Posicao pos){
       PecaXadrez p = (PecaXadrez) getTabuleiro().peca(pos);
       return p != null && p.getCor() != cor;
    }
}
