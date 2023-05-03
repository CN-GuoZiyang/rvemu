package moe.ziyang.rvemu.infra;

public class Debug {

    public static void panic(EmuException e) {
        System.err.println(e.toString());
        e.printStackTrace();
        System.exit(0);
    }

}
