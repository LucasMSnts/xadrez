/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Xadrez;

import Xadrez.pecas.Rei;
import Xadrez.pecas.Torre;
import jogoTabuleiro.Peca;
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
    
    public boolean[][] movimentosPossiveis(xadrezPosicao xadrexOrigem){
        Posicao pos = xadrexOrigem.toPosicao();
        validarOrigemPosicao(pos);
        return tabuleiro.peca(pos).possivelMovimentos();
    }
    
    public PecaXadrez perfomaceMovimentoXadrez(xadrezPosicao xadrezOrigem, xadrezPosicao xadrezDestino){
        Posicao Origem = xadrezOrigem.toPosicao();
        Posicao Destino = xadrezDestino.toPosicao();
        validarOrigemPosicao(Origem);
        validarDestinoPosicao(Origem, Destino);
        Peca capturarPeca = fazerMovimento(Origem, Destino);
        return (PecaXadrez) capturarPeca;
    }
    
    private Peca fazerMovimento(Posicao Origem, Posicao Destino){
        Peca p = tabuleiro.removerPeca(Origem);
        Peca capturarPeca = tabuleiro.removerPeca(Destino);
        tabuleiro.lugarPeca(p, Destino);
        return capturarPeca;
    }
    
    private void validarOrigemPosicao(Posicao pos){
        if(!tabuleiro.temPeca(pos)){
            throw new xadrezException("Nao existe peca na posicao de origem!");
        }
        if(!tabuleiro.peca(pos).temAlgumMovimentoPossivel()){
            throw new xadrezException("Nao existe movimento possivel nessa peca!");
        }
    }
    
    private void validarDestinoPosicao(Posicao posOri, Posicao posDes){
        if(!tabuleiro.peca(posOri).movimentoPossivel(posDes)){
            throw new xadrezException("A peca escolhida nao pode ser movida ao destino");
        }
    }
    
    private void lugarNovoPeca (char coluna, int linha, PecaXadrez peca){
        tabuleiro.lugarPeca(peca, new xadrezPosicao(coluna, linha).toPosicao());
    }
    
    private void iniciarSetup(){
        /*lugarNovoPeca('b' , 6, new Torre(Cor.PRETO, tabuleiro));
        lugarNovoPeca('e' , 1, new Rei(Cor.BRANCO, tabuleiro));
        lugarNovoPeca('g' , 3,new Torre(Cor.BRANCO, tabuleiro));
        lugarNovoPeca('e' , 8, new Rei(Cor.PRETO, tabuleiro));*/
        
        lugarNovoPeca('c', 1, new Torre(Cor.BRANCO, tabuleiro));
        lugarNovoPeca('c', 2, new Torre(Cor.BRANCO, tabuleiro));
        lugarNovoPeca('d', 2, new Torre(Cor.BRANCO, tabuleiro));
        lugarNovoPeca('e', 2, new Torre(Cor.BRANCO, tabuleiro));
        lugarNovoPeca('e', 1, new Torre(Cor.BRANCO, tabuleiro));
        lugarNovoPeca('d', 1, new Rei(Cor.BRANCO, tabuleiro));

        lugarNovoPeca('c', 7, new Torre(Cor.PRETO, tabuleiro));
        lugarNovoPeca('c', 8, new Torre(Cor.PRETO, tabuleiro));
        lugarNovoPeca('d', 7, new Torre(Cor.PRETO, tabuleiro));
        lugarNovoPeca('e', 7, new Torre(Cor.PRETO, tabuleiro));
        lugarNovoPeca('e', 8, new Torre(Cor.PRETO, tabuleiro));
        lugarNovoPeca('d', 8, new Rei(Cor.PRETO, tabuleiro));
    }
}
