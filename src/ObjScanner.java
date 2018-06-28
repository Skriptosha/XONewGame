import java.util.Scanner;

public class ObjScanner {

    /**
     * Объект сканер, используется для консольной версии.
     *
     * @return Ввод пользователя
     */
    public String scan() {
        Scanner sc = new Scanner(System.in);
        return sc.next();
    }
}
