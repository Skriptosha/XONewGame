
public class CoursemainConsole implements Runnable{

    private UserProcess userProcess = new UserProcess();
    private PaintConsole paintConsole = new PaintConsole();

    @Override

    public void run() {

    }

    public void mainCourseGamer(String[][] matrix, String WhoWalksFirst, int course, String gamer) {
        boolean repeat = false;
        int c = matrix.length;
        do {
            try {
                repeat = paintConsole.specialClassPaint(matrix, userProcess.proc(course, c, gamer), WhoWalksFirst, course, gamer, null);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } while (repeat);
        //System.out.println("Paint.isRepeat()");

    }

    public void mainCourseComp(String[][] matrix, String WhoWalksFirst, int course, String gamer, int WinCombination) {
        CompLogic ai;
        if (WhoWalksFirst.equals("ai")) ai = new CompLogicEven();
        else ai = new CompLogicOdd();
        boolean repeat = false;

        do {
            System.out.println("\033[35m" + "Ходит компьютер..." + "\033[0m");
            if (course < 3) {
                try {
                    repeat = paintConsole.specialClassPaint(matrix, ai.initLogic(matrix, course, WinCombination), WhoWalksFirst, course, gamer, null);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if (course > 2) {
                try {
                    repeat = paintConsole.specialClassPaint(matrix, ai.initLogicMain(matrix, course, WinCombination), WhoWalksFirst, course, gamer, null);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } while(repeat);

    }

    public void endofgame(int course, String gamer, int winner) {

        String winnerS = gamer;
        if (winner == 10) winnerS = "Компьютер";

        if (winner == 0) System.out.println("\033[1m" + "Ничья!" + "\033[0m");
        else System.out.println("Игра закончена, победил:" + "\033[1m" + winnerS + "\033[0m");
        System.out.println("Колличество ходов: " + "\033[1m" + course + "\033[0m");


    }


}
