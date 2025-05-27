import java.util.Arrays;

public class SLR {

    public int sumX(int[] x){
        int sumaX = 0;
        for (int i = 0; i < x.length; i++){
            sumaX += x[i];
        }
        return sumaX;
    }

    public int sumX2(int[] x){
        int sumaX2 = 0;
        for (int i = 0; i < x.length; i++){
            sumaX2 += x[i] * x[i];
        }
        return sumaX2;
    }

    public int sumY(int[] y){
        int sumaY = 0;
        for (int i = 0; i < y.length; i++){
            sumaY += y[i];
        }
        return sumaY;
    }

    public int sumY2(int[] y){
        int sumaY2 = 0;
        for (int i = 0; i < y.length; i++){
            sumaY2 += y[i] * y[i];
        }
        return sumaY2;
    }

    public int sumXY(int[] x, int[] y){
        int sumaXY = 0;
        for (int i = 0; i < x.length; i++){
            sumaXY += x[i] * y[i];
        }
        return sumaXY;
    }

    public double B1(int[] x, int[] y){
        int n = x.length;
        int sumX = sumX(x);
        int sumY = sumY(y);
        int sumX2 = sumX2(x);
        int sumXY = sumXY(x, y);

        return (double) (n * sumXY - sumX * sumY) / (n * sumX2 - sumX * sumX);
    }

    public double B0(int[] x, int[] y){
        int n = x.length;
        double b1 = B1(x, y);
        int sumX = sumX(x);
        int sumY = sumY(y);

        return (double) (sumY - b1 * sumX) / n;
    }

    public double[] calculaCoeficientes(int[] x, int[] y) {
        return new double[]{ B0(x, y), B1(x, y) };
    }

    public double correlationCoeficient(int[] xTest, int[] yTest, double[] coef) {
        double xProm = Arrays.stream(xTest).average().orElse(0);
        double yProm = Arrays.stream(yTest).average().orElse(0);

        double cov = 0, varX = 0, varY = 0;

        for (int i = 0; i < xTest.length; i++) {
            double x = xTest[i];
            double y = yTest[i];
            cov += (x - xProm) * (y - yProm);
            varX += Math.pow(x - xProm, 2);
            varY += Math.pow(y - yProm, 2);
        }

        return cov / Math.sqrt(varX * varY);
    }

    public double determinationCoeficient(int[] x, int[] y, double[] coef) {
        double r = correlationCoeficient(x, y, coef);
        return r * r;
    }

    public String ecuacion(int[] x, int[] y){
        double b0 = B0(x, y);
        double b1 = B1(x, y);
        return String.format("y = %.4f + %.4f * x", b0, b1);
    }

    public double predecir(int x, double[] coef) {
        return coef[0] + coef[1] * x;
    }
}
