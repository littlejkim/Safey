package com.example.android.safey;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class DownloadReportData extends AsyncTask<Void,Integer,String> {
    Context context;
    String serverAddress;
    RecyclerView recyclerView;

    public DownloadReportData(Context c, String s, RecyclerView r) {
        this.context = c;
        this.serverAddress = s;
        this.recyclerView = r;
    }

    private String getData() {
        InputStream inputStream = null;
        String output;

        try
        {
            URL url = new URL(serverAddress);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            inputStream = new BufferedInputStream(con.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuffer stringBuffer = new StringBuffer();

            if(bufferedReader != null)
            {
                while ((output = bufferedReader.readLine()) != null)
                {
                    stringBuffer.append(output + "n");
                }
            }else
            {
                return null;
            }

            return stringBuffer.toString();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(inputStream != null)
            {
                try { inputStream.close(); }
                catch (IOException e) { e.printStackTrace(); }
            }
        }
        return null;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(Void... voids) {
        String downloaded = this.getData();
        return downloaded;
    }


    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if(s != null)
        {
            ParseData p=new ParseData(context, s, recyclerView);
            p.execute();

        }else {
            Toast.makeText(context,"Unable to download", Toast.LENGTH_SHORT).show();
        }
    }
}
