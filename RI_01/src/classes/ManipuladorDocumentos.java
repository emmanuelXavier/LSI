package classes;

import javax.swing.JOptionPane;
import principal.Main;

/**
 *
 * @author emmanuelsilvaxavier
 */
public class ManipuladorDocumentos extends AbstractManipulador{
    private AbstractManipulador fileStopWords, fileAdv; 
    
    public ManipuladorDocumentos(String nomeFile, AbstractManipulador fileStopWords, AbstractManipulador fileAdv) {
        setNomeFile(nomeFile);
        setFileAdv(fileAdv);
        setFileStopWords(fileStopWords);
        if (abrirArquivo()) {
            if (getFile().listFiles().length > 0) 
                if (!carregarArquivo())
                    JOptionPane.showMessageDialog(Main.getJanela(), "Falha ao carregar documentos do diretório.", Main.getJanela().getTitle(), JOptionPane.ERROR_MESSAGE);
            
        }
    }
    
    protected boolean carregarArquivo(){
        String linha;
        int i;
        try {
            for (i = 0; i < getFile().listFiles().length; i++){
                if (getFile().listFiles()[i].getName().equals(".DS_Store"))
                    continue;
                Documentos documento = new Documentos();
                documento.setNome(getFile().listFiles()[i].getName());
                documento.setTamanho(getFile().listFiles()[i].length());
                documento.retirarStopWords(getFile().listFiles()[i], fileStopWords, fileAdv);
                documento.setTermoMaiorFrequencia(Termo.getMaiorFrequencia());
                getLista().add(documento);
                Colecao.incQtdDocumentos();
            }
            return true;
        } catch (Exception erro) {
            return false;
        }
    }

    public AbstractManipulador getFileStopWords() {
        return fileStopWords;
    }

    public void setFileStopWords(AbstractManipulador fileStopWords) {
        this.fileStopWords = fileStopWords;
    }

    public AbstractManipulador getFileAdv() {
        return fileAdv;
    }

    public void setFileAdv(AbstractManipulador fileAdv) {
        this.fileAdv = fileAdv;
    }
    
    
    
}
