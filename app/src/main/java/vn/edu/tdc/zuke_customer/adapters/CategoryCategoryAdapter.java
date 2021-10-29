package vn.edu.tdc.zuke_customer.adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import vn.edu.tdc.zuke_customer.Class.Category;
import vn.edu.tdc.zuke_customer.R;
import vn.edu.tdc.zuke_customer.adapters.category.ItemClickRefreshCategory;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class CategoryCategoryAdapter extends RecyclerView.Adapter<CategoryCategoryAdapter.CategoryViewHolder>{
    Context mContext;
    private ItemClickRefreshCategory itemClickRefreshCategory;
    ArrayList<Category> arrCategory;
    List<LinearLayout> cardViewList = new ArrayList<>();


    public CategoryCategoryAdapter(Context mContext, ArrayList<Category> arrCategory, ItemClickRefreshCategory itemClickRefreshCategory) {
        this.mContext = mContext;
        this.arrCategory = arrCategory;
        this.itemClickRefreshCategory=itemClickRefreshCategory;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_category_category, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        cardViewList.add(holder.cardCategoryCategory);
        //Load data
        Category model = arrCategory.get(position);
        if(model.getName().length()<11){
            holder.tvCardCategoryCategory.setText(model.getName());
        }else{
            String strName = model.getName().substring(0,8)+"..";
            holder.tvCardCategoryCategory.setText(strName);
        }
        StorageReference imageRef = FirebaseStorage.getInstance().getReference("images/categories/"+model.getImage());
        imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri.toString()).resize(holder.imgCardCategoryCategory.getWidth(), holder.imgCardCategoryCategory.getHeight()).into(holder.imgCardCategoryCategory);
            }
        });

        holder.cardCategoryCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickRefreshCategory.onItemClickCategory(model.getIdCategory());
                holder.cardCategoryCategory.setBackgroundResource(R.color.teal_200);
                //The selected card is set to colorSelected
                for(LinearLayout cardView : cardViewList){
                    cardView.setBackgroundResource(R.color.colorBackground);
                }
                //The selected card is set to colorSelected
                holder.cardCategoryCategory.setBackgroundResource(R.color.colorSelected);

            }
        });
    }

    @Override
    public int getItemCount() {
        return arrCategory.size();
    }


    public class CategoryViewHolder extends RecyclerView.ViewHolder {
        private TextView tvCardCategoryCategory;
        private CircleImageView imgCardCategoryCategory;
        private LinearLayout cardCategoryCategory;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCardCategoryCategory=itemView.findViewById(R.id.tvCardCategoryCategory);
            imgCardCategoryCategory=itemView.findViewById(R.id.imgCardCategoryCategory);
            cardCategoryCategory=itemView.findViewById(R.id.cardCategoryCategory);

        }
    }
}