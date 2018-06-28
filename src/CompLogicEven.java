class CompLogicEven extends CompLogic {
    private String ret = "";

    /**
     * Четные шаги ПК, Первые два шага.
     *
     * @param matrix         Игровая матрица
     * @param course         Текущий ход
     * @param WinCombination Колличество Х или О на одной линии для выигрышной комбинации
     * @return Возвращает Число на игровой матрице, куда сходить.
     */
    @Override
    String initLogic(String[][] matrix, int course, int WinCombination) {
        if (CompLogic.getInitializationCount() != 1) {
            System.out.println("CompLogic не была инициализированна");
            return "-1";
        }

        // шаг 1
        int c = matrix.length;
        if (course == 0) super.setPrevious_course(ret = cl.rand_coal(matrix));
        sm_player.search(matrix, "X");

        // шаг 2
        if (course == 2 && sm_player.getI() == (c - 1) / 2 && sm_player.getJ() == (c - 1) / 2) {

            if (ai_pair1.contains(super.getPrevious_course())) {
                ai_pair1.remove(super.getPrevious_course());
                super.setPrevious_course(ret = ai_pair1.get(0));
            }
            if (ai_pair2.contains(super.getPrevious_course())) {
                ai_pair2.remove(super.getPrevious_course());
                super.setPrevious_course(ret = ai_pair2.get(0));
            }
        } else if (course == 2) {
            // System.out.println("jfgnjkn " + super.getPrevious_course());
            sm_ai.search(matrix, "O");
            // System.out.println(sm_player.getI() + " " + sm_ai.getI());
            if (sm_player.getI() == sm_ai.getI()) {
                do {
                    super.setPrevious_course(ret = cl.rand_coal(matrix));
                    //System.out.println(ret + " sm_player.getI() == sm_ai.getI()");
                    sm_temp.search(matrix, ret);
                } while (sm_temp.getI() == sm_player.getI());

            }
            // System.out.println(sm_player.getJ() + " " + sm_ai.getJ());
            if (sm_player.getJ() == sm_ai.getJ()) {
                do {
                    super.setPrevious_course(ret = cl.rand_coal(matrix));
                    //System.out.println(ret + " sm_temp.getJ() == sm_player.getJ()");
                    sm_temp.search(matrix, ret);
                } while (sm_temp.getJ() == sm_player.getJ());

            }

            if (sm_player.getI() != sm_ai.getI() && sm_player.getJ() != sm_ai.getJ()) {
                //System.out.println(ret + " cl.rand_coal(matrix)");
                super.setPrevious_course(ret = cl.rand_coal(matrix));
            }
        }
        // System.out.println("end " + ret);
        return ret;
    }
}