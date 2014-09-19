package classes.pesos.tfidf;

import classes.Termo;
import classes.pesos.idf.InverseFrequencyStrategy;
import classes.pesos.tf.LogNormalizationStrategy;
import java.util.List;

/**
 *
 * 
 * @author emmanuelsilvaxavier
 */
public class StrategyTFIDF03 implements IAlgoritmoTFIDF{
    
        public double calcularPeso(Termo termo, List listaDocumentos){
            LogNormalizationStrategy lnStrategy = new LogNormalizationStrategy();
            InverseFrequencyStrategy ifStrategy = new InverseFrequencyStrategy();
            return lnStrategy.calcularPeso(termo,null) * ifStrategy.calcularPeso(termo, listaDocumentos);
        }
}
