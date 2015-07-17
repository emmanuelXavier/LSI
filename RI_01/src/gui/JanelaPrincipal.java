package gui;

import Jama.Matrix;
import classes.AbstractManipulador;
import classes.Colecao;
import classes.Consulta;
import classes.Documentos;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.table.DefaultTableModel;
import classes.ManipuladorArquivos;
import classes.ManipuladorDocumentos;
import classes.similaridade.ModeloVetorial;
import classes.ProcessadorDocumentos;
import classes.Termo;
import classes.lsi.SimpleValueDecomposition;
import classes.similaridade.Similarity;
import classes.similaridade.LatentSemanticIndexing;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

public class JanelaPrincipal extends JFrame implements ActionListener {

    private JPanel pnTitulo, pnConteudo;
    private JLabel lbTitulo, lbCaminhoDir, lbDocumentos, lbSelecioneDoc, lbWords, lbPesquisa, lbDetalhes;
    private JButton btAddStopWord, btAddAd, btLoadStop, btLoadAdv, btSelectDir, btProcessar, btPesquisa, btLsi;
    private JTextField tfCaminhoDir, tfPesquisa;
    private JTable tStopWords, tAdverAdj, tDocumentos, tWords, tIdf;
    private DefaultTableModel modeloStop, modeloAdv, modeloDoc, modeloWords, modeloIdf;
    private AbstractManipulador fileStopWords, fileAdv, dirDocumentos;
    private Matrix matrixK;

    public JanelaPrincipal(String titulo, String caption) {
        super(titulo);

        this.pack();
        this.setSize(new Dimension(1020, 620));
        //this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getContentPane().setLayout(new BorderLayout());

        //Painel titulo
        pnTitulo = new JPanel();
        this.add(pnTitulo, BorderLayout.NORTH);
        pnTitulo.setLayout(new BorderLayout());
        pnTitulo.setPreferredSize(new Dimension(1, 40));
        pnTitulo.setBackground(new Color(51, 102, 0));
        lbTitulo = new JLabel(" " + caption);
        lbTitulo.setForeground(Color.WHITE);
        lbTitulo.setFont(new Font("Century Gothic", Font.BOLD, 17));
        pnTitulo.add(lbTitulo, BorderLayout.WEST);
        lbDetalhes = new JLabel();
        lbDetalhes.setForeground(Color.WHITE);
        lbDetalhes.setFont(new Font("Century Gothic", Font.BOLD, 12));
        pnTitulo.add(lbDetalhes, BorderLayout.EAST);

        //painel conteudo
        SpringLayout layout = new SpringLayout();
        pnConteudo = new JPanel(layout);
        this.add(pnConteudo);
        pnConteudo.setBackground(Color.WHITE);


        Icon iBtDelete = new ImageIcon("src/resources/images/delete.png");//Luis, caso as imagens nos botões não carreguem, favor alterar a barra "\"
        Icon iBtAdicionar = new ImageIcon("src/resources/images/add.png");
        Icon iBtLoad = new ImageIcon("src/resources/images/load.png");

        btAddStopWord = new JButton("Adicionar", iBtAdicionar);
        btAddStopWord.addActionListener(this);

        btLoadStop = new JButton("Carregar", iBtLoad);
        btLoadStop.addActionListener(this);

        btAddAd = new JButton("Adicionar", iBtAdicionar);
        btAddAd.addActionListener(this);

        btLoadAdv = new JButton("Carregar", iBtLoad);
        btLoadAdv.addActionListener(this);

        tStopWords = new JTable();
        tStopWords.setSize(200, 200);
        Object[] colunas = new Object[]{"StopWords"};
        modeloStop = new DefaultTableModel();
        modeloStop.setColumnIdentifiers(colunas);
        tStopWords.setModel(modeloStop);
        JScrollPane jsStop = new JScrollPane(tStopWords);
        jsStop.setPreferredSize(new Dimension(300, 200));

        tAdverAdj = new JTable();
        tAdverAdj.setSize(200, 200);
        Object[] colunasAdv = new Object[]{"Advérbios e adjetivos"};
        modeloAdv = new DefaultTableModel();
        modeloAdv.setColumnIdentifiers(colunasAdv);
        tAdverAdj.setModel(modeloAdv);
        JScrollPane jsAdv = new JScrollPane(tAdverAdj);
        jsAdv.setPreferredSize(new Dimension(300, 200));

        lbCaminhoDir = new JLabel("Diretório com os documentos");

        tfCaminhoDir = new JTextField();
        tfCaminhoDir.setEditable(false);
        tfCaminhoDir.setPreferredSize(new Dimension(500, 40));

        btSelectDir = new JButton("Selecionar diretório");
        btSelectDir.setPreferredSize(new Dimension(150, 40));
        btSelectDir.addActionListener(this);

        lbDocumentos = new JLabel("Documentos encontrados");

        lbSelecioneDoc = new JLabel("Selecione um documento e clique em processar");

        tDocumentos = new JTable();
        tDocumentos.setSize(200, 200);
        Object[] colunasDoc = new Object[]{"Nome", "Tamanho", "Qt. de palavras", "Qt. de stopwords", "Qt. de adjetivos e advérbios"};
        modeloDoc = new DefaultTableModel();
        modeloDoc.setColumnIdentifiers(colunasDoc);
        tDocumentos.setModel(modeloDoc);
        JScrollPane jsDoc = new JScrollPane(tDocumentos);
        jsDoc.setPreferredSize(new Dimension(650, 140));

        btProcessar = new JButton("Processar");
        btProcessar.setPreferredSize(new Dimension(110, 40));
        btProcessar.addActionListener(this);

        lbWords = new JLabel("Documento processado");

        tWords = new JTable();
        tWords.setSize(200, 200);
        Object[] colunasWords = new Object[]{"Palavra", "Frequência", "TF", "IDF", "TF-IDF"};
        modeloWords = new DefaultTableModel();
        modeloWords.setColumnIdentifiers(colunasWords);
        tWords.setModel(modeloWords);
        JScrollPane jsWords = new JScrollPane(tWords);
        jsWords.setPreferredSize(new Dimension(650, 140));

        lbPesquisa = new JLabel("Termos da consulta");

        tfPesquisa = new JTextField();
        tfPesquisa.setPreferredSize(new Dimension(500, 40));

        btPesquisa = new JButton("Consultar");
        btPesquisa.setPreferredSize(new Dimension(150, 40));
        btPesquisa.addActionListener(this);

        btLsi = new JButton("Matriz");
        btLsi.setPreferredSize(new Dimension(150, 40));
        btLsi.addActionListener(this);

        pnConteudo.add(jsStop);
        layout.putConstraint(SpringLayout.NORTH, jsStop, 20, SpringLayout.NORTH, pnConteudo);
        layout.putConstraint(SpringLayout.WEST, jsStop, 10, SpringLayout.WEST, pnConteudo);

        pnConteudo.add(btAddStopWord);
        layout.putConstraint(SpringLayout.NORTH, btAddStopWord, 5, SpringLayout.SOUTH, jsStop);
        layout.putConstraint(SpringLayout.EAST, btAddStopWord, 0, SpringLayout.EAST, jsStop);

        pnConteudo.add(btLoadStop);
        layout.putConstraint(SpringLayout.NORTH, btLoadStop, 5, SpringLayout.SOUTH, jsStop);
        layout.putConstraint(SpringLayout.WEST, btLoadStop, 0, SpringLayout.WEST, jsStop);

        pnConteudo.add(btAddAd);
        layout.putConstraint(SpringLayout.NORTH, btAddAd, 5, SpringLayout.SOUTH, jsAdv);
        layout.putConstraint(SpringLayout.EAST, btAddAd, 0, SpringLayout.EAST, jsAdv);

        pnConteudo.add(btLoadAdv);
        layout.putConstraint(SpringLayout.NORTH, btLoadAdv, 5, SpringLayout.SOUTH, jsAdv);
        layout.putConstraint(SpringLayout.WEST, btLoadAdv, 0, SpringLayout.WEST, jsAdv);


        pnConteudo.add(jsAdv);
        layout.putConstraint(SpringLayout.NORTH, jsAdv, 20, SpringLayout.SOUTH, btAddStopWord);
        layout.putConstraint(SpringLayout.WEST, jsAdv, 10, SpringLayout.WEST, pnConteudo);

        pnConteudo.add(lbCaminhoDir);
        layout.putConstraint(SpringLayout.NORTH, lbCaminhoDir, 20, SpringLayout.NORTH, pnConteudo);
        layout.putConstraint(SpringLayout.WEST, lbCaminhoDir, 20, SpringLayout.EAST, jsStop);

        pnConteudo.add(tfCaminhoDir);
        layout.putConstraint(SpringLayout.NORTH, tfCaminhoDir, 1, SpringLayout.SOUTH, lbCaminhoDir);
        layout.putConstraint(SpringLayout.WEST, tfCaminhoDir, 0, SpringLayout.WEST, lbCaminhoDir);

        pnConteudo.add(btSelectDir);
        layout.putConstraint(SpringLayout.NORTH, btSelectDir, 0, SpringLayout.NORTH, tfCaminhoDir);
        layout.putConstraint(SpringLayout.WEST, btSelectDir, 1, SpringLayout.EAST, tfCaminhoDir);

        pnConteudo.add(lbDocumentos);
        layout.putConstraint(SpringLayout.NORTH, lbDocumentos, 10, SpringLayout.SOUTH, tfCaminhoDir);
        layout.putConstraint(SpringLayout.WEST, lbDocumentos, 0, SpringLayout.WEST, tfCaminhoDir);

        pnConteudo.add(lbSelecioneDoc);
        layout.putConstraint(SpringLayout.NORTH, lbSelecioneDoc, 10, SpringLayout.SOUTH, tfCaminhoDir);
        layout.putConstraint(SpringLayout.EAST, lbSelecioneDoc, 0, SpringLayout.EAST, btSelectDir);

        pnConteudo.add(jsDoc);
        layout.putConstraint(SpringLayout.NORTH, jsDoc, 5, SpringLayout.SOUTH, lbDocumentos);
        layout.putConstraint(SpringLayout.WEST, jsDoc, 0, SpringLayout.WEST, lbDocumentos);

        //  pnConteudo.add(btProcessar);
        //  layout.putConstraint(SpringLayout.NORTH, btProcessar, 5, SpringLayout.SOUTH, jsDoc);
        //  layout.putConstraint(SpringLayout.EAST, btProcessar, 0, SpringLayout.EAST, jsDoc);

        pnConteudo.add(lbWords);
        layout.putConstraint(SpringLayout.NORTH, lbWords, 48, SpringLayout.SOUTH, jsDoc);
        layout.putConstraint(SpringLayout.WEST, lbWords, 0, SpringLayout.WEST, jsDoc);

        pnConteudo.add(jsWords);
        layout.putConstraint(SpringLayout.NORTH, jsWords, 5, SpringLayout.SOUTH, lbWords);
        layout.putConstraint(SpringLayout.WEST, jsWords, 0, SpringLayout.WEST, lbWords);

        pnConteudo.add(lbPesquisa);
        layout.putConstraint(SpringLayout.NORTH, lbPesquisa, 18, SpringLayout.SOUTH, jsWords);
        layout.putConstraint(SpringLayout.WEST, lbPesquisa, 0, SpringLayout.WEST, jsWords);

        pnConteudo.add(tfPesquisa);
        layout.putConstraint(SpringLayout.NORTH, tfPesquisa, 5, SpringLayout.SOUTH, lbPesquisa);
        layout.putConstraint(SpringLayout.WEST, tfPesquisa, 0, SpringLayout.WEST, lbPesquisa);

        pnConteudo.add(btPesquisa);
        layout.putConstraint(SpringLayout.NORTH, btPesquisa, 0, SpringLayout.NORTH, tfPesquisa);
        layout.putConstraint(SpringLayout.WEST, btPesquisa, 1, SpringLayout.EAST, tfPesquisa);

        //pnConteudo.add(btLsi);
        //layout.putConstraint(SpringLayout.NORTH, btLsi, 0, SpringLayout.NORTH, btPesquisa);
        //layout.putConstraint(SpringLayout.WEST, btLsi, 1, SpringLayout.EAST, btPesquisa);

        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(btLoadStop)) {
            carregarStopWords();
        }

        if (e.getSource().equals(btLoadAdv)) {
            carregarAdv();
        }
        if (e.getSource().equals(btAddStopWord)) {
            if (fileStopWords == null) {
                fileStopWords = new ManipuladorArquivos("stopwords.txt");
            }
            String item = JOptionPane.showInputDialog(this, "Informe o stopword", getTitle(), JOptionPane.INFORMATION_MESSAGE);
            if (item != null) {
                fileStopWords.add(item, modeloStop);
            }
        }

        if (e.getSource().equals(btAddAd)) {
            if (fileAdv == null) {
                fileAdv = new ManipuladorArquivos("adverbios.txt");
            }
            String item = JOptionPane.showInputDialog(this, "Informe o advérbio, adjetivo", getTitle(), JOptionPane.INFORMATION_MESSAGE);
            if (item != null) {
                fileAdv.add(item, modeloAdv);
            }
        }

        if (e.getSource().equals(btSelectDir)) {
            carregarStopWords();
            carregarAdv();
            JFileChooser fChooser = new JFileChooser();
            fChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            if (fChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                tfCaminhoDir.setText(fChooser.getSelectedFile().getPath());
                dirDocumentos = new ManipuladorDocumentos(fChooser.getSelectedFile().getPath(), fileStopWords, fileAdv);
                carregarDocumentos(dirDocumentos, modeloDoc);
                processarDocumentos();
            }
        }
        if (e.getSource().equals(btProcessar)) {
            int indice = tDocumentos.getSelectedRow();
            if (indice == -1) {
                JOptionPane.showMessageDialog(this, "Selecione um documento.", getTitle(), JOptionPane.INFORMATION_MESSAGE);
                return;
            } else {
                String[] strategiesTF = new String[]{"RawFrequencyStrategy", "LogNormalizationStrategy", "DoubleNormalizationStrategy"};
                String strategyTF = "DoubleNormalizationStrategy";//(String) JOptionPane.showInputDialog(this, "Escolha o algoritmo de TF", getTitle(), JOptionPane.INFORMATION_MESSAGE, null, strategiesTF, strategiesTF[0]);

                String[] strategiesIDF = new String[]{"InverseFrequencyStrategy", "InvFrequencySmoothStrategy", "InvFrequencyMaxStrategy", "ProbabilisticInvFrequencyStrategy"};
                String strategyIDF = "InverseFrequencyStrategy";//(String) JOptionPane.showInputDialog(this, "Escolha o algoritmo de IDF", getTitle(), JOptionPane.INFORMATION_MESSAGE, null, strategiesIDF, strategiesIDF[0]);

                String[] strategiesTFIDF = new String[]{"StrategyTFIDF01", "StrategyTFIDF02", "StrategyTFIDF03"};
                String strategyTFIDF = "StrategyTFIDF04";//(String) JOptionPane.showInputDialog(this, "Escolha o algoritmo de IDF", getTitle(), JOptionPane.INFORMATION_MESSAGE, null, strategiesTFIDF, strategiesTFIDF[0]);

                ProcessadorDocumentos processador = new ProcessadorDocumentos("classes.pesos.tf." + strategyTF, "classes.pesos.idf." + strategyIDF, "classes.pesos.tfidf." + strategyTFIDF);
                processador.processar((Documentos) dirDocumentos.getLista().get(indice), dirDocumentos.getLista());
                carregarTermos((Documentos) dirDocumentos.getLista().get(indice), modeloWords);

            }

        }

        if (e.getSource().equals(btPesquisa)) {
            Consulta consulta = new Consulta();
            consulta.retirarStopWords(tfPesquisa.getText(), fileStopWords, fileAdv);
            Matrix vetorConsulta = consulta.processarConsultaLSI(Colecao.getListaTermosColecao());
            System.out.print("Vetor Consulta");
            vetorConsulta.print(8, 3);
            LatentSemanticIndexing lsi = new LatentSemanticIndexing();
            lsi.calcularSimilaridade(Colecao.getListaTermosColecao(), dirDocumentos.getLista(), matrixK, vetorConsulta);
            lsi.showSimilaridadeColecao();
            //consulta.processarConsulta();
            //ModeloVetorial vetorial = new ModeloVetorial();
            //vetorial.calcularSimilaridade(consulta.getListaTermosProcessados(), dirDocumentos.getLista());
            //vetorial.showSimilaridadeColecao();
        }
        if (e.getSource().equals(btLsi)) {
        }

    }

    private void processarDocumentos() {
        int indice;
        String[] strategiesTF = new String[]{"RawFrequencyStrategy", "LogNormalizationStrategy", "DoubleNormalizationStrategy"};
        String strategyTF = "DoubleNormalizationStrategy";//(String) JOptionPane.showInputDialog(this, "Escolha o algoritmo de TF", getTitle(), JOptionPane.INFORMATION_MESSAGE, null, strategiesTF, strategiesTF[0]);

        String[] strategiesIDF = new String[]{"InverseFrequencyStrategy", "InvFrequencySmoothStrategy", "InvFrequencyMaxStrategy", "ProbabilisticInvFrequencyStrategy"};
        String strategyIDF = "InvFrequencySmoothStrategy";//(String) JOptionPane.showInputDialog(this, "Escolha o algoritmo de IDF", getTitle(), JOptionPane.INFORMATION_MESSAGE, null, strategiesIDF, strategiesIDF[0]);

        String[] strategiesTFIDF = new String[]{"StrategyTFIDF01", "StrategyTFIDF02", "StrategyTFIDF03"};
        String strategyTFIDF = "StrategyTFIDF04";//(String) JOptionPane.showInputDialog(this, "Escolha o algoritmo de IDF", getTitle(), JOptionPane.INFORMATION_MESSAGE, null, strategiesTFIDF, strategiesTFIDF[0]);
        ProcessadorDocumentos processador;

        for (indice = 0; indice < Colecao.getQtdDocumentos(); indice++) {

            processador = new ProcessadorDocumentos("classes.pesos.tf." + strategyTF, "classes.pesos.idf." + strategyIDF, "classes.pesos.tfidf." + strategyTFIDF);
            processador.processar((Documentos) dirDocumentos.getLista().get(indice), dirDocumentos.getLista());
            Collections.sort(Colecao.getListaTermosColecao());
            carregarTermos((Documentos) dirDocumentos.getLista().get(indice), modeloWords);
          //  JOptionPane.showMessageDialog(this, "O documento \""+((Documentos) dirDocumentos.getLista().get(indice)).getNome() + "\" processado com sucesso.", getTitle(), JOptionPane.INFORMATION_MESSAGE);
            
        }
        
        gerarSvd();
    }

    private void gerarSvd() {
        SimpleValueDecomposition svd = new SimpleValueDecomposition();
        svd.criarMatriz(Colecao.getListaTermosColecao(), dirDocumentos.getLista());
        svd.normaEuclidiana();
        Matrix a = svd.gerarPesos();
        //svd.gerarPesosTFIDF();
        this.matrixK = svd.svd(a);
        JOptionPane.showMessageDialog(this, "As matrizes geradas pelo SVD estão impressas na saída padrão.", getTitle(), JOptionPane.INFORMATION_MESSAGE);
    }

    private void carregarTermos(Documentos documento, DefaultTableModel modelo) {
        Termo termo;
        int i, tam = modelo.getRowCount();
        for (i = 0; i < tam; i++) {
            modelo.removeRow(0);
        }




        List listaTermosProcessados = documento.getListaTermosProcessados();
        Collections.sort(listaTermosProcessados);



        if (listaTermosProcessados.isEmpty()) {
            JOptionPane.showMessageDialog(this, "O documento não possui termos válidos.", getTitle(), JOptionPane.INFORMATION_MESSAGE);
        }
        for (i = 0; i < listaTermosProcessados.size(); i++) {
            List linha = new ArrayList();
            termo = (Termo) listaTermosProcessados.get(i);
            linha.add(termo.getWord());
            linha.add(termo.getFrequencia());
            linha.add(termo.getTf());
            linha.add(termo.getIdf());
            linha.add(termo.getTfIdf());
            modelo.addRow(linha.toArray());
        }
    }

    private void carregarDocumentos(AbstractManipulador file, DefaultTableModel modelo) {
        int i, tam = modelo.getRowCount();
        Documentos documento;
        for (i = 0; i < tam; i++) {
            modelo.removeRow(0);
        }

        if (file.getLista().size() == 0) {
            JOptionPane.showMessageDialog(this, "O diretório está vazio.", getTitle(), JOptionPane.INFORMATION_MESSAGE);
        }
        for (i = 0; i < file.getLista().size(); i++) {
            List linha = new ArrayList();
            documento = (Documentos) file.getLista().get(i);
            linha.add(documento.getNome());
            linha.add(documento.getTamanho());
            linha.add(documento.getQtPalavras());
            linha.add(documento.getQtStopWord());
            linha.add(documento.getQtAdv());
            modelo.addRow(linha.toArray());
        }
    }

    private void carregarTabela(AbstractManipulador file, DefaultTableModel modelo) {
        int i, tam = modelo.getRowCount();
        for (i = 0; i < tam; i++) {
            modelo.removeRow(0);
        }

        Vector<Object> dados = new Vector<Object>(file.getLista());
        if (dados.size() == 0) {
            JOptionPane.showMessageDialog(this, "O arquivo está vazio.", getTitle(), JOptionPane.INFORMATION_MESSAGE);
        }
        for (i = 0; i < dados.size(); i++) {
            Vector<Object> linha = new Vector<Object>();
            linha.add(dados.get(i));
            modelo.addRow(linha);
        }
    }

    private void carregarAdv() {
        if (fileAdv == null) {
            fileAdv = new ManipuladorArquivos("adverbios.txt");
        }
        carregarTabela(fileAdv, modeloAdv);
    }

    private void carregarStopWords() {
        if (fileStopWords == null) {
            fileStopWords = new ManipuladorArquivos("stopwords.txt");
        }
        carregarTabela(fileStopWords, modeloStop);
    }

    public JLabel getLbDetalhes() {
        return lbDetalhes;
    }
}
