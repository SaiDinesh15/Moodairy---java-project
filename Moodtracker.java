import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class Moodtracker{

    static final String FILE_NAME = "mood_log.txt";
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            printBanner();
            printMenu();

            String choice = prompt("👉 Choose an option (1-4): ");

            switch (choice) {
                case "1":
                    logMood();
                    break;
                case "2":
                    showMoodHistory();
                    break;
                case "3":
                    showMoodSummary();
                    break;
                case "4":
                    System.out.println("\n✅ Thank you for using Mood Tracker. Stay positive! 🌞");
                    return;
                default:
                    System.out.println("⚠️ Invalid choice. Please enter a number between 1 and 4.");
            }
        }
    }

    static void printBanner() {
        System.out.println("\n========================================");
        System.out.println("🧠  Welcome to Your Daily Mood Tracker  ");
        System.out.println("========================================");
    }

    static void printMenu() {
        System.out.println("1️⃣  Log Today’s Mood");
        System.out.println("2️⃣  View Mood History");
        System.out.println("3️⃣  View Mood Summary");
        System.out.println("4️⃣  Exit");
    }

    static String prompt(String message) {
        System.out.print(message);
        return scanner.nextLine();
    }

static void logMood() {
    System.out.println("\n📅 Logging mood for " + LocalDate.now());
    System.out.println("How are you feeling today?");
    System.out.println("1️⃣ Happy 😊");
    System.out.println("2️⃣ Sad 😔");
    System.out.println("3️⃣ Stressed 😣");
    System.out.println("4️⃣ Angry 😠");
    System.out.println("5️⃣ Relaxed 😌");

    String mood = "";
    String message = "";

    try {
        int moodChoice = Integer.parseInt(prompt("👉 Enter your mood (1-5): "));

        switch (moodChoice) {
            case 1 -> {
                mood = "Happy 😊";
                message = "That’s awesome! Keep spreading those positive vibes 🌟";
            }
            case 2 -> {
                mood = "Sad 😔";
                message = "It’s okay to feel down sometimes. Better days are coming 💪";
            }
            case 3 -> {
                mood = "Stressed 😣";
                message = "Take a deep breath. You’ve got this. One step at a time 🧘‍♀️";
            }
            case 4 -> {
                mood = "Angry 😠";
                message = "Try to pause and refocus. You control the storm, not the other way around 🌬️";
            }
            case 5 -> {
                mood = "Relaxed 😌";
                message = "Great to hear! Stay grounded and enjoy the calm 🧘";
            }
            default -> {
                System.out.println("⚠️ Invalid mood choice.");
                return;
            }
        }

        String entry = LocalDate.now() + ": " + mood;
        writeToFile(entry);

        // Friendly UX
        System.out.println("✅ Mood logged successfully!");
        System.out.println("💬 Message for you: " + message);

    } catch (NumberFormatException e) {
        System.out.println("⚠️ Invalid input. Please enter a number.");
    } catch (IOException e) {
        System.out.println("❌ Error writing to file: " + e.getMessage());
    }
}


    static void writeToFile(String entry) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true));
        writer.write(entry);
        writer.newLine();
        writer.close();
    }

    static void showMoodHistory() {
        System.out.println("\n📖 Your Mood History:");
        System.out.println("----------------------------------------");

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            boolean hasData = false;
            while ((line = reader.readLine()) != null) {
                hasData = true;
                System.out.println("🗓️  " + line);
            }
            if (!hasData) {
                System.out.println("⚠️ No mood entries yet. Start logging today!");
            }
        } catch (IOException e) {
            System.out.println("❌ Error reading file: " + e.getMessage());
        }
    }

    static void showMoodSummary() {
        System.out.println("\n📊 Mood Summary");
        System.out.println("----------------------------------------");

        Map<String, Integer> moodCount = new LinkedHashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(": ");
                if (parts.length == 2) {
                    String mood = parts[1];
                    moodCount.put(mood, moodCount.getOrDefault(mood, 0) + 1);
                }
            }

            if (moodCount.isEmpty()) {
                System.out.println("⚠️ No data to summarize.");
            } else {
                moodCount.forEach((mood, count) -> {
                    System.out.printf("• %-15s → %d time(s)%n", mood, count);
                });
            }

        } catch (IOException e) {
            System.out.println("❌ Error reading file: " + e.getMessage());
        }
    }
}
