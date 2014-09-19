package classes.pesos.tf;

import classes.Documentos;
import classes.Termo;

/**
 *
 * @author emmanuelsilvaxavier
 */
public class RawFrequencyStrategy implements IAlgoritmoTF{
    
    public double calcularPeso(Termo termo,Documentos documento){
        return termo.getFrequencia();
    }
}
