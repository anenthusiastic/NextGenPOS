package com.company.customTypes;

import java.io.Serializable;

public class ItemID implements Serializable {
    private final String id;

    public ItemID(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return id;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof ItemID)) {
            return false;
        }
        return id.equals(((ItemID) obj).getId());
    }

    @Override
    public int hashCode() {
        //can be replaced with more complex hashing operation
        return Integer.parseInt(id);
    }
}
