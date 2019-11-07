package com.fernandishe.buscadormusica.fun;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class LeeArchivos
{

    public static boolean existeArchivo(String nomFile, Context c, int id)
    {
        boolean output;
        String existe = "";
        try
        {
            //Load the file from assets folder - don't forget to INCLUDE the extension
            existe = LoadFile(nomFile, false, c, id);
            output = true;
        }
        catch (IOException e)
        {
            output = false;
        }
        return output;
    }
    public static String LoadFile(String fileName, boolean loadFromRawFolder, Context c, int id) throws IOException
    {
        //Create a InputStream to read the file into
        InputStream iS;

        if (loadFromRawFolder)
        {
            iS = c.getResources().openRawResource(id);
        }
        else
        {
            //get the file as a stream
            iS = c.getResources().getAssets().open(fileName);
        }

        //create a buffer that has the same size as the InputStream
        byte[] buffer = new byte[iS.available()];
        //read the text file as a stream, into the buffer
        iS.read(buffer);
        //create a output stream to write the buffer into
        ByteArrayOutputStream oS = new ByteArrayOutputStream();
        //write this buffer to the output stream
        oS.write(buffer);
        //Close the Input and Output streams
        oS.close();
        iS.close();

        //return the output stream as a String
        return oS.toString();
    }

    public static String leeJson(int flName, Context context, String codificacion)
    {
        String json = null;
        try
        {

            InputStream is =  context.getResources().openRawResource(flName);
            int size = is.available();

            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, codificacion);
            Log.v("LeerArchivos.leeJson", "Lectura de Json Ok");
        } catch (IOException ex) {
            Log.v("LeerArchivos.leeJson", "Error: " + ex.getMessage());
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    public static String getProperty(String key,Context context, String propertiesFile) throws IOException
    {
        Properties properties = new Properties();
        AssetManager assetManager = context.getAssets();
        InputStream inputStream = assetManager.open(propertiesFile);
        properties.load(inputStream);
        inputStream.close();
        return properties.getProperty(key);

    }

    public static String leeJsonService(InputStream is, String codificacion)
    {
        String json = null;
        try
        {
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, codificacion);
            Log.v("LeerArchivos.leeJsonS", "Lectura de Json Ok");
            is.close();
        } catch (IOException ex) {
            Log.v("LeerArchivos.leeJsonS", "Error: " + ex.getMessage());
            ex.printStackTrace();
            return null;
        }
        return json;
    }
}
