package vn.edu.tdc.zuke_customer.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import vn.edu.tdc.zuke_customer.Class.AllCategory;
import vn.edu.tdc.zuke_customer.Class.AllManufactures;
import vn.edu.tdc.zuke_customer.Class.AllProducts;
import vn.edu.tdc.zuke_customer.Class.Manufactures;
import vn.edu.tdc.zuke_customer.Class.Products;
import vn.edu.tdc.zuke_customer.R;
import vn.edu.tdc.zuke_customer.activites.Other.DetailProductActivity;
import vn.edu.tdc.zuke_customer.adapters.CategoryCategoryAdapter;
import vn.edu.tdc.zuke_customer.adapters.category.ItemClickListener;
import vn.edu.tdc.zuke_customer.adapters.category.ItemClickRefreshCategory;
import vn.edu.tdc.zuke_customer.adapters.category.ListManufacturesProducts;
import vn.edu.tdc.zuke_customer.adapters.category.Section;
import vn.edu.tdc.zuke_customer.adapters.category.SectionedExpandableLayoutHelper;

import java.util.ArrayList;

public class UserListFragment extends Fragment implements ItemClickListener, ItemClickRefreshCategory {
    RecyclerView rcvCategoryCategory, rcVCategoryManufactures;
    ArrayList<Products> arrProduct;
    ArrayList<Manufactures> arrManufactures;
    CategoryCategoryAdapter categoryAdapter;
    ArrayList<ListManufacturesProducts> arrayListManufacturesProducts;
    SectionedExpandableLayoutHelper sectionedExpandableLayoutHelper = null;
    ListManufacturesProducts listManufacturesProducts;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_list, container, false);
        replaceLayoutMenuTop();
        setControl(view);
        arrProduct = new ArrayList<>();
        arrManufactures = new ArrayList<>();
        arrayListManufacturesProducts = new ArrayList<>();

        //

        //
        setRecyclerViewCategory();
        setRecyclerManufactures();
        loadDataDefault();
        return view;
    }


    private void setControl(View v) {
        rcvCategoryCategory = v.findViewById(R.id.rcvCategoryCategory);
        rcVCategoryManufactures = v.findViewById(R.id.rcVCategoryManufactures);
    }

    private void setRecyclerManufactures() {
        sectionedExpandableLayoutHelper = new SectionedExpandableLayoutHelper(getContext(),
                rcVCategoryManufactures, this, 2);
    }

    private void setRecyclerViewCategory() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        ;
        rcvCategoryCategory.setLayoutManager(linearLayoutManager);
        categoryAdapter = new CategoryCategoryAdapter(getContext(), AllCategory.getArrAllCategory(), this);
        rcvCategoryCategory.setAdapter(categoryAdapter);
    }


    private void replaceLayoutMenuTop() {
        FragmentManager fragmentManager = getChildFragmentManager();
        final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        final SearchItemFragment newFragment = new SearchItemFragment();
        fragmentTransaction.replace(R.id.layoutCategory, newFragment);
        fragmentTransaction.commit();
    }


    @Override
    public void itemClicked(Products item) {
        Intent intent = new Intent(getContext(), DetailProductActivity.class);
        intent.putExtra("productId",item.getKey());
        startActivity(intent);
    }

    @Override
    public void itemClicked(Section section) {

    }

    @Override
    public void onItemClickCategory(String idCategory) {

        arrManufactures.clear();
        sectionedExpandableLayoutHelper.removeAll();
        sectionedExpandableLayoutHelper.notifyDataSetChanged();
        arrayListManufacturesProducts.clear();
        for (int i = 0; i < AllManufactures.getAllManufactures().size(); i++) {
            if (AllManufactures.getAllManufactures().get(i).getIdCategory()
                    .equals(idCategory)) {
                String manuName = AllManufactures.getAllManufactures().get(i).getName();
                arrProduct = new ArrayList<>();
                for (int j = 0; j < AllProducts.getArrAllProducts().size(); j++) {
                    if (AllManufactures.getAllManufactures().get(i).getIdManufactures()
                            .equals(AllProducts.getArrAllProducts().get(j).getManu_id())) {
                        arrProduct.add(AllProducts.getArrAllProducts().get(j));
                    }
                }
                listManufacturesProducts = new ListManufacturesProducts(manuName, arrProduct);

                arrayListManufacturesProducts.add(listManufacturesProducts);
            }
        }
        for (int i = 0; i < arrayListManufacturesProducts.size(); i++) {
            sectionedExpandableLayoutHelper.addSection(arrayListManufacturesProducts.get(i).getNameManufactures(),
                    arrayListManufacturesProducts.get(i).getArrProducts());
        }
        sectionedExpandableLayoutHelper.notifyDataSetChanged();

    }


    //Load data default
    private void loadDataDefault(){

        for (int i = 0; i < AllManufactures.getAllManufactures().size(); i++) {
            if (AllManufactures.getAllManufactures().get(i).getIdCategory()
                    .equals("-MmICkZXAMpn3DNIg7Ue")) {
                String manuName = AllManufactures.getAllManufactures().get(i).getName();
                arrProduct = new ArrayList<>();
                for (int j = 0; j < AllProducts.getArrAllProducts().size(); j++) {
                    if (AllManufactures.getAllManufactures().get(i).getIdManufactures()
                            .equals(AllProducts.getArrAllProducts().get(j).getManu_id())) {
                        arrProduct.add(AllProducts.getArrAllProducts().get(j));
                    }
                }
                listManufacturesProducts = new ListManufacturesProducts(manuName, arrProduct);

                arrayListManufacturesProducts.add(listManufacturesProducts);
            }
        }
        for (int i = 0; i < arrayListManufacturesProducts.size(); i++) {
            sectionedExpandableLayoutHelper.addSection(arrayListManufacturesProducts.get(i).getNameManufactures(),
                    arrayListManufacturesProducts.get(i).getArrProducts());
        }
        sectionedExpandableLayoutHelper.notifyDataSetChanged();
    }
}