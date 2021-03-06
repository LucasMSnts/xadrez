/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplicacao;

import Xadrez.PartidaXadrez;
import Xadrez.PecaXadrez;
import Xadrez.xadrezPosicao;
import Xadrez.xadrezException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {
    
    public static void main(String[] args){
        
        Scanner sc = new Scanner(System.in);
        PartidaXadrez partidaxz = new PartidaXadrez();
        List<PecaXadrez> capturados = new ArrayList<>();
        
        while(!partidaxz.getXequeMate()){     
            try {
                IU.limparTela();
                IU.printPartida(partidaxz, capturados);
                System.out.println();
                System.out.print("Origem: ");
                xadrezPosicao origem = IU.lerPosicaoXadrez(sc);
                
                boolean[][] movPossiveis = partidaxz.movimentosPossiveis(origem);
                IU.limparTela();
                IU.printTabuleiro(partidaxz.getPecas(), movPossiveis);

                System.out.println();
                System.out.print("Destino: ");
                xadrezPosicao destino = IU.lerPosicaoXadrez(sc);

                PecaXadrez capturarPeca = partidaxz.perfomaceMovimentoXadrez(origem, destino);
                
                if(capturarPeca != null){
                    capturados.add(capturarPeca);
                }
                
                if(partidaxz.getPromocao() != null) {
                    System.out.println("Entre com a peca para a promocao: (B/C/T/Q) ");
                    String tipo = sc.nextLine();
                    partidaxz.recolocarPecaPromovida(tipo);
                }
                
            } catch (xadrezException | InputMismatchException e) {
                System.out.println(e.getMessage());
                sc.nextLine();
            }
        }
        IU.limparTela();
        IU.printPartida(partidaxz, capturados);
    }    
}
