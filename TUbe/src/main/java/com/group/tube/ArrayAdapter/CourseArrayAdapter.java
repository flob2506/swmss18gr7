package com.group.tube.ArrayAdapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.group.tube.Models.Course;
import com.group.tube.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.util.ArrayList;


public class CourseArrayAdapter extends ArrayAdapter<Course> {

    private Context context;
    String filename = "myfile.txt";
    String fileContents = "TEST";

    FileOutputStream Outstream;
    FileInputStream Inpstream;


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
        tv_course_title.setText(currentCourse.getCourseTitle());

        TextView tv_course_number = listItem.findViewById(R.id.textViewCourseOverviewItemCourseNumber);
        //TODO fill course number
        tv_course_number.setText("");

        TextView tv_semester = listItem.findViewById(R.id.textViewCourseOverviewItemSemester);
        tv_semester.setText(currentCourse.getSemesterString());

        final ToggleButton toggleButton = listItem.findViewById(R.id.toggleButton);




        try{

            Outstream = context.openFileOutput(filename, context.MODE_PRIVATE);
            Outstream.write(fileContents.getBytes());
            Outstream.close();

        }
        catch(Exception e)  {
            e.printStackTrace();
        }


        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if(b)
                {
                    try {


                }
                catch(Exception e)  {
                    e.printStackTrace();
                }

                    System.out.println("checked TRUE");

                }
                else
                               System.out.println("checked FALSE");
            }
        });

        return listItem;
    }
}