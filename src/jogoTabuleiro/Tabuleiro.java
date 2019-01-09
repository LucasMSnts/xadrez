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
        this.linhas = linhas;
        this.colunas = colunas;
        pecas = new Peca[linhas][colunas];
    }

    public int getLinhas() {
        return linhas;
    }

    public void setLinhas(int linhas) {
        this.linhas = linhas;
    }

    public int getColunas() {
        return colunas;
    }

    public void setColunas(int colunas) {
        this.colunas = colunas;
    }
    
    public Peca PECA(int linha, int coluna){
        return pecas[linha][coluna];
    }
    
    public Peca PECA(Posicao posicao) {
        return pecas[posicao.getLinha()][posicao.getColuna()];
    }
    
    public void lugarPeca(Peca p, Posicao pos){
        pecas[pos.getLinha()][pos.getColuna()] = p;
        p.posicao = pos;
    }
}
