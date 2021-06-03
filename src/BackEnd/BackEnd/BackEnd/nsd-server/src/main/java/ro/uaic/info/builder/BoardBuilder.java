package ro.uaic.info.builder;

import ro.uaic.info.entity.Board;

public class BoardBuilder extends Board {
    public BoardBuilder id(Integer ID) {
        setID(ID);
        return this;
    }
    public BoardBuilder name(String name) {
        setName(name);
        return this;
    }
    public BoardBuilder description(String description) {
        setDescription(description);
        return this;
    }
    public BoardBuilder owner(int owner) {
        setOwner(owner);
        return this;
    }
}
