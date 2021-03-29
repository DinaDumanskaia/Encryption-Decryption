import java.util.*;

class AsciiCharSequence implements CharSequence {
    byte[] bytes;

    public AsciiCharSequence(byte[] bytes) {
        this.bytes = new byte[bytes.length];
        System.arraycopy(bytes, 0, this.bytes, 0, bytes.length);
    }

    @Override
    public int length() {
        return bytes.length;
    }

    @Override
    public char charAt(int i) {
        return (char) bytes[i];
    }

    @Override
    public CharSequence subSequence(int start, int end) {
        return new AsciiCharSequence(Arrays.copyOfRange(bytes, start, end));
    }
    // implementation

    public String toString() {
        return new String(bytes, StandardCharsets.UTF_8);
    }
}
