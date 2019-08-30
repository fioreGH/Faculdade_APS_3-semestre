package appapsfinal;

import java.io.*;
import java.util.*;

public class AppApsFinal {

    public static void main(String[] args) throws FileNotFoundException {

        Scanner leitora = new Scanner(System.in);
        DadosBrutos numeros = new DadosBrutos();

        System.out.printf("Informe o nome de arquivo:\n");
        numeros.setArquivo(leitora.nextLine()); 

        

        numeros.leArquivo(numeros.getArquivo());
        
        numeros.bubblesort();
        
        numeros.exibirQuantidadeDeDados();
        
        numeros.exibirLimites();
        
        numeros.calcularDeltaT();
        
        numeros.calcularNumeroDeLinhas();
        
        numeros.calcularIntervaloDeClasse();
        
        numeros.construirTabela();

        numeros.calcularMedia();

        numeros.calculaModa();
        
        numeros.calcularMediana();
        
        numeros.calculaVariancia();
        
        numeros.calcularDesvioPadrao();
        
     
    }

}
