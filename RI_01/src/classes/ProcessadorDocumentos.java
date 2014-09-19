package classes;

import classes.pesos.idf.IAlgoritmoIDF;
import classes.pesos.tf.IAlgoritmoTF;
import classes.pesos.tfidf.IAlgoritmoTFIDF;
import java.util.List;
import javax.swing.JOptionPane;
import principal.Main;

/**
 *
 * @author emmanuelsilvaxavier
 */
public class ProcessadorDocumentos {
    
    private IAlgoritmoTF strategyTF;
    private IAlgoritmoIDF strategyIDF;
    private IAlgoritmoTFIDF strategyTFIDF;
    
    public ProcessadorDocumentos(String strategyTF,String strategyIDF,String strategyTFIDF){
        this.strategyTF = (IAlgoritmoTF) instanciar(strategyTF);
        this.strategyIDF = (IAlgoritmoIDF) instanciar(strategyIDF);
        this.strategyTFIDF = (IAlgoritmoTFIDF) instanciar(strategyTFIDF);
    }
    
    public void processar(Documentos documento, List listaDocumentos){
        Termo termo;
        List listaTermosProcessados = documento.getListaTermosProcessados();
        
        for (int i = 0; i < listaTermosProcessados.size(); i++){
            termo = (Termo) listaTermosProcessados.get(i);
            termo.setTf(strategyTF.calcularPeso(termo,documento));
            double inverseDF = strategyIDF.calcularPeso(termo, listaDocumentos);
            termo.setIdf(inverseDF);
            Colecao.addTermo(termo,inverseDF);
            //termo.setTfIdf(strategyTFIDF.calcularPeso(termo, listaDocumentos));
            termo.setTfIdf(termo.getTf() * termo.getIdf());
        }
        
    }
    
    private Object instanciar(String nomeStrategy){
        try {
            try {
                return Class.forName(nomeStrategy).newInstance();
            } catch (ClassNotFoundException ex) {
                JOptionPane.showMessageDialog(Main.getJanela(), "Reflexão computacional: a classe "+nomeStrategy+" não existe", Main.getJanela().getTitle(), JOptionPane.ERROR_MESSAGE);
                return null;
            }
        } catch (InstantiationException ex) {
            JOptionPane.showMessageDialog(Main.getJanela(), "Reflexão computacional: falha ao instanciar a classe "+nomeStrategy, Main.getJanela().getTitle(), JOptionPane.ERROR_MESSAGE);
            return null;
        } catch (IllegalAccessException ex) {
            JOptionPane.showMessageDialog(Main.getJanela(), "Reflexão computacional: sem acesso a classe"+nomeStrategy, Main.getJanela().getTitle(), JOptionPane.ERROR_MESSAGE);
            return null;
        }
        
    }
    
}
