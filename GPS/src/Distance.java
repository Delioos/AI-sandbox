import java.lang.reflect.Array;
import java.util.ArrayList;

import static java.lang.Math.min;

public class Distance {
    private Commune[] top50;
    private Commune[] top100;

    public Distance(Commune[] top50, Commune[] top100) {
        this.top50 = top50;
        this.top100 = top100;
    }

    public static double kmBetween(Commune a, Commune b) {
        double lat1 = a.getLatitude();
        double lon1 = a.getLongitude();
        double lat2 = b.getLatitude();
        double lon2 = b.getLongitude();
        double R = 6371e3; // metres
        double φ1 = Math.toRadians(lat1);
        double φ2 = Math.toRadians(lat2);
        double Δφ = Math.toRadians(lat2 - lat1);
        double Δλ = Math.toRadians(lon2 - lon1);
        double a1 = Math.sin(Δφ / 2) * Math.sin(Δφ / 2) +
                Math.cos(φ1) * Math.cos(φ2) *
                        Math.sin(Δλ / 2) * Math.sin(Δλ / 2);
        double c = 2 * Math.atan2(Math.sqrt(a1), Math.sqrt(1 - a1));
        double d = R * c / 1000;
        System.out.println("Distance between " + a.getNom() + " and " + b.getNom() + " : " + d);
        return d;
    }

    public double timeBetween(Commune a, Commune b) {
        // On calcule la vitesse entre les deux communes en regardant
        // le type de route
        int speed = roadSpeed(a, b);

        // On calcule la distance entre les deux communes
        double distance = kmBetween(a, b);

        // On calcule le temps de trajet
        double time = distance / speed;

        System.out.println("Time between " + a.getNom() + " and " + b.getNom() + " : " + time);
        return time;

    }

    private int roadSpeed(Commune a, Commune b) {
        int departementale = 70;
        int voieRapide = 90;
        int autoroute = 130;
        int speedA = departementale, speedB = departementale;
        int speed = 0;
        for (Commune commune : top50) {
            if (a == commune)
                speedA = autoroute;
            if (b == commune)
                speedB = autoroute;
        }
        for (Commune commune : top100) {
            if (a == commune)
                speedA = voieRapide;
            if (b == commune)
                speedB = voieRapide;
        }

        if (speedA == speedB)
            speed = speedA;
        else
            speed = min(speedA, speedB);

        System.out.println("Speed between " + a.getNom() + " and " + b.getNom() + " : " + speed);
        return speed;
    }

    /*
    public double duree(Commune a, Commune b, double vitesse)  {
        double distance = distance(a, b);
        String statutA = a.getStatut();
        String statutB = b.getStatut();

        double Δt= distance / vitesse;
        return Δt;
    }

     */
}
