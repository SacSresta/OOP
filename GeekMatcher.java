import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.Collection;


public class GeekMatcher {
    private static ArrayList<Geek> geeksList = new ArrayList<>();

    public static void main(String[] args) {
        loadGeeksFile("allGeeks.txt");

        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter your name: ");
        String name = scanner.nextLine();

        System.out.print("Enter your sport: ");
        String sport = scanner.nextLine();

        System.out.print("Enter your favourite desserts (separated by commas): ");
        String dessertsInput = scanner.nextLine();
        Set<String> favoriteDesserts = parseDesserts(dessertsInput);

        Geek individualGeek = new Geek(name, "", 0, sport, "", favoriteDesserts);

        // Find compatible geeks based on sport
        Collection<Geek> sportCompatibleGeeks = findSportCompatibleGeeks(individualGeek);
        System.out.println("Geeks with sport compatibility with " + name + ":");
        printGeeksInfo(sportCompatibleGeeks);

        // Find compatible geeks based on favorite desserts
        CompatibilityResult dessertsCompatibilityResult = findDessertsCompatibleGeeks(individualGeek);
        System.out.println("Geeks with favourite dessert compatibility with " + name + ":");
        printGeeksInfo(dessertsCompatibilityResult.getCompatibleGeeks());
        System.out.println("Common Desserts: " + dessertsCompatibilityResult.getCommonDesserts());

        // Find the subset of geeks with desserts in common AND compatible sport
        Collection<Geek> commonSportAndDessertsGeeks = findCommonSportAndDessertsGeeks(
        sportCompatibleGeeks, dessertsCompatibilityResult.getCompatibleGeeks());
    System.out.println("Geeks with desserts in common AND compatible sport:");
    printGeeksInfo(commonSportAndDessertsGeeks);
    System.out.println("===============================");
    }

    private static void loadGeeksFile(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            // Skip the header line
            br.readLine();
    
            String line;
            while ((line = br.readLine()) != null) {
                Geek geek = createGeekFromLine(line);
                geeksList.add(geek);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Geek createGeekFromLine(String line) {
        Geek geek = null;
    
        String[] geekData = line.split(",");
        if (geekData.length >= 6) {
            String name = geekData[0].trim();
            String gender = geekData[1].trim();
    
            try {
                int age = Integer.parseInt(geekData[2].trim());
                String sport = geekData[3].trim();
                String status = geekData[4].trim();
    
                String dessertsInput = geekData[5].trim();
                Set<String> favoriteDesserts = parseDesserts(dessertsInput);
    
                geek = new Geek(name, gender, age, sport, status, favoriteDesserts);
            } catch (NumberFormatException e) {
                System.err.println("Error parsing age. Please enter a valid number for age.");
            }
        }
    
        return geek;
    }
    // Method to find geeks with sport compatibility with an individual geek
    private static Collection<Geek> findSportCompatibleGeeks(Geek individualGeek) {
        List<Geek> sportCompatibleGeeks = new ArrayList<>();
        for (Geek geek : geeksList) {
            if (individualGeek.isSportSimilar(geek)) {
                sportCompatibleGeeks.add(geek);
            }
        }
        return sportCompatibleGeeks;
    }

    // Method to find geeks with favorite desserts compatibility with an individual geek
    private static CompatibilityResult findDessertsCompatibleGeeks(Geek individualGeek) {
        List<Geek> compatibleGeeks = new ArrayList<>();
        Set<String> commonDesserts = new HashSet<>();

        for (Geek geek : geeksList) {
            Set<String> commonDessertSet = individualGeek.findCommonFavoriteDesserts(geek);
            if (!commonDessertSet.isEmpty()) {
                compatibleGeeks.add(geek);
                commonDesserts.addAll(commonDessertSet);
            }
        }

        return new CompatibilityResult(compatibleGeeks, commonDesserts);
    }

    private static Collection<Geek> findCommonSportAndDessertsGeeks(
    Collection<Geek> sportCompatibleGeeks, Collection<Geek> dessertsCompatibleGeeks) {
    List<Geek> commonGeeks = new ArrayList<>();
    for (Geek sportGeek : sportCompatibleGeeks) {
        for (Geek dessertGeek : dessertsCompatibleGeeks) {
            if (sportGeek.getSport().equals(dessertGeek.getSport())) {
                commonGeeks.add(sportGeek);
                break;
            }
        }
    }
    return commonGeeks;
}


    // Helper method to parse the string of favorite desserts into a Set
    private static Set<String> parseDesserts(String desserts) {
        Set<String> dessertSet = new HashSet<>();
        String[] dessertArray = desserts.split(", ");
        for (String dessert : dessertArray) {
            dessertSet.add(dessert);
        }
        return dessertSet;
    }

    // Helper method to print information of geeks
    private static void printGeeksInfo(Collection<Geek> geeks) {
        for (Geek geek : geeks) {
            System.out.print("Name: " + geek.getName() + ", Sport: " + geek.getSport());

            // Check if common desserts are available
            Set<String> commonDesserts = geek.findCommonFavoriteDesserts(geek);
            if (!commonDesserts.isEmpty()) {
                System.out.print(", Common Desserts: " + commonDesserts);
            }

            System.out.println();
        }
    }

    // Helper class to store the result of desserts compatibility
    private static class CompatibilityResult {
        private final List<Geek> compatibleGeeks;
        private final Set<String> commonDesserts;

        public CompatibilityResult(List<Geek> compatibleGeeks, Set<String> commonDesserts) {
            this.compatibleGeeks = compatibleGeeks;
            this.commonDesserts = commonDesserts;
        }

        public List<Geek> getCompatibleGeeks() {
            return compatibleGeeks;
        }

        public Set<String> getCommonDesserts() {
            return commonDesserts;
        }
    }
}
