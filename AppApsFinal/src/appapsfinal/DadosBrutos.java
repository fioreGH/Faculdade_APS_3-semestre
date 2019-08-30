package appapsfinal;

import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.*;

public class DadosBrutos {

    DecimalFormat dc = new DecimalFormat("0.00");
    DecimalFormat linha = new DecimalFormat("0");
    private String arquivo;
    private double[] dados;
    private double numeroDeLinhas;
    private double intervaloDeClasse;
    private double tabela[][];
    private double tabela2[][];
    private boolean defineTabela = false;
    private double mediaAritmetica;

    public double[] getDados() {
        return dados;
    }

    public void setDados(double[] dados) {
        this.dados = dados;
    }

    public String getArquivo() {
        return arquivo;
    }

    public void setArquivo(String arquivo) {
        this.arquivo = arquivo;
    }

    public double getNumeroDeLinhas() {
        return numeroDeLinhas;
    }

    public void setNumeroDeLinhas(double numeroDeLinhas) {
        this.numeroDeLinhas = numeroDeLinhas;
    }

    public double getIntervaloDeClasse() {
        return intervaloDeClasse;
    }

    public void setIntervaloDeClasse(double intervaloDeClasse) {
        this.intervaloDeClasse = intervaloDeClasse;
    }

    public double[][] getTabela() {
        return tabela;
    }

    public void setTabela(double[][] tabela) {
        this.tabela = tabela;
    }

    public double[][] getTabela2() {
        return tabela2;
    }

    public void setTabela2(double[][] tabela2) {
        this.tabela2 = tabela2;
    }

    public boolean isDefineTabela() {
        return defineTabela;
    }

    public void setDefineTabela(boolean defineTabela) {
        this.defineTabela = defineTabela;
    }

    public double getMediaAritmetica() {
        return mediaAritmetica;
    }

    public void setMediaAritmetica(double mediaAritmetica) {
        this.mediaAritmetica = mediaAritmetica;
    }

    public void leArquivo(String arquivo) {

        String linha = "";
        System.out.println("");
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(arquivo)));
            while (br.ready()) {
                linha = br.readLine();
                System.out.println("DADOS BRUTOS");
                System.out.println(linha);
                System.out.println("");

            }
            br.close();
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }

        String[] arrayDeNumeros = linha.split(", ");

        dados = Arrays.stream(arrayDeNumeros).mapToDouble(Double::parseDouble).toArray();

    }

    public void bubblesort() {

        double temp = 0;
        for (int i = 0; i < dados.length; i++) {
            for (int j = 0; j < (dados.length - 1); j++) {
                if (dados[j] > dados[j + 1]) {
                    temp = dados[j + 1];
                    dados[j + 1] = dados[j];
                    dados[j] = temp;
                }
            }
        }

        System.out.println("ROL ORGANIZADO");
        System.out.println(Arrays.toString(dados));
        System.out.println("");
    }

    public void exibirQuantidadeDeDados() {

        System.out.println("QUANTIDADE DE DADOS DO ROL");
        System.out.println("N : " + dc.format(dados.length));
        System.out.println("");

    }

    public void exibirLimites() {

        System.out.println("LIMITES DO ROL");
        System.out.println("limite inferior: " + dc.format(dados[0]));
        System.out.println("limite superior: " + dc.format(dados[dados.length - 1]));
        System.out.println("");

    }

    public void calcularDeltaT() {

        System.out.println("DELTA T DO ROL");
        System.out.println("delta T: " + dc.format(dados[dados.length - 1] - dados[0]));
        System.out.println("");

    }

    public void calcularNumeroDeLinhas() {

        double calculo = 0;
        int ajuste = 0;

        numeroDeLinhas = 1 + (3.3 * Math.log10(dados.length));
        ajuste = (int) numeroDeLinhas;
        calculo = ajuste + 0.5;

        if (calculo <= numeroDeLinhas) {
            numeroDeLinhas = ajuste + 1;
            System.out.println("NUMERO DE LINHAS DO ROL");
            System.out.println("i : " + linha.format(numeroDeLinhas) + " (com tolerancia +-1)");
            System.out.println("");

        } else {
            numeroDeLinhas = ajuste;
            System.out.println("NUMERO DE LINHAS DO ROL");
            System.out.println("i : " + linha.format(numeroDeLinhas));
            System.out.println("");
        }
    }

    public void calcularIntervaloDeClasse() {

        intervaloDeClasse = (dados[dados.length - 1] - dados[0]) / numeroDeLinhas;
        System.out.println("INTERVALO DE CLASSE DO ROL");
        System.out.println("intervalo do classe : " + linha.format(intervaloDeClasse));
        System.out.println("");

    }

    public void construirTabela() {

        //construindo a tabela
        int l; // variavel usada para converter em inteiro o numero de linhas
        int ic; //varial usada para converter em inteiro o intervalo de classe
        double limite;

        l = (int) numeroDeLinhas;
        ic = (int) intervaloDeClasse;

        limite = ic + 0.5;

        if (limite <= intervaloDeClasse) {

            ic = ic + 1;

        }

        this.tabela = new double[l][8]; //criando a tabela numero de linhas por 8 colunas

        tabela[0][0] = dados[0];

        //coluna limite inferior
        for (int i = 1; i < tabela.length; i++) {

            tabela[i][0] += tabela[i - 1][0] + ic;

        }

        //coluna intervalo de classe
        for (int i = 0; i < tabela.length; i++) {

            tabela[i][1] = tabela[i][0] + ic;

        }

        //coluna frequencia simples - Fi
        double contador = 0;
        for (int i = 0; i < tabela.length; i++) {

            for (int j = 0; j < dados.length; j++) {

                if ((dados[j] >= tabela[i][0]) && (dados[j] < tabela[i][1])) {

                    contador++;
                }

            }
            tabela[i][2] = contador;
            contador = 0;
        }
//////////////////////////////////////AQUI ANALISA SE ENTRA O ULTIMO INDICE DE CLASSE NO Fi./////////////////////////
        double b = dados[dados.length - 1];
        double a = tabela[tabela.length - 1][1];

        if (b == a) {
            for (int i = 0; i < dados.length; i++) {

                if (a == dados[i]) {

                    contador++;
                }

            }
            tabela[tabela.length - 1][2] = contador + tabela[tabela.length - 1][2];
        }
//////////////////////AQUI VERIFICA SE EXISTE A NECESSIDADE DE UMA LINHA A MAIS////////////////////////////////////////
        if (tabela[tabela.length - 1][1] < dados[dados.length - 1]) {

            this.defineTabela = true;
            this.tabela2 = new double[l + 1][8]; //criando a tabela2 com 1 linha a mais por 8 colunas

            tabela2[0][0] = dados[0];

            //coluna limite inferior
            for (int i = 1; i < tabela2.length; i++) {

                tabela2[i][0] += tabela2[i - 1][0] + ic;

            }

            //coluna intervalo de classe
            for (int i = 0; i < tabela2.length; i++) {

                tabela2[i][1] = tabela2[i][0] + ic;

            }

            //coluna frequencia simples - Fi
            contador = 0;
            for (int i = 0; i < tabela2.length; i++) {

                for (int j = 0; j < dados.length; j++) {

                    if ((dados[j] >= tabela2[i][0]) && (dados[j] < tabela2[i][1])) {

                        contador++;
                    }

                }
                tabela2[i][2] = contador;
                contador = 0;
            }

            b = dados[dados.length - 1];
            a = tabela2[tabela2.length - 1][1];

            if (b == a) {
                for (int i = 0; i < dados.length; i++) {

                    if (a == dados[i]) {

                        contador++;
                    }

                }
                tabela2[tabela2.length - 1][2] = contador + tabela2[tabela2.length - 1][2];
            }

            //coluna frequencia acumulada - Fiac
            tabela2[0][3] = tabela2[0][2];

            for (int i = 1; i < tabela2.length; i++) {

                tabela2[i][3] = tabela2[i - 1][3] + tabela2[i][2];

            }

            //coluna frequencia relativa - Fr%
            for (int i = 0; i < tabela2.length; i++) {
                double calculo = 0;
                calculo = (tabela2[i][2] * 100) / dados.length;
                BigDecimal bd = new BigDecimal(calculo).setScale(2, RoundingMode.HALF_EVEN);
                tabela2[i][4] = bd.doubleValue();

            }

            //coluna frequencia relativa acumulada Frac%  
            tabela2[0][5] = tabela2[0][4];

            for (int i = 1; i < tabela2.length; i++) {

                tabela2[i][5] = tabela2[i - 1][5] + tabela2[i][4];

            }

            //coluna Xi
            for (int i = 0; i < tabela2.length; i++) {

                tabela2[i][6] = (tabela2[i][0] + tabela2[i][1]) / 2;

            }
            //coluna Xi.Fi
            for (int i = 0; i < tabela2.length; i++) {

                tabela2[i][7] = tabela2[i][6] * tabela2[i][2];

            }
            System.out.println("L.I\t" + "I.C\t" + "Fi\t" + "Fiac\t" + "Fr\t" + "Frac\t" + "XI\t" + "Fi.XI");

            for (int z = 0; z < tabela2.length; z++) {
                for (int c = 0; c < tabela2[0].length; c++) {
                    System.out.print(tabela2[z][c] + "\t"); //imprime caracter a caracter
                }
                System.out.println(" "); //muda de linha
            }

        } else {
            //coluna frequencia acumulada - Fiac
            tabela[0][3] = tabela[0][2];

            for (int i = 1; i < tabela.length; i++) {

                tabela[i][3] = tabela[i - 1][3] + tabela[i][2];

            }

            //coluna frequencia relativa - Fr%
            for (int i = 0; i < tabela.length; i++) {
                double calculo = 0;
                calculo = (tabela[i][2] * 100) / dados.length;
                BigDecimal bd = new BigDecimal(calculo).setScale(2, RoundingMode.HALF_EVEN);
                tabela[i][4] = bd.doubleValue();

            }

            //coluna frequencia relativa acumulada Frac%  
            tabela[0][5] = tabela[0][4];

            for (int i = 1; i < tabela.length; i++) {

                tabela[i][5] = tabela[i - 1][5] + tabela[i][4];

            }

            //coluna Xi
            for (int i = 0; i < tabela.length; i++) {

                tabela[i][6] = (tabela[i][0] + tabela[i][1]) / 2;

            }

            //coluna Xi.Fi
            for (int i = 0; i < tabela.length; i++) {

                tabela[i][7] = tabela[i][6] * tabela[i][2];

            }
            System.out.println("L.I\t" + "I.C\t" + "Fi\t" + "Fiac\t" + "Fr\t" + "Frac\t" + "XI\t" + "Fi.XI");

            for (int z = 0; z < tabela.length; z++) {
                for (int c = 0; c < tabela[0].length; c++) {
                    System.out.print(tabela[z][c] + "\t"); //imprime caracter a caracter
                }
                System.out.println(" "); //muda de linha
            }

        }
        System.out.println("");

    }

//MEDIA ARITMÉTICA//////////////////////////////////////////////////////////////////////////////////////////
    public void calcularMedia() {

        double autoSomaDeFi = 0;
        double autoSomaDeFixXi = 0;

        if (defineTabela == false) {
            for (int i = 0; i < tabela.length; i++) {

                autoSomaDeFi = autoSomaDeFi + tabela[i][2];

            }

            for (int i = 0; i < tabela.length; i++) {

                autoSomaDeFixXi = autoSomaDeFixXi + tabela[i][7];

            }

            this.mediaAritmetica = autoSomaDeFixXi / autoSomaDeFi;

            BigDecimal bd = new BigDecimal(mediaAritmetica).setScale(2, RoundingMode.HALF_EVEN);

            System.out.println("MEDIA ARITMÉTICA");
            System.out.println("A média do dados é igual a :" + bd + "\n");

        } else {

            for (int i = 0; i < tabela2.length; i++) {

                autoSomaDeFi = autoSomaDeFi + tabela2[i][2];

            }

            for (int i = 0; i < tabela2.length; i++) {

                autoSomaDeFixXi = autoSomaDeFixXi + tabela2[i][7];

            }

            this.mediaAritmetica = autoSomaDeFixXi / autoSomaDeFi;

            BigDecimal bd = new BigDecimal(mediaAritmetica).setScale(2, RoundingMode.HALF_EVEN);

            System.out.println("MEDIA ARITMÉTICA");
            System.out.println("A média do dados é igual a :" + bd + "\n");
        }
    }

    public void calculaModa() {

        double l = 0;
        double h = 0;
        double fiModal = 0;
        double fiAnterior = 0;
        double fiPosterior = 0;
        double d1 = 0;
        double d2 = 0;
        double moda = 0;

        if (defineTabela == false) {

            for (int i = 0; i < tabela.length; i++) {

                if ((mediaAritmetica >= tabela[i][0]) && (mediaAritmetica < tabela[i][1])) {

                    l = tabela[i][0];
                    h = tabela[i][1] - tabela[i][0];
                    fiModal = tabela[i][2];
                    fiAnterior = tabela[i - 1][2];
                    fiPosterior = tabela[i + 1][2];
                }
            }

            d1 = fiModal - fiAnterior;
            d2 = fiModal - fiPosterior;

            moda = l + ((d1 / (d1 + d2)) * h);

            BigDecimal bd = new BigDecimal(moda).setScale(2, RoundingMode.HALF_EVEN);

            System.out.println("MODA");
            System.out.println("A média do dados é igual a :" + bd + "\n");

        } else {

            for (int i = 0; i < tabela2.length; i++) {

                if ((mediaAritmetica >= tabela2[i][0]) && (mediaAritmetica < tabela2[i][1])) {

                    l = tabela2[i][0];
                    h = tabela2[i][1] - tabela2[i][0];
                    fiModal = tabela2[i][2];
                    fiAnterior = tabela2[i - 1][2];
                    fiPosterior = tabela2[i + 1][2];
                }
            }

            d1 = fiModal - fiAnterior;
            d2 = fiModal - fiPosterior;

            moda = l + ((d1 / (d1 + d2)) * h);

            BigDecimal bd = new BigDecimal(moda).setScale(2, RoundingMode.HALF_EVEN);

            System.out.println("MODA");
            System.out.println("A média do dados é igual a :" + bd + "\n");
        }

    }

    public void calcularMediana() {

        double autoSomaDeFi = 0;
        double posicao;
        double l = 0;
        double h = 0;
        double fiModal = 0;
        double facAnterior = 0;
        double mediana = 0;
        double calculo = 0;
        double correcaoDecimal = 0;

        if (defineTabela == false) {

            for (int i = 0; i < tabela.length; i++) {

                autoSomaDeFi = autoSomaDeFi + tabela[i][2];

            }

            posicao = autoSomaDeFi / 2;

            for (int i = 0; i < tabela.length; i++) {

                if ((mediaAritmetica >= tabela[i][0]) && (mediaAritmetica < tabela[i][1])) {

                    l = tabela[i][0];
                    h = tabela[i][1] - tabela[i][0];
                    fiModal = tabela[i][2];

                }
            }

            for (int i = 0; i < tabela.length; i++) {

                if (posicao <= tabela[i][3]) {

                    facAnterior = tabela[i - 1][3];
                    break;

                }
            }
            calculo = (posicao - facAnterior) / fiModal;
            BigDecimal ca = new BigDecimal(calculo).setScale(2, RoundingMode.HALF_EVEN);

            correcaoDecimal = ca.doubleValue();
            mediana = l + (correcaoDecimal * h);
            BigDecimal bd = new BigDecimal(mediana).setScale(2, RoundingMode.HALF_EVEN);

            System.out.println("MEDIANA");
            System.out.println("A mediana do dados é igual a :" + bd + "\n");

        } else {

            for (int i = 0; i < tabela2.length; i++) {

                autoSomaDeFi = autoSomaDeFi + tabela2[i][2];

            }

            posicao = autoSomaDeFi / 2;

            for (int i = 0; i < tabela2.length; i++) {

                if ((mediaAritmetica >= tabela2[i][0]) && (mediaAritmetica < tabela2[i][1])) {

                    l = tabela2[i][0];
                    h = tabela2[i][1] - tabela2[i][0];
                    fiModal = tabela2[i][2];

                }
            }

            for (int i = 0; i < tabela2.length; i++) {

                if (posicao <= tabela2[i][3]) {

                    facAnterior = tabela2[i - 1][3];
                    break;

                }
            }
            calculo = (posicao - facAnterior) / fiModal;
            BigDecimal ca = new BigDecimal(calculo).setScale(2, RoundingMode.HALF_EVEN);

            correcaoDecimal = ca.doubleValue();
            mediana = l + (correcaoDecimal * h);
            BigDecimal bd = new BigDecimal(mediana).setScale(2, RoundingMode.HALF_EVEN);

            System.out.println("MEDIANA");
            System.out.println("A mediana do dados é igual a :" + bd + "\n");

        }
    }

    public void calculaVariancia() {

        //MEDIA
        double temp2 = 0;
        for (int i = 0; i < dados.length; i++) {

            temp2 += dados[i];
        }
         
        double media = temp2 / dados.length;

        //calcular variancia
        double temp3 = 0;
        for (int i = 0; i < dados.length; i++) {

            temp3 += Math.pow((dados[i] - media), 2);
        }

        double desvio = temp3 / dados.length;

        System.out.println("VARIÂNCIA");
        System.out.println("A variância do Rol é: " + dc.format(desvio) + "\n");

    }

    public void calcularDesvioPadrao() {

        //MEDIA
        double temp2 = 0;
        for (int i = 0; i < dados.length; i++) {

            temp2 += dados[i];
        }
        
        double media = temp2 / dados.length;

        double temp3 = 0;
        for (int i = 0; i < dados.length; i++) {

            temp3 += Math.pow((dados[i] - media), 2);
        }

        double desvio = temp3 / dados.length;
        //calcular Desvio padrão
        double variancia = Math.sqrt(desvio);

        System.out.println("DESVIO PADRÃO");
        System.out.println("O Desvio Padrão do Rol é: " + dc.format(variancia) + "\n");
    }
    
 
    
}
//C:\Users\administrator\Desktop
