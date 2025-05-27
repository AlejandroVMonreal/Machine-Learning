import java.util.*;

public class DatasetSplitter {
    private int[][] trainX;
    private int[][] trainY;
    private int[][] testX;
    private int[][] testY;

    public DatasetSplitter(DataSet data, int repeticiones, double porcentajeEntrenamiento) {
        int n = data.getX().length;
        int cantidadEntrenamiento = (int) (n * porcentajeEntrenamiento);
        int cantidadTest = n - cantidadEntrenamiento;

        trainX = new int[repeticiones][cantidadEntrenamiento];
        trainY = new int[repeticiones][cantidadEntrenamiento];
        testX = new int[repeticiones][cantidadTest];
        testY = new int[repeticiones][cantidadTest];

        for (int i = 0; i < repeticiones; i++) {
            Integer[] indices = new Integer[n];
            for (int j = 0; j < n; j++) indices[j] = j;
            Collections.shuffle(Arrays.asList(indices));

            int[] x = data.getX();
            int[] y = data.getY();

            for (int j = 0; j < cantidadEntrenamiento; j++) {
                trainX[i][j] = x[indices[j]];
                trainY[i][j] = y[indices[j]];
            }

            for (int j = 0; j < cantidadTest; j++) {
                testX[i][j] = x[indices[cantidadEntrenamiento + j]];
                testY[i][j] = y[indices[cantidadEntrenamiento + j]];
            }
        }
    }

    public int[][] getTrainX() { return trainX; }
    public int[][] getTrainY() { return trainY; }
    public int[][] getTestX() { return testX; }
    public int[][] getTestY() { return testY; }
}
