import java.util.ArrayList;
import java.util.List;

public class Rle {
    public static List<Byte> compress(byte[] data) {
        List<Byte> result = new ArrayList<>();
        if (data.length == 0) {
            return result;
        }

        int count = 1;
        byte current = data[0];

        for (int i = 1; i < data.length; i++) {
            byte b = data[i];
            if (b == current && count < 255) {
                count++;
            } else {
                result.add((byte) count);
                result.add(current);
                current = b;
                count = 1;
            }
        }

        // Push the last group
        result.add((byte) count);
        result.add(current);

        return result;
    }

    public static List<Byte> decompress(byte[] data) {
        List<Byte> result = new ArrayList<>();
        if (data.length == 0) {
            return result;
        }

        int i = 0;
        while (i < data.length) {
            if (i + 1 >= data.length) {
                break;
            }

            int count = data[i] & 0xFF;
            byte value = data[i + 1];
            for (int j = 0; j < count; j++) {
                result.add(value);
            }
            i += 2;
        }

        return result;
    }

    public static byte[] toByteArray(List<Byte> list) {
        byte[] arr = new byte[list.size()];
        for (int i = 0; i < list.size(); i++) {
            arr[i] = list.get(i);
        }
        return arr;
    }
}
