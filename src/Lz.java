import java.util.ArrayList;
import java.util.List;

public class Lz {
    private static final int WINDOW_SIZE = 20;

    public static List<Byte> compress(byte[] data) {
        List<Byte> result = new ArrayList<>();
        if (data.length == 0) {
            return result;
        }

        int i = 0;
        while (i < data.length) {
            int windowStart = Math.max(0, i - WINDOW_SIZE);
            int windowLength = i - windowStart;

            int bestMatchLen = 0;
            int bestMatchOffset = 0;

            for (int offset = 1; offset <= windowLength; offset++) {
                int matchLen = 0;
                while (i + matchLen < data.length &&
                        matchLen < 255 &&
                        (windowLength - offset + matchLen) < windowLength &&
                        data[windowStart + windowLength - offset + matchLen] == data[i + matchLen]) {
                    matchLen++;
                }

                if (matchLen > bestMatchLen) {
                    bestMatchLen = matchLen;
                    bestMatchOffset = offset;
                }
            }

            if (bestMatchLen >= 3) {
                result.add((byte) 0x01);
                result.add((byte) bestMatchOffset);
                result.add((byte) bestMatchLen);
                i += bestMatchLen;
            } else {
                result.add((byte) 0x00);
                result.add(data[i]);
                i += 1;
            }
        }

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

            byte flag = data[i];
            if (flag == 0x00) {
                if (i + 1 < data.length) {
                    result.add(data[i + 1]);
                    i += 2;
                }
            } else if (flag == 0x01) {
                if (i + 2 < data.length) {
                    int offset = data[i + 1] & 0xFF;
                    int length = data[i + 2] & 0xFF;

                    int start = Math.max(0, result.size() - offset);
                    for (int j = 0; j < length; j++) {
                        if (start + j < result.size()) {
                            result.add(result.get(start + j));
                        }
                    }
                    i += 3;
                }
            } else {
                i += 1;
            }
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


// import java.util.ArrayList;
// import java.util.List;

// public class Lz {
//     public static final int WINDOW_SIZE = 20;

//     public List<Byte> compress(byte[] data) {
//         if (data.length == 0) {
//             List<Byte> result = new ArrayList<>();
//             return result;
//         }

//         List<Byte> result = new ArrayList<>();
//         int i = 0;

//         while (i < data.length) {
//             int windowStart = Math.max(0, i - WINDOW_SIZE);
//             int windowLength = i - windowStart;

//             int bestMatchLen = 0;
//             int bestMatchOffset = 0;

//             for (int offset = 1; offset <= windowLength; offset++) {
//                 int matchLen = 0;
//                 while (i + matchLen < data.length &&
//                         matchLen < 255 &&
//                         (windowLength - offset + matchLen) < windowLength &&
//                         data[windowStart + windowLength - offset + matchLen] == data[i + matchLen]) {
//                     matchLen++;
//                 }

//                 if (matchLen > bestMatchLen) {
//                     bestMatchLen = matchLen;
//                     bestMatchOffset = offset;
//                 }
//             }

//             if (bestMatchLen >= 3) {
//                 // Output a match
//                 result.add((byte) 0x01);
//                 result.add((byte) bestMatchOffset);
//                 result.add((byte) bestMatchLen);
//                 i += bestMatchLen;
//             } else {
//                 // Output a literal
//                 result.add((byte) 0x00);
//                 result.add(data[i]);
//                 i += 1;
//             }
//         }
//         return result;

//     }

    
// }
