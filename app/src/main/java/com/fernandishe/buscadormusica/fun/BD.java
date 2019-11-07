package com.fernandishe.buscadormusica.fun;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import com.fernandishe.buscadormusica.variablesGlobales.CamposTabla;

import java.util.ArrayList;

public class BD {
    private static Cursor c;
    private static ArrayList<String>[] RetoCad;
    private static SQLiteDatabase base;


    public static ArrayList<String>[] queryConsulta(String rutaDB, String tabla, String where, String Query)
    {
        String[] campos = null;

        try
        {
            base = SQLiteDatabase.openDatabase(rutaDB, null, SQLiteDatabase.CREATE_IF_NECESSARY);
            switch (tabla)
            {
                case "tMusica":
                    campos = CamposTabla.tMusica;
                    break;
                //otras tablas
            }
            RetoCad =  null;
            if(Query.equals(""))
            {
                //c = base.query(tabla, campos, where, null, null, null, null); c = BDPanel.rawQuery("SELECT * FROM PSM_APP_MONITOREO", campos);
                c = base.rawQuery("SELECT * FROM " + tabla + where, null);
                RetoCad = new ArrayList[c.getCount()];
                int p = 0;
                if(c.getCount()>0) {
                    if (c.moveToFirst())
                    {
                        do
                        {
                            RetoCad[p] = new ArrayList<>();
                            for (int m = 0; m < campos.length; m++)
                            {
                                if(c.getType(m)==1)
                                    RetoCad[p].add(m, c.getInt(m) + "");
                                else
                                    RetoCad[p].add(m, c.getString(m));
                            }
                            p+=1;
                        } while (c.moveToNext());
                    }
                }
            }
            else
            {
                c = base.rawQuery(Query.toString(), null);
                if(c.getCount()>0)
                {
                    RetoCad = new ArrayList[c.getCount()];
                    RetoCad[0] = new ArrayList<>();
                    if(c.moveToFirst()) {
                        if (c.getInt(0) == 0)
                            RetoCad[0].add(0, "0");
                        else
                            RetoCad[0].add(0, c.getInt(0) + "");
                    }
                }
            }
        }catch (SQLiteException sql)
        {
            sql.getMessage();
        }catch (Exception ex)
        {
            ex.getMessage();
        }
        return RetoCad;
    }

    public Boolean eliminaRegistro(String rutaDB, String tabla, String where)
    {
        boolean reto = false;
        String DELETE_DATA;

        base = SQLiteDatabase.openDatabase(rutaDB, null, SQLiteDatabase.CREATE_IF_NECESSARY);
        try
        {
            DELETE_DATA = "DELETE FROM " + tabla + where;
            base.execSQL(DELETE_DATA);
            reto = true;
        }
        catch(SQLException sql)
        {
            Log.e("eliminaRegistro", "Error SQL al eliminar en tabla " + tabla + "\n" + sql.getMessage());
            reto = false;
        }
        catch(Exception ex)
        {
            Log.e("eliminaRegistro", "Error Excepción al eliminar en tabla " + tabla + "\n" + ex.getMessage());
            reto = false;
        }
        return reto;
    }
    public Boolean ActualizaRegistro(ArrayList<String> DataUpdate, String rutaDB, String tabla, ArrayList<String> campos, String where)
    {
        boolean reto = false;
        String UPDATE_DATA;
        base = SQLiteDatabase.openDatabase(rutaDB, null, SQLiteDatabase.CREATE_IF_NECESSARY);
        try
        {
            UPDATE_DATA = "UPDATE " + tabla + " SET ";
            for(int x = 0; x < campos.size();x ++)
            {
                UPDATE_DATA+= campos.get(x) + " = '" + DataUpdate.get(x) + "', ";
            }
            UPDATE_DATA = UPDATE_DATA.substring(0, UPDATE_DATA.length()-2);
            UPDATE_DATA += where;
            base.execSQL(UPDATE_DATA);
            reto = true;
        }catch(SQLException sqlEx)
        {
            Log.e("sql ActualizaRegistro", sqlEx.getMessage());
            reto = false;
        }catch(Exception ex)
        {
            Log.e("Err ActualizaRegistro", ex.getMessage());
        }
        return reto;
    }
    public static Boolean InsertaRegistro(ArrayList<String> DataInsert, String rutaDB, String tabla)
    {
        boolean reto = false;
        String INSERT_DATA;
        String[] campos = {};
        base = SQLiteDatabase.openDatabase(rutaDB, null, SQLiteDatabase.CREATE_IF_NECESSARY);
        try
        {
            INSERT_DATA = "INSERT INTO " + tabla +"(";
            switch(tabla)
            {
                case "tMusica":
                    campos = CamposTabla.tMusica;
                    break;
            }

            for(int x = 0; x < campos.length; x ++)
            {
                INSERT_DATA += campos[x] + ", ";
            }

            INSERT_DATA = INSERT_DATA.substring(0, INSERT_DATA.length()-2);
            INSERT_DATA+= ") values(";

            for(int y = 0; y < DataInsert.size(); y ++)
            {
                INSERT_DATA += "'" + DataInsert.get(y) + "', ";
            }

            INSERT_DATA = INSERT_DATA.substring(0, INSERT_DATA.length()-2);
            INSERT_DATA += ")";
            base.execSQL(INSERT_DATA);
            reto = true;
        }catch(SQLiteException sql)
        {
            Log.e("Error sql(Inserta):",sql.getMessage());
            reto = false;
        }catch(Exception ex)
        {
            Log.e("Error Excepción:", ex.getMessage());
            reto = false;
        }
        return reto;
    }

}
