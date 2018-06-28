import java.util.HashSet;

public class UserProcess {

    /**
     * Используется только в консольной версии, вызывается когда наступает очередь ходить игроку
     *
     * @param course       Текущий Ход в игре
     * @param matrixlenght Длина игровой матрицы
     * @param gamer        Имя игрока
     * @return Значение, введеное пользователем
     */
    public String proc(int course, int matrixlenght, String gamer) {
        String number = "";
        System.out.println("\033[36m" + "Ходит игрок:" + gamer + "\033[0m");
        System.out.println("Введите цифру ячейки, где необходимо поставить крестик!");
        if (course < 2) System.out.println("Для выхода из игры нажмите Q");
        ObjScanner sc = new ObjScanner();
        HashSet possible = new HashSet();
        int c = matrixlenght;

        for (int i = 1; i < (int) (Math.pow(c, 2) + 1); i++)
            possible.add(String.valueOf(i));

        while (!possible.contains(number)) {
            number = sc.scan();
            if (number.equalsIgnoreCase("Q")) {
                System.exit(0);
            }
            if (!possible.contains(number)) {
                System.out.println("Необходимо ввести число от 1 до " + (int) (Math.pow(c, 2) + 1));
                System.out.println("Повторите ввод");
                System.out.println();
            }
        }
        return number;
    }
}