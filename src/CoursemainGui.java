
class CoursemainGui {

    static final Object monitorCoursemainGUI = new Object();
    private PaintGUI paintGUI = new PaintGUI();

    /**
     * Ход игрока
     *
     * @param matrix        Игровая матрица
     * @param WhoWalksFirst Строка, чей ход был первым
     * @param course        Текущий ход
     * @param gamer         Строка, имя игрока
     * @param guiInterface  Если используется GUI интерфейс, ссылка на его экземпляр, в случае консоли должен быть null
     */
    void mainCourseGamer(String[][] matrix, String WhoWalksFirst, int course, String gamer, GuiInterface guiInterface) {
        boolean repeat;
        guiInterface.setjLabelText("Ходит: игрок " + gamer + ". Ход: " + course);
        System.out.println("Пауза");
        do {
            synchronized (monitorCoursemainGUI) {
                try {
                    monitorCoursemainGUI.wait();
                } catch (InterruptedException e) {
                    synchronized (monitorCoursemainGUI) {
                        monitorCoursemainGUI.notify();
                    }
                    System.out.println("InterruptedException " + Thread.currentThread().getName() + " "
                            + Thread.currentThread().getState());
                }
            }
            if (guiInterface.returnNumber().equals("-1")) {
                break;
            }
            System.out.println("Вышел из паузы: " + guiInterface.returnNumber());
            repeat = paintGUI.specialClassPaint(matrix, guiInterface.returnNumber(), WhoWalksFirst, course,
                    gamer, guiInterface);

        } while (repeat);
    }

    /**
     * Ход ПК
     *
     * @param matrix         Игровая матрица
     * @param WhoWalksFirst  Строка, чей ход был первым
     * @param course         Текущий ход
     * @param gamer          Строка, имя игрока
     * @param WinCombination Колличество Х или О на одной линии для выигрышной комбинации
     * @param guiInterface   Если используется GUI интерфейс, ссылка на его экземпляр, в случае консоли должен быть null
     */
    void mainCourseComp(String[][] matrix, String WhoWalksFirst, int course, String gamer, int WinCombination,
                        GuiInterface guiInterface) {
        CompLogic ai;
        if (WhoWalksFirst.equals("ai")) ai = new CompLogicEven();
        else ai = new CompLogicOdd();
        boolean repeat = false;
        guiInterface.setjLabelText("Ходит: компьютер. Ход: " + course);
        do {
            if (course < 3) {
                //System.out.println("paintGUI.specialClassPaint 1");
                repeat = paintGUI.specialClassPaint(matrix, ai.initLogic(matrix, course, WinCombination), WhoWalksFirst,
                        course, gamer, guiInterface);
            }
            if (course > 2) {
                //System.out.println("paintGUI.specialClassPaint 2");
                repeat = paintGUI.specialClassPaint(matrix, ai.initLogicMain(matrix, course, WinCombination),
                        WhoWalksFirst, course, gamer, guiInterface);
            }
        } while (repeat);
    }

    /**
     * Конец игры. Отображение в GUI
     *
     * @param course       Текущий ход
     * @param gamer        Строка, имя игрока
     * @param winner       Имя игрока, который выиграл
     * @param fieldSize    Размер поля
     * @param guiInterface Если используется GUI интерфейс, ссылка на его экземпляр, в случае консоли должен быть null
     */
    void endofgame(int course, String gamer, int winner, int fieldSize, GuiInterface guiInterface) {

        for (int i = 1; i < (int) (Math.pow(fieldSize, 2) + 1); i++) {
            guiInterface.getButton(String.valueOf(i)).setEnabled(false);
        }
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        guiInterface.onGIFAnimation();

        String winnerS = gamer;
        if (winner == 10) winnerS = "Компьютер";
        if (winner == 0) {
            guiInterface.setjLabelText("Игра закончена, Ничья!" + " Колличество ходов: " + course + ".");
        } else {
            guiInterface.setjLabelText("Игра закончена, победил: " + winnerS + "." + " Колличество ходов: " + course
                    + ".");
        }
    }
}