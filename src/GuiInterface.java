import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

@SuppressWarnings("deprecation")
public class GuiInterface extends JFrame {
    private JButton[] buttons;
    private JLabel jLabel;
    private JLabel jLabelgif;
    private String selectedNumber;
    private int FieldSize;
    private String gamer;
    private ArrayList<Thread> threads = new ArrayList<Thread>();
    private Image imagemain;
    private Icon iconmain;

    /**
     * При выходе из ожидания, необходимо получить число в игровой матрице, куда тыкнул игрок.
     *
     * @return Число на игровой матрице
     */
    public String returnNumber() {
        return selectedNumber;
    }

    /**
     * Управление "кнопкой" из других классов
     *
     * @param ActionCommand Число на игровой матрице, которое необходимо изменить
     * @return Возвращает кнопку
     */
    public JButton getButton(String ActionCommand) {
        JButton ret = null;
        for (int i = 1; i < (int) (Math.pow(FieldSize, 2) + 1); i++) {
            if (buttons[i].getActionCommand().equals(ActionCommand)) ret = buttons[i];
        }
        return ret;
    }

    public void setButton(JButton button, String ActionCommand) {
        this.buttons[Integer.getInteger(ActionCommand)] = button;
    }

    /**
     * Установка имени игрока при запуске GameSetting()
     *
     * @param gamer Строка, имя игрока
     */
    void setNameGamer(String gamer) {
        this.gamer = gamer;
    }

    /**
     * Установка анимации при завершении игры.
     */
    private void setGIFAnimation() {
        ImageIcon imageIcon = new ImageIcon("C:\\Users\\manapov-ay\\Desktop\\Крестики-Нолики\\GameOv1cropped.gif");
        jLabelgif = new JLabel(imageIcon);
        jLabelgif.setOpaque(true);
        jLabelgif.setBackground(Color.darkGray);

    }

    /**
     * Включить анимацию
     */
    void onGIFAnimation() {
        getContentPane().add(jLabelgif, BorderLayout.CENTER);
        jLabelgif.repaint();
    }

    /**
     * Выключить анимацию
     */
    void offGIFAnimation() {
        getContentPane().remove(jLabelgif);
    }

    /**
     * Установка текста информационного сообщения внизу окна
     *
     * @param jLabelText Строка, текст который необходимо установить
     */
    void setjLabelText(String jLabelText) {
        this.jLabel.setText(jLabelText);
    }

    /**
     * Констуктор
     *
     * @param FieldSize    Размер поля
     * @param gameSwingGUI ссылка на экземпляр класса GameSwingGUI
     */
    GuiInterface(int FieldSize, GameSwingGUI gameSwingGUI) {
        super("Крестики-нолики");
        try {
            imagemain = ImageIO.read(new File("C:\\Users\\manapov-ay\\Desktop\\Крестики-Нолики\\unnamed.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        iconmain = new ImageIcon("C:\\Users\\manapov-ay\\Desktop\\Крестики-Нолики\\unnamedsmall.png");
        threads.add(Thread.currentThread());
        setupGUI(FieldSize, gameSwingGUI);
        setGIFAnimation();
    }

    /**
     * Выйти из игры
     */
    void closeFrame() {
        Object[] options = {"Да", "Нет!"};
        int rc = JOptionPane.showOptionDialog(
                getContentPane(), "Выйти из игры?",
                "Крестики-нолики", JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                iconmain, options, options[0]);
        if (rc == 0) {
            getContentPane().setVisible(false);
            System.exit(0);
        }
    }

    /**
     * Меню окна, содержит кнопки Свернуть, Начать заново, Выход
     *
     * @return возвращает созданный обьект JMenu
     */
    private JMenu MainMenu() {

        JMenu file = new JMenu("Файл");
        JMenuItem minimize = new JMenuItem("Свернуть");
        JMenuItem repeat = new JMenuItem("Начать заново");
        JMenuItem exit = new JMenuItem("Выход");

        file.setForeground(Color.WHITE);
        file.setBackground(Color.darkGray);

        file.add(minimize);
        file.add(repeat);
        file.addSeparator();
        file.add(exit);

        minimize.addActionListener(e -> {
            setState(JFrame.ICONIFIED);
        });

        minimize.setAccelerator(KeyStroke.getKeyStroke('M', KeyEvent.CTRL_DOWN_MASK));
        repeat.addActionListener(e -> {
            Object[] options = {"Да", "Нет!"};

            int rc = JOptionPane.showOptionDialog(
                    getContentPane(), "Вы действительно хотите начать игру заново?",
                    "Подтверждение", JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE, iconmain, options, options[0]);
            if (rc == 0) {
                offGIFAnimation();
                Icon icon = new ImageIcon("C:\\Users\\manapov-ay\\Desktop\\Крестики-Нолики\\empty_11_150.jpg");

                for (int i = 1; i < (int) (Math.pow(FieldSize, 2) + 1); i++) {
                    buttons[i].setEnabled(true);
                    buttons[i].setIcon(icon);
                    for (ActionListener al : buttons[i].getActionListeners()) {
                        buttons[i].removeActionListener(al);
                    }
                    buttons[i].addActionListener(new Push());
                }

                selectedNumber = "-1";

                if (threads.size() > 0) threads.get(threads.size() - 1).interrupt();
                System.out.println("Начало новой игры");
                GameSwingGUI gameSwingGUI = new GameSwingGUI(this, this.FieldSize, this.gamer);
                threads.add(new Thread(gameSwingGUI));
                threads.get(threads.size() - 1).start();

                for (Thread thread1 : threads) {
                    System.out.println(thread1.getName() + " " + thread1.getState());
                }
            }
        });

        repeat.setAccelerator(KeyStroke.getKeyStroke('R', KeyEvent.CTRL_DOWN_MASK));
        exit.addActionListener(e -> closeFrame());
        exit.setAccelerator(KeyStroke.getKeyStroke('E', KeyEvent.CTRL_DOWN_MASK));
        return file;
    }

    /**
     * Слушатель, вешается на кнопки поля. Пробуждает остановленный основной поток в классе CoursemainGui.
     */
    class Push implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            selectedNumber = e.getActionCommand();
            synchronized (CoursemainGui.monitorCoursemainGUI) {
                CoursemainGui.monitorCoursemainGUI.notify();
            }
        }
    }

    /**
     * Если кнопка уже прожата была, вешается данный слушатель. Показывает предупреждающее сообщение, и так же
     * пробуждает поток класса CoursemainGui
     */
    class WrongPush implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JOptionPane.showMessageDialog(getContentPane(), "Данное поле уже отмечано!" +
                            " Необходимо выбрать другое пустое поле, для совершения хода!",
                    "Ошибка хода", JOptionPane.PLAIN_MESSAGE, iconmain);
            synchronized (CoursemainGui.monitorCoursemainGUI) {
                CoursemainGui.monitorCoursemainGUI.notify();
            }
        }
    }

    /**
     * Верхний фон (картинка)
     */
    class ImagePanel extends JPanel {
        private BufferedImage image;

        ImagePanel() {
            try {
                image = ImageIO.read(new File("C:\\Users\\manapov-ay\\Desktop\\Крестики-Нолики\\576328.jpg"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(image, 0, 0, null);
        }
    }

    /**
     * Отрисовка самого окна, запускается через конструктор класса
     *
     * @param FieldSize    Размер поля
     * @param gameSwingGUI ссылка на экземпляр класса GameSwingGUI
     */
    private void setupGUI(int FieldSize, GameSwingGUI gameSwingGUI) {
        new JLayeredPane();
        setAlwaysOnTop(true);
        setIconImage(imagemain);
        this.FieldSize = FieldSize;
        setSize(FieldSize * 150 + 30, FieldSize * 150 + 70);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setBackground(Color.darkGray);

        JMenuBar jMenuBar = new JMenuBar();
        jMenuBar.add(MainMenu());
        setJMenuBar(jMenuBar);
        jMenuBar.setBackground(Color.darkGray);
        jMenuBar.setOpaque(true);

        super.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent event) {
                closeFrame();
            }

            @Override
            public void windowClosed(WindowEvent e) {
            }

            @Override
            public void windowIconified(WindowEvent e) {
            }

            @Override
            public void windowDeiconified(WindowEvent e) {
            }

            @Override
            public void windowActivated(WindowEvent e) {
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
            }
        });
//        Dimension dimension_image = new Dimension(150 * FieldSize + 10, 99);
//        ImagePanel imagePanel = new ImagePanel();
//        imagePanel.setPreferredSize(dimension_image);
//        imagePanel.setMinimumSize(dimension_image);
//        imagePanel.setMaximumSize(dimension_image);
//        getContentPane().add(imagePanel, BorderLayout.NORTH);

        JPanel gPanel = new JPanel(new GridLayout(FieldSize, FieldSize, 6, 6));
        Dimension dimension_main = new Dimension(150 * FieldSize, 150 * FieldSize);
        gPanel.setPreferredSize(dimension_main);
        gPanel.setMaximumSize(dimension_main);
        gPanel.setMinimumSize(dimension_main);

        buttons = new JButton[(int) (Math.pow(FieldSize, 2) + 1)];

        for (int i = 1; i < (int) (Math.pow(FieldSize, 2) + 1); i++) {
            buttons[i] = new JButton();
            buttons[i].setActionCommand(String.valueOf(i));
            Icon icon = new ImageIcon("C:\\Users\\manapov-ay\\Desktop\\Крестики-Нолики\\empty_11_150.jpg");
            buttons[i].setIcon(icon);
            buttons[i].addActionListener(new Push());
            gPanel.add(buttons[i]);
        }

        getContentPane().add(gPanel, BorderLayout.CENTER);

        jLabel = new JLabel();
        JPanel sPanel = new JPanel();
        sPanel.add(jLabel);
        jLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
        jLabel.setBackground(Color.darkGray);
        jLabel.setForeground(Color.WHITE);
        Dimension dimension_label = new Dimension(150 * FieldSize + 30, 27);
        sPanel.setPreferredSize(dimension_label);
        sPanel.setMinimumSize(dimension_label);
        sPanel.setMaximumSize(dimension_label);
        getContentPane().add(sPanel, BorderLayout.SOUTH);
        //gPanel.setLayout(grid);
        sPanel.setBackground(Color.darkGray);
        gPanel.setBackground(Color.darkGray);
        //getContentPane().add(bPanel);
        //add(bPanel);
        //setOpacity(0.55f);
        setVisible(true);
    }
}