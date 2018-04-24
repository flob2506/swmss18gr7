package com.group.tube.ArrayAdapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.group.tube.Models.Course;
import com.group.tube.Models.Episodes;
import com.group.tube.R;
import com.group.tube.utils.Utils;

import java.util.ArrayList;


public class CourseArrayAdapter extends ArrayAdapter<Course> {

    private Context context;

    public CourseArrayAdapter(Context context, ArrayList<Course> list) {
        super(context, 0, list);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if (listItem == null)
            listItem = LayoutInflater.from(context).inflate(R.layout.courses_overview_item, parent, false);

        Course currentCourse = this.getItem(position);

        TextView tv_course_title = listItem.findViewById(R.id.textViewCourseOverviewItemCourseTitle);
        tv_course_title.setText(currentCourse.course_title);

        TextView tv_course_number = listItem.findViewById(R.id.textViewCourseOverviewItemCourseNumber);
        tv_course_number.setText(currentCourse.course_number);

        TextView tv_semester = listItem.findViewById(R.id.textViewCourseOverviewItemSemester);
        tv_semester.setText(currentCourse.getSemesterString());

        return listItem;
    }
}