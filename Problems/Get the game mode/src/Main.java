
class Problem {
    public static void main(String[] args) {
        String result = null;
        for (int i = 0; i < args.length; i++) {
            if (i % 2 == 0 && args[i].equals("mode")) {
                result = args[i + 1];
                System.out.println(result);
            }
        }
        if (result == null) {
            System.out.println("default");
        }
    }
}