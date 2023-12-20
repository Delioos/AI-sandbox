import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

public class Graph {
    Distance distance;
    ArrayList<Commune> communes;
    ArrayList<Commune> availableCommunes;


    public Graph(Distance distance, ArrayList<Commune> communes) {
        this.distance = distance;
        this.communes = communes;
        this.availableCommunes = new ArrayList<Commune>();
        for (int i = 0; i < 100; i++) {
            availableCommunes.add(communes.get(i));
        }
    }

    /**
     * methode qui retourne le chemin le plus rapide entre deux communes
     *
     * on prends la distance a vol d oiseau comme heuristique pour l algo A*, le cout est le temps de trajet, on ne circule que sur des villes du top 100
     * @param a : commune de départ
     * @param b : commune d'arrivée
     * @return path : duree + chemin ville par ville le plus rapide entre a et b
     */
    public String fastestPath(Commune a, Commune b) {
        // Creation of l heuristique
        Commune currentNode = a;

        StringBuilder path = new StringBuilder();
        double totalDistance = 0;
        double totalDuration = 0;

        PriorityQueue<Commune> frontier = new PriorityQueue<>(Comparator.comparingDouble(commune -> distance.timeBetween(a, commune) + distance.kmBetween(commune, b)));
        ArrayList<Commune> explored = new ArrayList<>();

        frontier.add(a);

        while (!frontier.isEmpty() && !(currentNode == b)) {
            currentNode = frontier.poll();

            totalDistance += distance.kmBetween(a, currentNode);
            totalDuration += distance.timeBetween(a, currentNode);
            path.append(currentNode.getNom() + " -> ");
            explored.add(currentNode);

            // on ajoute les voisins du noeud courant dans la frontiere
            for (Commune commune : availableCommunes) {
                if (distance.timeBetween(currentNode, commune) != 0 && !explored.contains(commune) && !frontier.contains(commune)) {
                    frontier.add(commune);
                }
            }
        }
        // retire le dernier " -> "
        path.delete(path.length() - 4, path.length());
        path.append("\nDistance : " + totalDistance + " km");
        path.append("\nDuration : " + totalDuration + " h");
        return path.toString();
    }


    /**
     * methode qui retourne le chemin le plus court entre deux communes
     *
     * etant donne que le sujet ne ous precise pas de routes entre les villes et seulemetn qu on peux passer par les 100 plus grandes villes.
     * si a ou b fait parti du top 100 on renvoie simplement la distance entre les deux villes
     * sinon on prends les villes les plus proche de a et b et on calcule le chemin le plus court entre ces deux villes
     *
     * @param a : commune de départ
     * @param b : commune d'arrivée
     * @return path : distance + chemin ville par ville le plus court entre a et b
     */
    public String shortestPath(Commune a, Commune b) {
        // si a ou b fait parti du top 100 on renvoie simplement la distance entre les deux villes
        if (availableCommunes.contains(a) || availableCommunes.contains(b)) {
            return "Distance : " + distance.kmBetween(a, b) + "km - Duration : " + distance.timeBetween(a, b) + "h";
        }

        Commune closestA = getClosest(a);

        Commune closestB = getClosest(b);

        Commune closest;
        if (distance.kmBetween(closestA, a) < distance.kmBetween(closestB, b)) {
            closest = closestA;
        } else {
            closest = closestB;
        }

        double totalDistance = distance.kmBetween(a, closest) + distance.kmBetween(b, closest);
        double totalDuration = distance.timeBetween(a, closest) + distance.timeBetween(b, closest);
        return "Distance : " + totalDistance + "km - Duration : " + totalDuration + "h";
    }

    private Commune getClosest(Commune a) {
        Commune closestA = availableCommunes.get(0);
        double distanceA = distance.kmBetween(a, closestA);
        // plus proche de a
        for (Commune commune : availableCommunes) {
            if (distance.kmBetween(a, commune) < distanceA) {
                closestA = commune;
                distanceA = distance.kmBetween(a, commune);
            }
        }
        return closestA;
    }
}
