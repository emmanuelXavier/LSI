/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package classes.pesos.tfidf;

import classes.Termo;
import classes.pesos.idf.InverseFrequencyStrategy;
import classes.pesos.tf.DoubleNormalizationStrategy;
import classes.pesos.tf.LogNormalizationStrategy;
import java.util.List;

/**
 *
 * @author emmanuelsilvaxavier
 */
public class StrategyTFIDF04 implements IAlgoritmoTFIDF{
    public double calcularPeso(Termo termo, List listaDocumentos){
            DoubleNormalizationStrategy dbStrategy = new DoubleNormalizationStrategy();
            InverseFrequencyStrategy ifStrategy = new InverseFrequencyStrategy();
            return dbStrategy.calcularPeso(termo,null) * ifStrategy.calcularPeso(termo, listaDocumentos);
        }
}
