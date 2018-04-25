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



    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);
        if(integer==1)
        {
            adapter=new Adapter(context, reportArrayList);
            recyclerView.setAdapter(adapter);
        }else {
            Toast.makeText(context,"Unable to parse", Toast.LENGTH_SHORT).show();
        }
    }

    private int parseData() {
        try
        {
            JSONArray ja=new JSONArray(s);
            JSONObject jo=null;

            reportArrayList.clear();

            for(int i=0;i<ja.length();i++)
            {
                jo=ja.getJSONObject(i);
                Report report = new Report(jo.getString("comment"), jo.getString("longitude"), jo.getString("latitude"), jo.getString("date"));
                reportArrayList.add(report);
            }

            return 1;

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return 0;
    }
}
