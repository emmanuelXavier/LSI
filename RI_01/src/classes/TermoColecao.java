package classes;


/**
 *
 * @author emmanuelsilvaxavier
 */
public class TermoColecao  implements Comparable<TermoColecao>{
    private String word;
    private double idf;

    
    public TermoColecao(String word, double idf){
        this.word = word;
        this.idf = idf;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public double getIdf() {
        return idf;
    }

    public void setIdf(double idf) {
        this.idf = idf;
    }

   
    public int compareTo(TermoColecao  obj){
        return word.compareTo(obj.getWord());
    }
    
    
    
}
