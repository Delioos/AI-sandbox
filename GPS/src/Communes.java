import java.net.ProtocolException;
import java.util.Arrays;
import java.util.Comparator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Communes implements java.io.Serializable {
    private Commune[] listeCommunes;

    public Communes() {

        try {
            URL url = new URL("https://geo.api.gouv.fr/communes");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            conn.disconnect();

            String response = content.toString();
            JSONArray jsonArray = new JSONArray(response);
            listeCommunes = new Commune[jsonArray.length()];

            // CHANGER L INCREMENTATION DU I POUR AVOIR TOUT LES RESULTATS
            for (int i = 0; i < jsonArray.length(); i++) {
                // Afficher une barre de progression
                if (i % 100 == 0) {

                    // Barre et pourcentage
                    System.out.print("\r" + (int) (100 * i / jsonArray.length()) + "%");
                }

                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String nom = jsonObject.getString("nom");
                try {
                    String code = jsonObject.getString("code");
                    String codeDepartement = jsonObject.getString("codeDepartement");
                    int codeRegion = jsonObject.getInt("codeRegion");
                    int population = jsonObject.getInt("population");
                    String[] codesPostaux = jsonObject.getJSONArray("codesPostaux").toList().stream().map(x -> x.toString()).toArray(String[]::new);

                /*
                // mock to check if the code works
                nom = "Nancy";

                 */
                    // Récupérer les coordonnées GPS à l'aide d'une seconde API
                    String urlName = getUrlName(nom);


                    String gpsUrl = "https://api-adresse.data.gouv.fr/search/?q=" + urlName + "&postcode=" + codesPostaux[0];

                    JSONObject gpsJsonObject = getJsonObject(gpsUrl);
                    JSONArray featuresArray = gpsJsonObject.getJSONArray("features");
                    if (featuresArray.length() == 0) {
                        System.out.println("No features found for " + nom);
                        continue;
                    }

                    JSONObject firstFeature = featuresArray.getJSONObject(0);
                    JSONObject geometry = firstFeature.getJSONObject("geometry");
                    JSONArray coordinates = geometry.getJSONArray("coordinates");
                    double longitude = coordinates.getDouble(0);
                    double latitude = coordinates.getDouble(1);

                    //System.out.println(nom + " - coordinates: [" + latitude + ", " + longitude + "]");

                    // Construire l'objet commune
                    listeCommunes[i] = new Commune(nom, code, codeDepartement, 0, 0, codeRegion, population, codesPostaux, latitude, longitude);
                } catch (IOException e) {
                    System.out.println("Error 504 for " + nom + " : " + e.getMessage());
                    continue;
                } catch (JSONException e) {
                    System.out.println("Error parsing JSON for " + nom + " : " + e.getMessage());
                    continue;
                }

            }
        } catch (MalformedURLException | ProtocolException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static String getUrlName(String nom) {
        /*nom = nom.toLowerCase();
        String[] fuckTedXBT = nom.split("");
        String urlName = "";
        for (String letter : fuckTedXBT) {
            switch (letter) {
                case "'":
                    urlName += "%27";
                    break;
                case " ":
                    urlName += "%20";
                    break;
                case "-":
                    urlName += "%20";
                    break;
                case "é":
                    urlName += "%C3%A9";
                    break;
                case "è":
                    urlName += "%C3%A8";
                    break;
                case "ê":
                    urlName += "%C3%AA";
                    break;
                case "à":
                    urlName += "%C3%A0";
                    break;
                case "â":
                    urlName += "%C3%A2";
                    break;
                case "ç":
                    urlName += "%C3%A7";
                    break;
                case "ù":
                    urlName += "%C3%B9";
                    break;
                case "û":
                    urlName += "%C3%BB";
                    break;
                case "î":
                    urlName += "%C3%AE";
                    break;
                case "ô":
                    urlName += "%C3%B4";
                    break;
                default:
                    urlName += letter;
            }
        }
        return urlName;

         */
        try {
            return URLEncoder.encode(nom, StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    private static JSONObject getJsonObject(String gpsUrl) throws IOException {
        URL gpsUrlObj = new URL(gpsUrl);
        HttpURLConnection gpsConn = (HttpURLConnection) gpsUrlObj.openConnection();
        gpsConn.setRequestMethod("GET");
        gpsConn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.3");
        // si on obtient une 504, on skippe la commune
        if (gpsConn.getResponseCode() == 504) {
            throw new IOException("504");
        }
        BufferedReader gpsIn = new BufferedReader(new InputStreamReader(gpsConn.getInputStream()));
        String gpsInputLine;
        StringBuffer gpsContent = new StringBuffer();
        while ((gpsInputLine = gpsIn.readLine()) != null) {
            gpsContent.append(gpsInputLine);
        }
        gpsIn.close();
        gpsConn.disconnect();

        JSONObject gpsJsonObject = new JSONObject(gpsContent.toString());
        return gpsJsonObject;
    }

    public Commune[] getListeCommunes() {
        return listeCommunes;
    }

    /**
     * méthode qui retourne une liste avec comme premier élément les 50 communes les plus peuplées, puis les 50 suivantes, et toutes les autres en dernier objet
     */

    public Commune[] getListeCommunesParPopulation() {
        // Trier la liste de communes par population
        Arrays.sort(listeCommunes, Comparator.comparingInt(Commune::getPopulation));

        // Créer une liste de communes avec les 50 premières communes les plus peuplées
        Commune[] top50 = Arrays.copyOfRange(listeCommunes, 0, 50);

        // Créer une liste de communes avec les 50 communes suivantes les plus peuplées
        Commune[] next50 = Arrays.copyOfRange(listeCommunes, 50, 100);

        // Créer une liste de communes avec les autres communes
        Commune[] autres = Arrays.copyOfRange(listeCommunes, 100, listeCommunes.length);

        // Renvoyer une liste rassemblant les 3 listes précédentes
        return Arrays.copyOf(top50, top50.length + next50.length + autres.length);
    }


}

