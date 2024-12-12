import java.io.*;
import java.util.Scanner;

public class HashTableTextGenerator {
    private static final int HASH_TABLE_SIZE = 1000;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Get user input for window size and output length
        System.out.print("Enter a positive window size: ");
        int w = scanner.nextInt(); // Window size
        scanner.nextLine();

        System.out.print("Enter a positive length of the output text: ");
        int outputLength = scanner.nextInt();
        scanner.nextLine();

        // Read input text from file
        String inputText = readFile("merchant.txt");
        if (inputText == null) {
            System.out.println("Failed to read input file. Exiting.");
            return;
        }

        // Check if input text is long enough for the given window size
        if (inputText.length() < w) {
            System.out.println("Input text is too short for the given window size.");
            return;
        }

        // Create hash table to store character distributions
        HashTable table = new HashTable(HASH_TABLE_SIZE);

        // Build character distributions
        for (int i = 0; i <= inputText.length() - w - 1; i++) {
            String window = inputText.substring(i, i + w);
            char nextChar = inputText.charAt(i + w);

            CharDistribution dist = table.get(window);
            if (dist == null) {
                dist = new CharDistribution();
                table.put(window, dist);
            }
            dist.occurs(nextChar);
        }

        // Generate output text
        StringBuilder output = new StringBuilder(inputText.substring(0, w));
        for (int i = w; i < outputLength; i++) {
            String window = output.substring(output.length() - w);
            CharDistribution dist = table.get(window);

            if (dist == null) {
                output.append(' ');
            } else {
                char nextChar = dist.getRandomChar();
                output.append(nextChar);
            }
        }

        // Write output to file
        writeFile("output.txt", output.toString());
        System.out.println("Output has been written to output.txt");
    }

    // Helper method to read file content
    private static String readFile(String filename) {
        StringBuilder content = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                content.append(line).append(" ");
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + filename);
            e.printStackTrace();
            return null;
        }
        return content.toString().toLowerCase();
    }

    // Helper method to write content to file
    private static void writeFile(String filename, String content) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write(content);
        } catch (IOException e) {
            System.err.println("Error writing to file: " + filename);
            e.printStackTrace();
        }
    }

    // Node class for hash table entries
    static class HashNode {
        String key;
        CharDistribution value;
        HashNode next;

        HashNode(String key, CharDistribution value) {
            this.key = key;
            this.value = value;
            this.next = null;
        }
    }

    // Hash table implementation using separate chaining
    static class HashTable {
        private HashNode[] buckets;
        private int size;
        private int capacity;

        HashTable(int capacity) {
            this.capacity = capacity;
            this.size = 0;
            this.buckets = new HashNode[capacity];
        }

        // Hash function using polynomial rolling hash
        private int hash(String key) {
            int hash = 0;
            for (char c : key.toCharArray()) {
                hash = (hash * 31 + c) % capacity;
            }
            return hash;
        }

        // Insert or update a key-value pair in the hash table
        void put(String key, CharDistribution value) {
            int index = hash(key);
            HashNode newNode = new HashNode(key, value);

            if (buckets[index] == null) {
                buckets[index] = newNode;
            } else {
                HashNode current = buckets[index];
                while (current.next != null) {
                    if (current.key.equals(key)) {
                        current.value = value;
                        return;
                    }
                    current = current.next;
                }
                if (current.key.equals(key)) {
                    current.value = value;
                } else {
                    current.next = newNode;
                }
            }
            size++;
        }

        // Retrieve a value from the hash table by key
        CharDistribution get(String key) {
            int index = hash(key);
            HashNode current = buckets[index];
            while (current != null) {
                if (current.key.equals(key)) {
                    return current.value;
                }
                current = current.next;
            }
            return null;
        }
    }

    // Class to store character distribution for a given window
    static class CharDistribution {
        private int[] freq;
        private int totalChars;

        CharDistribution() {
            freq = new int[27]; // 26 lowercase letters + space
            totalChars = 0;
        }

        // Record occurrence of a character
        void occurs(char c) {
            int index = Character.isLetter(c) ? c - 'a' : 26;
            if (index < 0 || index >= 27) {
                index = 26; // Use last index for non-letter characters
            }
            freq[index]++;
            totalChars++;
        }

        // Get a random character based on the distribution
        char getRandomChar() {
            if (totalChars == 0) {
                return ' ';
            }

            int rand = (int) (Math.random() * totalChars) + 1;
            int cumulative = 0;

            for (int i = 0; i < freq.length; i++) {
                cumulative += freq[i];
                if (rand <= cumulative) {
                    return (i == 26) ? ' ' : (char) ('a' + i);
                }
            }

            return ' '; // Default return, should never reach here
        }
    }
}