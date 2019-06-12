package com.jamalonexpress.testhcut;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class SectionPage extends AppCompatActivity {

    private ArrayList<String> titles = new ArrayList<>();
    private ArrayList<Integer> images = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_section_page);

        //icons from --> www.flaticon.com
        titles.add("test1");
        titles.add("test2");
        titles.add("test1");
        titles.add("test2");
        titles.add("test1");
        titles.add("test2");
        images.add(R.drawable.scissor);
        images.add(R.drawable.las);
        images.add(R.drawable.scissor);
        images.add(R.drawable.las);
        images.add(R.drawable.scissor);
        images.add(R.drawable.las);

        RecyclerView recyclerView = findViewById(R.id.recycleViewSections);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));
        RecycleViewAdapter adapter = new RecycleViewAdapter(getApplicationContext(), titles, images);
        recyclerView.setAdapter(adapter);

    }
}
