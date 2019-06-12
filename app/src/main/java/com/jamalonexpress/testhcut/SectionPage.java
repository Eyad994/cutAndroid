package com.jamalonexpress.testhcut;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class SectionPage extends AppCompatActivity {

    private ArrayList<String> titles = new ArrayList<>();
    private ArrayList<String> images = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_section_page);

        titles.add("test");
        titles.add("test2");
        images.add("https://image.flaticon.com/icons/svg/1810/1810109.svg");
        images.add("drawable/laser.png");
        titles.add("test");
        titles.add("test2");
        images.add("https://cdn.jamalon.com/c/p/3070376.jpg");
        images.add("https://cdn.jamalon.com/c/p/3070376.jpg");
        titles.add("test");
        titles.add("test2");
        images.add("https://cdn.jamalon.com/c/p/3070376.jpg");
        images.add("https://cdn.jamalon.com/c/p/3070376.jpg");

        RecyclerView recyclerView = findViewById(R.id.recycleViewSections);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));
        RecycleViewAdapter adapter = new RecycleViewAdapter(getApplicationContext(), titles, images);
        recyclerView.setAdapter(adapter);

    }
}
