import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        if (args.length < 4) {
            System.err.println("Usage: java Main <compress|decompress> <input> <output> --algorithm <rle|lz>");
            System.exit(1);
        }

        String operation = args[0];
        String inputPath = args[1];
        String outputPath = args[2];
        String algorithm = parseAlgorithmArg(args);

        try {
            byte[] inputData = Files.readAllBytes(Path.of(inputPath));
            List<Byte> outputList;

            switch (operation) {
                case "compress":
                    outputList = switch (algorithm) {
                        case "rle" -> Rle.compress(inputData);
                        case "lz" -> Lz.compress(inputData);
                        default -> throw new IllegalArgumentException("Unsupported algorithm: " + algorithm);
                    };
                    break;

                case "decompress":
                    outputList = switch (algorithm) {
                        case "rle" -> Rle.decompress(inputData);
                        case "lz" -> Lz.decompress(inputData);
                        default -> throw new IllegalArgumentException("Unsupported algorithm: " + algorithm);
                    };
                    break;

                default:
                    throw new IllegalArgumentException("Unsupported operation: " + operation);
            }

            Files.write(Path.of(outputPath), toByteArray(outputList));
            System.out.println("Operation completed successfully!");
        } catch (IOException e) {
            System.err.println("I/O error: " + e.getMessage());
            System.exit(1);
        } catch (IllegalArgumentException e) {
            System.err.println("Argument error: " + e.getMessage());
            System.exit(1);
        }
    }

    private static String parseAlgorithmArg(String[] args) {
        for (int i = 3; i < args.length - 1; i++) {
            if (args[i].equals("--algorithm") || args[i].equals("-a")) {
                return args[i + 1];
            }
        }
        throw new IllegalArgumentException("Missing --algorithm argument (rle or lz)");
    }

    private static byte[] toByteArray(List<Byte> list) {
        byte[] arr = new byte[list.size()];
        for (int i = 0; i < list.size(); i++) {
            arr[i] = list.get(i);
        }
        return arr;
    }
}
