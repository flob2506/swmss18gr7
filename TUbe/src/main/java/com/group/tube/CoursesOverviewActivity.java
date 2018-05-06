package com.group.tube;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.group.tube.ArrayAdapter.CourseArrayAdapter;
import com.group.tube.Dialogs.CourseSemesterFilterDialogFragment;
import com.group.tube.Models.Course;
import com.group.tube.networking.AsyncResponse;
import com.group.tube.networking.NetworkConnector;
import com.group.tube.utils.Utils;

import java.util.ArrayList;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

public class CoursesOverviewActivity extends AppCompatActivity implements CourseSemesterFilterDialogFragment.CourseSemesterFilterDialogListener {
    public static final String EXTRA_MESSAGE = "com.group.tube.coursesOverviewActivity.MESSAGE";

    private boolean chosenIsWs;
    private int chosenSemesterYear;
    ListView listView;
    ArrayList<Course> courses;
    ArrayList<Course> allCourses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.courses_overview);
        setTitle("All Courses");

        listView  = findViewById(R.id.listViewCourses);

        final Activity that = this;

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Intent intent = new Intent(that, EpisodesOverviewActivity.class);
                intent.putExtra(EXTRA_MESSAGE, courses.get(position).getId());
                startActivity(intent);
            }
        });

        final NetworkConnector networkConnector = new NetworkConnector();
        networkConnector.loadCourses(new AsyncResponse<ArrayList<Course>>() {
            @Override
            public void processFinish(ArrayList<Course> response) {
                courses = response;
                allCourses = new ArrayList<Course>(courses);
                initializeListView(courses);
            }
        });
        initializeFilterButton();

        Pair<Integer, Boolean> currentSemester = Utils.getCurrentSemester();
        setChosenSemester(currentSemester.first, currentSemester.second);
    }

    private void initializeFilterButton()
    {
        ImageView filterButton = findViewById(R.id.imageViewFilterCourseList);
        filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });
    }

    private void showDialog()
    {
        CourseSemesterFilterDialogFragment dialog = new CourseSemesterFilterDialogFragment();
        dialog.setChosenIsWs(chosenIsWs);
        System.out.println("chosen sem year" + chosenSemesterYear);
        dialog.setChosenSemesterYear(chosenSemesterYear);
        dialog.show(getFragmentManager(), "");
    }

    private void initializeListView(final ArrayList<Course> courses) {
        CourseArrayAdapter arrayAdapter = new CourseArrayAdapter(this, courses);
        listView.setAdapter(arrayAdapter);
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog, int semesterYear, boolean isWs) {
        setChosenSemester(semesterYear, isWs);
        semesterYear -= 2000;
        CourseArrayAdapter courseAdapter = ((CourseArrayAdapter)listView.getAdapter());
        courseAdapter.clear();
        for(int i = 0; i < allCourses.size(); i++) {
            if (allCourses.get(i).isWs() == isWs && allCourses.get(i).getSemesterYear() == semesterYear) {
                courseAdapter.add(allCourses.get(i));
                System.out.println("filtered course: " + allCourses.get(i).getCourseTitle() + ", " + semesterYear + ", " + isWs);
            }
        }
        courseAdapter.notifyDataSetChanged();
    }

    private void setChosenSemester(int semesterYear, boolean isWs) {
        int stringResource = isWs ? R.string.ws : R.string.ss;
        String semesterType = getResources().getString(stringResource);
        TextView textView = findViewById(R.id.textViewChosenSemester);
        textView.setText(String.format("This semester (%02d%s)", semesterYear % 100, semesterType));
        this.chosenSemesterYear = semesterYear;
        this.chosenIsWs = isWs;
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {

    }
}
