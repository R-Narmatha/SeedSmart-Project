package com.example.seedsmartapp.adapters;

import android.view.*;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.seedsmartapp.R;
import com.example.seedsmartapp.models.HistoryModel;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    List<HistoryModel> list;

    // 🔥 Interface for delete
    public interface OnItemLongClick {
        void onDelete(int position);
    }

    OnItemLongClick listener;

    // 🔥 Updated constructor
    public HistoryAdapter(List<HistoryModel> list, OnItemLongClick listener) {
        this.list = list;
        this.listener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvCrop, tvFertilizer, tvCost, tvTime;

        public ViewHolder(View itemView) {
            super(itemView);

            tvCrop = itemView.findViewById(R.id.tvCrop);
            tvFertilizer = itemView.findViewById(R.id.tvFertilizer);
            tvCost = itemView.findViewById(R.id.tvCost);
            tvTime = itemView.findViewById(R.id.tvTime);

            // 🔥 Long press to delete
            itemView.setOnLongClickListener(v -> {
                if (listener != null && getAdapterPosition() != RecyclerView.NO_POSITION) {
                    listener.onDelete(getAdapterPosition());
                }
                return true;
            });
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_history, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        HistoryModel model = list.get(position);

        holder.tvCrop.setText("🌾 " + model.getCrop());
        holder.tvFertilizer.setText("🌱 " + model.getFertilizer());
        holder.tvCost.setText("💰 " + model.getCost());
        holder.tvTime.setText("⏳ " + model.getTime());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}