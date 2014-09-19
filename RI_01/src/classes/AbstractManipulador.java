/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import principal.Main;

/**
 *
 * @author emmanuelsilvaxavier
 */
public abstract class AbstractManipulador {

    private List lista = new ArrayList();
    private String nomeFile;
    private File file;

    public List getLista() {
        return lista;
    }

    public void setLista(List lista) {
        this.lista = lista;
    }

    public String getNomeFile() {
        return nomeFile;
    }

    public void setNomeFile(String nomeFile) {
        this.nomeFile = nomeFile;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    protected boolean abrirArquivo() {
        setFile(new File(getNomeFile()));
        return getFile().exists();
    }
    
    public void add(String item, DefaultTableModel modelo){
        try {
            FileWriter writer = new FileWriter(getFile(),true);
            getLista().add(item.toLowerCase());
            writer.write(item.toLowerCase()+"\n");
            writer.flush();
            writer.close();
            modelo.addRow(new Object[] {item.toLowerCase()});
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(Main.getJanela(), "Falha ao escrever no arquivo.", Main.getJanela().getTitle(), JOptionPane.ERROR_MESSAGE);
        }
    }

    protected abstract boolean carregarArquivo();
}
