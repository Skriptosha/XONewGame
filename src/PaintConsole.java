public class PaintConsole extends Paint {

    /**
     * Класс specialClassPaint для консольной версии. Печатает в консоль System.out.printом.
     *
     * @param matrix        Игровая матрица
     * @param number        Число на игровой матрице
     * @param WhoWalksFirst Строка, чей ход был первым
     * @param course        текущй ход
     * @param gamer         Строка, имя игрока
     * @param guiInterface  Если используется GUI интерфейс, ссылка на его экземпляр, в случае консоли должен быть null
     * @return Возвращает, успешно ли совершен ход, или нет
     * @throws InterruptedException
     */
    @Override
    boolean specialClassPaint(String[][] matrix, String number, String WhoWalksFirst, int course, String gamer,
                              GuiInterface guiInterface) throws InterruptedException {
        boolean ret = false;
        String gap;
        int c = matrix.length;
        if (number.equals("0")) System.out.println("Начало игры.");

        if (!logicPaint(matrix, number, WhoWalksFirst, course) || number.equals("0")) {
            System.out.println(" " + "\033[4;1m" + "   " + "\033[0m" + " " + "\033[4;1m" + "   " + "\033[0m"
                    + " " + "\033[4;1m" + "   " + "\033[0m" + " ");
            for (int i = 0; i < c; i++) {
                for (int j = 0; j < c; j++) {
                    if (j == 0) {
                        System.out.print("|");
                    }
                    if (matrix[i][j].length() > 1) gap = "";
                    else gap = " ";
                    System.out.print("\033[4;1m" + " " + matrix[i][j] + gap + "\033[0m" + "|");
                }
                System.out.println();
            }
            System.out.println();
            ret = false;
        } else {
            if (!number.equals("0")) {
                System.out.println("Данное поле уже отмечено или не существует!");
                System.out.println("Повтор хода:");
                ret = true;
            }
        }
        return ret;
    }
}