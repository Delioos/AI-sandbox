public class Commune implements java.io.Serializable{
    private String nom;
    private String code;
    private String codeDepartement;
    private int siren;
    private int codeEpci;
    private int codeRegion;
    private int population;
    private String[] codePostaux;

    private double latitude;
    private double longitude;

    public Commune(String nom, String code, String codeDepartement, int siren, int codeEpci, int codeRegion, int population, String[] codePostaux, double latitude, double longitude) {
        this.nom = nom;
        this.code = code;
        this.codeDepartement = codeDepartement;
        this.siren = siren;
        this.codeEpci = codeEpci;
        this.codeRegion = codeRegion;
        this.population = population;
        this.codePostaux = codePostaux;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getNom() {
        return nom;
    }

    public String getCode() {
        return code;
    }

    public String getCodeDepartement() {
        return codeDepartement;
    }

    public int getSiren() {
        return siren;
    }

    public int getCodeEpci() {
        return codeEpci;
    }

    public int getCodeRegion() {
        return codeRegion;
    }

    public int getPopulation() {
        return population;
    }

    public String[] getCodePostaux() {
        return codePostaux;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }


    public String toString() {
        return "Commune{" +
                "nom='" + nom + '\'' +
                ", codeDepartement=" + codeDepartement +
                ", population=" + population +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                '}';
    }
}
