package com.group.tube.ArrayAdapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.group.tube.List.FavouriteList;
import com.group.tube.Models.Course;
import com.group.tube.R;
import com.group.tube.utils.LocalStorageUtils;

import java.util.ArrayList;
import java.util.Set;


public class CourseArrayAdapter extends ArrayAdapter<Course> {

    private Context context;
    private String fileContents = "TEST";
    private ArrayList<Course> courses;

    public CourseArrayAdapter(Context context, ArrayList<Course> list) {
        super(context, 0, list);
        this.context = context;
        this.courses = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if (listItem == null)
            listItem = LayoutInflater.from(context).inflate(R.layout.courses_overview_item, parent, false);

        final Course currentCourse = this.getItem(position);

        TextView tv_course_title = listItem.findViewById(R.id.textViewCourseOverviewItemCourseTitle);
        tv_course_title.setText(currentCourse.getCourseTitle());

        TextView tv_course_number = listItem.findViewById(R.id.textViewCourseOverviewItemCourseNumber);
        //TODO fill course number
        //tv_course_number.setText("");
        tv_course_number.setVisibility(View.GONE);

        TextView tv_semester = listItem.findViewById(R.id.textViewCourseOverviewItemSemester);
        tv_semester.setText(currentCourse.getSemesterString());

        final ToggleButton toggleButton = listItem.findViewById(R.id.toggleButton);

        toggleButton.setOnCheckedChangeListener(null);
        toggleButton.setChecked(currentCourse.isFavorite());
        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                View parentRow = (View) compoundButton.getParent();
                ListView listView = (ListView) parentRow.getParent();
                final int position = listView.getPositionForView(parentRow);

                Course course = (Course)listView.getAdapter().getItem(position);


                Set<String> favorites = FavouriteList.getInstance();
                boolean hasFavorite = favorites.contains(course.getId());
                if (isChecked && !hasFavorite) {
                    favorites.add(course.getId());
                }
                else if (!isChecked && hasFavorite) {
                    favorites.remove(course.getId());
                }
                course.setFavorite(isChecked);
                LocalStorageUtils.writeCourseFavoriteListToFile(context);
            }});

        return listItem;
    }
}