import java.util.Random;

public class RandomCoal {
    //public String[] ai_1;
    private SearchMatrix sm_ai = new SearchMatrix();
    private Random r = new Random();
    private String value_1 = "";
    private String value_2 = "";
    private String value_3 = "";
    private String value_4 = "";
    private int bound = 0;

    public String rand_coal(String[][] matrix) {
        int c = matrix.length;
        String[] ai_1 = new String[]{matrix[0][0], matrix[0][c - 1], matrix[c - 1][0], matrix[c - 1][c - 1]};
        String ret = "";
        for (String H : ai_1) {
            if (sm_ai.search(matrix, H)) {
                if (value_1.equals("")) value_1 = H;
                else if (value_2.equals("")) value_2 = H;
                else if (value_3.equals("")) value_3 = H;
                else if (value_4.equals("")) value_4 = H;
            }
        }
        if (!value_1.equals("")) bound++;
        if (!value_2.equals("")) bound++;
        if (!value_3.equals("")) bound++;
        if (!value_4.equals("")) bound++;
        if (bound > 0) {
        int bound_c = r.nextInt(bound) + 1;
        switch (bound_c)
        {
            case 1:
                ret = value_1;
                break;
            case 2:
                ret = value_2;
                break;
            case 3:
                ret = value_3;
                break;
            case 4:
                ret = value_4;
                break;
        }
        }
        //System.out.println("rand_coal ret" + ret);
        return ret;
    }
}
