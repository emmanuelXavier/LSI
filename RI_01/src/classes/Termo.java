package classes;

/**
 *
 * @author nonilto
 */
public class Termo implements Comparable<Termo> {

    private String word;
    private double tf;
    private double idf;
    private double tfIdf;
    private int frequencia;
    private static int maiorFrequencia = 0;

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public double getTf() {
        return tf;
    }

    public void setTf(double tf) {
        this.tf = tf;
    }

    public double getIdf() {
        return idf;
    }

    public void setIdf(double idf) {
        this.idf = idf;
    }

    public double getTfIdf() {
        return tfIdf;
    }

    public void setTfIdf(double tfIdf) {
        this.tfIdf = tfIdf;
    }

    public int getFrequencia() {
        return frequencia;
    }

    public static int getMaiorFrequencia() {
        return maiorFrequencia;
    }

    public static void setMaiorFrequencia(int maiorFrequencia) {
        Termo.maiorFrequencia = maiorFrequencia;
    }

    

    public void incFrequencia() {
        frequencia++;
        if (frequencia > Termo.maiorFrequencia)
            Termo.setMaiorFrequencia(frequencia);
    }
    
    public int compareTo(Termo obj){
        return Double.compare(tf, obj.tf);
    }

  
}
