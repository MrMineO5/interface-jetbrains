public class SimplifyBranch {
    public void simplifyBranch() {
        if (true) {
            System.out.println(true); //sleep 100ms
        }
        System.out.println(false); //sleep 100ms
    }

    public void simplifyBranch2() {
        System.out.println(false); //sleep 100ms
        if (true) {
            System.out.println(true); //sleep 100ms
        }
        System.out.println(false); //sleep 100ms
    }
}