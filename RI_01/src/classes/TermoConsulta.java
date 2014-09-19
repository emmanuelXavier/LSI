package classes;

/**
 *
 * @author emmanuelsilvaxavier
 */
public class TermoConsulta {
   
    private String word;
    private int frequencia;
    private double peso;

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public int getFrequencia() {
        return frequencia;
    }

    

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }
    
    public void incFrequencia() {
        frequencia++;
    }
    
    
    
    
}
