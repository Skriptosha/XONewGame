public class SearchMatrix {
    private int i = 0;
    private int j = 0;

    public boolean search(String[][] mtrx, String number) {
        boolean find = false;
        int c = mtrx.length;
        for (int a = 0; a < c; a++) {
            for (int b = 0; b < c; b++) {
                if (number.equals(mtrx[a][b])) {
                    i = a;
                    j = b;
                    find = true;
                }
            }
        }
       return find;
    }

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    public int getJ() {
        return j;
    }

    public void setJ(int j) {
        this.j = j;
    }
}