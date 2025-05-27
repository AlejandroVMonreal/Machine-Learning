import java.util.*;

public class Main {
    public static void main(String[] args) {

        int[] x = {23, 26, 30, 34, 43, 48, 52, 57, 58};
        int[] y = {651, 762, 856, 1063, 1190, 1298, 1421, 1440, 1518};

        DataSet dataSet = new DataSet(x, y);

        double[] porcentajes = {0.7, 0.3, Math.random() * 0.6 + 0.2};
        String[] titulos = {"Splitting (70%/30%)", "Splitting (30%/70%)", "splitting (Aleatoria)"};

        double mejorR2 = -1;
        String mejorModeloGlobal = "";

        for (int i = 0; i < 3; i++) {
            DatasetSplitter splitter = new DatasetSplitter(dataSet, 1, porcentajes[i]);
            int[][] trainX = splitter.getTrainX();
            int[][] trainY = splitter.getTrainY();
            int[][] testX = splitter.getTestX();
            int[][] testY = splitter.getTestY();

            SLR slr = new SLR();
            CuadraticRegresion qr = new CuadraticRegresion();
            CubicRegression cr = new CubicRegression();

            double[] coefLineal = slr.calculaCoeficientes(trainX[0], trainY[0]);
            double rLineal = slr.correlationCoeficient(testX[0], testY[0], coefLineal);
            double r2Lineal = slr.determinationCoeficient(testX[0], testY[0], coefLineal);

            double[] coefCuad = qr.calculaCoeficientes(trainX[0], trainY[0]);
            double rCuad = qr.correlacion(testX[0], testY[0], coefCuad);
            double r2Cuad = qr.determinacion(testX[0], testY[0], coefCuad);

            double[] coefCub = cr.calculaCoeficientes(trainX[0], trainY[0]);
            double rCub = cr.correlacion(testX[0], testY[0], coefCub);
            double r2Cub = cr.determinacion(testX[0], testY[0], coefCub);

            if (r2Lineal > mejorR2) {
                mejorR2 = r2Lineal;
                mejorModeloGlobal = "Lineal";
            }
            if (r2Cuad > mejorR2) {
                mejorR2 = r2Cuad;
                mejorModeloGlobal = "Cuadrática";
            }
            if (r2Cub > mejorR2) {
                mejorR2 = r2Cub;
                mejorModeloGlobal = "Cúbica";
            }

            System.out.println("\n==============================");
            System.out.println(" " + titulos[i]);
            System.out.println("==============================\n");

            System.out.println("Regresión Lineal:");
            System.out.println("  Ecuación:     " + slr.ecuacion(trainX[0], trainY[0]));
            System.out.printf("  Coeficientes: [b0=%.4f, b1=%.4f]\n", coefLineal[0], coefLineal[1]);
            System.out.printf("  Correlación:  %.4f\n", rLineal);
            System.out.printf("  R²:           %.4f\n\n", r2Lineal);

            System.out.println("Regresión Cuadrática:");
            System.out.println("  Ecuación:     " + qr.ecuacion(trainX[0], trainY[0]));
            System.out.printf("  Coeficientes: [b0=%.4f, b1=%.4f, b2=%.4f]\n", coefCuad[0], coefCuad[1], coefCuad[2]);
            System.out.printf("  Correlación:  %.4f\n", rCuad);
            System.out.printf("  R²:           %.4f\n\n", r2Cuad);

            System.out.println("Regresión Cúbica:");
            System.out.println("  Ecuación:     " + cr.ecuacion(trainX[0], trainY[0]));
            System.out.printf("  Coeficientes: [b0=%.4f, b1=%.4f, b2=%.4f, b3=%.4f]\n",
                    coefCub[0], coefCub[1], coefCub[2], coefCub[3]);
            System.out.printf("  Correlación:  %.4f\n", rCub);
            System.out.printf("  R²:           %.4f\n\n", r2Cub);
        }

        System.out.println("==============================");
        System.out.println(" Mejor modelo");
        System.out.println("==============================");
        System.out.printf("Mejor R² observado: %.4f\n", mejorR2);
        System.out.println("Modelo con mejor R²: " + mejorModeloGlobal);

        System.out.println("\n===========================");
        System.out.println(" predicciones (x = 40–100)");
        System.out.println("===========================\n");

        SLR slrFinal = new SLR();
        CuadraticRegresion qrFinal = new CuadraticRegresion();
        CubicRegression crFinal = new CubicRegression();

        double[] coefL = { slrFinal.B0(x, y), slrFinal.B1(x, y) };
        double[] coefQ = qrFinal.calculaCoeficientes(x, y);
        double[] coefC = crFinal.calculaCoeficientes(x, y);

        int[] valoresX = {40, 55, 65, 75, 100};
        System.out.printf("%-6s %-12s %-15s %-15s\n", "x", "Lineal", "Cuadrática", "Cúbica");
        System.out.println("--------------------------------------------");

        for (int xi : valoresX) {
            double yl = coefL[0] + coefL[1] * xi;
            double yq = qrFinal.predecir(xi, coefQ);
            double yc = crFinal.predecir(xi, coefC);
            System.out.printf("%-6d %-12.2f %-15.2f %-15.2f\n", xi, yl, yq, yc);
        }
    }
}
