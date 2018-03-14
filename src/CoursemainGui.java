import javax.swing.*;
import java.io.IOException;

public class CoursemainGui {

    public static final Object monitorCoursemainGUI = new Object();
    private PaintGUI paintGUI = new PaintGUI();

    public void mainCourseGamer(String[][] matrix, String WhoWalksFirst, int course, String gamer, GuiInterface guiInterface) {
        boolean repeat;
        guiInterface.setjLabelText("Ходит: игрок " + gamer + ". Ход: " + course);
        System.out.println("Пауза");
        do {
           // run();
            synchronized (monitorCoursemainGUI) {
                try {
                    monitorCoursemainGUI.wait();
                } catch (InterruptedException e) {
                    synchronized (monitorCoursemainGUI) {
                        monitorCoursemainGUI.notify();
                    }
                    System.out.println("InterruptedException " + Thread.currentThread().getName() + " " + Thread.currentThread().getState());
                }
            }
            if (guiInterface.returnNumber().equals("-1")) {
                //System.out.println("mainCourseGamer прерван");
                break;
            }

            System.out.println("Вышел из паузы: " + guiInterface.returnNumber());
            repeat = paintGUI.specialClassPaint(matrix, guiInterface.returnNumber(), WhoWalksFirst, course, gamer, guiInterface);

        } while (repeat);

    }

    public void mainCourseComp(String[][] matrix, String WhoWalksFirst, int course, String gamer, int WinCombination, GuiInterface guiInterface) {
        CompLogic ai;
        if (WhoWalksFirst.equals("ai")) ai = new CompLogicEven();
        else ai = new CompLogicOdd();
        boolean repeat = false;
        guiInterface.setjLabelText("Ходит: компьютер. Ход: " + course);
        do {
            if (course < 3) {
                //System.out.println("paintGUI.specialClassPaint 1");
                    repeat = paintGUI.specialClassPaint(matrix, ai.initLogic(matrix, course, WinCombination), WhoWalksFirst, course, gamer, guiInterface);
                }
            if (course > 2) {
                //System.out.println("paintGUI.specialClassPaint 2");
                    repeat = paintGUI.specialClassPaint(matrix, ai.initLogicMain(matrix, course, WinCombination), WhoWalksFirst, course, gamer, guiInterface);
                }
        } while (repeat);
    }

    public void endofgame(int course, String gamer, int winner, int fieldSize, GuiInterface guiInterface) {
        for (int i = 1; i < (int) (Math.pow(fieldSize, 2) + 1); i++) {
            guiInterface.getButton(String.valueOf(i)).setEnabled(false);
        }

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String winnerS = gamer;
        if (winner == 10) winnerS = "Компьютер";
        if (winner == 0) {
            guiInterface.setjLabelText("Игра закончена, Ничья!" + " Колличество ходов: " + course + ".");
            JOptionPane.showMessageDialog(guiInterface.getContentPane(), "Игра закончена, Ничья!",
                    "Конец игры", JOptionPane.PLAIN_MESSAGE);
        } else {
            guiInterface.setjLabelText("Игра закончена, победил: " + gamer + "." + " Колличество ходов: " + course + ".");
            JOptionPane.showMessageDialog(guiInterface.getContentPane(), "Игра закончена, победил: " + winnerS + ".",
                    "Конец игры", JOptionPane.PLAIN_MESSAGE);
        }


    }
}





