package classes.pesos.tf;

import classes.Documentos;
import classes.Termo;

/**
 *
 * @author emmanuelsilvaxavier
 */
public class LogNormalizationStrategy implements IAlgoritmoTF{
    
    public double calcularPeso(Termo termo,Documentos documento){
        return (1 + Math.log(termo.getFrequencia()));//Math.log(2));
    }
}
