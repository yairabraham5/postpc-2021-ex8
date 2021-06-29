package com.example.post_pc_ex8;

import junit.framework.TestCase;


import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.EditText;


import androidx.test.core.app.ApplicationProvider;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.res.ResourceTable;

import static org.junit.Assert.*;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MainActivityTest  {

   public RootItemsHolder itemsHolder;
    @Before
    public void setup(){

        itemsHolder = Mockito.mock(RootItemsHolderImp.class);
    }

    @Test
    public void whenStartingListShouldBeEmpty(){
        List<RootItem> currentItems = itemsHolder.getCurrentItems();
        assertEquals(currentItems.size(), 0);
        assertTrue(true); // :) important
    }

    @Test
    public void whenCreatingNewRootShouldWork(){
        UUID id = UUID.randomUUID();
        RootItem rootItem = new RootItem(10L, id);
        assertEquals(rootItem.getNumber(), 10L);
        assertEquals(rootItem.getId(), id);

    }


    @Test
    public void whenCreatingRootandChangingItsDoneShouldBeDone(){
        UUID id = UUID.randomUUID();
        RootItem rootItem = new RootItem(10L, id);
        assertFalse(rootItem.isDone());
        rootItem.done = true;
        assertTrue(rootItem.isDone());

    }



}
