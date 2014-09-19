package classes.pesos.idf;

import classes.Termo;
import java.util.List;

/**
 *
 * @author emmanuelsilvaxavier
 */
public class InvFrequencyMaxStrategy extends IAlgoritmoIDF{
    private static int termoMaiorFrequencia = 0;
    
    
    
    public double calcularPeso(Termo termo, List listaDocumentos){
        int qtDocumentosTermo = getQtDocumentosTermo(termo.getWord(),listaDocumentos);
        if (qtDocumentosTermo > termoMaiorFrequencia)
            termoMaiorFrequencia = qtDocumentosTermo; 
        return Math.log((float)(1 + (float) InvFrequencyMaxStrategy.termoMaiorFrequencia/qtDocumentosTermo));//Math.log(2);
    }
    

}