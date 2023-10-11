package com.chillarcards.campuswallet.NewPreOrder;

public class DummyCategory {
    private String Id,Name;

    public DummyCategory(String id, String name) {
        Id = id;
        Name = name;
    }

    public DummyCategory() {
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
