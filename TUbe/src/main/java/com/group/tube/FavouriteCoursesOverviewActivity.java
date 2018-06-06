package com.group.tube;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.group.tube.ArrayAdapter.CourseArrayAdapter;
import com.group.tube.List.FavouriteList;
import com.group.tube.Models.Course;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FavouriteCoursesOverviewActivity extends CoursesOverviewActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("My Courses");
        LinearLayout filterLayout = findViewById(R.id.linearLayout5);
        filterLayout.setVisibility(View.GONE);

    }

    @Override
    protected void onResume() {
        super.onResume();
        //update drawer
        navigationView.getMenu().getItem(0).setChecked(false);
        navigationView.getMenu().getItem(1).setChecked(true);
        navigationView.getMenu().getItem(2).setChecked(false);
    }

    @Override
    public void initializeListView(final ArrayList<Course> courses) {
        Iterator<Course> i = courses.iterator();
        while(i.hasNext()){
            Course course = i.next();
            boolean isFavorite = false;
            for(String courseId : FavouriteList.getInstance()) {
                if(course.getId().equals(courseId)) {
                    isFavorite = true;
                    course.setFavorite(true);
                    break;
                }
            }
            if(!isFavorite){
                i.remove();
            }
        }
        CourseArrayAdapter arrayAdapter = new CourseArrayAdapter(this, courses);
        listView.setAdapter(arrayAdapter);
    }


    @Override
    public void initializeFilterButton(){
        //Do nothing
    }

    @Override
    public void setContentViewOverride() {
        setContentView(R.layout.favourite_courses_overview);
    }

}
