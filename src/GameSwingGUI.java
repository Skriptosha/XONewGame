
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Objects;
import java.util.Random;


public class GameSwingGUI extends Thread {

    private String gamer;
    private int fieldSize;
    private String[][] matrix_1;
    private GuiInterface guiInterface;
    private Icon icon;
    private CompLogic compLogic = new CompLogic() {
        @Override
        String initLogic(String[][] matrix, int course, int WinCombination) {
            return null;
        }
    };

    /**
     * Конструктор, используется при перезапуске игры
     *
     * @param guiInterface Если используется GUI интерфейс, ссылка на его экземпляр, в случае консоли должен быть null
     * @param fieldSize    Размер поля
     * @param gamer        Cтрока, имя игрока
     */
    GameSwingGUI(GuiInterface guiInterface, int fieldSize, String gamer) {
        this.guiInterface = guiInterface;
        this.fieldSize = fieldSize;
        this.gamer = gamer;
    }

    /**
     * Установка Иконки для окна выбора размера поля и имени игрока, установка шрифта, цвета.
     */
    private void setIcon() {
        File file_a = new File("C:\\Users\\manapov-ay\\Desktop\\Крестики-Нолики\\unnamedsmall.png");
        Image image = null;
        try {
            image = ImageIO.read(file_a);
        } catch (IOException e) {
            e.printStackTrace();
        }
        icon = new ImageIcon(Objects.requireNonNull(image));

        FontUIResource font = new FontUIResource(new Font("Tahoma", Font.BOLD, 11));
        Enumeration<Object> keys = UIManager.getDefaults().keys();
        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if (value instanceof FontUIResource) {
//                FontUIResource orig = (FontUIResource) value;
//                Font font = new Font(f.getFontName(), orig.getStyle(), f.getSize());
                UIManager.put(key, font);
            }
        }
        font = new FontUIResource(new Font("Tahoma", Font.BOLD, 12));
        UIManager.put("OptionPane.messageFont", font);
        UIManager.put("OptionPane.messageForeground", Color.WHITE);
        UIManager.put("Panel.background", Color.darkGray);
        UIManager.put("OptionPane.background", Color.darkGray);
    }

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
                //System.out.println(String.valueOf(stack));
                matrix_1[i][j] = String.valueOf(stack);
                stack++;
            }
        }
    }

    /**
     * Установка Имени игрока, размера игрового поля
     * запуск GameMain, randwhoWalksFirst
     */
    void GameSetting() {
        setIcon();
        Object result;
        JFrame.setDefaultLookAndFeelDecorated(true);
        JFrame jFrame = new JFrame("Крестики-нолики");
        jFrame.setAlwaysOnTop(true);
        String[] fieldsize = {"Три клетки",
                "Пять клеток"};
        result = JOptionPane.showInputDialog(jFrame, "Выберите размер поля:", "Выбор размера поля", JOptionPane.QUESTION_MESSAGE,
                icon, fieldsize, fieldsize[0]);
        if (result == null) {
            System.exit(0);
        }
        if (result == fieldsize[0]) this.fieldSize = 3;
        else this.fieldSize = 5;
        jFrame.dispose();

        GuiInterface guiInterface = new GuiInterface(fieldSize, this);

        String result_name;
        do {
            Random r = new Random();
            int m = Math.abs(r.nextInt(1500));
            result_name = JOptionPane.showInputDialog(guiInterface.getContentPane(), "Введите имя игрока!", "Игрок" + m);
            if (result_name == null) {
                Objects.requireNonNull(guiInterface).closeFrame();
            }
        } while (result_name == null || result_name.equals(""));
        gamer = result_name;
        guiInterface.setNameGamer(gamer);

        GameMain(randwhoWalksFirst(guiInterface), guiInterface, fieldSize);
    }

    /**
     * Метод по выбору кто будет ходить первым
     *
     * @param guiInterface Если используется GUI интерфейс, ссылка на его экземпляр, в случае консоли должен быть null
     * @return Строка, чей ход был первым
     */
    private String randwhoWalksFirst(GuiInterface guiInterface) {

        Random r = new Random();
        String whoWalksFirst;

        JOptionPane.showMessageDialog(guiInterface.getContentPane(),
                "Сейчас будет жеребьевка кто будет ходить первым!", "Главное Меню",
                JOptionPane.PLAIN_MESSAGE, icon);
        if (r.nextInt(2) == 1) whoWalksFirst = "ai";
        else whoWalksFirst = "player";

        //тут прикрутить бы анимацию
        if (whoWalksFirst.equals("ai"))
            JOptionPane.showMessageDialog(guiInterface.getContentPane(), "Первым ходит компьютер...!",
                    "Главное Меню", JOptionPane.PLAIN_MESSAGE, icon);
        else
            JOptionPane.showMessageDialog(guiInterface.getContentPane(), "Первым ходит игрок: " + gamer,
                    "Главное Меню", JOptionPane.PLAIN_MESSAGE, icon);
        CompLogic.setInitializationCount(0);
        //GameMain(whoWalksFirst);
        return whoWalksFirst;
    }

    /**
     * Запуск ходов, метод в котором вызываются методы хода игрока и хода ПК + проверка не закончилась ли игра.
     *
     * @param whoWalksFirst Строка, чей ход был первым
     * @param guiInterface  Если используется GUI интерфейс, ссылка на его экземпляр, в случае консоли должен быть null
     * @param fieldSize     Размер поля
     */
    private void GameMain(String whoWalksFirst, GuiInterface guiInterface, int fieldSize) {
        //System.out.println(Thread.currentThread().getName());
        EndGameChek endGameChek = new EndGameChek();
        CoursemainGui coursemainGui = new CoursemainGui();
        int course = 0;
        int winCombination = fieldSize;

        setMatrix(fieldSize);
        compLogic.initializationCompLogic(matrix_1, fieldSize);

        if (whoWalksFirst.equals("ai")) {
            while (endGameChek.end(matrix_1, winCombination)) {

                coursemainGui.mainCourseComp(matrix_1, whoWalksFirst, course, gamer, winCombination, guiInterface);
                System.out.println("Шаг завершен: mainCourseComp");

                course++;
                if (!endGameChek.end(matrix_1, winCombination)) break;

                coursemainGui.mainCourseGamer(matrix_1, whoWalksFirst, course, gamer, guiInterface);
                if (guiInterface.returnNumber().equals("-1")) break;
                course++;
                System.out.println("Шаг завершен: run");
            }
        } else if (whoWalksFirst.equals("player")) {
            while (endGameChek.end(matrix_1, winCombination)) {

                coursemainGui.mainCourseGamer(matrix_1, whoWalksFirst, course, gamer, guiInterface);
                if (guiInterface.returnNumber().equals("-1")) break;
                course++;
                System.out.println("Шаг завершен: run");

                if (!endGameChek.end(matrix_1, winCombination)) break;

                coursemainGui.mainCourseComp(matrix_1, whoWalksFirst, course, gamer, winCombination, guiInterface);
                System.out.println("Шаг завершен: mainCourseComp");
                course++;
            }
        }
        if (!guiInterface.returnNumber().equals("-1")) {
            coursemainGui.endofgame(course, gamer, endGameChek.getWinner(), fieldSize, guiInterface);
        }
    }

    /**
     * При перезапуски игры создается новый поток в классе GuiInterface, предыдущий завершается.
     */
    @Override
    public void run() {
        setIcon();
        GameMain(randwhoWalksFirst(this.guiInterface), this.guiInterface, this.fieldSize);
    }
}