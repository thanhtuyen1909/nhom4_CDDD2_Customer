package vn.edu.tdc.zuke_customer.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import vn.edu.tdc.zuke_customer.Class.CustomerConslutant;
import vn.edu.tdc.zuke_customer.R;

import java.util.List;

public class TheCustomerConslutantAdapter extends RecyclerView.Adapter<TheCustomerConslutantAdapter.ConslutantViewHolder> {
    List<CustomerConslutant> list;

    public TheCustomerConslutantAdapter(List<CustomerConslutant> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ConslutantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_the_customer_conslutant, parent, false);
        return new ConslutantViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ConslutantViewHolder holder, int position) {
        CustomerConslutant conslutant = list.get(position);
        holder.tvCardName.setText(conslutant.getTitle());
        holder.tvCardDescription.setText(conslutant.getDescription());

        boolean isExpanded = list.get(position).isExpand();
        holder.expandLayout.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ConslutantViewHolder extends RecyclerView.ViewHolder {
        TextView tvCardName, tvCardDescription;
        LinearLayout lnLayout;
        RelativeLayout expandLayout;

        public ConslutantViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCardName=itemView.findViewById(R.id.tvCardName);
            tvCardDescription=itemView.findViewById(R.id.tvCardDescription);
            lnLayout=itemView.findViewById(R.id.lnLayout);
            expandLayout=itemView.findViewById(R.id.expandLayout);
            lnLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CustomerConslutant conslutant = list.get(getAdapterPosition());
                    conslutant.setExpand(!conslutant.isExpand());
                    notifyItemChanged(getAdapterPosition());
                }
            });
        }
    }
}
