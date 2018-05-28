package com.group.tube;

import android.os.Bundle;

import com.group.tube.ArrayAdapter.CourseArrayAdapter;
import com.group.tube.List.FavouriteList;
import com.group.tube.Models.Course;

import java.util.ArrayList;
import java.util.Iterator;

public class FavouriteCoursesOverviewActivity extends CoursesOverviewActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("My Courses");
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

}
