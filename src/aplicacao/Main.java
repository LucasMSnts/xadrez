/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplicacao;

import Xadrez.PartidaXadrez;

public class Main {
    
    public static void main(String[] args){
        
        PartidaXadrez partidaxz = new PartidaXadrez();
        IU.printTabuleiro(partidaxz.getPecas());
    }    
}
