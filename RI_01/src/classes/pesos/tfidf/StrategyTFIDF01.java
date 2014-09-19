package classes.pesos.tfidf;

import classes.Termo;
import classes.pesos.idf.InverseFrequencyStrategy;
import classes.pesos.tf.RawFrequencyStrategy;
import java.util.List;

/**
 *
 * @author emmanuelsilvaxavier
 */
public class StrategyTFIDF01 implements IAlgoritmoTFIDF{
    
        public double calcularPeso(Termo termo, List listaDocumentos){
            RawFrequencyStrategy rfStrategy = new RawFrequencyStrategy();
            InverseFrequencyStrategy ifStrategy = new InverseFrequencyStrategy();
            return rfStrategy.calcularPeso(termo,null) * ifStrategy.calcularPeso(termo, listaDocumentos);
        }
}
