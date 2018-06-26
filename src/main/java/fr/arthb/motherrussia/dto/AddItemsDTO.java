package fr.arthb.motherrussia.dto;


import javax.validation.constraints.NotNull;
import java.util.Set;

public class AddItemsDTO {

    @NotNull
    private int id;

    @NotNull
    private Set<Integer> ids;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Set<Integer> getIds() {
        return ids;
    }

    public void setIds(Set<Integer> ids) {
        this.ids = ids;
    }
}
