import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

class PaintGUI extends Paint {

    @Override
    boolean specialClassPaint(String[][] matrix, String number, String WhoWalksFirst, int course, String gamer, GuiInterface guiInterface) {
        boolean ret = false;
        SearchMatrix sm_player = new SearchMatrix();
        //System.out.println("painting_gui " + number);
        int c = matrix.length;
        if (sm_player.search(matrix, number)) {
            int i = sm_player.getI();
            int j = sm_player.getJ();
        }

        try {
            if (!logicPaint(matrix, number, WhoWalksFirst, course)) {
                ChangeBotton(number, WhoWalksFirst, guiInterface, course);
                ret = false;
            } else ret = true;


        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return ret;
    }

     private void ChangeBotton(String number, String WhoWalksFirst, GuiInterface guiInterface, int course) {
        File file_x = new File("C:\\Users\\manapov-ay\\Desktop\\Крестики-Нолики\\krestic_11_150.jpg");
        File file_o = new File("C:\\Users\\manapov-ay\\Desktop\\Крестики-Нолики\\nolic_11_150.jpg");
        Image image = null;
        try {
         image = ImageIO.read(file_o);

         if (WhoWalksFirst.equals("player") && course % 2 == 0) image = ImageIO.read(file_x);
         if (WhoWalksFirst.equals("ai") && course % 2 != 0) image = ImageIO.read(file_x);


        } catch (IOException e) {
            e.printStackTrace();
        }
        Icon icon = new ImageIcon(Objects.requireNonNull(image));

        guiInterface.getButton(number).setIcon(icon);
        guiInterface.getButton(number).addActionListener(guiInterface.new WrongPush());
    }
}

