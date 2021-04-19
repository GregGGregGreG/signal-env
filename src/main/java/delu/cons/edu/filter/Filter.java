package delu.cons.edu.filter;

public interface Filter {
    boolean isAllowed();

    void reset();

    long periodNan();
}
