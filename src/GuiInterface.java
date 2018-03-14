import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

@SuppressWarnings("deprecation")
public class GuiInterface extends JFrame {
    private JButton[] buttons;
    private JLabel jLabel;
    private String selectedNumber;
    private int FieldSize;
    private String Namegamer;
    private ArrayList<Thread> threads = new ArrayList<Thread>();

    public String returnNumber() {
        return selectedNumber;
    }

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

    public void setNameGamer(String Namegamer) {
        this.Namegamer = Namegamer;
    }


    public void setjLabelText(String jLabelText) {
        this.jLabel.setText(jLabelText);
    }

    GuiInterface(int FieldSize, GameSwingGUI gameSwingGUI) {
        super("Крестики-нолики");
        threads.add(Thread.currentThread());
        setupGUI(FieldSize, gameSwingGUI);
    }

    void closeFrame() {
        Object[] options = {"Да", "Нет!"};
        int rc = JOptionPane.showOptionDialog(
                getContentPane(), "Закрыть окно?",
                "Подтверждение", JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null, options, options[0]);
        if (rc == 0) {
            getContentPane().setVisible(false);
            System.exit(0);
        }

    }

    private JMenu MainMenu() {

        JMenu file = new JMenu("Файл");
        JMenuItem minimize = new JMenuItem("Свернуть");
        JMenuItem repeat = new JMenuItem("Начать заново");
        JMenuItem exit = new JMenuItem("Выход");
        file.setForeground(Color.WHITE);
        file.setBackground(Color.darkGray);
        //exit.setIcon(new ImageIcon("images/exit.png"));

        file.add(minimize);
        file.add(repeat);
        file.addSeparator();
        file.add(exit);
        file.setMnemonic('F');
        //file.setAccelerator(KeyStroke.getKeyStroke('F', KeyEvent.CTRL_DOWN_MASK));
        minimize.addActionListener(e -> {
            //JOptionPane.showMessageDialog(getContentPane(), "Свернуть!", "Главное Меню", JOptionPane.DEFAULT_OPTION);
            setState(JFrame.ICONIFIED);

        });
        minimize.setMnemonic('M');
        minimize.setAccelerator(KeyStroke.getKeyStroke('M', KeyEvent.CTRL_DOWN_MASK));
        repeat.addActionListener(e -> {
            Object[] options = {"Да", "Нет!"};
            int rc = JOptionPane.showOptionDialog(
                    getContentPane(), "Вы действительно хотите начать игру заново?",
                    "Подтверждение", JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null, options, options[0]);
            if (rc == 0) {
                File file_e = new File("C:\\Users\\manapov-ay\\Desktop\\Крестики-Нолики\\empty_11_150.jpg");
                Image image = null;
                try {
                    image = ImageIO.read(file_e);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                Icon icon = new ImageIcon(Objects.requireNonNull(image));

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
                //System.out.println("GUI " + Thread.currentThread().getName() + " " + Thread.currentThread().getId());
                //gameSwingGUI.GameMain(gameSwingGUI.randwhoWalksFirst());
                GameSwingGUI gameSwingGUI = new GameSwingGUI(this, this.FieldSize, this.Namegamer);
                threads.add(new Thread(gameSwingGUI));
                threads.get(threads.size() - 1).start();

                for (Thread thread1 : threads) {
                    System.out.println(thread1.getName() + " " + thread1.getState());
                }

            }

        });
        repeat.setMnemonic('R');
        repeat.setAccelerator(KeyStroke.getKeyStroke('R', KeyEvent.CTRL_DOWN_MASK));

        exit.addActionListener(e -> closeFrame());
        exit.setMnemonic('E');
        exit.setAccelerator(KeyStroke.getKeyStroke('E', KeyEvent.CTRL_DOWN_MASK));
        return file;
    }

    class Push implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            selectedNumber = e.getActionCommand();
            synchronized (CoursemainGui.monitorCoursemainGUI) {
                CoursemainGui.monitorCoursemainGUI.notify();
            }
        }
    }

    class WrongPush implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JOptionPane.showMessageDialog(getContentPane(), "Данное поле уже отмечано! Необходимо выбрать другое пустое поле, для совершения хода!", "Ошибка хода", JOptionPane.DEFAULT_OPTION);
            synchronized (CoursemainGui.monitorCoursemainGUI) {
                CoursemainGui.monitorCoursemainGUI.notify();
            }
        }
    }

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

    private void setupGUI(int FieldSize, GameSwingGUI gameSwingGUI) {
        JFrame.setDefaultLookAndFeelDecorated(true);
        new JLayeredPane();
        setAlwaysOnTop(true);
        this.FieldSize = FieldSize;
        setSize(FieldSize * 150 + 10, FieldSize * 150 + 215);
        //System.out.println("width " + getWidth() + "; height " + getHeight());
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        JMenuBar jMenuBar = new JMenuBar();
        jMenuBar.add(MainMenu());
        setJMenuBar(jMenuBar);
        jMenuBar.setBackground(Color.darkGray);
        //jMenuBar.setForeground(Color.WHITE);

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

        JPanel bPanel = new JPanel(new BorderLayout());
        //getContentPane().add(bPanel);
        //bPanel.
        bPanel.setBackground(Color.darkGray);

        Dimension dimension_image = new Dimension(150 * FieldSize + 10, 99);
        ImagePanel imagePanel = new ImagePanel();
        imagePanel.setPreferredSize(dimension_image);
        imagePanel.setMinimumSize(dimension_image);
        imagePanel.setMaximumSize(dimension_image);
        getContentPane().add(imagePanel, BorderLayout.NORTH);

        JPanel gPanel = new JPanel(new GridLayout(FieldSize, FieldSize, 6, 6));
        Dimension dimension_main = new Dimension(150 * FieldSize + 10, 150 * FieldSize + 10);
        gPanel.setPreferredSize(dimension_main);
        gPanel.setMaximumSize(dimension_main);
        gPanel.setMinimumSize(dimension_main);


        buttons = new JButton[(int) (Math.pow(FieldSize, 2) + 1)];

        for (int i = 1; i < (int) (Math.pow(FieldSize, 2) + 1); i++) {
            buttons[i] = new JButton();
            buttons[i].setActionCommand(String.valueOf(i));
            File file_e = new File("C:\\Users\\manapov-ay\\Desktop\\Крестики-Нолики\\empty_11_150.jpg");
            Image image = null;
            try {
                image = ImageIO.read(file_e);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Icon icon = new ImageIcon(image);
            buttons[i].setIcon(icon);
            buttons[i].addActionListener(new Push());
            gPanel.add(buttons[i]);
        }

        getContentPane().add(gPanel, BorderLayout.CENTER);

        jLabel = new JLabel();
        JPanel sPanel = new JPanel();
        sPanel.add(jLabel);
        jLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
        jLabel.setBackground(Color.darkGray);
        jLabel.setForeground(Color.WHITE);
        Dimension dimension_label = new Dimension(150 * FieldSize + 10, 27);
        sPanel.setPreferredSize(dimension_label);
        sPanel.setMinimumSize(dimension_label);
        sPanel.setMaximumSize(dimension_label);
        getContentPane().add(sPanel, BorderLayout.SOUTH);

        //gPanel.setLayout(grid);
        sPanel.setBackground(Color.darkGray);
        gPanel.setBackground(Color.darkGray);

        //getContentPane().add(bPanel);
        //add(bPanel);
        setVisible(true);
    }
}










