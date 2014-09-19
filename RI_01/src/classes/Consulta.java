package classes;

import Jama.Matrix;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import principal.Main;

/**
 *
 * @author emmanuelsilvaxavier
 */
public class Consulta {

    private  int qtPalavras = 0;
    private  int qtStopWord = 0;
    private  int qtAdv = 0;
    private  List listaTermosProcessados = new ArrayList();
    private  List listaTermos = new ArrayList();
    private double[][] vetorQ;

    public  List getListaTermosProcessados() {
        return listaTermosProcessados;
    }
    
   

    public  boolean retirarStopWords(String consulta, AbstractManipulador stopwords, AbstractManipulador adverbios) {
        int i,posicao;
        String palavra;
        TermoConsulta termo;
        String termos[] = consulta.toLowerCase().split(" |,|:|;|!|<|>|_|=|" + Pattern.quote("|") + "|" + Pattern.quote("(") + "|" + Pattern.quote(")") + "|" + Pattern.quote("?") + "|" + Pattern.quote("+") + "|" + Pattern.quote("*") + "|" + Pattern.quote(".") + "|" + Pattern.quote("-") + "|" + Pattern.quote("\"") + "|" + Pattern.quote("\'"));
        for (i = 0; i < termos.length; i++) {
            palavra = termos[i];
            if (palavra.equals("")) {
                continue;
            }
            qtPalavras++;
            listaTermos.add(palavra);
            if (stopwords.getLista().contains(palavra)) {
                qtStopWord++;
            } else if (adverbios.getLista().contains(palavra)) {
                qtAdv++;
            } else {
                posicao = pesquisar(palavra);
                if (posicao == -1) {
                    termo = new TermoConsulta();
                    termo.setWord(palavra);
                    termo.incFrequencia();
                    listaTermosProcessados.add(termo);
                } else {
                    termo = (TermoConsulta) listaTermosProcessados.get(posicao);
                    termo.incFrequencia();
                }
            }
        }
        return true;
    }
    
    private  int pesquisar(String word) {
        for (int i = 0; i < listaTermosProcessados.size(); i++) {
            if (((TermoConsulta) listaTermosProcessados.get(i)).getWord().equals(word)) {
                return i;
            }
        }
        return -1;
    }
    
    public void processarConsulta(){
        TermoConsulta termo;
        int posicaoColecao;
        double idf;
         for (int i = 0; i < listaTermosProcessados.size(); i++){
            termo = (TermoConsulta) listaTermosProcessados.get(i);
            posicaoColecao = Colecao.pesquisar(termo.getWord());
            if (posicaoColecao == -1)
                termo.setPeso(0);
            else{
                idf = ((TermoColecao) Colecao.getListaTermosColecao().get(posicaoColecao)).getIdf();
                termo.setPeso((Float.parseFloat("0.5") + ((float) termo.getFrequencia()/2)) * idf);
            }
        }
        showPesos();
        
    }
    
    public Matrix processarConsultaLSI(List listaTermosColecao){
         int i, j,linhas,colunas, posicao;
         TermoColecao termoC;
         linhas = listaTermosColecao.size();
         vetorQ = new double[linhas][1];
         for (i = 0; i < listaTermosColecao.size(); i++) {
            termoC = (TermoColecao) listaTermosColecao.get(i);
            posicao = pesquisar(termoC.getWord());
            if (posicao == -1) 
                vetorQ[i][0] = 0;
            else 
                vetorQ[i][0] = ((TermoConsulta) listaTermosProcessados.get(posicao)).getFrequencia();
         }
         return new Matrix(vetorQ);
    }
    
    
    
    private  void showPesos(){
        String texto = "Análise da Consulta\nQtd de palavras: " + qtPalavras + "\t Qtd de stopword: " + qtStopWord + "\t Qtd de advérbios, adjetivos e verbos: " + qtAdv + "\n\nTermos";
        int i;
        TermoConsulta termo;
        for (i = 0; i < listaTermosProcessados.size(); i++){
            termo = (TermoConsulta) listaTermosProcessados.get(i);
            texto = texto + "\n"+ termo.getWord() + "\t Tf: " + termo.getFrequencia() + "\t Peso: " + termo.getPeso();  
        }
        JOptionPane.showMessageDialog(Main.getJanela(), texto, Main.getJanela().getTitle(), JOptionPane.INFORMATION_MESSAGE);
    }
}
