package com.group.tube;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.util.Pair;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.group.tube.ArrayAdapter.CourseArrayAdapter;
import com.group.tube.Dialogs.CourseSemesterFilterDialogFragment;
import com.group.tube.Models.Course;
import com.group.tube.networking.AsyncResponse;
import com.group.tube.networking.NetworkConnector;
import com.group.tube.utils.TestDataGenerator;
import com.group.tube.utils.Utils;

import java.util.ArrayList;

public class CoursesOverviewActivity extends AppCompatActivity implements CourseSemesterFilterDialogFragment.CourseSemesterFilterDialogListener {
    Boolean courseListLoaded = false;

    public static final String EXTRA_COURSE_OBJECT = "com.group.tube.coursesOverviewActivity.EXTRA_COURSE_OBJECT";

    private boolean chosenIsWs;
    private int chosenSemesterYear;
    RelativeLayout loadingBar;
    ListView listView;
    ArrayList<Course> courses;
    ArrayList<Course> allCourses;
    private final Pair<Integer, Boolean> currentSemester = Utils.getCurrentSemester();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.courses_overview);
        setTitle("All Courses");

        listView = findViewById(R.id.listViewCourses);
        loadingBar = findViewById(R.id.loadingIconCourses);

        final Activity that = this;

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Intent intent = new Intent(that, EpisodesOverviewActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(EXTRA_COURSE_OBJECT, courses.get(position));
                intent.putExtras(bundle);

                startActivity(intent);
            }
        });

        final NetworkConnector networkConnector = new NetworkConnector();

        networkConnector.networkTask.setLoginAndPassword(NetworkConnector.USERNAME, NetworkConnector.PASSWORD);
        networkConnector.loadAllCourses(new AsyncResponse<ArrayList<Course>>() {
            @Override
            public void processFinish(ArrayList<Course> response) {
                courses = response;
                allCourses = new ArrayList<Course>(courses);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        initializeListView(courses);
                        filterCoursesBySemester(currentSemester.first, currentSemester.second);
                        loadingBar.setVisibility(View.GONE);
                    }
                });
            }

            @Override
            public void handleProcessException(Exception e) {
                //dialog.show("oops "); sleep(1000); kill();
            }
        });
        initializeFilterButton();

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
        dialog.setChosenSemesterYear(chosenSemesterYear);
        dialog.show(getFragmentManager(), "");
    }

    private void initializeListView(final ArrayList<Course> courses) {
        CourseArrayAdapter arrayAdapter = new CourseArrayAdapter(this, courses);
        listView.setAdapter(arrayAdapter);
        courseListLoaded = true;
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog, int semesterYear, boolean isWs) {
        filterCoursesBySemester(semesterYear, isWs);
    }

    private void filterCoursesBySemester(int semesterYear, boolean isWs) {
        setChosenSemester(semesterYear, isWs);
        CourseArrayAdapter courseAdapter = ((CourseArrayAdapter)listView.getAdapter());
        courseAdapter.clear();
        for(int i = 0; i < allCourses.size(); i++) {
            if (allCourses.get(i).isWs() == isWs && allCourses.get(i).getSemesterYear() == semesterYear) {
                courseAdapter.add(allCourses.get(i));
            }
        }
        courseAdapter.notifyDataSetChanged();
    }

    private void setChosenSemester(int semesterYear, boolean isWs) {
        TextView textView = findViewById(R.id.textViewChosenSemester);
        textView.setText(Utils.getChosenSemesterText(semesterYear, isWs, this));
        this.chosenSemesterYear = semesterYear;
        this.chosenIsWs = isWs;
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {

    }
}
