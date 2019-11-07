package com.fernandishe.buscadormusica;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by andres on 04/10/15.
 */
public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> implements Filterable {
    private ArrayList<Canciones> canciones;
    private ArrayList<Canciones> cancionesFilter;
    private CustomFilter mFilter;
    private Drawable drawable;


    // Provee una referencia a cada item dentro de una vista y acceder a ellos facilmente
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Cada uno de los elementos de mi vista
        public TextView nameTextView, descriptionTextView;
        public CardView cardView;
        public LinearLayout colorLl;
        public RelativeLayout parentBodyRl;
        public ImageView imagenMusica;

        public ViewHolder(View v) {
            super(v);
            parentBodyRl = (RelativeLayout) v.findViewById(R.id.parent_body_rl);
            cardView = (CardView) v.findViewById(R.id.card_view);
            nameTextView = (TextView) v.findViewById(R.id.name_tv);
            imagenMusica = (ImageView) v.findViewById(R.id.imagenMusica);
            descriptionTextView = (TextView) v.findViewById(R.id.description_tv);
            colorLl = (LinearLayout) v.findViewById(R.id.color_ll);
        }
    }

    // Constructor
    public ListAdapter(ArrayList<Canciones> canciones) {

        this.canciones = canciones;
        this.cancionesFilter = new ArrayList<>();
        this.cancionesFilter.addAll(canciones);
        this.mFilter = new CustomFilter(ListAdapter.this);
    }

    @Override
    public Filter getFilter() {
        return mFilter;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        // inflo la vista (vista padre)
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_adapter, parent, false);
        // creo el grupo de vistas
        ViewHolder vh = new ViewHolder(v);

        return vh;
    }

    // Reemplaza en contenido de la vista
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        viewHolder.nameTextView.setText(cancionesFilter.get(position).getName());
        try {
            drawable = Drawable.createFromStream((InputStream)
                    new URL(cancionesFilter.get(position).getUrlImagen()).getContent(), "src");
        }catch (Exception ex)
        {
            ex.getStackTrace();
        }
        final Context context;
        viewHolder.imagenMusica.setImageDrawable(drawable);
        viewHolder.descriptionTextView.setText(cancionesFilter.get(position).getDescription());
        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity activity = cancionesFilter.get(position).getActivity();
                Intent in = new Intent(activity, DetalleMusica.class);
                List listaDetalle = cancionesFilter.get(position).getDetalleMusica();
                in.putExtra("urlImagen", listaDetalle.get(0).toString());
                in.putExtra("album", listaDetalle.get(1).toString());
                in.putExtra("urlVideo", listaDetalle.get(2).toString());
                in.putExtra("urlLista", listaDetalle.get(3).toString());
                activity.startActivity(in);
            }
        });
    }

    // Retorna el tamano de nuestra data
    @Override
    public int getItemCount() {
        return cancionesFilter.size();
    }

    /*Filtro*/
    public class CustomFilter extends Filter {
        private ListAdapter listAdapter;

        private CustomFilter(ListAdapter listAdapter) {
            super();
            this.listAdapter = listAdapter;
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            cancionesFilter.clear();
            final FilterResults results = new FilterResults();
            if (constraint.length() == 0) {
                cancionesFilter.addAll(canciones);
            } else {
                final String filterPattern = constraint.toString().toLowerCase().trim();
                for (final Canciones canciones : ListAdapter.this.canciones) {
                    if (canciones.getName().toLowerCase().contains(filterPattern)) {
                        cancionesFilter.add(canciones);
                    }
                }
            }
            results.values = cancionesFilter;
            results.count = cancionesFilter.size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            this.listAdapter.notifyDataSetChanged();
        }
    }

}