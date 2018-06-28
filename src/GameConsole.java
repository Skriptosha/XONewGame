import java.util.Random;

public class GameConsole {
    private String gamer;
    private int fieldSize;
    private int WinCombination;
    private String[][] matrix_1;
    private EndGameChek endGameChek = new EndGameChek();
    private PaintConsole paintConsole = new PaintConsole();
    private CoursemainConsole coursemainConsole = new CoursemainConsole();
    private CompLogic compLogic = new CompLogic() {
        @Override
        String initLogic(String[][] matrix, int course, int WinCombination) {
            return null;
        }
    };

    /**
     * Инициализация игровой матрицы
     *
     * @param fieldSize Размер поля
     */
    private void setMatrix(int fieldSize) {
        matrix_1 = new String[fieldSize][fieldSize];
        this.fieldSize = fieldSize;
        int stack = 1;
        for (int i = 0; i < fieldSize; i++) {
            for (int j = 0; j < fieldSize; j++) {
                System.out.println(String.valueOf(stack));
                matrix_1[i][j] = String.valueOf(stack);
                stack++;
            }
        }
    }

    /**
     * Установка Имени игрока, размера игрового поля, жеребьевка кто будет ходить первым, Инициализация CompLogic
     * запуск GameMain.
     */
    public void GameSetting() {
        System.out.println("Пожалуйста введите имя игрока!");
        String scan;
        ObjScanner sc = new ObjScanner();
        do {
            scan = sc.scan();
            //System.out.println("Необходимо ввести не пустое значение имени!");
        } while (scan.equals("Y") & scan.equals("N"));
        gamer = scan;

        System.out.println("Пожалуйста выберите размер поля: 3 или 5!");
        do {
            scan = sc.scan();
            //System.out.println("Необходимо ввести не пустое значение имени!");
        } while (scan.equals("3") & scan.equals("5"));
        fieldSize = Integer.parseInt(scan);
        setMatrix(fieldSize);

        System.out.println("Сейчас будет жеребьевка игрока, который бует ходить первым..");
        String whoWalksFirst;
        Random random = new Random();
        if (random.nextInt(2) == 1) whoWalksFirst = "ai";
        else whoWalksFirst = "player";
        if (whoWalksFirst.equals("ai")) System.out.println("\033[31m" + "Первым ходит компьютер...!" + "\033[0m");
        else System.out.println("Первым ходит игрок: " + "\033[31m" + gamer + "\033[0m");
        System.out.println();
        CompLogic.setInitializationCount(0);
        GameMain(whoWalksFirst);
    }

    /**
     * Запуск ходов, метод в котором вызываются методы хода игрока и хода ПК + проверка не закончилась ли игра.
     *
     * @param whoWalksFirst Строка, чей ход был первым
     */
    void GameMain(String whoWalksFirst) {
        int course = 0;
        WinCombination = fieldSize;
        try {
            paintConsole.specialClassPaint(matrix_1, "0", whoWalksFirst, course, gamer, null);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        compLogic.initializationCompLogic(matrix_1, fieldSize);

        if (whoWalksFirst.equals("ai")) {
            while (endGameChek.end(matrix_1, WinCombination)) {
                coursemainConsole.mainCourseComp(matrix_1, whoWalksFirst, course, gamer, fieldSize);
                System.out.println("Шаг завершен: mainCourseComp");

                course++;
                if (!endGameChek.end(matrix_1, WinCombination)) break;

                coursemainConsole.mainCourseGamer(matrix_1, whoWalksFirst, course, gamer);

                course++;
                System.out.println("Шаг завершен: run");
            }
        } else if (whoWalksFirst.equals("player")) {
            while (endGameChek.end(matrix_1, WinCombination)) {
                coursemainConsole.mainCourseGamer(matrix_1, whoWalksFirst, course, gamer);

                course++;
                System.out.println("Шаг завершен: run");

                if (!endGameChek.end(matrix_1, WinCombination)) break;

                coursemainConsole.mainCourseComp(matrix_1, whoWalksFirst, course, gamer, fieldSize);
                System.out.println("Шаг завершен: mainCourseComp");
                course++;
            }
        }
        coursemainConsole.endofgame(course, gamer, endGameChek.getWinner());
    }
}