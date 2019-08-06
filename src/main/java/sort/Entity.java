package sort;

public class Entity {

    private String name;
    private int sort;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public Entity(String name, int sort) {
        this.name = name;
        this.sort = sort;
    }

    @Override
    public String toString() {
        return "Entity{" +
                "name='" + name + '\'' +
                ", sort=" + sort +
                '}';
    }
}
