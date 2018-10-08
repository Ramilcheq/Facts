import java.util.Objects;
import java.util.Set;

public class Fact {
    private String name;

    Fact(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Fact fact = (Fact) o;
        return Objects.equals(name, fact.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    boolean isTrueFact(Set<Fact> trueFacts) {
        return trueFacts.contains(this);
    }

    @Override
    public String toString() {
        return name;
    }
}
