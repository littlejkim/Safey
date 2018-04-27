package com.example.android.safey;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

class ParseData extends AsyncTask<Void, Integer, Integer> {

    Context context;
    String s;
    RecyclerView recyclerView;
    Adapter adapter;
    ArrayList<Report> reportArrayList = new ArrayList<Report>();

    public ParseData(Context context, String s, RecyclerView recyclerView) {
        this.context = context;
        this.s = s;
        this.recyclerView = recyclerView;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Integer doInBackground(Void... voids) {
        int x = parseData();
        return x;
    }
    // set adapter on recyclerview
    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);
        if(integer==1)
        {
            adapter = new Adapter(context, reportArrayList);
            recyclerView.setAdapter(adapter);
        } else {
            Toast.makeText(context,"Unable to parse", Toast.LENGTH_SHORT).show();
        }
    }

    // store report information in report arraylist (parse JsonObject into report object)
    private int parseData() {
        try
        {
            JSONArray jsonArray = new JSONArray(s);
            JSONObject jsonObject = null;
            reportArrayList.clear();

            for(int i = 0; i< jsonArray.length(); i++)
            {
                jsonObject = jsonArray.getJSONObject(i);
                Report report = new Report(jsonObject.getString("comment"), jsonObject.getString("longitude"), jsonObject.getString("latitude"), jsonObject.getString("date"));
                reportArrayList.add(report);
            }

            return 1;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
