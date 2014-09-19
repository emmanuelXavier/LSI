package classes.pesos.tf;

import classes.Documentos;
import classes.Termo;

/**
 *
 * @author emmanuelsilvaxavier
 */
public class DoubleNormalizationStrategy implements IAlgoritmoTF{
    private static final double constantek = 0.5;
    
    public double calcularPeso(Termo termo,Documentos documento){
        return constantek + (1 - constantek) * ((float)termo.getFrequencia()/documento.getTermoMaiorFrequencia());
    }
}

