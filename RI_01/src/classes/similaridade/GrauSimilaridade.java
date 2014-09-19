package classes.similaridade;

import classes.Documentos;

/**
 *
 * @author emmanuelsilvaxavier
 */
public class GrauSimilaridade implements Comparable<GrauSimilaridade> {
    private Documentos documento;
    private Double grauSimilaridade;
    
    public GrauSimilaridade(Documentos documento, double grauSimilaridade){
        this.documento = documento;
        Double nan = new Double(Double.NaN);
        this.grauSimilaridade = new Double(grauSimilaridade);
        if  (this.grauSimilaridade.equals(nan))
            this.grauSimilaridade = new Double(0);

    }

    public Documentos getDocumento() {
        return documento;
    }

    public void setDocumento(Documentos documento) {
        this.documento = documento;
    }

    public double getGrauSimilaridade() {
        return grauSimilaridade;
    }

    public void setGrauSimilaridade(double grauSimilaridade) {
        this.grauSimilaridade = grauSimilaridade;
    }
    
    public int compareTo(GrauSimilaridade  obj){
       // if(Math.abs(grauSimilaridade-obj.getGrauSimilaridade()) < ERR) return 0;    
        return Double.compare(grauSimilaridade, obj.getGrauSimilaridade());
    }
    
    
}
