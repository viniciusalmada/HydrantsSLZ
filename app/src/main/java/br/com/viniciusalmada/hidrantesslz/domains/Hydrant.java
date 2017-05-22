package br.com.viniciusalmada.hidrantesslz.domains;

/**
 * Created by vinicius-almada on 17/05/17.
 */

public class Hydrant {
    private double lat;
    private double lgn;
    private String name;

    public Hydrant() {
    }

    public Hydrant(double lat, double lgn, String name) {
        this.lat = lat;
        this.lgn = lgn;
        this.name = name;
    }

    public double getLat() {
        return lat;
    }

    public double getLgn() {
        return lgn;
    }

    public String getName() {
        return name;
    }
}
