/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jogoTabuleiro;


public abstract class Peca {
    protected Posicao posicao;
    private Tabuleiro tabuleiro;

    public Peca(Tabuleiro tabuleiro) {
        this.tabuleiro = tabuleiro;
        posicao = null;
    }  

    protected Tabuleiro getTabuleiro() {
        return tabuleiro;
    }    
    
    public abstract boolean[][] possivelMovimentos();

    public boolean movimentoPossivel(Posicao pos){
        return possivelMovimentos()[pos.getLinha()][pos.getColuna()];                
    }
    
    public boolean temAlgumMovimentoPossivel(){
        boolean[][] mat = possivelMovimentos();
        for(int i=0;i<mat.length;i++){
            for(int j=0;j<mat.length;j++){
                if(mat[i][j]){
                    return true;
                }
            }
        }
        return false;
    }
}
