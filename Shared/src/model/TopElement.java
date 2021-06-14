package model;

public class TopElement implements Comparable<TopElement> {
    private String name;
    private int reproduccions;

    public TopElement(String name, int reproduccions) {
        this.name = name;
        this.reproduccions = reproduccions;
    }

    @Override
    public int compareTo(TopElement o) {
        if (o == null) {
            return -1;
        }
        int c = Integer.compare(reproduccions, o.reproduccions);
        if (c != 0) {
            return c;
        }
        return name.compareTo(o.name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getReproduccions() {
        return reproduccions;
    }

    public void setReproduccions(int reproduccions) {
        this.reproduccions = reproduccions;
    }
}
