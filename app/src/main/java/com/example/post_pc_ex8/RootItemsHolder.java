package com.example.post_pc_ex8;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

public interface RootItemsHolder extends Serializable {

    /** Get a copy of the current items list */
    List<RootItem> getCurrentItems();

    /**
     * Creates a new TodoItem and adds it to the list, with the @param description and status=IN-PROGRESS
     * Subsequent calls to [getCurrentItems()] should have this new TodoItem in the list
     */
    int addNewInProgressItem(RootItem rootItem);

    /** mark the @param item as DONE */
    int markItemDone(UUID id);

    /** delete the @param item */
    void deleteItem(RootItem item);

    int itemIndex(RootItem item);

    int itemPosByID(UUID id);

}
