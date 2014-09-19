package classes;

import java.io.BufferedReader;
import java.io.FileReader;
import javax.swing.JOptionPane;
import principal.Main;

/**
 *
 * @author emmanuelsilvaxavier
 */
public class ManipuladorArquivos extends AbstractManipulador{

    public ManipuladorArquivos(String nomeFile) {
        this.setNomeFile(nomeFile);
        if (abrirArquivo()) {
            if (!carregarArquivo())
                JOptionPane.showMessageDialog(Main.getJanela(), "Falha ao ler o arquivo.", Main.getJanela().getTitle(), JOptionPane.ERROR_MESSAGE);
        } else {
            if (JOptionPane.showConfirmDialog(Main.getJanela(), "O arquivo não existe. Deseja criar o arquivo?", Main.getJanela().getTitle(), JOptionPane.INFORMATION_MESSAGE) == 0) {
                if (criarArquivo()) {
                    if (!carregarArquivo())
                        JOptionPane.showMessageDialog(Main.getJanela(), "Falha ao ler o arquivo", Main.getJanela().getTitle(), JOptionPane.ERROR_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(Main.getJanela(), "O arquivo não pode ser gerado", Main.getJanela().getTitle(), JOptionPane.ERROR_MESSAGE);
                }
            }
        }

    }

    private boolean criarArquivo() {
        try {
            if (!getFile().createNewFile())
                return false;
            return true;
        } catch (Exception erro) {
            return false;
        }
    }

    protected boolean carregarArquivo() {
        String linha;
        try {
            BufferedReader leitor = new BufferedReader(new FileReader(getNomeFile()));
            while (true) {
                linha = leitor.readLine();
                if (linha == null) {
                    break;
                }
                getLista().add(linha);
                
            }
            return true;
        } catch (Exception erro) {
            return false;
        }
    }
    
    

    
    
    
}
