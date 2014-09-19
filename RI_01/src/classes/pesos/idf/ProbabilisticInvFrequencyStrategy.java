package classes.pesos.idf;

import classes.Termo;
import java.util.List;

/**
 *
 * @author emmanuelsilvaxavier
 */
public class ProbabilisticInvFrequencyStrategy extends IAlgoritmoIDF{
    
    public double calcularPeso(Termo termo, List listaDocumentos){
        int qtDocumentosTermo = getQtDocumentosTermo(termo.getWord(),listaDocumentos);
        int qtDocumentos = listaDocumentos.size();
        return Math.log(((float)((float)(qtDocumentos-qtDocumentosTermo))/qtDocumentosTermo));///Math.log(2);
    }
    

}
