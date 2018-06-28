import javax.swing.*;

class PaintGUI extends Paint {

    /**
     * Класс specialClassPaint для Java Swing интерфейса. Меняет кнопки через ссылку GuiInterface guiInterface
     *
     * @param matrix        Игровая матрица
     * @param number        Число на игровой матрице
     * @param WhoWalksFirst Строка, чей ход был первым
     * @param course        Текущий ход
     * @param gamer         Строка, имя игрока
     * @param guiInterface  Если используется GUI интерфейс, ссылка на его экземпляр, в случае консоли должен быть null
     * @return Возвращает, успешно ли совершен ход, или нет
     */
    @Override
    boolean specialClassPaint(String[][] matrix, String number, String WhoWalksFirst, int course, String gamer,
                              GuiInterface guiInterface) {
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

    /**
     * @param number        Число на игровой матрице
     * @param WhoWalksFirst Строка, чей ход был первым
     * @param guiInterface  Ссылка на GUI интерфейс
     * @param course        Текущий ход
     */
    private void ChangeBotton(String number, String WhoWalksFirst, GuiInterface guiInterface, int course) {

        ImageIcon image_x = new ImageIcon("C:\\Users\\manapov-ay\\Desktop\\Крестики-Нолики\\krestic_11_150.jpg");
        ImageIcon image_o = new ImageIcon("C:\\Users\\manapov-ay\\Desktop\\Крестики-Нолики\\nolic_11_150.jpg");

        Icon icon = image_o;
        if (WhoWalksFirst.equals("player") && course % 2 == 0) icon = image_x;
        if (WhoWalksFirst.equals("ai") && course % 2 != 0) icon = image_x;

        guiInterface.getButton(number).setIcon(icon);
        guiInterface.getButton(number).addActionListener(guiInterface.new WrongPush());
    }
}