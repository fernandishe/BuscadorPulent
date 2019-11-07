package com.fernandishe.buscadormusica.fun;

import android.content.Context;
import android.os.StrictMode;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ResponseServicios {

    public static String retoResponse(String endPoint, Context context, String nomProperties, String[][]params, String metodo)
    {
        String response = "";
        try {
            response = traerDatos(
                    LeeArchivos.getProperty(endPoint, context, nomProperties), params, metodo);
        }catch (IOException io)
        {
            io.printStackTrace();
        }
        return response;
    }
    public static String traerDatos(String endPoint, String[][] params, String metodo)
    {
        URL url;
        StringBuilder response = new StringBuilder();
        try
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            url = new URL(endPoint);
            HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
            urlConnection.setConnectTimeout(7000);
            urlConnection.setRequestMethod(metodo);

            if(params!=null)
            for(int g=0;g<params.length;g++)
            {
                urlConnection.setRequestProperty(params[g][0], params[g][1]);
            }

            urlConnection.setRequestProperty("Content-type", "application/json");

            int responseCode = urlConnection.getResponseCode();

            if(responseCode == HttpURLConnection.HTTP_OK)
            {
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                String line;
                while ((line = reader.readLine())!=null)
                {
                    response.append(line);
                }
            }
            urlConnection.disconnect();
        }catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return response.toString();
    }
}
