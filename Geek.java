import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Geek {
    private String name;
    private String gender;
    private int age;
    private String sport;
    private String status;
    private Set<String> favoriteDesserts = new HashSet<>();
    private static final Set<String> sportGroups = new HashSet<>();
    private static final Set<String> dessertGroups = new HashSet<>();
    private static final Map<String, Set<String>> compatibilitySets = new HashMap<>();


    // Static block to initialize sport and dessert groups
    static {
        initializeSportGroups();
        initializeDessertGroups();
    }

    // Constructors
    public Geek(String name) {
        this.name = name;
        initializeCompatibilitySets();
    }

    public Geek(String name, String gender, int age, String sport, String status, Set<String> favoriteDesserts) {
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.sport = sport;
        this.status = status;
        this.favoriteDesserts = favoriteDesserts;
        initializeCompatibilitySets();
    }

    // Getter methods
    public String getName() {
        return name;
    }

    public String getGender() {
        return gender;
    }

    public int getAge() {
        return age;
    }

    public String getSport() {
        return sport;
    }

    public String getStatus() {
        return status;
    }

    public Set<String> getFavoriteDesserts() {
        return new HashSet<>(favoriteDesserts);
    }

    // Setter methods
    public void setName(String name) {
        this.name = name;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setSport(String sport) {
        this.sport = sport;
        initializeCompatibilitySets(); // Re-initialize compatibility sets when sport is set
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setFavoriteDesserts(Set<String> favoriteDesserts) {
        this.favoriteDesserts = favoriteDesserts;
    }

    // Method to add new desserts to the set of favorite desserts
    public void addFavoriteDessert(String dessert) {
        favoriteDesserts.add(dessert);
    }

    // Method to compare the similarity between the geek’s sport and another geek’s sport
    public boolean isSportSimilar(Geek otherGeek) {
        return sportGroups.contains(sport) && sportGroups.contains(otherGeek.sport);
    }

    // Method to compare the similarity between the geek’s favorite desserts and another geek’s favorite desserts
    public Set<String> findCommonFavoriteDesserts(Geek otherGeek) {
        Set<String> commonDesserts = new HashSet<>(favoriteDesserts);
        commonDesserts.retainAll(otherGeek.favoriteDesserts);
        return commonDesserts;
    }

    public boolean isCompatibleWith(Geek otherGeek) {
        return sport.equals(otherGeek.sport) && !findCommonFavoriteDesserts(otherGeek).isEmpty();
    }

    // Helper method to initialize sport groups
    private static void initializeSportGroups() {
        sportGroups.add("Team");
        sportGroups.add("Individual");
        sportGroups.add("Combat");
        sportGroups.add("Water");
        sportGroups.add("Winter");
        sportGroups.add("Extreme");
        sportGroups.add("Mind");
        sportGroups.add("Customized");
    }

    // Helper method to initialize dessert groups
    private static void initializeDessertGroups() {
        // Implement initialization of dessertGroups based on the provided groups
        // ...
    }

    // Helper method to initialize compatibility sets
    /**
     * 
     */
    private void initializeCompatibilitySets() {
        // Initialize compatibility sets based on sport and dessert groups
    
        // Example: For simplicity, let's consider two sport groups (Team and Individual)
        // and two dessert groups (Sweet and Savory).
    
        // Compatible sports for each group
        Set<String> teamSports = new HashSet<>();
        teamSports.add("Football");
        teamSports.add("Basketball");
    
        Set<String> individualSports = new HashSet<>();
        individualSports.add("Tennis");
        individualSports.add("Golf");
    
        // Compatible desserts for each group
        Set<String> sweetDesserts = new HashSet<>();
        sweetDesserts.add("Chocolate Cake");
        sweetDesserts.add("Cheesecake");
    
        Set<String> savoryDesserts = new HashSet<>();
        savoryDesserts.add("Pasta");
        savoryDesserts.add("Sushi");
    
        // Initialize compatibility sets based on the geek's sport
        if (sportGroups.contains("Team")) {
            // Geek belongs to the Team sport group
            compatibilitySets.put("Team", teamSports);
        } else if (sportGroups.contains("Individual")) {
            // Geek belongs to the Individual sport group
            compatibilitySets.put("Individual", individualSports);
        }
    
        // Initialize compatibility sets based on the geek's dessert preferences
        if (dessertGroups.contains("Sweet")) {
            // Geek prefers Sweet desserts
            compatibilitySets.put("Sweet", sweetDesserts);
        } else if (dessertGroups.contains("Savory")) {
            // Geek prefers Savory desserts
            compatibilitySets.put("Savory", savoryDesserts);
        }
    }
    
}
