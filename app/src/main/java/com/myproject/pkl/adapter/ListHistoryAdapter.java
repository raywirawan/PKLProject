package com.myproject.pkl.adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.myproject.pkl.MyUtils;
import com.myproject.pkl.R;
import com.myproject.pkl.model.IdentifiedObject;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ListHistoryAdapter extends RecyclerView.Adapter<ListHistoryAdapter.CardViewViewHolder> implements Filterable {
    private List<IdentifiedObject> listObject;
    private List<IdentifiedObject> listObjectFull;

    public ListHistoryAdapter(List<IdentifiedObject> listObject){
        this.listObject = listObject;
        listObjectFull = new ArrayList<>(listObject);
    }

    @NonNull
    @Override
    public ListHistoryAdapter.CardViewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card_history, parent, false);
        return new CardViewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListHistoryAdapter.CardViewViewHolder holder, int position) {
        IdentifiedObject io = listObject.get(position);
        Bitmap bmp = BitmapFactory.decodeByteArray(io.getPhoto(), 0, io.getPhoto().length);

        holder.imgPhoto.setImageBitmap(bmp);
        holder.tvJenis.setText("Nilam "+io.getJenis());
        holder.tvTangggal.setText(MyUtils.getTanggalFormat(io.getTanggal()));
        holder.tvJam.setText(io.getJam());

    }

    @Override
    public int getItemCount() {
        return listObject.size();
    }

    @Override
    public Filter getFilter() {
        return listfilter;
    }

    private Filter listfilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<IdentifiedObject> filterlist = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filterlist.addAll(listObjectFull);
            } else {
                String filterpattern = constraint.toString().toLowerCase().trim();
                for (IdentifiedObject identifiedObject : listObjectFull) {
                    if (identifiedObject.getJenis().toLowerCase().contains(filterpattern)) {
                        filterlist.add(identifiedObject);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filterlist;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            listObject.clear();
            listObject.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    public class CardViewViewHolder extends RecyclerView.ViewHolder {
        CircleImageView imgPhoto;
        TextView tvJenis, tvTangggal, tvJam;
        public CardViewViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPhoto = itemView.findViewById(R.id.item_card_history_gambar);
            tvJenis = itemView.findViewById(R.id.item_card_history_jenis);
            tvTangggal = itemView.findViewById(R.id.item_card_history_tanggal);
            tvJam = itemView.findViewById(R.id.item_card_history_jam);
        }
    }
}
