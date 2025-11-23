package core.panels;

public class PanelManager {

    private final Panel1 panel1 = new Panel1();
    private final Panel2 panel2 = new Panel2();
    private final Panel3 panel3 = new Panel3();
    private final Panel4 panel4 = new Panel4();
    private final Panel5 panel5 = new Panel5();
    private final Panel6 panel6 = new Panel6();
    private final Panel7 panel7 = new Panel7();

    public PanelManager() {
        /*
        initPanel1();
        */
    }

    private void initPanel1() {
        /*
        panel1 = new Panel1();
        panel1.setScroll(40);
        panel1.setCustom("ae");
        */
    }


    public Panel1 getPanel1() {
        return panel1;
    }

    public Panel2 getPanel2() {
        return panel2;
    }

    public Panel3 getPanel3() {
        return panel3;
    }

    public Panel4 getPanel4() {
        return panel4;
    }

    public Panel5 getPanel5() {
        return panel5;
    }

    public Panel6 getPanel6() {
        return panel6;
    }

    public Panel7 getPanel7() {
        return panel7;
    }
}
