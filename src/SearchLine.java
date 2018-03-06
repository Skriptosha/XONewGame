public class SearchLine {
    private String coordinate;
    private String coordinate_temp;
    private int line;

    public int searchDiagonalLeft(String[][] mtrx, String number, int WinCombination) {
        int countDiag = 0;
        int countDiag_temp = 0;
        line = -1;
        coordinate = "";
        int c = mtrx.length;
        for (int i = 0; i < c; i++) {
            if (mtrx[i][i].equals(number)) {
                for (int j = 0; j < c - i; j++) {
                    if (mtrx[i + j][i + j].equals(number)) countDiag_temp++;
                    else break;
                }
            }
            if (countDiag_temp > countDiag) countDiag = countDiag_temp;
            countDiag_temp = 0;
            if (!mtrx[i][i].equals("X") && !mtrx[i][i].equals("O")) coordinate = mtrx[i][i];
        }
        //System.out.println("searchDiagonalLeft " + countDiag);
        return countDiag;
    }

    public int searchDiagonalRight(String[][] mtrx, String number, int WinCombination) {
        int countDiag = 0;
        line = -1;
        coordinate = "";
        int c = mtrx.length;
        int countDiag_temp = 0;
        for (int i = 0; i < c; i++) {
            if (mtrx[i][Math.abs(c-i)-1].equals(number)) {
                for (int j = 0; j < c - i; j++) {
                    if (mtrx[i + j][Math.abs(c-i)-1 - j].equals(number)) countDiag_temp++;
                    else break;
                }
            }
            if (countDiag_temp > countDiag) countDiag = countDiag_temp;
            countDiag_temp = 0;
            if (!mtrx[i][Math.abs(c-i)-1].equals("X") && !mtrx[i][Math.abs(c-i)-1].equals("O")) coordinate = mtrx[i][Math.abs(c-i)-1];
        }
        //System.out.println("searchDiagonalRight " + countDiag);
        return countDiag;
    }

    public int searchGorizontal(String[][] mtrx, String number, int WinCombination) {
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
                }
//                else if (Paint.getMatrix_1()[i][j].equals(number)) {
//                    for (int n = 0; n < c - j; n++) {
//                        if (Paint.getMatrix_1()[i][j + n].equals(number)) countDiag_temp++;
//                        else if (!Paint.getMatrix_1()[i][j + n].equals("X") & !Paint.getMatrix_1()[i][j + n].equals("O")) {
//                            coordinate_temp = Paint.getMatrix_1()[i][j + n];
//                            break;
//                        } else break;
//                    }
//
//
//                    if (countDiag_temp > countDiag) {
//                        countDiag = countDiag_temp;
//                        line = i;
//                        coordinate = coordinate_temp;
//                    }
//                    countDiag_temp = 0;
//                }

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
                }
//                else if (Paint.getMatrix_1()[i][j].equals(number)) {
//                    for (int n = 0; n < c - i; n++) {
//                        if (Paint.getMatrix_1()[i + n][j].equals(number)) countDiag_temp++;
//                        else if (!Paint.getMatrix_1()[i + n][j].equals("X") & !Paint.getMatrix_1()[i + n][j].equals("O")) {
//                            coordinate_temp = Paint.getMatrix_1()[i + n][j];
//                            break;
//                        } else break;
//                    }
//
//
//                    if (countDiag_temp > countDiag) {
//                        countDiag = countDiag_temp;
//                        line = j;
//                        coordinate = coordinate_temp;
//                    }
//                    countDiag_temp = 0;
//                }
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

    public String getCoordinate() {

        if (coordinate != null) return coordinate;
        else return coordinate_temp;
    }

    public int getLine() {
        return line;
    }
}
