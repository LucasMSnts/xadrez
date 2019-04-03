/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Xadrez;

import Xadrez.pecas.Bispo;
import Xadrez.pecas.Cavalo;
import Xadrez.pecas.Peao;
import Xadrez.pecas.Rainha;
import Xadrez.pecas.Rei;
import Xadrez.pecas.Torre;
import java.security.InvalidParameterException;
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
    private PecaXadrez enPassantVuneravel;
    private PecaXadrez promocao;

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
    
    public PecaXadrez getEnPassantVuneravel() {
        return enPassantVuneravel;
    }
    
    public PecaXadrez getPromocao() {
        return promocao;
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
        
        PecaXadrez moveuPeca = (PecaXadrez)tabuleiro.peca(Destino);
        
        // MOVIMENTO ESPECIAL - PROMOCAO
        promocao = null;
        if (moveuPeca instanceof Peao){
            if ((moveuPeca.getCor() == Cor.BRANCO && Destino.getLinha() == 0) || (moveuPeca.getCor() == Cor.PRETO && Destino.getLinha() == 7)) {
                promocao = (PecaXadrez)tabuleiro.peca(Destino);
                promocao = recolocarPecaPromovida("Q");
            }
        }
        
        
        xeque = (testeXeque(Oponente(playerCor))) ? true : false;
         
        if(testeXequeMate(Oponente(playerCor))) {
           xequeMate = true;
        } else {
            proximoTurno();
        }
        
        // MOVIMENTO ESPECIAL - EN PASSANT
        if (moveuPeca instanceof Peao && (Destino.getLinha() == Origem.getLinha() - 2 || Destino.getLinha() == Origem.getLinha() + 2)){
            enPassantVuneravel = moveuPeca;
        } else {
            enPassantVuneravel = null;
        }
        
        return (PecaXadrez) capturarPeca;
    }   
    
    public PecaXadrez recolocarPecaPromovida(String tipo) {
        if (promocao == null) {
            throw new IllegalStateException("Nao ha peca para ser promovida!");
        }
        if (!tipo.equals("B") && !tipo.equals("C") && !tipo.equals("T") && !tipo.equals("Q")) {
            throw new InvalidParameterException("Tipo invalido para promocao");
        }
        
        Posicao pos = promocao.getXadrezPosicao().toPosicao();
        Peca p = tabuleiro.removerPeca(pos);
        pecasNoTabuleiro.remove(p);
        
        PecaXadrez novaP = novaPeca(tipo, promocao.getCor());
        tabuleiro.lugarPeca(novaP, pos);
        pecasNoTabuleiro.add(novaP);
        
        return novaP;
    }
    
    private PecaXadrez novaPeca(String tipo, Cor cor) {
        if (tipo.equals("C")) return new Cavalo(cor, tabuleiro);
        if (tipo.equals("B")) return new Bispo(cor, tabuleiro);
        if (tipo.equals("T")) return new Torre(cor, tabuleiro);
        return new Rainha(cor, tabuleiro);
    } 
    
    private Peca fazerMovimento(Posicao Origem, Posicao Destino){
        PecaXadrez p = (PecaXadrez)tabuleiro.removerPeca(Origem);
        p.acrescentarMovimentoConta();
        Peca capturarPeca = tabuleiro.removerPeca(Destino);
        tabuleiro.lugarPeca(p, Destino); //faz um upcasting automaticamente
        
        if (capturarPeca != null){
            pecasNoTabuleiro.remove(capturarPeca);
            pecasCapturadas.add(capturarPeca);
        }
        
        // MOVIMENTO ESPECIAL - ROQUE PEQUENO
        if (p instanceof Rei && Destino.getColuna() == Origem.getColuna() + 2) {
            Posicao origemT = new Posicao(Origem.getLinha(), Origem.getColuna() + 3);
            Posicao destinoT = new Posicao(Origem.getLinha(), Origem.getColuna() + 1);
            PecaXadrez torre = (PecaXadrez)tabuleiro.removerPeca(origemT);
            tabuleiro.lugarPeca(torre, destinoT);
            torre.acrescentarMovimentoConta();                    
        }  
        
        // MOVIMENTO ESPECIAL - ROQUE GRANDE
        if (p instanceof Rei && Destino.getColuna() == Origem.getColuna() - 2) {
            Posicao origemT = new Posicao(Origem.getLinha(), Origem.getColuna() - 4);
            Posicao destinoT = new Posicao(Origem.getLinha(), Origem.getColuna() - 1);
            PecaXadrez torre = (PecaXadrez)tabuleiro.removerPeca(origemT);
            tabuleiro.lugarPeca(torre, destinoT);
            torre.acrescentarMovimentoConta();                    
        }
        
        // MOVIMENTO ESPECIAL - EN PASSANT
        if (p instanceof Peao) {
            if (Origem.getColuna() != Destino.getColuna() && capturarPeca == null) {
                Posicao peaoPosicao;
                if (p.getCor() == Cor.BRANCO) {
                    peaoPosicao = new Posicao(Destino.getLinha() + 1, Destino.getColuna());
                } else {
                    peaoPosicao = new Posicao(Destino.getLinha() - 1, Destino.getColuna());
                }
                capturarPeca = tabuleiro.removerPeca(peaoPosicao);
                pecasCapturadas.add(capturarPeca);
                pecasNoTabuleiro.remove(capturarPeca);
            }
        }
        
        return capturarPeca;
    }
    
    private void desfazerMovimento(Posicao Origem, Posicao Destino, Peca pecaCapturada){
        PecaXadrez p = (PecaXadrez)tabuleiro.removerPeca(Destino);
        p.desacrescentarMovimentoConta();
        tabuleiro.lugarPeca(p, Origem);
        
        if (pecaCapturada != null){
            tabuleiro.lugarPeca(pecaCapturada, Destino);
            pecasCapturadas.remove(pecaCapturada);
            pecasNoTabuleiro.add(pecaCapturada);
        }
        
         // MOVIMENTO ESPECIAL - ROQUE PEQUENO
        if (p instanceof Rei && Destino.getColuna() == Origem.getColuna() + 2) {
            Posicao origemT = new Posicao(Origem.getLinha(), Origem.getColuna() + 3);
            Posicao destinoT = new Posicao(Origem.getLinha(), Origem.getColuna() + 1);
            PecaXadrez torre = (PecaXadrez)tabuleiro.removerPeca(destinoT);
            tabuleiro.lugarPeca(torre, origemT);
            torre.desacrescentarMovimentoConta();
        }  
        
        // MOVIMENTO ESPECIAL - ROQUE GRANDE
        if (p instanceof Rei && Destino.getColuna() == Origem.getColuna() - 2) {
            Posicao origemT = new Posicao(Origem.getLinha(), Origem.getColuna() - 4);
            Posicao destinoT = new Posicao(Origem.getLinha(), Origem.getColuna() - 1);
            PecaXadrez torre = (PecaXadrez)tabuleiro.removerPeca(destinoT);
            tabuleiro.lugarPeca(torre, origemT);
            torre.desacrescentarMovimentoConta();
        } 
        
        // MOVIMENTO ESPECIAL - EN PASSANT
        if (p instanceof Peao) {
            if (Origem.getColuna() != Destino.getColuna() && pecaCapturada == enPassantVuneravel) {
                PecaXadrez peao = (PecaXadrez)tabuleiro.removerPeca(Destino);
                Posicao peaoPosicao;
                if (p.getCor() == Cor.BRANCO) {
                    peaoPosicao = new Posicao(3, Destino.getColuna());
                } else {
                    peaoPosicao = new Posicao(4, Destino.getColuna());
                }
                tabuleiro.lugarPeca(peao, peaoPosicao);
                        
            }
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
                
        lugarNovoPeca('a', 1, new Torre(Cor.BRANCO, tabuleiro));
        lugarNovoPeca('b', 1, new Cavalo(Cor.BRANCO, tabuleiro));
        lugarNovoPeca('c', 1, new Bispo(Cor.BRANCO, tabuleiro));
        lugarNovoPeca('d', 1, new Rainha(Cor.BRANCO, tabuleiro));
        lugarNovoPeca('e', 1, new Rei(Cor.BRANCO, tabuleiro, this));
        lugarNovoPeca('f', 1, new Bispo(Cor.BRANCO, tabuleiro));
        lugarNovoPeca('g', 1, new Cavalo(Cor.BRANCO, tabuleiro));
        lugarNovoPeca('h', 1, new Torre(Cor.BRANCO, tabuleiro));
        lugarNovoPeca('a', 2, new Peao(Cor.BRANCO, tabuleiro, this));
        lugarNovoPeca('b', 2, new Peao(Cor.BRANCO, tabuleiro, this));
        lugarNovoPeca('c', 2, new Peao(Cor.BRANCO, tabuleiro, this));
        lugarNovoPeca('d', 2, new Peao(Cor.BRANCO, tabuleiro, this));
        lugarNovoPeca('e', 2, new Peao(Cor.BRANCO, tabuleiro, this));
        lugarNovoPeca('f', 2, new Peao(Cor.BRANCO, tabuleiro, this));
        lugarNovoPeca('g', 2, new Peao(Cor.BRANCO, tabuleiro, this));
        lugarNovoPeca('h', 2, new Peao(Cor.BRANCO, tabuleiro, this));

        lugarNovoPeca('a', 8, new Torre(Cor.PRETO, tabuleiro));
        lugarNovoPeca('b', 8, new Cavalo(Cor.PRETO, tabuleiro));
        lugarNovoPeca('c', 8, new Bispo(Cor.PRETO, tabuleiro));
        lugarNovoPeca('d', 8, new Rainha(Cor.PRETO, tabuleiro));
        lugarNovoPeca('e', 8, new Rei(Cor.PRETO, tabuleiro, this));
        lugarNovoPeca('f', 8, new Bispo(Cor.PRETO, tabuleiro));
        lugarNovoPeca('g', 8, new Cavalo(Cor.PRETO, tabuleiro));
        lugarNovoPeca('h', 8, new Torre(Cor.PRETO, tabuleiro));
        lugarNovoPeca('a', 7, new Peao(Cor.PRETO, tabuleiro, this));
        lugarNovoPeca('b', 7, new Peao(Cor.PRETO, tabuleiro, this));
        lugarNovoPeca('c', 7, new Peao(Cor.PRETO, tabuleiro, this));
        lugarNovoPeca('d', 7, new Peao(Cor.PRETO, tabuleiro, this));
        lugarNovoPeca('e', 7, new Peao(Cor.PRETO, tabuleiro, this));
        lugarNovoPeca('f', 7, new Peao(Cor.PRETO, tabuleiro, this));
        lugarNovoPeca('g', 7, new Peao(Cor.PRETO, tabuleiro, this));
        lugarNovoPeca('h', 7, new Peao(Cor.PRETO, tabuleiro, this));
    }
}
