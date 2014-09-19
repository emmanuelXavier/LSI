package classes.lsi;

import Jama.Matrix;
import Jama.SingularValueDecomposition;
import classes.Documentos;
import classes.TermoColecao;
import classes.Termo;
import java.util.List;

/**
 *
 * @author emmanuelsilvaxavier
 */
public class SimpleValueDecomposition {

    private double[][] matriz;
    private double[][] matrizEuclidiana;
    private double[][] matrizPesos;
    Matrix matrixK;
    private int linhas;
    private int colunas;
    private static final double PERCENTUAL = 0.5; //percentual de descarte do SVD
    private List listaTermosColecao, listaDocumentos;

    public void criarMatriz(List listaTermosColecao, List listaDocumentos) {
        int i, j, posicao;
        this.listaTermosColecao = listaTermosColecao;
        this.listaDocumentos = listaDocumentos;
        TermoColecao termoC;
        Documentos documento;
        linhas = listaTermosColecao.size();
        colunas = listaDocumentos.size();
        matriz = new double[linhas][colunas];
        for (i = 0; i < listaTermosColecao.size(); i++) {
            termoC = (TermoColecao) listaTermosColecao.get(i);
            for (j = 0; j < listaDocumentos.size(); j++) {
                documento = (Documentos) listaDocumentos.get(j);
                posicao = documento.pesquisar(termoC.getWord());
                if (posicao == -1) {
                    matriz[i][j] = 0;
                } else {
                    matriz[i][j] = ((Termo) documento.getListaTermosProcessados().get(posicao)).getFrequencia();
                }
            }
        }
        /*linhas = 16;
         colunas = 17;
         matriz = new double[16][17];
         matriz[0][2] = 1;
         matriz[0][4] = 1;
         matriz[0][6] = 1;
         matriz[1][2] = 1;
         matriz[1][16] = 1;
         matriz[2][10] = 1;
         matriz[2][11] = 1;
         matriz[3][3] = 1;
         matriz[3][7] = 1;
         matriz[3][9] = 1;
         matriz[3][10] = 1;
         matriz[3][11] = 1;
         matriz[3][12] = 1;
         matriz[3][13] = 1;
         matriz[3][14] = 1;
         matriz[4][0] = 1;
         matriz[4][1] = 1;
         matriz[4][3] = 1;
         matriz[4][7] = 1;
         matriz[4][9] = 1;
         matriz[4][10] = 1;
         matriz[4][11] = 1;
         matriz[4][12] = 1;
         matriz[4][13] = 1;
         matriz[4][14] = 1;
         matriz[5][2] = 1;
         matriz[5][6] = 1;
         matriz[6][0] = 1;
         matriz[6][15] = 1;
         matriz[6][16] = 1;
         matriz[7][4] = 1;
         matriz[7][5] = 1;
         matriz[8][7] = 1;
         matriz[8][13] = 1;
         matriz[9][8] = 1;
         matriz[9][12] = 1;
         matriz[10][7] = 1;
         matriz[10][9] = 1;
         matriz[11][10] = 1;
         matriz[11][11] = 1;
         matriz[12][3] = 1;
         matriz[12][12] = 1;
         matriz[13][5] = 1;
         matriz[13][6] = 1;
         matriz[13][15] = 1;
         matriz[14][5] = 1;
         matriz[14][7] = 1;
         matriz[14][8] = 1;
         matriz[15][2] = 1;
         matriz[15][10] = 1;
         matriz[15][11] = 1;
         matriz[15][16] = 1;

         */

        showMatriz("Matriz termos-documentos", new Matrix(matriz), listaTermosColecao, listaDocumentos);


    }

    public void gerarPesosTFIDF() {
        int i, j, posicao;
        TermoColecao termoC;
        Documentos documento;
        linhas = listaTermosColecao.size();
        colunas = listaDocumentos.size();
        matrizPesos = new double[linhas][colunas];
        for (i = 0; i < listaTermosColecao.size(); i++) {
            termoC = (TermoColecao) listaTermosColecao.get(i);
            for (j = 0; j < listaDocumentos.size(); j++) {
                documento = (Documentos) listaDocumentos.get(j);
                posicao = documento.pesquisar(termoC.getWord());
                if (posicao == -1) {
                    matrizPesos[i][j] = 0;
                } else {
                    matrizPesos[i][j] = ((Termo) documento.getListaTermosProcessados().get(posicao)).getTfIdf();
                }
            }
        }

        showMatriz("Matriz termos-documentos com pesos (TF-IDF)", new Matrix(matrizPesos), listaTermosColecao, listaDocumentos);


    }

    public void normaEuclidiana() {
        int i, j;
        double somatorio;
        double norma;
        matrizEuclidiana = new double[2][colunas]; //linha[0] -> norma euclidiana da coluna, linha[1] -> somatorio das frequencias da coluna
        for (j = 0; j < colunas; j++) {
            somatorio = 0;
            norma = 0;
            for (i = 0; i < linhas; i++) {
                somatorio += matriz[i][j];
                norma += matriz[i][j] * matriz[i][j];
            }
            matrizEuclidiana[0][j] = Math.sqrt(norma);
            matrizEuclidiana[1][j] = somatorio;
        }

    }

    public Matrix gerarPesos() {
        int i, j;

        matrizPesos = new double[linhas][colunas];
        for (i = 0; i < linhas; i++) {
            for (j = 0; j < colunas; j++) {
                if (matriz[i][j] > 0) {
                  //  matrizPesos[i][j] = matrizEuclidiana[0][j] / matrizEuclidiana[1][j];
                    matrizPesos[i][j] = matriz[i][j] / matrizEuclidiana[1][j];
                }
            }

        }

        showMatriz("Matriz termos-documentos com pesos (NormaEuclidiana)", new Matrix(matrizPesos), listaTermosColecao, listaDocumentos);

        Matrix m = pesosIDF(new Matrix(matrizPesos));
        showMatriz("Matriz termos-documentos com pesos (TF-IDF)", m, listaTermosColecao, listaDocumentos);
        return m;
    }

    public Matrix svd(Matrix a) {
        int k;
        Matrix  u, v, s;

        a = new Matrix(matriz);

        SingularValueDecomposition svd = new SingularValueDecomposition(a);

        //decomposição
        u = svd.getU();
        v = svd.getV();
        s = svd.getS();

        System.out.println("Matriz U (SVD)");
        u.print(15, 4);
        System.out.println("Matriz Somatório (SVD)");
        s.print(15, 4);
        System.out.println("Matriz V (SVD)");
        v.print(15, 4);

        Double rank = new Double(s.getColumnDimension() * PERCENTUAL);
        // k = rank.intValue();
        k = (int) Math.floor(Math.sqrt(a.getColumnDimension()));
        if (k == 0) {
            k++;
        }
        Matrix uk = u.getMatrix(0, u.getRowDimension() - 1, 0, k - 1);
        Matrix sk = s.getMatrix(0, k - 1, 0, k - 1);
        Matrix vk = v.getMatrix(0, v.getRowDimension() - 1, 0, k - 1);
        System.out.println("Nível k: " + k + "\n");
        matrixK = uk.times(sk).times(vk.transpose());
        System.out.println("Matriz Uk (SVD)");
        u.print(15, 4);
        System.out.println("Matriz Somatóriok (SVD)");
        s.print(15, 4);
        System.out.println("Matriz Vk (SVD)");
        v.print(15, 4);
        showMatriz("Matriz transformada k", matrixK, listaTermosColecao, listaDocumentos);

        for (int j = 0; j < matrixK.getColumnDimension(); j++) {
            double sum = somar(matrixK.getMatrix(0, matrixK.getRowDimension() - 1, j, j));
            for (int i = 0; i < matrixK.getRowDimension(); i++) {
                matrixK.set(i, j, Math.abs((matrixK.get(i, j)) / sum));
            }
        }
        showMatriz("Matriz transformada k", matrixK, listaTermosColecao, listaDocumentos);
        return matrixK;
    }

    public void showMatriz(String descricao, Matrix matrix, List listaTermosColecao, List listaDocumentos) {

        System.out.println(descricao);
        System.out.printf("%15s", " ");
        for (int i = 0; i < listaDocumentos.size(); i++) {
            System.out.printf("%15s", ((Documentos) listaDocumentos.get(i)).getNome());
        }
        System.out.println();
        for (int i = 0; i < listaTermosColecao.size(); i++) {
            System.out.printf("%15s", ((TermoColecao) listaTermosColecao.get(i)).getWord());
            for (int j = 0; j < listaDocumentos.size(); j++) {
                System.out.printf("%15.4f", matrix.get(i, j));
            }
            System.out.println();
        }
        System.out.println();
    }

    public Matrix pesosIDF(Matrix matrix) {
        // Phase 1: apply IDF weight to the raw word frequencies
        int n = matrix.getColumnDimension();
        for (int j = 0; j < matrix.getColumnDimension(); j++) {
            for (int i = 0; i < matrix.getRowDimension(); i++) {
                double matrixElement = matrix.get(i, j);
                if (matrixElement > 0.0D) {
                    double dm = countDocsWithWord(
                            matrix.getMatrix(i, i, 0, matrix.getColumnDimension() - 1));
                    matrix.set(i, j, matrix.get(i, j) * (1 + Math.log(n) - Math.log(dm)));
                }
            }
        }
        // Phase 2: normalize the word scores for a single document
        for (int j = 0; j < matrix.getColumnDimension(); j++) {
            double sum = sum(matrix.getMatrix(0, matrix.getRowDimension() - 1, j, j));
            for (int i = 0; i < matrix.getRowDimension(); i++) {
                matrix.set(i, j, (matrix.get(i, j) / sum));
            }
        }
        return matrix;
    }

    private double sum(Matrix colMatrix) {
        double sum = 0.0D;
        for (int i = 0; i < colMatrix.getRowDimension(); i++) {
            sum += colMatrix.get(i, 0);
        }
        return sum;
    }
    
     private double somar(Matrix colMatrix) {
    double sum = 0.0D;
    for (int i = 0; i < colMatrix.getRowDimension(); i++) {
      sum += colMatrix.get(i, 0);
    }
    return sum;
  }

    private double countDocsWithWord(Matrix rowMatrix) {
        double numDocs = 0.0D;
        for (int j = 0; j < rowMatrix.getColumnDimension(); j++) {
            if (rowMatrix.get(0, j) > 0.0D) {
                numDocs++;
            }
        }
        return numDocs;
    }
}
