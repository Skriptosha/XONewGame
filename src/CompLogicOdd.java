public class CompLogicOdd extends CompLogic {
    private RandomCoal cl = new RandomCoal();

    @Override
    String initLogic(String[][] matrix, int course, int WinCombination) {
        if (CompLogic.getInitializationCount() != 1) {
            System.out.println("CompLogic не была инициализированна");
            return "-1";
        }
        // Необходима доработка.
        int c = matrix.length;
        String ret = "";
        if (course == 1 && !matrix[c - 1][c - 1].equals("O") && !matrix[c - 1][c - 1].equals("X")) super.setPrevious_course(ret = matrix[c - 1][c - 1]);
        else super.setPrevious_course(ret = cl.rand_coal(matrix));

        if (course == 3) {

            if (super.getPrevious_course().equals(matrix[c - 1][c - 1])) {

                if (searchLine.searchGorizontal(matrix,"X", WinCombination) == 2 && searchLine.searchGorizontal(matrix, "O", WinCombination) == 1 && searchLine.getLine() == 1) {
                    ret = cl.rand_coal(matrix);
                }

                if (searchLine.searchVertical(matrix,"X", WinCombination) == 2 && searchLine.searchVertical(matrix, "O", WinCombination) == 1 && searchLine.getLine() == 1) {
                    ret = cl.rand_coal(matrix);
                }

                if (ret.equals("")) ret = cl.rand_coal(matrix);

            }

            if (!super.getPrevious_course().equals(matrix[c - 1][c - 1])) {
                ret = super.initLogicMain(matrix, course, WinCombination);
            }

        }
        return ret;
    }

}
