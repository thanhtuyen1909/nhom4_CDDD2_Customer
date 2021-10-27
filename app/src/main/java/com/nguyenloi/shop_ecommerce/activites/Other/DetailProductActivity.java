package com.nguyenloi.shop_ecommerce.activites.Other;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.nguyenloi.shop_ecommerce.Class.AllCommentProduct;
import com.nguyenloi.shop_ecommerce.Class.AllProducts;
import com.nguyenloi.shop_ecommerce.Class.Cart_Detail;
import com.nguyenloi.shop_ecommerce.Class.CustomerProductDetail;
import com.nguyenloi.shop_ecommerce.Class.Favorite;
import com.nguyenloi.shop_ecommerce.Class.GlobalIdUser;
import com.nguyenloi.shop_ecommerce.Class.Products;
import com.nguyenloi.shop_ecommerce.Class.Rating;
import com.nguyenloi.shop_ecommerce.Class.RatingForUI;
import com.nguyenloi.shop_ecommerce.R;
import com.nguyenloi.shop_ecommerce.adapters.ProductDetailCommentAdapter;
import com.nguyenloi.shop_ecommerce.adapters.ProductsHomeTopSoldAdapter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DetailProductActivity extends AppCompatActivity {
    RecyclerView rcvProductDetail, rcvDetailProductRating;
    ImageView imgProductDetailMain, imgProductDetailCart,
            imgProductDetailCall, imgProductDetailTym;
    TextView tvProductDetailName, tvProductDetailPrice, tvProductDetailSold,
            tvProductDetailDescription, tvProductDetailShopNow, tvDetailProductGotoPageComment, tvDetailProductNumberRating;
    RatingBar ratingBarProductDetailTotal;
    String productId, keyFavorite, userId, productIdd;
    ProductsHomeTopSoldAdapter topSoldAdapter;
    ProductDetailCommentAdapter detailCommentAdapter;
    ArrayList<Products> arrProduct;
    ArrayList<Rating> arrRating;
    ArrayList<RatingForUI> arrRatingUI, arrRatingUI5;
    ArrayList<CustomerProductDetail> arrCustomer;
    Rating ratin;
    RatingForUI ratingForUI;
    CustomerProductDetail customerProductDetail;
    View view;

    Query queryByCategory, queryByFavorite, queryByCartDetail, queryByRating, queryAllUser;
    Favorite favor;
    Cart_Detail cart_detail;
    boolean isFavorite = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_product);
        this.getSupportActionBar().setTitle("Chi tiết sản phẩm");
        assert getSupportActionBar() != null;   //null check
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setControl();

        arrProduct = new ArrayList<>();
        arrRating = new ArrayList<>();
        arrRatingUI = new ArrayList<>();
        arrRatingUI5 = new ArrayList<>();
        arrCustomer = new ArrayList<>();
        //Get productId throw intent
        Intent i = getIntent();
        productId = i.getStringExtra("productId");
        //Load data
        loadInformationProduct();
        //Query by category
        queryByCategory = FirebaseDatabase.getInstance().getReference()
                .child("Products").orderByChild("category_id")
                .equalTo(arrProduct.get(0).getCategory_id());
        queryByFavorite = FirebaseDatabase.getInstance().getReference()
                .child("Favorite").orderByChild("userId").equalTo(GlobalIdUser.userId);
        queryByCartDetail = FirebaseDatabase.getInstance().getReference()
                .child("Cart_Detail").orderByChild("cartID").equalTo(GlobalIdUser.cartId);
        queryByRating = FirebaseDatabase.getInstance().getReference()
                .child("Rating").orderByChild("productId").equalTo(productId);
        queryAllUser = FirebaseDatabase.getInstance().getReference()
                .child("Customer");
//Load data rating
        loadDataRating();
        //LoadUI
        loadUiFromData();

        //Call phone
        imgProductDetailCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = "0568442815";
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
                startActivity(intent);
            }
        });

        //Load category
        loadDataByCategory();
        //Load status favorite
        loadFavoriteUserDatabase();
        //Load statusCart
        loadOderDetailUserDatabase();

        //Click tym
        imgProductDetailTym.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Insert
                if (!isFavorite) {
                    insertTymFirebase();
                }
                //Delete
                else {
                    AlertDialog.Builder btn = new AlertDialog.Builder(DetailProductActivity.this);
                    btn.setMessage("Bạn có muốn xoá sản phẩm này ra khỏi danh sách yêu thích không? ");
                    btn.setIcon(R.drawable.ic_launcher_background);
                    btn.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            deleteTymFirebase();
                        }
                    });
                    btn.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    btn.create().show();

                }
            }
        });
        imgProductDetailCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cart_detail == null) {
                    insertThisProductToFirebase();
                } else {
                    showSnackbarNotification("Bạn đã thêm sản phẩm này vào giỏ hàng rồi");
                }
            }
        });

        tvProductDetailShopNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cart_detail == null) {
                    insertThisProductToFirebase();
                    Toast.makeText(DetailProductActivity.this, "Lệnh chuyển qua màn hình giỏ hàng ", Toast.LENGTH_SHORT).show();
                } else {
                    showSnackbarNotification("Bạn đã thêm sản phẩm này vào giỏ hàng rồi");
                }
            }
        });

        tvDetailProductGotoPageComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent o = new Intent(DetailProductActivity.this, DetailProductComment.class);
                startActivity(o);
            }
        });

    }

    private void loadDataRating() {
        //Set type rcvView
        rcvDetailProductRating.setLayoutManager(new LinearLayoutManager(this));
        detailCommentAdapter = new ProductDetailCommentAdapter(DetailProductActivity.this, arrRatingUI5);
        rcvDetailProductRating.setAdapter(detailCommentAdapter);
        queryByRating.addListenerForSingleValueEvent(new ValueEventListener() {
            boolean processDone = false;

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot rating : snapshot.getChildren()) {
                        int numStart = rating.getValue(Rating.class).getNumStar();
                        String userId = rating.getValue(Rating.class).getUserId();
                        String productIdRating = rating.getValue(Rating.class).getProductId();
                        String commentDescription = rating.getValue(Rating.class).getCommentDescription();
                        String idRating = rating.getKey();
                        ratin = new Rating(numStart, userId, productIdRating, commentDescription, idRating);
                        arrRating.add(ratin);
                        processDone = true;
                    }
                } else {
                    processDone = true;
                }
                if (processDone) {
                    //When created completed rating then continue with customer
                    loadAllCustomer();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void filterDataRating() {
        int count = 0;
        for (int i = 0; i < arrRating.size(); i++) {
            for (int j = 0; j < arrCustomer.size(); j++) {
                if (arrRating.get(i).getUserId()
                        .equals(arrCustomer.get(j).getId())) {
                    count++;
                    ratingForUI = new RatingForUI(arrRating.get(i).getCommentDescription(),
                            arrCustomer.get(j).getName(), arrCustomer.get(j).getImage(),
                            arrRating.get(i).getNumStar());
                    arrRatingUI.add(ratingForUI);
                    if (count <= 5) {
                        arrRatingUI5.add(ratingForUI);
                    }
                }
            }
        }
        AllCommentProduct.setArrRatingUI(arrRatingUI);
        detailCommentAdapter.notifyDataSetChanged();
    }

    private void loadAllCustomer() {
        queryAllUser.addListenerForSingleValueEvent(new ValueEventListener() {
            boolean processDone = false;

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot customer : snapshot.getChildren()) {
                        String idUser = customer.getValue(CustomerProductDetail.class).getId();
                        String idCustomer = customer.getKey();
                        String image = customer.getValue(CustomerProductDetail.class).getImage();
                        String name = customer.getValue(CustomerProductDetail.class).getName();
                        customerProductDetail = new CustomerProductDetail(idUser, idCustomer, image, name);
                        arrCustomer.add(customerProductDetail);
                        processDone = true;
                    }
                } else {
                    processDone = true;
                }
                if (processDone) {
                    //After load completed all customer then continue with function filter
                    filterDataRating();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void insertThisProductToFirebase() {
        Map<String, Object> map = new HashMap<>();
        map.put("cartID", GlobalIdUser.cartId);
        map.put("productID", productId);
        map.put("amount", 1);
        map.put("totalPrice", arrProduct.get(0).getPrice());
        FirebaseDatabase.getInstance().getReference().child("Cart_Detail")
                .push().setValue(map)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        showSnackbarNotification("Thêm vào giỏ hàng thành công");
                    }
                });
    }

    private void showSnackbarNotification(String str) {
        Snackbar snackbar = Snackbar
                .make(view, str, Snackbar.LENGTH_LONG)
                .setAction("Đóng", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //Code khi bấm vào nút thư lại ở đây
                    }
                });
        snackbar.setActionTextColor(Color.RED);
        snackbar.show();
    }

    private void deleteTymFirebase() {
        FirebaseDatabase.getInstance().getReference().child("Favorite")
                .child(favor.getKey()).removeValue()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        imgProductDetailTym.setImageResource(R.drawable.ic_favorite_border);
                        isFavorite = false;
                    }
                });
    }

    private void insertTymFirebase() {
        Map<String, Object> map = new HashMap<>();
        map.put("userId", GlobalIdUser.userId);
        map.put("productId", productId);
        FirebaseDatabase.getInstance().getReference().child("Favorite")
                .push().setValue(map)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        imgProductDetailTym.setImageResource(R.drawable.ic_favorite);
                        isFavorite = true;
                    }
                });
    }

    private void loadFavoriteUserDatabase() {
        queryByFavorite.addValueEventListener(new ValueEventListener() {
            boolean processDone = false;

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!processDone && snapshot.exists()) {
                    for (DataSnapshot favorite : snapshot.getChildren()) {
                        productIdd = favorite.getValue(Favorite.class).getProductId();
                        keyFavorite = favorite.getKey();
                        userId = favorite.getValue(Favorite.class).getUserId();
                        if (productIdd.equals(productId)) {
                            favor = new Favorite(productIdd, userId, keyFavorite);
                        }
                        processDone = true;
                    }
                } else {
                    processDone = true;
                }
                //Filter data for recycler view
                if (processDone) {
//                    Handle until load complete data
                    if (favor != null) {
                        imgProductDetailTym.setImageResource(R.drawable.ic_favorite);
                        isFavorite = true;
                    }
                    processDone = false;
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    private void loadOderDetailUserDatabase() {
        queryByCartDetail.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot ca : snapshot.getChildren()) {
                        int amount = ca.getValue(Cart_Detail.class).getAmount();
                        int price = ca.getValue(Cart_Detail.class).getTotalPrice();
                        String productIdCartDetail = ca.getValue(Cart_Detail.class).getProductID();
                        String cartIdDetail = ca.getKey();
                        String cartId = ca.getValue(Cart_Detail.class).getCartID();
                        if (productId.equals(productIdCartDetail)) {
                            cart_detail = new Cart_Detail(amount, price, cartId, productIdCartDetail, cartIdDetail);
                        }

                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    private void loadUiFromData() {
        tvProductDetailName.setText(arrProduct.get(0).getName());
        tvProductDetailSold.setText("Đã bán: " + arrProduct.get(0).getSold() + "");
        tvProductDetailPrice.setText("Giá: " + arrProduct.get(0).getPrice() + "đ");
        ratingBarProductDetailTotal.setRating(Float.parseFloat(arrProduct.get(0).getRating() + ".0"));
        //load image
        StorageReference imageRef = FirebaseStorage.getInstance().getReference("images/products/" + arrProduct.get(0).getName() + "/" + arrProduct.get(0).getName() + ".jpg");
        imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri.toString()).resize(imgProductDetailMain.getWidth(), imgProductDetailMain.getHeight()).into(imgProductDetailMain);
            }
        });
        tvProductDetailDescription.setText(arrProduct.get(0).getDescription());
    }

    private void loadInformationProduct() {
        for (int i = 0; i < AllProducts.getArrAllProducts().size(); i++) {
            if (AllProducts.getArrAllProducts().get(i).getKey().equals(productId)) {
                arrProduct.add(AllProducts.getArrAllProducts().get(i));
            }
        }
    }

    private void loadDataByCategory() {
        //Set type rcvView
        GridLayoutManager layoutManager = new GridLayoutManager(DetailProductActivity.this, 2);
        layoutManager.setOrientation(rcvProductDetail.VERTICAL);
        rcvProductDetail.setLayoutManager(layoutManager);
        //Load data
        FirebaseRecyclerOptions<Products> options =
                new FirebaseRecyclerOptions.Builder<Products>()
                        .setQuery(queryByCategory, Products.class)
                        .build();
        topSoldAdapter = new ProductsHomeTopSoldAdapter(options, DetailProductActivity.this);
        rcvProductDetail.setAdapter(topSoldAdapter);
    }

    private void setControl() {
        rcvProductDetail = findViewById(R.id.rcvProductDetail);
        imgProductDetailCart = findViewById(R.id.imgProductDetailCart);
        imgProductDetailCall = findViewById(R.id.imgProductDetailCall);
        imgProductDetailMain = findViewById(R.id.imgProductDetailMain);
        tvProductDetailName = findViewById(R.id.tvProductDetailName);
        tvProductDetailPrice = findViewById(R.id.tvProductDetailPrice);
        tvProductDetailSold = findViewById(R.id.tvProductDetailSold);
        tvProductDetailShopNow = findViewById(R.id.tvProductDetailShopNow);
        tvProductDetailDescription = findViewById(R.id.tvProductDetailDescription);
        imgProductDetailTym = findViewById(R.id.imgProductDetailTym);
        rcvDetailProductRating = findViewById(R.id.rcvDetailProductRating);
        view = findViewById(R.id.viewProductDetail);
        ratingBarProductDetailTotal = findViewById(R.id.ratingBarProductDetailTotal);
        tvDetailProductGotoPageComment = findViewById(R.id.tvDetailProductGotoPageComment);
        tvDetailProductNumberRating = findViewById(R.id.tvDetailProductNumberRating);
    }

    @Override
    public void onStart() {
        super.onStart();
        topSoldAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        topSoldAdapter.stopListening();
        ;
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}