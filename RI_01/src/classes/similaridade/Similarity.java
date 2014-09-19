package classes.similaridade;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.JOptionPane;
import principal.Main;

/**
 *
 * @author emmanuelsilvaxavier
 */
public class Similarity {

    protected List listaGrauSimilaridade = new ArrayList();

    public void showSimilaridadeColecao() {
        Collections.reverse(listaGrauSimilaridade);
        String texto = "Grau de similaridade da Consulta com os documentos da coleção\n";
        int i;
        GrauSimilaridade sim;
        for (i = 0; i < listaGrauSimilaridade.size(); i++) {
            sim = (GrauSimilaridade) listaGrauSimilaridade.get(i);
            texto = texto + "\n" + sim.getDocumento().getNome() + "\t Grau de similaridade: " + new DecimalFormat("0.00").format(sim.getGrauSimilaridade() * 100) + " %";
        }
        JOptionPane.showMessageDialog(Main.getJanela(), texto, Main.getJanela().getTitle(), JOptionPane.INFORMATION_MESSAGE);
    }
}
