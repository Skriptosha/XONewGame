class CompLogicEven extends CompLogic {
    private String ret = "";

    @Override
    String initLogic(String[][] matrix, int course, int WinCombination) {
        if (CompLogic.getInitializationCount() != 1) {
            System.out.println("CompLogic не была инициализированна");
            return "-1";
        }
        // шаг 1
        int c = matrix.length;
        if (course == 0) super.setPrevious_course(ret = cl.rand_coal(matrix));
        //System.out.println(previous_course);
        //System.out.println("sm.i " + sm.i + "sm.j " + sm.j);

        sm_player.search(matrix, "X");
        //System.out.println("Main.course " + Main.course);

        // шаг 2
        if (course == 2 & sm_player.getI() == (c-1)/2 & sm_player.getJ() == (c-1)/2) {
            System.out.println(super.getPrevious_course());
            System.out.println(ai_pair1);
            if (ai_pair1.contains(super.getPrevious_course())) {
                ai_pair1.remove(super.getPrevious_course());
                ret = ai_pair1.get(0);
            }
            if (ai_pair2.contains(super.getPrevious_course())) {
                ai_pair2.remove(super.getPrevious_course());
                ret = ai_pair2.get(0);
            }
        }

        if (course == 2 & (sm_player.getI() != (c-1)/2 || sm_player.getJ() != (c-1)/2)) {
            //sm.search(Paint.matrix_1, "X");
            sm_ai.search(super.getMatrix_temp(), super.getPrevious_course());
            if (sm_player.getI() == sm_ai.getI()) {
                ai_pair3.remove(ai_pair3.indexOf(sm_ai.getI()));
                //System.out.println("sm.i " + sm_1.i + "sm.j " + sm_1.j);
                super.setPrevious_course(ret = super.getMatrix_temp()[ai_pair3.get(0)][sm_ai.getJ()]);
            }

            if (sm_player.getJ() == sm_ai.getJ()) {
                //System.out.println("sm_ai.getJ() " + sm_ai.getJ());
                System.out.println(ai_pair3);
                ai_pair3.remove(ai_pair3.indexOf(sm_ai.getJ()));
                //System.out.println("sm.i " + sm_1.i + "sm.j " + sm_1.j);
                super.setPrevious_course(ret = super.getMatrix_temp()[sm_ai.getI()][ai_pair3.get(0)]);
            }

            if (sm_player.getI() != sm_ai.getI() & sm_player.getJ() != sm_ai.getJ()) {

                if (random.nextInt(1) == 0) {
                    ai_pair3.remove(ai_pair3.indexOf(sm_ai.getI()));
                    super.setPrevious_course(ret = super.getMatrix_temp()[ai_pair3.get(0)][sm_ai.getJ()]);
                } else {
                    ai_pair3.remove(ai_pair3.indexOf(sm_ai.getI()));
                    super.setPrevious_course(ret = super.getMatrix_temp()[sm_ai.getI()][ai_pair3.get(0)]);
                }
            }
        }


        return ret;
    }


}
