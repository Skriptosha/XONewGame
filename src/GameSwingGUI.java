
import javax.swing.*;
import java.util.Objects;
import java.util.Random;

public class GameSwingGUI extends Thread {

        private String gamer;
        private int fieldSize;
        private String[][] matrix_1;
        private GuiInterface guiInterface;

        private CompLogic compLogic = new CompLogic() {
            @Override
            String initLogic(String[][] matrix, int course, int WinCombination) {
                return null;
            }
        };

    GameSwingGUI(GuiInterface guiInterface, int fieldSize, String Namegamer) {
        this.guiInterface = guiInterface;
        this.fieldSize = fieldSize;
        this.gamer = Namegamer;
    }

    private void setMatrix(int fieldSize){

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


       public void GameSetting() {

                Object result;
                JFrame jFrame = new JFrame("Крестики-нолики");
                jFrame.setAlwaysOnTop(true);
                String[] fieldsize = {"Три клетки",
                        "Пять клеток"};
           result = JOptionPane.showInputDialog(jFrame, "Выберите размер поля:", "Выбор размера поля", JOptionPane.QUESTION_MESSAGE,
                   null, fieldsize, fieldsize[0]);
           if (result == null) {
               System.exit(0);
           }
           if (result == fieldsize[0]) this.fieldSize = 3;
                else this.fieldSize = 5;
                jFrame.dispose();
                //System.out.println(this);
                GuiInterface guiInterface = new GuiInterface(fieldSize, this);

            String result_name;
            do {
                result_name = JOptionPane.showInputDialog(guiInterface.getContentPane(), "Введите имя игрока!");
                if (result_name == null) {
                    Objects.requireNonNull(guiInterface).closeFrame();
                }
            } while (result_name == null || result_name.equals(""));
            gamer = result_name;


            GameMain(randwhoWalksFirst(guiInterface), guiInterface, fieldSize);
            }


        public String randwhoWalksFirst(GuiInterface guiInterface) {

            Random r = new Random();
            String whoWalksFirst;

            JOptionPane.showMessageDialog(guiInterface.getContentPane(), "Сейчас будет жеребьевка кто будет ходить первым!", "Главное Меню", JOptionPane.PLAIN_MESSAGE);
            if (r.nextInt(2) == 1) whoWalksFirst = "ai";
            else whoWalksFirst = "player";

            //тут прикрутить бы анимацию
            if (whoWalksFirst.equals("ai"))
                JOptionPane.showMessageDialog(guiInterface.getContentPane(), "Первым ходит компьютер...!", "Главное Меню", JOptionPane.PLAIN_MESSAGE);
            else
                JOptionPane.showMessageDialog(guiInterface.getContentPane(), "Первым ходит игрок: " + gamer, "Главное Меню", JOptionPane.PLAIN_MESSAGE);
                CompLogic.setInitializationCount(0);
                //GameMain(whoWalksFirst);
                return whoWalksFirst;
       }



        void GameMain(String whoWalksFirst, GuiInterface guiInterface, int fieldSize) {
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

            System.out.println(Thread.currentThread().getName()); // jghjhgjghj

        }


    @Override
    public void run() {
        GameMain(randwhoWalksFirst(this.guiInterface), this.guiInterface, this.fieldSize);
    }
}
