package com.example.post_pc_ex8;

import java.io.Serializable;
import java.util.List;

public interface RootItemsHolder extends Serializable {

    /** Get a copy of the current items list */
    List<RootItem> getCurrentItems();

    /**
     * Creates a new TodoItem and adds it to the list, with the @param description and status=IN-PROGRESS
     * Subsequent calls to [getCurrentItems()] should have this new TodoItem in the list
     */
    void addNewInProgressItem(String description);

    /** mark the @param item as DONE */
    void markItemDone(RootItem item);

    /** mark the @param item as IN-PROGRESS */
    void markItemInProgress(RootItem item);

    /** delete the @param item */
    void deleteItem(RootItem item);

    int itemIndex(RootItem item);

}
