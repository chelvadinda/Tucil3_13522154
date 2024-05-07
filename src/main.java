import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class main {

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
    private static boolean isValidEnglishWord(String word) {
        return dictionary.contains(word.toLowerCase());
    }

    public static void main(String[] args) {
        // Memuat kamus saat program dimulai
        loadDictionary();

        // Mendapatkan input dari pengguna
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Masukkan kata awal: ");
            String startWord = scanner.nextLine().toLowerCase(); // Konversi input ke huruf kecil
            System.out.print("Masukkan kata akhir: ");
            String endWord = scanner.nextLine().toLowerCase(); // Konversi input ke huruf kecil

            // Memvalidasi panjang kata
            if (startWord.length() != endWord.length()) {
                System.out.println("Panjang kata awal dan kata akhir harus sama.");
                return;
            }

            // Memvalidasi kata dalam bahasa Inggris
            if (!isValidEnglishWord(startWord) || !isValidEnglishWord(endWord)) {
                System.out.println("Kata yang dimasukkan harus dalam bahasa Inggris.");
                return;
            }

            System.out.print("Pilih algoritma (1 = UCS, 2 = Greedy Best First Search, 3 = A*): ");
            int algorithmChoice = scanner.nextInt(); // Membaca pilihan algoritma dari pengguna

            // Memilih algoritma yang sesuai
            switch (algorithmChoice) {
                case 1:
                    UCS ucsSolver = new UCS();
                    ucsSolver.solve(startWord, endWord); // Menyelesaikan masalah dengan algoritma UCS
                    break;
                case 2:
                    GreedyBestFirstSearch gbfsSolver = new GreedyBestFirstSearch();
                    gbfsSolver.solve(startWord, endWord); // Menyelesaikan masalah dengan algoritma Greedy Best First
                                                          // search
                    break;
                case 3:
                    AStar aStarSolver = new AStar(); // Membuat objek solver A*
                    aStarSolver.solve(startWord, endWord); // Menyelesaikan masalah dengan algoritma A*
                    break;
                default:
                    System.out.println("Pilihan algoritma tidak valid."); // Menampilkan pesan kesalahan jika pilihan
                                                                          // algoritma tidak valid
            }
        }
    }
}