package classes.pesos.tfidf;

import classes.Termo;
import classes.pesos.tf.LogNormalizationStrategy;
import java.util.List;

/**
 *
 * @author emmanuelsilvaxavier
 */
public class StrategyTFIDF02 implements IAlgoritmoTFIDF{
    
        public double calcularPeso(Termo termo, List listaDocumentos){
            LogNormalizationStrategy lnStrategy = new LogNormalizationStrategy();
            
            return lnStrategy.calcularPeso(termo,null);
        }
}
