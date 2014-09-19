package classes.pesos.idf;

import classes.Documentos;
import classes.Termo;
import java.util.List;

/**
 *
 * @author emmanuelsilvaxavier
 */
public abstract class IAlgoritmoIDF {

    public abstract double calcularPeso(Termo termo, List listaDocumentos);

    protected int getQtDocumentosTermo(String termo, List listaDocumentos) {
        int qtDocumentos = 0;
        List listaTermosProcessados;
        for (int i = 0; i < listaDocumentos.size(); i++){
            listaTermosProcessados = ((Documentos) listaDocumentos.get(i)).getListaTermosProcessados();
            for (int j = 0; j < listaTermosProcessados.size(); j++){
                if (termo.equals(((Termo) listaTermosProcessados.get(j)).getWord())){
                    qtDocumentos++;
                    break;
                }
            }
        }
        return qtDocumentos;
    }
}
