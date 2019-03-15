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
    private int movimentoConta;

    public PecaXadrez(Cor cor, Tabuleiro tabuleiro) {
        super(tabuleiro);
        this.cor = cor;
    }

    public Cor getCor() {
        return cor;
    } 
    
    public int getMovimentoConta() {
        return movimentoConta;
    }
    
    public void acrescentarMovimentoConta(){
        movimentoConta++;
    }
    
    public void desacrescentarMovimentoConta(){
        movimentoConta--;
    }
    
    public xadrezPosicao getXadrezPosicao(){
        return xadrezPosicao.fromPosicao(posicao);
    }
    
    protected boolean temPecaAdversaria(Posicao pos){
       PecaXadrez p = (PecaXadrez) getTabuleiro().peca(pos);
       return p != null && p.getCor() != cor;
    }
}
