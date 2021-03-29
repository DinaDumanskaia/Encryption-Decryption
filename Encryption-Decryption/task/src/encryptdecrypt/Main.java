package encryptdecrypt;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import static encryptdecrypt.Main.Mode.DECRYPTION;
import static encryptdecrypt.Main.Mode.ENCRYPTION;

public class Main {
    private static final String SHIFT = "shift";

    private static final Map<Integer, Character> LWRCS_LETTERS = new HashMap<>();
    private static final Map<Integer, Character> UPRCS_LETTERS = new HashMap<>();

    static {
        int index = 0;
        for (char letter = 'a'; letter <= 'z'; letter++) {
            LWRCS_LETTERS.put(index++, letter);
        }

        index = 0;
        for (char letter = 'A'; letter <= 'Z'; letter++) {
            UPRCS_LETTERS.put(index++, letter);
        }
    }

    public static void main(String[] args) throws IOException {
        Arguments arguments = parseArguments(args);
        // 1. execute algorithm
        String result;
        if (arguments.getMode() == ENCRYPTION) {
            result = arguments.getAlgorithm().encryption(arguments.getData(), arguments.getKey());
        } else {
            result = arguments.getAlgorithm().decryption(arguments.getData(), arguments.getKey());
        }

        // 2. output results
        if (result == null || result.isEmpty()) {
            // распечатать на экран
            printOnScreen(result);
        } else {
            // отправить в файл
            writeToFile(arguments.getOutputFilePath(), result);
        }
    }


    // todo: implement
    private static void printOnScreen(String result) {
        System.out.println(result);
    }

    // todo: implement
    private static void writeToFile(String outputFilePath, String result) throws IOException {
        if (outputFilePath != null) {
            File file = new File(outputFilePath);
            FileWriter writer = new FileWriter(file); // overwrites the file

            writer.write(result);

            writer.close();
        } else {
            printOnScreen(result);
        }
    }

    private static Arguments parseArguments(String[] args) {
        Arguments arguments = new Arguments();

        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "-mode":
                    Mode mode = args[i + 1].equals("enc") ? ENCRYPTION : DECRYPTION;
                    arguments.setMode(mode);
                    i++;
                    break;
                case "-key":
                    arguments.setKey(Integer.parseInt(args[i + 1]));
                    i++;
                    break;
                case "-data":
                    arguments.setData(args[i + 1]);
                    i++;
                    break;
                case "-in":
                    arguments.setInputFilePath(args[i + 1]);
                    i++;
                    break;
                case "-out":
                    arguments.setOutputFilePath(args[i + 1]);
                    i++;
                    break;
                case "-alg": // todo: check parameter name
                    arguments.setAlgorithm(args[i + 1]);
                    i++;
                    break;
            }
        }

        // set defaults
        if (arguments.getMode() == null) {
            arguments.setMode(ENCRYPTION);
        }

        // todo: тоже самое для остальных полей
        if (arguments.getAlgorithm() == null) {
            arguments.setAlgorithm("shift");
        }
        if (arguments.getData() == null && arguments.getInputFilePath() != null) {
            arguments.setData(getStringFromFile(arguments.getInputFilePath()));
        } else if (arguments.getData() == null) {
            arguments.setData("");
        }
        return arguments;
    }

    private static String getStringFromFile(String fileName) {
        String toCrypt = "";
        try {
            toCrypt = new String(Files.readAllBytes(Paths.get(fileName)));
        } catch (IOException e) {
            System.out.println("Error! Cannot read file: " + e.getMessage());
        }
        return toCrypt;
    }

    // Additional enums/interfaces/classes
    enum Mode {
        ENCRYPTION,
        DECRYPTION
    }

    interface CryptAlgorithm {
        String encryption(String toProcess, int key);

        String decryption(String toProcess, int key);
    }

    // todo: implement
    static class ShiftAlgorithm implements CryptAlgorithm {

        @Override
        public String encryption(String toProcess, int key) {
            return shiftMethod(toProcess, key, ENCRYPTION);
        }

        @Override
        public String decryption(String toProcess, int key) {
            return shiftMethod(toProcess, key, DECRYPTION);
        }

        private String shiftMethod(String toProcess, int key, Mode mode) {
            StringBuilder builder = new StringBuilder();
            char[] toEncrypt = toProcess.toCharArray();
            for (char c : toEncrypt) {
                if (mode == ENCRYPTION) {
                    if (LWRCS_LETTERS.containsValue(c)) {
                        shiftLetter(key, builder, c, LWRCS_LETTERS, ENCRYPTION);
                    } else if (UPRCS_LETTERS.containsValue(c)) {
                        shiftLetter(key, builder, c, UPRCS_LETTERS, ENCRYPTION);
                    } else {
                        builder.append(c);
                    }
                } else {
                    if (LWRCS_LETTERS.containsValue(c)) {
                        shiftLetter(key, builder, c, LWRCS_LETTERS, DECRYPTION);
                    } else if (UPRCS_LETTERS.containsValue(c)) {
                        shiftLetter(key, builder, c, UPRCS_LETTERS, DECRYPTION);
                    } else {
                        builder.append(c);
                    }
                }
            }
            return builder.toString();
        }

        private static void shiftLetter(int key, StringBuilder builder, char c, Map<Integer, Character> letters, Mode mode) {
            int shifted;
            if (mode == ENCRYPTION) {
                shifted = letterCounter(c, letters) + key;
                if (shifted > letters.size() - 1) {
                    shifted = letters.size() - shifted;
                }
            } else {
                shifted = letterCounter(c, letters) - key;
                if (shifted < 0) {
                    shifted = letters.size() + shifted;
                }
            }
            builder.append(letters.get(Math.abs(shifted)));
        }

        private static int letterCounter(char c, Map<Integer, Character> letters) {
            int counter = 0;
            for (int i = 0; i < letters.size(); i++) {
                if (c != letters.get(i)) {
                    counter++;
                } else {
                    return counter;
                }
            }
            return counter;
        }

    }

    // todo: implement
    static class UnicodeAlgorithm implements CryptAlgorithm {

        @Override
        public String encryption(String toProcess, int key) {
            char[] toEncrypt = toProcess.toCharArray();
            char[] encrypted = new char[toProcess.length()];

            if (toEncrypt.length != 0) {
                encrypted = getEnc(toEncrypt, key);
            } else {
                System.out.println("Data is an empty string");
            }
            return String.valueOf(encrypted);
        }

        private char[] getEnc(char[] toEncrypt, int key) {
            char[] result = new char[toEncrypt.length];
            for (int i = 0; i < toEncrypt.length; i++) {
                result[i] = (char) (toEncrypt[i] + key);
            }
            return result;
        }

        @Override
        public String decryption(String toProcess, int key) {
            char[] toDecrypt = toProcess.toCharArray();
            char[] decrypted = new char[toProcess.length()];

            if (toDecrypt.length == 0) {
                System.out.println("Data is an empty string");
            } else {
                decrypted = getDec(toDecrypt, key);
            }
            return String.valueOf(decrypted);
        }

        private static char[] getDec(char[] toDecrypt, int key) {
            char[] result = new char[toDecrypt.length];
            for (int i = 0; i < toDecrypt.length; i++) {
                result[i] = (char) (toDecrypt[i] - key);
            }
            return result;
        }
    }


    static class Arguments {
        private static final int NOT_INITIALIZED = Integer.MIN_VALUE;
        private int key = NOT_INITIALIZED;
        private Mode mode = null;
        private String data = null;
        private String inputFilePath = null;
        private String outputFilePath = null;
        private CryptAlgorithm algorithm = null;

        int getKey() {
            return key == NOT_INITIALIZED ? 0 : key;
        }

        void setKey(int key) {
            this.key = key;
        }

        Mode getMode() {
            return mode == null ? ENCRYPTION : mode;
        }

        void setMode(Mode mode) {
            this.mode = mode;
        }

        String getData() {
            return data;
        }

        void setData(String data) {
            this.data = data;
        }

        String getInputFilePath() {
            return inputFilePath;
        }

        void setInputFilePath(String inputFilePath) {
            this.inputFilePath = inputFilePath;
        }

        String getOutputFilePath() {
            return outputFilePath;
        }

        void setOutputFilePath(String outputFilePath) {
            this.outputFilePath = outputFilePath;
        }

        CryptAlgorithm getAlgorithm() {
            return algorithm == null ? new ShiftAlgorithm() : algorithm;
        }

        void setAlgorithm(String algorithm) {
            this.algorithm = algorithm.equals(SHIFT) ? new ShiftAlgorithm() : new UnicodeAlgorithm();
        }
    }
}
