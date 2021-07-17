package com.example.smartfarmer.ui.customer;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartfarmer.R;
import com.example.smartfarmer.ui.market.Products;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;


public class MarketViewFragment extends Fragment {


    private RecyclerView productsList;
    private DatabaseReference myProductsDatabase;
    private StorageReference mStorageReference;
    public MarketViewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_market_view, container, false);
        myProductsDatabase= FirebaseDatabase.getInstance().getReference("Products");

        mStorageReference= FirebaseStorage.getInstance().getReference().child("Product Images");


        productsList=view.findViewById(R.id.productsListcustomer);

        GridLayoutManager gridLayoutManager=new GridLayoutManager(getContext(),2);
        productsList.setLayoutManager(gridLayoutManager);

        return view;
    }



    private void toast(String s) {
        Toast.makeText(getContext(), "Message: "+s, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onStart() {
        super.onStart();
        myProductsDatabase.keepSynced(true);
        FirebaseRecyclerOptions<Products> options=new FirebaseRecyclerOptions.Builder<Products>()
                .setQuery(myProductsDatabase,Products.class)
                .build();

        final FirebaseRecyclerAdapter<Products, MarketViewFragment.productsViewHolder> adapter=new FirebaseRecyclerAdapter<Products, MarketViewFragment.productsViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final MarketViewFragment.productsViewHolder holder, int position, @NonNull final Products model) {


                holder.tvName.setText(model.getProduct_name());
                holder.tvQty.setText(model.getProduct_quantity());
                holder.tvPrice.setText("Ksh: "+model.getProduct_price());
                holder.tvdetail.setText(model.getProduct_detail());
                holder.tvdate.setText("Harvested On - "+model.getProduct_date());
                Picasso.get().load(model.getPost_image()).resize(180,150).placeholder(R.drawable.ic_photo_library_black_24dp).into(holder.product_imageView);

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent in=new Intent(getContext(), CustomerDetailActivity.class);
                        in.putExtra("name",model.getProduct_name());
                        in.putExtra("maize", model.getProduct_maizetype());
                        in.putExtra("producedon", model.getProduct_date());
                        in.putExtra("qty",model.getProduct_quantity());
                        in.putExtra("price",model.getProduct_price());
                        in.putExtra("adddetail", model.getProduct_detail());
                        in.putExtra("image",model.getPost_image());
                        in.putExtra("detil", model.getProduct_description());
                        in.putExtra("poster",model.getProduct_poster());
                        in.putExtra("time",model.getPost_time());
                        in.putExtra("postId",model.getPost_id());
                        in.putExtra("category",model.getProduct_category());
                        startActivity(in);

                    }
                });

                myProductsDatabase.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        FirebaseUser firebaseUser=FirebaseAuth.getInstance().getCurrentUser();

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }

            @NonNull
            @Override
            public MarketViewFragment.productsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.single_product,viewGroup,false);
                MarketViewFragment.productsViewHolder viewHolder=new MarketViewFragment.productsViewHolder(view);


                return viewHolder;
            }
        };

        productsList.setAdapter(adapter);
        adapter.startListening();

        productsList.smoothScrollToPosition(adapter.getItemCount()+1);


    }
    public static class productsViewHolder extends RecyclerView.ViewHolder{
        private ImageView product_imageView;
        private TextView tvName,tvPrice,tvQty,tvdetail,  tvdate ;


        public productsViewHolder(@NonNull View itemView) {
            super(itemView);
            product_imageView=itemView.findViewById(R.id.product_image_view);
            tvName=itemView.findViewById(R.id.name_tv);
            tvPrice=itemView.findViewById(R.id.price_tv);
            tvQty=itemView.findViewById(R.id.qty_tv);
            tvdetail = itemView.findViewById(R.id.detail_tv);

            tvdate = itemView.findViewById(R.id.date_tv);
        }
    }
}