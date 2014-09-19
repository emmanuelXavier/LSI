package classes;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 *
 * @author emmanuelsilvaxavier
 */
public class Documentos {

    private String nome;
    private float tamanho;
    private int qtPalavras = 0;
    private int qtStopWord = 0;
    private int qtAdv = 0;
    private int termoMaiorFrequencia = 0;
    private List listaTermosProcessados = new ArrayList();
    private List listaTermos = new ArrayList();

    public int getTermoMaiorFrequencia() {
        return termoMaiorFrequencia;
    }

    public void setTermoMaiorFrequencia(int termoMaiorFrequencia) {
        this.termoMaiorFrequencia = termoMaiorFrequencia;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTamanho() {
        return tamanho + " bytes";
    }

    public void setTamanho(float tamanho) {
        this.tamanho = tamanho;
    }

    public int getQtStopWord() {
        return qtStopWord;
    }

    public void setQtStopWord(int qtStopWord) {
        this.qtStopWord = qtStopWord;
    }

    public int getQtAdv() {
        return qtAdv;
    }

    public void setQtAdv(int qtAdv) {
        this.qtAdv = qtAdv;
    }

    public int getQtPalavras() {
        return qtPalavras;
    }

    public void setQtPalavras(int qtPalavras) {
        this.qtPalavras = qtPalavras;
    }

    public List getListaTermosProcessados() {
        return listaTermosProcessados;
    }

    public void setListaTermosProcessados(List listaTermosProcessados) {
        this.listaTermosProcessados = listaTermosProcessados;
    }

    public List getListaTermos() {
        return listaTermos;
    }

    public void setListaTermos(List listaTermos) {
        this.listaTermos = listaTermos;
    }

    public boolean retirarStopWords(File documento, AbstractManipulador stopwords, AbstractManipulador adverbios) {
        String palavra;
        String termos[];
        int posicao;
        Termo termo;
        try {
            int i;
            Termo.setMaiorFrequencia(0);
            Scanner scanner = new Scanner(new FileReader(documento.getAbsolutePath()));
            while (scanner.hasNext()) {
                termos = scanner.next().toLowerCase().split(",|:|;|!|<|>|=|" + Pattern.quote("|") + "|" + Pattern.quote("(") + "|" + Pattern.quote(")") + "|" + Pattern.quote("?") + "|" + Pattern.quote("+") + "|" + Pattern.quote("*") + "|" + Pattern.quote(".") + "|" + Pattern.quote("-") + "|" + Pattern.quote("\"") + "|" + Pattern.quote("\'"));
                for (i = 0; i < termos.length; i++) {
                    palavra = termos[i];
                    if (palavra.equals(""))
                        continue;
                    qtPalavras++;
                    listaTermos.add(palavra);
                    if (stopwords.getLista().contains(palavra)) {
                        qtStopWord++;
                    } else if (adverbios.getLista().contains(palavra)) {
                        qtAdv++;
                    } else {
                        posicao = pesquisar(palavra);
                        if (posicao == -1) {
                            termo = new Termo();
                            termo.setWord(palavra);
                            termo.incFrequencia();
                            listaTermosProcessados.add(termo);
                        } else {
                            termo = (Termo) listaTermosProcessados.get(posicao);
                            termo.incFrequencia();
                        }
                    }
                }
            }

            return true;
        } catch (Exception ex) {
            return false;
        }

    }

    public int pesquisar(String palavra) {
        for (int i = 0; i < listaTermosProcessados.size(); i++) {
            if (((Termo) listaTermosProcessados.get(i)).getWord().equals(palavra)) {
                return i;
            }
        }
        return -1;
    }
}
