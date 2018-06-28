public class SearchLine {
    private String coordinate;
    private String coordinate_temp;
    private int line;

    /**
     * Посчитать колличество отмеченных Х или О по диагонали
     *
     * @param mtrx           Игровая матрица
     * @param number         Х или О, которое необходимо найти
     * @param WinCombination Колличество Х или О на одной линии для выигрышной комбинации
     * @return Возвращает колличество Х или О на найденных на одной линии
     */
    int searchDiagonalLeft(String[][] mtrx, String number, int WinCombination) {
        int countDiag = 0;
        int countDiag_temp = 0;
        line = -1;
        coordinate = "";
        int c = mtrx.length;
        int b = 0;

        for (int i = b; i < c; i++) {
            if (mtrx[i + b][i].equals(number)) {
                for (int j = 0; j < c - i - b; j++) {
                    if (mtrx[i + b + j][i + j].equals(number)) countDiag_temp++;
                    else break;
                }
            }
            if (countDiag_temp > countDiag) countDiag = countDiag_temp;
            countDiag_temp = 0;
            if (!mtrx[i][i].equals("X") && !mtrx[i][i].equals("O")) coordinate = mtrx[i][i];
            if (c - i - b < WinCombination) break;
            b++;
        }

        b = 0;

        for (int i = b; i < c; i++) {
            if (mtrx[i][i + b].equals(number)) {
                for (int j = 0; j < c - i - b; j++) {
                    if (mtrx[i + j][i + j + b].equals(number)) countDiag_temp++;
                    else break;
                }
            }
            if (countDiag_temp > countDiag) countDiag = countDiag_temp;
            countDiag_temp = 0;
            if (!mtrx[i][i].equals("X") && !mtrx[i][i].equals("O")) coordinate = mtrx[i][i];
            if (c - i - b < WinCombination) break;
            b++;
        }

// for (int i = 0; i + b + d < c; i++) {
//
//            if (mtrx[i + b][i + d].equals(number)) {
//
//                for (int j = 0; j < c - i - b - d; j++) {
//                    if (mtrx[i + b + j][i + d + j].equals(number)) countDiag_temp++;
//                    else break;
//                }
//
//
//            }
//
//
//            if (countDiag_temp > countDiag) countDiag = countDiag_temp;
//            countDiag_temp = 0;
//            if (!mtrx[i][i].equals("X") && !mtrx[i][i].equals("O")) coordinate = mtrx[i][i];
//        }
        System.out.println("searchDiagonalLeft " + countDiag);
        return countDiag;
    }

    /**
     * Посчитать колличество отмеченных Х или О по диагонали
     *
     * @param mtrx           Игровая матрица
     * @param number         Х или О, которое необходимо отметить
     * @param WinCombination Колличество Х или О на одной линии для выигрышной комбинации
     * @return Возвращает колличество Х или О на найденных на одной линии
     */
    int searchDiagonalRight(String[][] mtrx, String number, int WinCombination) {
        int countDiag = 0;
        line = -1;
        coordinate = "";
        int c = mtrx.length;
        int countDiag_temp = 0;

        for (int i = 0; i < c; i++) {
            if (mtrx[i][Math.abs(c - i) - 1].equals(number)) {
                for (int j = 0; j < c - i; j++) {
                    if (mtrx[i + j][Math.abs(c - i) - 1 - j].equals(number)) countDiag_temp++;
                    else break;
                }
            }
            if (countDiag_temp > countDiag) countDiag = countDiag_temp;
            countDiag_temp = 0;
            if (!mtrx[i][Math.abs(c - i) - 1].equals("X") && !mtrx[i][Math.abs(c - i) - 1].equals("O"))
                coordinate = mtrx[i][Math.abs(c - i) - 1];
        }
        //System.out.println("searchDiagonalRight " + countDiag);
        return countDiag;
    }

    /**
     * Посчитать колличество отмеченных Х или О по горизонтали
     *
     * @param mtrx           Игровая матрица
     * @param number         Х или О, которое необходимо отметить
     * @param WinCombination Колличество Х или О на одной линии для выигрышной комбинации
     * @return Возвращает колличество Х или О на найденных на одной линии
     */
    int searchGorizontal(String[][] mtrx, String number, int WinCombination) {
        int countDiag = 0;
        line = -1;
        coordinate = "";
        int c = mtrx.length;
        int countDiag_temp = 0;

        for (int i = 0; i < c; i++) {
            for (int j = 0; j < c; j++) {
                if (!mtrx[i][j].equals("X") && !mtrx[i][j].equals("O")) coordinate_temp = mtrx[i][j];
                if (WinCombination == mtrx.length) {
                    if (mtrx[i][j].equals(number)) countDiag_temp++;
                } else if (mtrx[i][j].equals(number)) {
                    for (int n = 0; n < c - j; n++) {
                        if (mtrx[i][j + n].equals(number)) countDiag_temp++;
                        else if (!mtrx[i][j + n].equals("X") & !mtrx[i][j + n].equals("O")) {
                            coordinate_temp = mtrx[i][j + n];
                            break;
                        } else break;
                    }
                    if (countDiag_temp > countDiag) {
                        countDiag = countDiag_temp;
                        line = i;
                        coordinate = coordinate_temp;
                    }
                    countDiag_temp = 0;
                }
            }
            if (WinCombination == mtrx.length) {
                if (countDiag_temp > countDiag) {
                    countDiag = countDiag_temp;
                    line = i;
                    coordinate = coordinate_temp;
                }
            }
            countDiag_temp = 0;
            coordinate_temp = "";
        }
        //System.out.println("coordinate " + coordinate);
        return countDiag;
    }

    /**
     * Посчитать колличество отмеченных Х или О по вертикали
     *
     * @param mtrx           Игровая матрица
     * @param number         Х или О, которое необходимо отметить
     * @param WinCombination Колличество Х или О на одной линии для выигрышной комбинации
     * @return Возвращает колличество Х или О на найденных на одной линии
     */
    public int searchVertical(String[][] mtrx, String number, int WinCombination) {
        int countDiag = 0;
        line = -1;
        coordinate = "";
        int c = mtrx.length;
        int countDiag_temp = 0;

        for (int j = 0; j < c; j++) {
            for (int i = 0; i < c; i++) {
                if (!mtrx[i][j].equals("X") && !mtrx[i][j].equals("O")) coordinate_temp = mtrx[i][j];

                if (WinCombination == mtrx.length) {
                    if (mtrx[i][j].equals(number)) countDiag_temp++;
                } else if (mtrx[i][j].equals(number)) {
                    for (int n = 0; n < c - i; n++) {
                        if (mtrx[i + n][j].equals(number)) countDiag_temp++;
                        else if (!mtrx[i + n][j].equals("X") & !mtrx[i + n][j].equals("O")) {
                            coordinate_temp = mtrx[i + n][j];
                            break;
                        } else break;
                    }
                    if (countDiag_temp > countDiag) {
                        countDiag = countDiag_temp;
                        line = j;
                        coordinate = coordinate_temp;
                    }
                    countDiag_temp = 0;
                }
            }
            if (WinCombination == mtrx.length) {
                if (countDiag_temp > countDiag) {
                    countDiag = countDiag_temp;
                    line = j;
                    coordinate = coordinate_temp;
                }
            }
            countDiag_temp = 0;
            coordinate_temp = "";
        }
        //System.out.println("searchVertical " + countDiag);
        return countDiag;
    }

    /**
     * Метод для получения числа, не отмеченного ни О ни Х на линии, из последнего вызванного метода
     * searchVertical или searchGorizontal или searchDiagonalRight или searchDiagonalLeft
     *
     * @return Возвращает данное число
     */
    public String getCoordinate() {
        if (coordinate != null) return coordinate;
        else return coordinate_temp;
    }

    /**
     * Метод возвращает линию, на котрой находится coordinate
     * Для searchVertical и searchGorizontal
     *
     * @return int линии, на которой находится coordinate
     */
    public int getLine() {
        return line;
    }
}