import java.util.ArrayList;
import java.util.Arrays;

@SuppressWarnings("deprecation")
public abstract class CompLogic implements Runnable {
    private static int initializationCount;
    private static String[][] matrix_temp;
    SearchMatrix sm_player = new SearchMatrix();
    SearchMatrix sm_ai = new SearchMatrix();
    SearchMatrix sm_temp = new SearchMatrix();
    private static String previous_course = "";
    static ArrayList<String> ai_pair1;
    static ArrayList<String> ai_pair2;
    static ArrayList<Integer> ai_pair3;
    RandomCoal cl = new RandomCoal();
    SearchLine searchLine = new SearchLine();

    public static void setInitializationCount(int initializationCount) {
        CompLogic.initializationCount = initializationCount;
    }

    /**
     * Создает копию игровой матрицы и необходимые массивы для работы, обязательна для игры против ПК
     *
     * @param matrix    Игровая матрица
     * @param fieldsize Размер игрового поля
     * @return возвращает всегда тру
     */
    public boolean initializationCompLogic(String[][] matrix, int fieldsize) {
        initializationCount++;
        Integer[] cd = {0, matrix.length - 1};
        CompLogic.matrix_temp = Arrays.copyOf(matrix, matrix.length);
        int c = fieldsize;
        String[] a = new String[]{CompLogic.matrix_temp[0][0], CompLogic.matrix_temp[c - 1][c - 1]};
        String[] b = new String[]{CompLogic.matrix_temp[0][c - 1], CompLogic.matrix_temp[c - 1][0]};

        ai_pair1 = new ArrayList<>(Arrays.asList(a));
        ai_pair2 = new ArrayList<>(Arrays.asList(b));
        ai_pair3 = new ArrayList<>(Arrays.asList(cd));

        return true;
    }

    @Override
    public void run() {
    }

    /**
     * Работает начиная с 3 хода. Основная логика.
     *
     * @param matrix         Игровая матрица
     * @param course         текущий ход
     * @param WinCombination Колличество Х или О на одной линии для выигрышной комбинации
     * @return возвращает Число на игровой матрице, куда сходить.
     */
    public String initLogicMain(String[][] matrix, int course, int WinCombination) {

        if (initializationCount != 1) {
            System.out.println("CompLogic не была инициализированна");
            return "-1";
        }
        String ret = "";
        int c = matrix.length;
        System.out.println("Main.course " + course);
        if (course > 2) {
            //System.out.println(Arrays.deepToString(matrix));
            //System.out.println(WinCombination);
            if (searchLine.searchGorizontal(matrix, "O", WinCombination) == c - 1 && !searchLine.getCoordinate().equals("")) {
                ret = searchLine.getCoordinate();
                //System.out.println("initLogicMain searchGorizontal " + ret);
            }

            if (ret.equals("") && searchLine.searchVertical(matrix, "O", WinCombination) == c - 1 && !searchLine.getCoordinate().equals("")) {
                ret = searchLine.getCoordinate();
                //System.out.println("initLogicMain searchVertical " + ret);
            }

            if (ret.equals("") && searchLine.searchDiagonalLeft(matrix, "O", WinCombination) == c - 1 && !searchLine.getCoordinate().equals("")) {
                ret = searchLine.getCoordinate();
            }

            if (ret.equals("") && searchLine.searchDiagonalRight(matrix, "O", WinCombination) == c - 1 && !searchLine.getCoordinate().equals("")) {
                ret = searchLine.getCoordinate();
            }

            if (ret.equals("") && searchLine.searchGorizontal(matrix, "X", WinCombination) == c - 1 && !searchLine.getCoordinate().equals("")) {
                ret = searchLine.getCoordinate();
            }

            if (ret.equals("") && searchLine.searchVertical(matrix, "X", WinCombination) == c - 1 && !searchLine.getCoordinate().equals("")) {
                ret = searchLine.getCoordinate();
            }

            if (ret.equals("") && searchLine.searchDiagonalLeft(matrix, "X", WinCombination) == c - 1 && !searchLine.getCoordinate().equals("")) {
                ret = searchLine.getCoordinate();
            }

            if (ret.equals("") && searchLine.searchDiagonalRight(matrix, "X", WinCombination) == c - 1 && !searchLine.getCoordinate().equals("")) {
                ret = searchLine.getCoordinate();
                //System.out.println("searchDiagonalRight:" + ret);
            }

        }
        //System.out.println("ret234:" + ret);
        //System.out.println("line:" + searchLine.getLine());

        if (course > 2 & ret.equals("")) {
            ret = cl.rand_coal(matrix);
            //System.out.println("ret2 " + ret);
            if (ret.equals("") && !matrix[(c - 1) / 2][(c - 1) / 2].equals("X") && !matrix[(c - 1) / 2][(c - 1) / 2].equals("O"))
                ret = matrix[(c - 1) / 2][(c - 1) / 2];
            if (ret.equals("")) {
                if (ret.equals("") && searchLine.searchGorizontal(matrix, "O", WinCombination) >= 1 && !searchLine.getCoordinate().equals("")) {
                    ret = searchLine.getCoordinate();
                }

                if (ret.equals("") && searchLine.searchVertical(matrix, "O", WinCombination) >= 1 && !searchLine.getCoordinate().equals("")) {
                    ret = searchLine.getCoordinate();
                }

                if (ret.equals("") && searchLine.searchDiagonalLeft(matrix, "O", WinCombination) >= 1 && !searchLine.getCoordinate().equals("")) {
                    ret = searchLine.getCoordinate();
                }

                if (ret.equals("") && searchLine.searchDiagonalRight(matrix, "O", WinCombination) >= 1 && !searchLine.getCoordinate().equals("")) {
                    ret = searchLine.getCoordinate();
                }
                //System.out.println("O > 1 " + ret);
//                for (int i = 1; i < (int) (Math.pow(Main.fieldSize, 2) + 1); i++) {
//                if (sm_ai.search(Paint.getMatrix_1(), String.valueOf(i))) ret = String.valueOf(i);
            }
        }
        return ret;
    }

    String getPrevious_course() {
        return previous_course;
    }

    void setPrevious_course(String previous_course) {
        CompLogic.previous_course = previous_course;
    }

    String[][] getMatrix_temp() {
        return matrix_temp;
    }

    /**
     * Счетчик initializationCompLogic
     *
     * @return возвращает колличество вызовов метода  initializationCompLogic
     */
    public static int getInitializationCount() {
        return initializationCount;
    }
    //System.out.println("ret " + ret);

    /**
     * Дополнительная логика для шагов ПК
     *
     * @param matrix         Игровая матрица
     * @param course         Текущий ход
     * @param WinCombination Колличество Х или О на одной линии для выигрышной комбинации
     * @return возвращает Число на игровой матрице, куда сходить.
     */
    abstract String initLogic(String[][] matrix, int course, int WinCombination);
}

