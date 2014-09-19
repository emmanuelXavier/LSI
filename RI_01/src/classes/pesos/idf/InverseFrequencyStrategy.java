package classes.pesos.idf;

import classes.Termo;
import java.util.List;

/**
 *
 * @author emmanuelsilvaxavier
 */
public class InverseFrequencyStrategy extends IAlgoritmoIDF{
    
    public double calcularPeso(Termo termo, List listaDocumentos){
        int qtDocumentosTermo = getQtDocumentosTermo(termo.getWord(),listaDocumentos);
        int qtDocumentos = listaDocumentos.size();
        return Math.log( (float) qtDocumentos/qtDocumentosTermo);//Math.log(2);
    }
    

}
