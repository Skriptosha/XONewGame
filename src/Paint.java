import java.io.IOException;

abstract class Paint {

    /**
     * Проставление Х или О в игровой матрице. Вызывается через specialClassPaint
     *
     * @param matrix        Игровая матрица
     * @param number        Число на игровой матрице
     * @param WhoWalksFirst Строка, чей ход был первым
     * @param course        текущй ход
     * @return Возвращает, успешно ли совершен ход, или нет
     * @throws InterruptedException
     */
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

            } else if (WhoWalksFirst.equals("player")) {
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

    /**
     * Непосредственно сама отрисовка, в зависимости от интерфейса игры
     *
     * @param matrix        Игровая матрица
     * @param number        Число на игровой матрице
     * @param WhoWalksFirst Строка, чей ход был первым
     * @param course        текущй ход
     * @param gamer         Строка, имя игрока
     * @param guiInterface  Если используется GUI интерфейс, ссылка на его экземпляр, в случае консоли должен быть null
     * @return Успешно ли прошла отрисовка
     * @throws InterruptedException
     * @throws IOException
     */
    abstract boolean specialClassPaint(String[][] matrix, String number, String WhoWalksFirst, int course,
                                       String gamer, GuiInterface guiInterface)
            throws InterruptedException, IOException;
}