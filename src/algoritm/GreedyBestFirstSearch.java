package algoritm;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class GreedyBestFirstSearch {

    public void solve(String startWord, String endWord) {
        long startTime = System.nanoTime(); // Waktu awal eksekusi

        // Inisialisasi set untuk menyimpan state yang sudah dieksplorasi
        Set<String> explored = new HashSet<>();

        // Inisialisasi priority queue untuk menyimpan state yang akan dieksplorasi
        PriorityQueue<Node> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(Node::getHeuristic));
        priorityQueue.add(new Node(startWord, heuristic(startWord, endWord), null));

        // Inisialisasi variabel untuk menyimpan jalur yang ditemukan
        List<String> path = null;

        // Inisialisasi variabel untuk menyimpan banyaknya node yang dikunjungi
        int visitedNodes = 0;

        // Memulai ekplorasi
        while (!priorityQueue.isEmpty()) {
            // Ambil state dengan heuristic terendah dari priority queue
            Node currentNode = priorityQueue.poll();

            // Tambahkan state saat ini ke dalam set explored
            explored.add(currentNode.getWord());

            // Increment jumlah node yang dikunjungi
            visitedNodes++;

            // Periksa jika state saat ini adalah end word
            if (currentNode.getWord().equals(endWord)) {
                // Rekam jalur yang ditemukan
                path = new ArrayList<>();
                Node traceBackNode = currentNode;
                while (traceBackNode != null) {
                    path.add(traceBackNode.getWord());
                    traceBackNode = traceBackNode.getParent();
                }
                Collections.reverse(path);
                break;
            }

            // Dapatkan tetangga dari state saat ini
            List<String> neighbors = getNeighbors(currentNode.getWord(), explored);

            // Eksplorasi tetangga
            for (String neighbor : neighbors) {
                Node neighborNode = new Node(neighbor, heuristic(neighbor, endWord), currentNode);
                priorityQueue.add(neighborNode);
            }
        }

        long endTime = System.nanoTime(); // Waktu akhir eksekusi
        double executionTime = (endTime - startTime) / 1e6; // Konversi waktu ke milidetik

        // Cetak output
        if (path != null) {
            System.out.println("Path: " + String.join(" -> ", path));
            System.out.println("Banyaknya node yang dikunjungi: " + visitedNodes);
            System.out.println("Waktu eksekusi program: " + executionTime + " milidetik");
        } else {
            System.out.println("Tidak ditemukan jalur yang menghubungkan " + startWord + " dan " + endWord);
            System.out.println("Waktu eksekusi program: " + executionTime + " milidetik");
        }
    }

    private int heuristic(String word, String endWord) {
        // Menghitung heuristik sebagai jumlah karakter yang berbeda antara word dan
        // endWord
        int count = 0;
        for (int i = 0; i < word.length(); i++) {
            if (word.charAt(i) != endWord.charAt(i)) {
                count++;
            }
        }
        return count;
    }

    private List<String> getNeighbors(String word, Set<String> explored) {
        // Metode untuk mendapatkan tetangga (kata yang berbeda satu huruf) dari sebuah
        // kata
        List<String> neighbors = new ArrayList<>();
        for (int i = 0; i < word.length(); i++) {
            char[] charArray = word.toCharArray();
            for (char c = 'a'; c <= 'z'; c++) {
                charArray[i] = c;
                String newWord = new String(charArray);
                if (!newWord.equals(word) && isValidEnglishDictionaryLocal(newWord) && !explored.contains(newWord)) {
                    neighbors.add(newWord);
                }
            }
        }
        return neighbors;
    }

    // Kamus bahasa Inggris
    private static final String DICTIONARY_FILE_PATH = "Tucil3_13522154/src/kamus.txt";
    private static Set<String> dictionary = new HashSet<>(); // Set untuk menyimpan kata-kata dalam kamus

    // Method untuk memuat kamus dari file teks ke dalam Set
    private static void loadDictionary() {
        try (BufferedReader reader = new BufferedReader(new FileReader(DICTIONARY_FILE_PATH))) {
            String word;
            while ((word = reader.readLine()) != null) {
                dictionary.add(word.toLowerCase());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method untuk memeriksa apakah kata ada dalam kamus
    private static boolean isValidEnglishDictionaryLocal(String word) {
        return dictionary.contains(word.toLowerCase());
    }

    static {
        // Memuat kamus saat program dimulai
        loadDictionary();
    }

    static class Node {
        private String word;
        private int heuristic;
        private Node parent;

        public Node(String word, int heuristic, Node parent) {
            this.word = word;
            this.heuristic = heuristic;
            this.parent = parent;
        }

        public String getWord() {
            return word;
        }

        public int getHeuristic() {
            return heuristic;
        }

        public Node getParent() {
            return parent;
        }
    }
}
