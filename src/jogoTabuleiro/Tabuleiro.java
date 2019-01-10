/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jogoTabuleiro;


public class Tabuleiro {
    
    private int linhas;
    private int colunas;
    private Peca[][] pecas;

    public Tabuleiro(int linhas, int colunas) {
        if (linhas < 1 || colunas < 1){
            throw new TabuleiroException("Erro ao criar o tabuleiro: é necessario ter pelo menos 1 linha e 1 coluna!");
        }
        this.linhas = linhas;
        this.colunas = colunas;
        pecas = new Peca[linhas][colunas];
    }

    public int getLinhas() {
        return linhas;
    }   

    public int getColunas() {
        return colunas;
    }  
    
    public Peca peca(int linha, int coluna){
        if (!posicaoExistente(linha, coluna)){
            throw new TabuleiroException("Posição não localizada no tabuleiro!");
        }
        return pecas[linha][coluna];
    }
    
    public Peca peca(Posicao posicao) {
        if (!posicaoExistente(posicao)){
            throw new TabuleiroException("Posição não localizada no tabuleiro!");
        }
        return pecas[posicao.getLinha()][posicao.getColuna()];
    }
    
    public void lugarPeca(Peca p, Posicao pos){
        if(temPeca(pos)){
            throw new TabuleiroException("Já existe peça nessa posição!");
        }
        pecas[pos.getLinha()][pos.getColuna()] = p;
        p.posicao = pos;
    }
    
    public boolean posicaoExistente(int l, int col){
        return l >=0 && l < linhas && col >= 0 && col < colunas;
    }
    
    public boolean posicaoExistente(Posicao pos){
        return posicaoExistente(pos.getLinha(), pos.getColuna());
    }
    
    public boolean temPeca(Posicao pos){
        if (!posicaoExistente(pos)){
            throw new TabuleiroException("Posição não localizada no tabuleiro!");
        }
        return peca(pos) != null;
    }
}
