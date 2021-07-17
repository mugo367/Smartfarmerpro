package com.example.smartfarmer.ui.market;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartfarmer.Addproduction;
import com.example.smartfarmer.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static android.app.Activity.RESULT_OK;

public class ProductFragment extends Fragment {

    private static final int GALLERY_REQUEST_CODE = 2345;
    private FloatingActionButton btnUpdate;
    private EditText productPrice, productDescription;
    Spinner productName;
    TextView productmaizetype,productdate,productCategory, productdetaild, productQuantity;
    private RecyclerView productsList;
    private FirebaseAuth mAuth;
    private String userId;

    private ArrayList<String> arraylist1 = new ArrayList<>();

    private Dialog addproductdialog, editdialog;
    private Uri imageUri=null;
    private ImageView profileImage;
    private DatabaseReference myProductsDatabase;
    private StorageReference mStorageReference;
    private ProgressDialog pd;


    public ProductFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_product, container, false);
        //FirebaseDatabase.getInstance().setPersistenceEnabled(true);

        mAuth=FirebaseAuth.getInstance();
        myProductsDatabase= FirebaseDatabase.getInstance().getReference("Products");

        mStorageReference= FirebaseStorage.getInstance().getReference().child("Product Images");

        btnUpdate=view.findViewById(R.id.fb);

        addproductdialog =new Dialog(getContext());
        addproductdialog.setContentView(R.layout.post_dialog);

        profileImage = addproductdialog.findViewById(R.id.productImage);
        productName = addproductdialog.findViewById(R.id.product_title);
        productmaizetype = addproductdialog.findViewById(R.id.product_maizetype);
        productdate = addproductdialog.findViewById(R.id.product_date);
        productdetaild = addproductdialog.findViewById(R.id.product_hdescription);
        productCategory = addproductdialog.findViewById(R.id.product_category);
        productQuantity = addproductdialog.findViewById(R.id.product_quantity);
        productPrice = addproductdialog.findViewById(R.id.product_price);
        productDescription = addproductdialog.findViewById(R.id.product_description);

        productsList=view.findViewById(R.id.productsList);

        GridLayoutManager gridLayoutManager=new GridLayoutManager(getContext(),2);
        productsList.setLayoutManager(gridLayoutManager);
        userId=mAuth.getCurrentUser().getUid();
        pd=new ProgressDialog(getContext());
        pd.setTitle("Uploading Product");
        pd.setMessage("Please wait...");
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bringPostDialog();
            }
        });
        return view;
    }
    private void bringPostDialog() {
        Button btnPost=addproductdialog.findViewById(R.id.btnUpload);

        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent=new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent,GALLERY_REQUEST_CODE);
            }
        });
        //production label display

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("productionrecords");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                arraylist1.clear();
                for(DataSnapshot item: dataSnapshot.getChildren()){
                    FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                    Addproduction addproduction = item.getValue(Addproduction.class);


                    assert addproduction != null;
                    assert firebaseUser != null;
                    if(addproduction.getId().equals(firebaseUser.getUid())) {
                        arraylist1.add(item.child("productionlabel").getValue(String.class));
                    }
                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getContext(), R.layout.support_simple_spinner_dropdown_item, arraylist1);
                productName.setAdapter(arrayAdapter);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        TextView showdetails = (TextView) addproductdialog.findViewById(R.id.showdetails);
        showdetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selectedname=productName.getSelectedItem().toString().trim();

                DatabaseReference refprod = FirebaseDatabase.getInstance().getReference("productionrecords");

                refprod.orderByChild("productionlabel").equalTo(selectedname).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot item: dataSnapshot.getChildren()){
                            FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                            Addproduction addproduction = item.getValue(Addproduction.class);

                            assert addproduction != null;
                            assert firebaseUser != null;
                            if(addproduction.getId().equals(firebaseUser.getUid())) {

                                productCategory.setText(addproduction.getProductiontype());
                                productmaizetype.setText( addproduction.getMaizetype());
                                productdate.setText(addproduction.getProductiondate());
                                productdetaild.setText(addproduction.getProductiondetails());
                                productQuantity.setText(addproduction.getProductionquantity() + "  "+ addproduction.getUnit());

                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });
        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name=productName.getSelectedItem().toString().trim().trim();
                String category=productCategory.getText().toString().trim();
                String quantity=productQuantity.getText().toString().trim();
                String producedetail = productdetaild.getText().toString().trim();
                String maizetype = productmaizetype.getText().toString().trim();
                String producedon = productdate.getText().toString().trim();
                String price=productPrice.getText().toString().trim();
                String description=productDescription.getText().toString().trim();

                if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(category)&& !TextUtils.isEmpty(maizetype) && !TextUtils.isEmpty(producedon)&& !TextUtils.isEmpty(quantity) &&!TextUtils.isEmpty(price))
                {

                    if (imageUri!=null)
                    {

                        //toast("Data is ready");
                        pd.show();
                        postData(name,category,maizetype, producedon, quantity, producedetail, price,description,imageUri);
                    }else {
                        toast("Tap the avatar to select product Image");
                    }
                }else {
                    toast("Check your Product Inputs. Some fields are blank");
                }
            }
        });


        addproductdialog.show();

    }

    private void postData(final String name, final String category,final String maizetype, final String producedon, final String quantity, final String producedetail, final String price,final String description, final Uri myUri) {
        String selectedname=productName.getSelectedItem().toString().trim();
        myProductsDatabase.orderByChild("product_name").equalTo(selectedname).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    toast("THIS PRODUCT ALREADY EXISTS");
                    addproductdialog.dismiss();
                    pd.dismiss();
                }else {
                    final StorageReference filePath=mStorageReference.child(myUri.getLastPathSegment()+".jpg");
                    filePath.putFile(myUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                            if (task.isSuccessful())
                            {
                                filePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        String downloadLink=uri.toString();

                                        DateFormat dateFormat=new SimpleDateFormat("HH:mm:ss ");
                                        Date date=new Date();
                                        String time=dateFormat.format(date);
                                        String productKey=myProductsDatabase.push().getKey();
                                        DatabaseReference newProduct=myProductsDatabase.child(productKey);
                                        HashMap<String,Object> myMap=new HashMap<>();
                                        myMap.put("post_id",productKey);
                                        myMap.put("product_name",name);
                                        myMap.put("product_category",category);
                                        myMap.put("product_maizetype", maizetype);
                                        myMap.put("product_date", producedon);
                                        myMap.put("product_quantity",quantity);
                                        myMap.put("product_detail", producedetail);
                                        myMap.put("product_price",price);
                                        myMap.put("product_description", description);
                                        myMap.put("post_time",time);
                                        myMap.put("product_poster",userId);
                                        myMap.put("product_views","0");
                                        myMap.put("product_likes","0");

                                        myMap.put("post_image",downloadLink);

                                        newProduct.updateChildren(myMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful())
                                                {
                                                    toast("Uploaded successfully");
                                                    pd.dismiss();
                                                    addproductdialog.dismiss();

                                                    productQuantity.setText("");
                                                    productPrice.setText("");
                                                    productDescription.setText("");
                                                    //imageUri=null;
                                                    profileImage.setImageURI(null);

                                                }else {
                                                    pd.dismiss();

                                                    toast(task.getException().getMessage());
                                                }
                                            }
                                        });



                                    }
                                });
                            }else {
                                toast(task.getException().getMessage());
                            }
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (GALLERY_REQUEST_CODE==requestCode && resultCode==RESULT_OK)
        {
            imageUri=data.getData();
            profileImage.setImageURI(imageUri);
        }


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

        final FirebaseRecyclerAdapter<Products,productsViewHolder> adapter=new FirebaseRecyclerAdapter<Products, productsViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final productsViewHolder holder, int position, @NonNull final Products model) {


                holder.tvName.setText(model.getProduct_name());
                holder.tvQty.setText(model.getProduct_quantity());
                holder.tvdetail.setText(model.getProduct_detail());
                holder.tvPrice.setText("Ksh: "+model.getProduct_price());
                holder.tvdate.setText("Harvested On - "+model.getProduct_date());
                Picasso.get().load(model.getPost_image()).resize(180,150).placeholder(R.drawable.ic_photo_library_black_24dp).into(holder.product_imageView);


                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent in=new Intent(getContext(), DetailActivity.class);
                        in.putExtra("name",model.getProduct_name());
                        in.putExtra("maize", model.getProduct_maizetype());
                        in.putExtra("producedon", model.getProduct_date());
                        in.putExtra("qty",model.getProduct_quantity());
                        in.putExtra("price",model.getProduct_price());
                        in.putExtra("image",model.getPost_image());
                        in.putExtra("detil", model.getProduct_description());
                        in.putExtra("adddetail", model.getProduct_detail());
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

                        if(model.getProduct_poster().equals(firebaseUser.getUid())){
                            holder.removedata.setVisibility(View.VISIBLE);
                            holder.editdata.setVisibility(View.VISIBLE);
                        }else {
                            holder.removedata.setVisibility(View.GONE);
                            holder.editdata.setVisibility(View.GONE);
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                final DatabaseReference prodRef = getRef(position);
                final String productDatabaseKey = prodRef.getKey();
                holder.removedata.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        myProductsDatabase.child(productDatabaseKey).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for(DataSnapshot deleteSnapshot : dataSnapshot.getChildren()){

                                    deleteSnapshot.getRef().removeValue();
                                    myProductsDatabase.keepSynced(true);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                });
                holder.editdata.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                       editdialog =new Dialog(getContext());
                        editdialog.setContentView(R.layout.editproduct);

                        final TextView prodname = editdialog.findViewById(R.id.productname);
                        final EditText prodprice = editdialog.findViewById(R.id.pprice);
                        final EditText prodetails = editdialog.findViewById(R.id.prodetails);
                        Button edit = editdialog.findViewById(R.id.editbutton);

                        prodname.setText(model.getProduct_name());
                        prodprice.setText(model.getProduct_price());
                        prodetails.setText(model.getProduct_description());

                        edit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String prodnametxt = prodname.getText().toString();
                                String prodpricetxt = prodprice.getText().toString();
                                String proddetails = prodetails.getText().toString();


                               DatabaseReference editProduct =FirebaseDatabase.getInstance().getReference("Products").child(productDatabaseKey);
                               editProduct.orderByChild("product_name").equalTo(prodnametxt);

                                Map<String, Object> updates = new HashMap<String,Object>();

                                updates.put("product_price", prodpricetxt);
                                updates.put("product_description", proddetails);

                                editProduct.updateChildren(updates).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        editdialog.dismiss();
                                    }
                                });

                            }
                        });


                        editdialog.show();
                    }
                });
            }

            @NonNull
            @Override
            public productsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.single_product,viewGroup,false);
                productsViewHolder viewHolder=new productsViewHolder(view);


                return viewHolder;
            }
        };

        productsList.setAdapter(adapter);
        adapter.startListening();

        productsList.smoothScrollToPosition(adapter.getItemCount()+1);


    }
    public static class productsViewHolder extends RecyclerView.ViewHolder{
        private ImageView product_imageView;
        private TextView tvName,tvPrice,tvdetail, tvQty, editdata,removedata, tvdate ;


        public productsViewHolder(@NonNull View itemView) {
            super(itemView);
            product_imageView=itemView.findViewById(R.id.product_image_view);
            tvName=itemView.findViewById(R.id.name_tv);
            tvPrice=itemView.findViewById(R.id.price_tv);
            tvdetail=itemView.findViewById(R.id.detail_tv);
            tvQty=itemView.findViewById(R.id.qty_tv);

            editdata =itemView.findViewById(R.id.edit_tv);
            removedata = itemView.findViewById(R.id.delete_tv);
            tvdate = itemView.findViewById(R.id.date_tv);
        }
    }
}