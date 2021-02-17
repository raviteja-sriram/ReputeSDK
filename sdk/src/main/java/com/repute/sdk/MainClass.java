package com.repute.sdk;

import android.app.ActivityManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainClass extends AppCompatActivity {
    TextView receivedMsg;
    Button button;
    TextView toSend;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        setTitle("AA SDK Activity");

        receivedMsg = (TextView)findViewById(R.id.textView);
        button = (Button)findViewById(R.id.button);
        toSend = (TextView)findViewById(R.id.editText2);

        Intent intent = getIntent();

        String str = intent.getStringExtra("str1");

        //receivedMsg.setText(printAllRunningActivities(getAllRunningActivities(getApplicationContext())));
        receivedMsg.setText(printAllRunningActivities(getAllRunningActivities(getApplicationContext())) + "\n" + getCallingAndCurrentActivity());

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.putExtra("str2", toSend.getText().toString());
                setResult(RESULT_OK, i);
                finish();
            }
        });
    }

    //Gets list of activities with process name - List<activityName, processName>
    public ArrayList<ActivityInformation> getAllRunningActivities(Context context) {
        ArrayList<ActivityInformation> activityInfos = new ArrayList<>();
        try {
            PackageInfo pi = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), PackageManager.GET_ACTIVITIES);
            ArrayList<ActivityInfo> activities = new ArrayList<>(Arrays.asList(pi.activities));
            for(ActivityInfo a : activities){
                ActivityInformation ai = new ActivityInformation(a.name, a.processName);
                activityInfos.add(ai);
            }
            return activityInfos;

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    //Returns list of activity name and process name as string
    public String printAllRunningActivities(ArrayList<ActivityInformation> ai){
        String result = "";
        result += "Activity Name - Process Id \n\n";
        for(ActivityInformation a : ai){
            result += a.activityName + " - " + getProcessIdBasedOnName(a.processName) + "\n";
        }
        return result;
    }

    //Gets calling and current activity
    public String getCallingAndCurrentActivity(){
        return String.format("Calling Activity \n \t %s \nCurrent Activity \n \t %s", getCallingActivity().getClassName(), getClass().getName());
    }

    public String getProcessIdBasedOnName(String processName){
        ActivityManager am = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> processes =  am.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo processInfo : processes) {
            if(processInfo.processName.equalsIgnoreCase(processName))
                return Integer.toString(processInfo.pid);
        }
        return "0";
    }

    private String getProcessInfo(){
        List<String> activePackages = new ArrayList<String>();
        int countOfProcesses = 0;
        //boolean isSdkRunningIndifferentProcess = true;
        ActivityManager am = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> l =  am.getRunningAppProcesses();
        if(l.size() < 2) return "In Same Process";
        String result = "";
        for (ActivityManager.RunningAppProcessInfo processInfo : l) {
//            if (processInfo.) {
//                activePackages.addAll(Arrays.asList(processInfo.pkgList));
//            }
            //result += processInfo.pid + " " + processInfo.processName + " " + android.os.Process.myPid() + "\n";
            if(android.os.Process.myPid() == processInfo.pid)
                countOfProcesses++;
        }
        //result = String.join(",", activePackages)
        result = //getCallingPackage() + " " + getCallingActivity().getPackageName() +
                "\n" + getCallingActivity().getClassName() +
                        "\n" + getPackageName() + "\n" + getClass().getName();
        return result;
        //return (countOfProcesses > 1) ? "In Same Process" : "Running in different process";
    }
}
