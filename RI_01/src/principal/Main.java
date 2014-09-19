package principal;

import gui.JanelaPrincipal;
import javax.swing.JFrame;

/**
 *
 * @author emmanuelsilvaxavier
 */
public class Main {
    private static JanelaPrincipal janela;
    
    public static void main(String[] args) {
        
                 
                 
                 
     Main.janela = new JanelaPrincipal("Mestrado em engenharia da computação","Laboratório de Information Retrieval");

    }

    public static JanelaPrincipal getJanela() {
        return Main.janela;
    }

    public static void setJanela(JanelaPrincipal janela) {
        Main.janela = janela;
    }
    
    
}
