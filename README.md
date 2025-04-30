# Compression-tool-java

A lightweight command-line tool for file compression and decompression using Run-Length Encoding (RLE) and LZ77 algorithms, implemented in Java.

---
## Features

- Compress and decompress files
- Two compression algorithms:
  - Run-Length Encoding (RLE)
  - Simplified LZ77
- Clean CLI interface
- Buildable as a standalone fat JAR
- Dockerized with Github Actions CI/CD
- GitHub Container Registry (GHCR) integration

---
## Usage

### Local (with Maven)

```bash
# Build
mvn clean package

# Run (RLE Compression)
java -jar target/java-compressor-1.0.0-jar-with-dependencies.jar compress input.txt output.rle --algorithm rle

# Run (LZ Decompression)
java -jar target/java-compressor-1.0.0-jar-with-dependencies.jar decompress output.lz input.txt --algorithm lz
```

### Using Docker

```bash
# Build
docker build -t java-compressor .

# Run

# Compress a file using RLE
docker run --rm -v $(pwd):/data ghcr.io/guy-ghis/java-compressor compress /data/input.txt /data/output.rle --algorithm rle

# Decompress using LZ
docker run --rm -v $(pwd):/data ghcr.io/guy-ghis/java-compressor decompress /data/output.lz /data/input.txt --algorithm lz

```
*Replace ghcr.io/guy-ghis/java-compressor with java-compressor if testing locally.*

### CLI Arguments
```bash
Usage: java-compressor <compress|decompress> <input> <output> --algorithm <rle|lz>
```

| Argument      | Description                   |
|---------------|-------------------------------|
| `compress`    | Compress the input file       |
| `decompress`  | Decompress the input file     |
| `<input>`     | path to the input file        |
| `<output>`    | Path to the output file       |
| `--algorithm` | Choose between `rle` and `lz` |

---
## License

This repository is licensed under the MIT License. See the LICENSE file for more information.