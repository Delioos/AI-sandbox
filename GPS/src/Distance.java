public class Distance {
    public static double distance(Commune a, Commune b) {
        double lat1 = a.getLatitude();
        double lon1 = a.getLongitude();
        double lat2 = b.getLatitude();
        double lon2 = b.getLongitude();
        double R = 6371e3; // metres
        double φ1 = Math.toRadians(lat1);
        double φ2 = Math.toRadians(lat2);
        double Δφ = Math.toRadians(lat2-lat1);
        double Δλ = Math.toRadians(lon2-lon1);
        double a1 = Math.sin(Δφ/2) * Math.sin(Δφ/2) +
                Math.cos(φ1) * Math.cos(φ2) *
                        Math.sin(Δλ/2) * Math.sin(Δλ/2);
        double c = 2 * Math.atan2(Math.sqrt(a1), Math.sqrt(1-a1));
        double d = R * c;
        return d;
    }

    public double duree(Commune a, Commune b, double vitesse)  {
        double distance = distance(a, b);
        String statutA = a.getStatut();
        String statutB = b.getStatut();

        double Δt= distance / vitesse;
        return Δt;
    }
}
