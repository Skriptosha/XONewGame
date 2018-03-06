import java.io.IOException;

abstract class Paint {


    boolean logicPaint(String[][] matrix, String number, String WhoWalksFirst, int course) throws InterruptedException {
            SearchMatrix sm = new SearchMatrix();
            int count = 0;
            //System.out.println("WhoWalksFirst " + WhoWalksFirst + "course " + course);
        boolean repeat = false;
        if (sm.search(matrix, number) && !number.equals("O") && !number.equals("X")) {
                if (WhoWalksFirst.equals("ai")) {
                    if (course % 2 == 0) {
                        matrix[sm.getI()][sm.getJ()] = "O";
                        Thread.sleep(500);
                    } else matrix[sm.getI()][sm.getJ()] = "X";

                }else if (WhoWalksFirst.equals("player")) {
                    if (course % 2 == 0) {
                        matrix[sm.getI()][sm.getJ()] = "X";

                    } else {
                        matrix[sm.getI()][sm.getJ()] = "O";
                        Thread.sleep(500);
                    }
                }
                repeat = false;

            } else repeat = true;

            return repeat;
        }
    abstract boolean specialClassPaint(String[][] matrix, String number, String WhoWalksFirst, int course, String gamer, GuiInterface guiInterface) throws InterruptedException, IOException;

}





