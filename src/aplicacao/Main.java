/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplicacao;

import Xadrez.PartidaXadrez;
import Xadrez.PecaXadrez;
import Xadrez.xadrezPosicao;
import java.util.Scanner;

public class Main {
    
    public static void main(String[] args){
        
        Scanner sc = new Scanner(System.in);
        PartidaXadrez partidaxz = new PartidaXadrez();
        
        while(true){         
            IU.printTabuleiro(partidaxz.getPecas());
            System.out.println();
            System.out.print("Origem: ");
            xadrezPosicao origem = IU.lerPosicaoXadrez(sc);
            
            System.out.println();
            System.out.print("Destino: ");
            xadrezPosicao destino = IU.lerPosicaoXadrez(sc);
            
            PecaXadrez capturarPeca = partidaxz.perfomaceMovimentoXadrez(origem, destino);
        }
    }    
}
