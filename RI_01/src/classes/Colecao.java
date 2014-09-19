package classes;

import java.util.ArrayList;
import java.util.List;
import principal.Main;

/**
 *
 * @author emmanuelsilvaxavier
 */
public class Colecao {
    
    private static int qtdDocumentos;
    private static int qtdTermos;
    private static List listaTermosColecao = new ArrayList();

    public static int getQtdDocumentos() {
        return qtdDocumentos;
    }

    public static void incQtdDocumentos() {
        Colecao.qtdDocumentos++;
        Main.getJanela().getLbDetalhes().setText("Qtd de documentos da coleção: " + Colecao.getQtdDocumentos() + "\t");
    }

    public static int getQtdTermos() {
        return qtdTermos;
    }

    public static void incQtdTermos() {
        Colecao.qtdTermos++;
        Main.getJanela().getLbDetalhes().setText("Qtd de documentos da coleção: " + Colecao.getQtdDocumentos() + "\t Qtd de Termos da coleção: " + Colecao.getQtdTermos() + "\t");
    }

    public static List getListaTermosColecao() {
        return listaTermosColecao;
    }

    
    
    
    public static void addTermo(Termo termo,double idf){
       int posicao = pesquisar(termo.getWord());
       if (posicao == -1){
           Colecao.listaTermosColecao.add(new TermoColecao(termo.getWord(),idf));
           Colecao.incQtdTermos();
       }else{
           TermoColecao termoColecao =  (TermoColecao) Colecao.listaTermosColecao.get(posicao);
           termoColecao.setIdf(idf);
       } 
    }
    
    public static int pesquisar(String word) {
        for (int i = 0; i < listaTermosColecao.size(); i++) {
            if (((TermoColecao) listaTermosColecao.get(i)).getWord().equals(word)) {
                return i;
            }
        }
        return -1;
    }
    
}
