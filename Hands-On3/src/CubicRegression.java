import java.util.Arrays;

public class CubicRegression {

    public double sum(double[] values) {
        double sum = 0;
        for (double v : values) sum += v;
        return sum;
    }

    public double sumX(int[] x) {
        double sumaX = 0;
        for (int value : x) sumaX += value;
        return sumaX;
    }

    public double sumX2(int[] x) {
        double sumaX2 = 0;
        for (int value : x) sumaX2 += Math.pow(value, 2);
        return sumaX2;
    }

    public double sumX3(int[] x) {
        double sumaX3 = 0;
        for (int value : x) sumaX3 += Math.pow(value, 3);
        return sumaX3;
    }

    public double sumX4(int[] x) {
        double sumaX4 = 0;
        for (int value : x) sumaX4 += Math.pow(value, 4);
        return sumaX4;
    }

    public double sumX5(int[] x) {
        double sumaX5 = 0;
        for (int value : x) sumaX5 += Math.pow(value, 5);
        return sumaX5;
    }

    public double sumX6(int[] x) {
        double sumaX6 = 0;
        for (int value : x) sumaX6 += Math.pow(value, 6);
        return sumaX6;
    }

    public double sumY(int[] y) {
        double sumaY = 0;
        for (int value : y) sumaY += value;
        return sumaY;
    }

    public double sumXY(int[] x, int[] y) {
        double sumaXY = 0;
        for (int i = 0; i < x.length; i++) sumaXY += x[i] * y[i];
        return sumaXY;
    }

    public double sumX2Y(int[] x, int[] y) {
        double sumaX2Y = 0;
        for (int i = 0; i < x.length; i++) sumaX2Y += Math.pow(x[i], 2) * y[i];
        return sumaX2Y;
    }

    public double sumX3Y(int[] x, int[] y) {
        double sumaX3Y = 0;
        for (int i = 0; i < x.length; i++) sumaX3Y += Math.pow(x[i], 3) * y[i];
        return sumaX3Y;
    }

    public double[] toDoubleArray(int[] values) {
        double[] result = new double[values.length];
        for (int i = 0; i < values.length; i++) {
            result[i] = values[i];
        }
        return result;
    }

    public Object[] formarSistema(int[] x, int[] y) {
        int n = x.length;
        double[] xd = toDoubleArray(x);
        double[] yd = toDoubleArray(y);

        double Sx = sumX(x);
        double Sx2 = sumX2(x);
        double Sx3 = sumX3(x);
        double Sx4 = sumX4(x);
        double Sx5 = sumX5(x);
        double Sx6 = sumX6(x);

        double Sy = sumY(y);
        double Sxy = sumXY(x, y);
        double Sx2y = sumX2Y(x, y);
        double Sx3y = sumX3Y(x, y);

        double[][] A = {
                {n, Sx, Sx2, Sx3},
                {Sx, Sx2, Sx3, Sx4},
                {Sx2, Sx3, Sx4, Sx5},
                {Sx3, Sx4, Sx5, Sx6}
        };

        double[] B = {Sy, Sxy, Sx2y, Sx3y};

        return new Object[]{A, B};
    }

    public double[] resolverSistema(double[][] A, double[] B) {
        int n = B.length;
        for (int p = 0; p < n; p++) {
            int max = p;
            for (int i = p + 1; i < n; i++) {
                if (Math.abs(A[i][p]) > Math.abs(A[max][p])) {
                    max = i;
                }
            }

            double[] tempA = A[p];
            A[p] = A[max];
            A[max] = tempA;

            double tempB = B[p];
            B[p] = B[max];
            B[max] = tempB;

            for (int i = p + 1; i < n; i++) {
                double alpha = A[i][p] / A[p][p];
                B[i] -= alpha * B[p];
                for (int j = p; j < n; j++) {
                    A[i][j] -= alpha * A[p][j];
                }
            }
        }

        double[] x = new double[n];
        for (int i = n - 1; i >= 0; i--) {
            double sum = 0;
            for (int j = i + 1; j < n; j++) {
                sum += A[i][j] * x[j];
            }
            x[i] = (B[i] - sum) / A[i][i];
        }
        return x;
    }

    public double[] calculaCoeficientes(int[] x, int[] y) {
        Object[] sistema = formarSistema(x, y);
        return resolverSistema((double[][]) sistema[0], (double[]) sistema[1]);
    }

    public String ecuacion(int[] x, int[] y) {
        double[] b = calculaCoeficientes(x, y);
        return String.format("y = %.4f + %.4f*x + %.4f*x^2 + %.4f*x^3", b[0], b[1], b[2], b[3]);
    }

    public double correlacion(int[] xTest, int[] yTest, double[] coef) {
        double yProm = Arrays.stream(yTest).average().orElse(0);
        double sxy = 0, sx2 = 0, sy2 = 0;

        for (int i = 0; i < xTest.length; i++) {
            double xi = xTest[i];
            double yi = yTest[i];
            double yPred = coef[0] + coef[1] * xi + coef[2] * xi * xi + coef[3] * xi * xi * xi;
            sxy += (yi - yProm) * (yPred - yProm);
            sx2 += Math.pow(yPred - yProm, 2);
            sy2 += Math.pow(yi - yProm, 2);
        }

        return sxy / Math.sqrt(sx2 * sy2);
    }

    public double determinacion(int[] x, int[] y, double[] coef) {
        double r = correlacion(x, y, coef);
        return r * r;
    }

    public double predecir(int x, double[] coef) {
        return coef[0] + coef[1] * x + coef[2] * x * x + coef[3] * x * x * x;
    }
}