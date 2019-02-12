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
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    
    public static void main(String[] args){
        
        Scanner sc = new Scanner(System.in);
        PartidaXadrez partidaxz = new PartidaXadrez();
        
        while(true){     
            try {
                IU.limparTela();
                IU.printPartida(partidaxz);
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
            } catch (xadrezException | InputMismatchException e) {
                System.out.println(e.getMessage());
                sc.nextLine();
            }
        }
    }    
}
