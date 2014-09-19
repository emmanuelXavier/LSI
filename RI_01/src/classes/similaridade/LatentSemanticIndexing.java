package classes.similaridade;

import Jama.Matrix;
import classes.Documentos;
import classes.Termo;
import classes.TermoConsulta;
import java.util.List;

/**
 *
 * @author emmanuelsilvaxavier
 */
public class LatentSemanticIndexing extends Similarity{
    
    public void calcularSimilaridade(List listaTermosColecao, List listaDocumentos,Matrix matrixk, Matrix vetorConsulta) {
       double similaridade, sumConsulta, sumMult, sumDocumento;
        TermoConsulta termoConsulta;
        Termo termo, t;
        int posicao,iDoc,iTerm;
        Documentos documento;
        GrauSimilaridade grauSim;
       
    	for(iDoc = 0; iDoc < listaDocumentos.size(); iDoc++) {
    		similaridade = sumConsulta = sumDocumento = sumMult = 0;
    		
    		for(iTerm = 0; iTerm < listaTermosColecao.size(); iTerm++) {
    			sumMult += (matrixk.get(iTerm, iDoc) * vetorConsulta.get(iTerm,0));
    			sumConsulta += (vetorConsulta.get(iTerm,0) * vetorConsulta.get(iTerm,0));
    			sumDocumento += (matrixk.get(iTerm, iDoc) * matrixk.get(iTerm, iDoc));
    		}
                
    		similaridade = sumMult / (Math.sqrt(sumConsulta * sumDocumento));
             //   JOptionPane.showMessageDialog(null, similaridade+" = "+ sumMult + " / (raiz( "+sumConsulta + " * " + sumDocumento+" )");
    		documento = (Documentos) listaDocumentos.get(iDoc);
                listaGrauSimilaridade.add(new GrauSimilaridade(documento, similaridade));
    	}   
    }
     
}
