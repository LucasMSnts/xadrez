/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Xadrez;

import jogoTabuleiro.Posicao;


public class xadrezPosicao {
    private char coluna;
    private int linha;

    public xadrezPosicao(char coluna, int linha) {
        if( coluna < 'a' || coluna > 'h' || linha < 1 || linha > 8){
            throw new xadrezException("Erro na instanciacao da posicao do xadrez. Os valores validos sao de a1 ate h8!");
        }
        this.coluna = coluna;
        this.linha = linha;
    } 

    public char getColuna() {
        return coluna;
    }

    public int getLinha() {
        return linha;
    }
    
    protected Posicao toPosicao(){
        return new Posicao(8 - linha, coluna - 'a');
    }
    
    protected static xadrezPosicao fromPosicao(Posicao pos){
        return new xadrezPosicao((char)('a' + pos.getColuna()), 8 - pos.getLinha());
    }

    @Override
    public String toString() {
        return "" + coluna + linha;
    }
    
    
}
