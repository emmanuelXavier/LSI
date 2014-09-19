package classes.similaridade;

import classes.Documentos;
import classes.Termo;
import classes.TermoConsulta;
import java.util.List;
/**
 *
 * @author emmanuelsilvaxavier
 */
public class ModeloVetorial extends Similarity{

   
    public void calcularSimilaridade(List listaTermosConsulta, List listaDocumentos) {
        double sim, simC, simB, simD;
        TermoConsulta termoConsulta;
        Termo termo, t;
        int posicao;
        Documentos documento;
        GrauSimilaridade grauSim;
        for (int i = 0; i < listaDocumentos.size(); i++) {
            simB = simC = simD = 0;
            documento = (Documentos) listaDocumentos.get(i);
            for (int j = 0; j < listaTermosConsulta.size(); j++) {
                termoConsulta = (TermoConsulta) listaTermosConsulta.get(j);
                if (termoConsulta.getPeso() == 0) {
                    continue;
                }

                posicao = documento.pesquisar(termoConsulta.getWord());
                if (posicao == -1) {
                    continue;
                }
                termo = (Termo) documento.getListaTermosProcessados().get(posicao);
                simC = simC + (termo.getTfIdf() * termoConsulta.getPeso());
                simB = simB + (termoConsulta.getPeso() * termoConsulta.getPeso());

            }
            for (int j = 0; j < documento.getListaTermosProcessados().size(); j++) {
                t = (Termo) documento.getListaTermosProcessados().get(j);
                simD = simD + (t.getTfIdf() * t.getTfIdf());
            }
            sim = simC / (Math.sqrt(simD) * Math.sqrt(simB));
            listaGrauSimilaridade.add(new GrauSimilaridade(documento, sim));
        }
    }

   
}
