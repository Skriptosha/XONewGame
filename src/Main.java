
public class Main {

    public static void main(String[] args) {

        System.out.println("Вы хотите начать игру? Y/N");
        String s;
        ObjScanner sc = new ObjScanner();

        s = sc.scan();
        while (!s.equalsIgnoreCase("Y") & !s.equalsIgnoreCase("N")) {
            System.out.println("Необходимо нажать клавишу Y - если да и N - если нет!");
            s = sc.scan();
        }

        if (s.equals("N")) {
            System.exit(0);
        }

        System.out.println("Выберите интерфейс игры: Консоль - K, Графический интерфейс - G!");
        s = sc.scan();
        while (!s.equalsIgnoreCase("K") & !s.equalsIgnoreCase("G")) {

            System.out.println("Необходимо нажать клавишу K - если Консоль и G - если Графический интерфейс!");
            s = sc.scan();
        }
        Thread.currentThread().setName("MainGame");
        if (s.equalsIgnoreCase("K")) {
            new GameConsole().GameSetting();
        } else {
            new GameSwingGUI(null, 0, null).GameSetting();
        }
    }
}
