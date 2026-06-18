# Adaptive File Compressor

A Java-based lossless file compressor and decompressor using the Huffman Coding algorithm.

## Features
* **Lossless Compression:** Perfect reconstruction of the original text using variable-length prefix codes.
* **Bit-Packing:** Converts binary strings into actual 8-bit bytes to reduce physical file size.
* **Built-in Verification:** Automatically compares the original and decompressed files byte-by-byte to ensure data integrity.
* **Efficiency Calculation:** Calculates and displays the compression ratio (space saved).

## Architecture
The project is designed with Object-Oriented Programming (OOP) and SOLID principles:
* **Facade Pattern:** A `CompressorFacade` manages the complex subsystems (`TreeManager`, `Encoder`, `Decoder`, `FileHandler`, `FrequencyAnalyzer`).
* **Dependency Injection:** Interfaces are injected into the Facade to decouple file I/O operations from the compression logic.
* **Single Responsibility:** Each class has a single, well-defined task (e.g., `FrequencyAnalyzer` only counts characters).

## How It Works
1. **Frequency Analysis:** Reads the input file and counts character occurrences.
2. **Tree Building:** Uses a Priority Queue (Min-Heap) to construct the Huffman Tree.
3. **Encoding:** Generates unique binary codes for each character (shorter codes for frequent characters).
4. **File I/O:** Saves the frequency map (header), valid bit length, and packed bytes into a `.bin` file. Decompression reverses this process.

## Performance Note (Header Overhead)
* **Large Files:** Highly efficient. For example, compressing large datasets (like Wikipedia text) yields a ~40% space reduction.
* **Small Files:** Very small files (e.g., 1 KB) may increase in size due to the "header overhead" required to store the frequency map inside the compressed file. The program's verification system correctly reports this as an expected algorithmic failure for small datasets.

## Usage
Run the `Main.java` class. The console interface will prompt you to select a test file:
```text
1. test.txt
2. wikipedia.txt
3. Exit

Select a file:
