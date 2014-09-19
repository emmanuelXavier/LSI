package classes.pesos.tfidf;

import classes.Termo;
import java.util.List;

/**
 *
 * @author emmanuelsilvaxavier
 */
public interface IAlgoritmoTFIDF {

    public double calcularPeso(Termo termo, List listaDocumentos);

}
