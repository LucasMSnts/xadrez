/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Xadrez;

import Xadrez.pecas.Rei;
import Xadrez.pecas.Torre;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import jogoTabuleiro.Peca;
import jogoTabuleiro.Posicao;
import jogoTabuleiro.Tabuleiro;

public class PartidaXadrez {
    private Tabuleiro tabuleiro;
    private int turno;
    private Cor playerCor;
    private boolean xeque;
    private boolean xequeMate;

    private List<Peca> pecasNoTabuleiro = new ArrayList<>();
    private List<Peca> pecasCapturadas = new ArrayList<>();
    
    public PartidaXadrez() {
        tabuleiro = new Tabuleiro(8, 8);
        turno = 1;
        playerCor = Cor.BRANCO;
        xeque = false;
        iniciarSetup();
    }

    public int getTurno() {
        return turno;
    }

    public Cor getPlayerCor() {
        return playerCor;
    }
    
    public boolean getXeque() {
        return xeque;
    }
    
    public boolean getXequeMate() {
        return xequeMate;
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
        
        if(testeXeque(playerCor)){
            desfazerMovimento(Origem, Destino, capturarPeca);
            throw new xadrezException("Voce nao pode se colocar em xeque!");
        }
        
        xeque = (testeXeque(Oponente(playerCor))) ? true : false;
         
        if(testeXequeMate(Oponente(playerCor))) {
           xequeMate = true;
        } else {
            proximoTurno();
        }
        return (PecaXadrez) capturarPeca;
    }    
    
    private Peca fazerMovimento(Posicao Origem, Posicao Destino){
        Peca p = tabuleiro.removerPeca(Origem);
        Peca capturarPeca = tabuleiro.removerPeca(Destino);
        tabuleiro.lugarPeca(p, Destino);
        
        if (capturarPeca != null){
            pecasNoTabuleiro.remove(capturarPeca);
            pecasCapturadas.add(capturarPeca);
        }
        
        return capturarPeca;
    }
    
    private void desfazerMovimento(Posicao Origem, Posicao Destino, Peca pecaCapturada){
        Peca p = tabuleiro.removerPeca(Destino);
        tabuleiro.lugarPeca(p, Origem);
        
        if (pecaCapturada != null){
            tabuleiro.lugarPeca(pecaCapturada, Destino);
            pecasCapturadas.remove(pecaCapturada);
            pecasNoTabuleiro.add(pecaCapturada);
        }
    }
    
    private void validarOrigemPosicao(Posicao pos){
        if(!tabuleiro.temPeca(pos)){
            throw new xadrezException("Nao existe peca na posicao de origem!");
        }
        if(playerCor != ((PecaXadrez)tabuleiro.peca(pos)).getCor()){
            throw new xadrezException("A peca escolhida nao eh sua!");
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
    
    private void proximoTurno() {
        turno++;
        playerCor = (playerCor == Cor.BRANCO) ? Cor.PRETO : Cor.BRANCO;
    }
    
    private Cor Oponente(Cor c){
        return (c == Cor.BRANCO) ? Cor.PRETO : Cor.BRANCO;
    }
    
    private PecaXadrez Rei(Cor cor){
        List<Peca> list = pecasNoTabuleiro.stream().filter(x -> ((PecaXadrez)x).getCor() == cor).collect(Collectors.toList());
        for (Peca p : list) {
            if (p instanceof Rei){
                return (PecaXadrez)p;
            }
        }
        throw new IllegalStateException("Nao tem o Rei " + cor + " no tabuleiro!");
    }
    
    private boolean testeXeque(Cor cor) {
        Posicao posicaoRei = Rei(cor).getXadrezPosicao().toPosicao();
        List<Peca> pecaOponente = pecasNoTabuleiro.stream().filter(x -> ((PecaXadrez)x).getCor() == Oponente(cor)).collect(Collectors.toList());
        for (Peca p : pecaOponente){
            boolean[][] mat = p.possivelMovimentos();
            if(mat[posicaoRei.getLinha()][posicaoRei.getColuna()]){
                return true;
            }
        }
        return false;
    }
    
     private boolean testeXequeMate(Cor cor) {
         if(!testeXeque(cor)){
             return false;             
         }
         List<Peca> lista = pecasNoTabuleiro.stream().filter(x -> ((PecaXadrez)x).getCor() == cor).collect(Collectors.toList());
         for (Peca p : lista) {
             boolean[][] mat = p.possivelMovimentos();
             for (int i=0; i<tabuleiro.getLinhas(); i++){
                 for (int j=0; j<tabuleiro.getColunas(); j++) {
                     if (mat[i][j]) {
                         Posicao origem = ((PecaXadrez)p).getXadrezPosicao().toPosicao();
                         Posicao destino = new Posicao(i, j);
                         Peca capturadaPeca = fazerMovimento(origem, destino);
                         boolean tstXeque = testeXeque(cor);
                         desfazerMovimento(origem, destino, capturadaPeca);
                         if (!tstXeque) {
                             return false;
                         }
                     }
                 }
             }
         }
         return true;
     }
    
    private void lugarNovoPeca (char coluna, int linha, PecaXadrez peca){
        tabuleiro.lugarPeca(peca, new xadrezPosicao(coluna, linha).toPosicao());
        pecasNoTabuleiro.add(peca);
    }
    
    private void iniciarSetup(){
                
        lugarNovoPeca('h', 7, new Torre(Cor.BRANCO, tabuleiro));
        lugarNovoPeca('d', 1, new Torre(Cor.BRANCO, tabuleiro));
        lugarNovoPeca('e', 1, new Rei(Cor.BRANCO, tabuleiro));

        lugarNovoPeca('b', 8, new Torre(Cor.PRETO, tabuleiro));
        lugarNovoPeca('a', 8, new Rei(Cor.PRETO, tabuleiro));
    }
}
