public class EndGameChek {

    private int winner = 0;
    private SearchLine searchLine = new SearchLine();

    public boolean end(String[][] matrix, int WinCombination)
    {
        int count = 0;
        int c = matrix.length;
        for (int i = 0; i < c; i++) {
            for (int j = 0; j < c; j++) {
                if (!matrix[i][j].equals("X") & !matrix[i][j].equals("O")) count++;

            }
        }
        System.out.println("Закончилась ли игра");
        if (searchLine.searchGorizontal(matrix, "X", WinCombination) == c) winner = 1;
        if (searchLine.searchGorizontal(matrix, "O", WinCombination) == c) winner = 10;

        if (searchLine.searchVertical(matrix, "X", WinCombination) == c) winner = 1;
        if (searchLine.searchVertical(matrix, "O", WinCombination) == c) winner = 10;

        if (searchLine.searchDiagonalLeft(matrix, "X", WinCombination) == c) winner = 1;
        if (searchLine.searchDiagonalRight(matrix, "X", WinCombination) == c) winner = 1;

        if (searchLine.searchDiagonalLeft(matrix, "O", WinCombination) == c) winner = 10;
        if (searchLine.searchDiagonalRight(matrix, "O", WinCombination) == c) winner = 10;
        System.out.println("Закончилась ли игра конец" + count + " winner " + winner);
        if (count == 0 | winner != 0) return false;
        else return true;

    }

    public int getWinner() {
        return winner;
    }
}
