package com.example.smartfarmer.ui.reports;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import com.example.smartfarmer.Addproduction;
import com.example.smartfarmer.R;
import com.github.barteksc.pdfviewer.PDFView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.html.WebColors;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class productionReportActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private DatabaseReference userRef;
     TextView mstartdate,mfinaldate;

    Spinner spinner;
    private DatabaseReference productionRef;
    DatePickerDialog.OnDateSetListener from_datelistener, to_datelistener;

    private ArrayList<String> arraylist1 = new ArrayList<>();
    public static String[] PERMISSIONS = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    public static int PERMISSION_ALL = 12;

    List<Addproduction> addproductionList;

    public static File productionFile;
    private File prodfile;
    private PDFView pdfView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_production);

        Toolbar toolbar = findViewById(R.id.toolroduction);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getSupportActionBar().setTitle("Production Report");
        mstartdate=  findViewById(R.id.startdateproduction);
         mfinaldate =  findViewById(R.id.enddateproduction);
        spinner = findViewById(R.id.spinnerfarm);
        Button button = findViewById(R.id.generate);

        pdfView = findViewById(R.id.production_pdf_viewer);
        mAuth = FirebaseAuth.getInstance();
        userRef = FirebaseDatabase.getInstance().getReference().child("Users");
        productionRef = FirebaseDatabase.getInstance().getReference().child("productionrecords");

        mstartdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(v.getContext(), from_datelistener, year, month, day);
                datePickerDialog.show();

                from_datelistener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year1, int month1, int dayOfMonth1) {
                        month1 =month1+1;
                        String date0 = dayOfMonth1+"/"+month1+"/"+year1;
                        mstartdate.setText(date0);

                    }
                };

            }
        });

        mfinaldate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(v.getContext(), to_datelistener, year, month, day);
                datePickerDialog.show();

                to_datelistener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month =month+1;
                        String date1 = dayOfMonth+"/"+month+"/"+year;
                        mfinaldate.setText(date1);

                    }
                };

            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addproductionList = new ArrayList<>();

                //create file
                prodfile = new File("/storage/emulated/0/Report/");
                //check if they exist, if not create them(directory)
                if ( !prodfile.exists()) {
                    prodfile.mkdirs();	        }
                productionFile = new File(prodfile, "production.pdf");
                //fetch payment and disabled users details;

                productuionReport();
                fetchproduction();
            }
        });


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
                        arraylist1.add(item.child("fieldname").getValue(String.class));
                    }
                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(productionReportActivity.this, R.layout.support_simple_spinner_dropdown_item, arraylist1);
                spinner.setAdapter(arrayAdapter);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

   

    }
    private void fetchproduction() {
        final String selectedname=spinner.getSelectedItem().toString().trim();
        String startdatepr=mstartdate.getText().toString().trim();
        String enddatepr=mfinaldate.getText().toString().trim();


        productionRef.orderByChild("productiondate")/*equalTo(selectedname)*/.startAt(startdatepr).endAt(enddatepr).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    //creating an object and setting to displlay

                    FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                    Addproduction addproduction1 = snapshot.getValue(Addproduction.class);
                    assert addproduction1 != null;
                    assert firebaseUser != null;
                    if(addproduction1.getId().equals(firebaseUser.getUid()) && addproduction1.getFieldname().equals(selectedname) ) {

                        Addproduction addproduction = new Addproduction();
                        addproduction.setProductiondate(snapshot.child("productiondate").getValue().toString());
                        addproduction.setProductiontype(snapshot.child("productiontype").getValue().toString());
                        addproduction.setMaizetype(snapshot.child("maizetype").getValue().toString());
                        addproduction.setProductionquantity(snapshot.child("productionquantity").getValue().toString());


                        //this just log details fetched from db(you can use Timber for logging
                        //Log.d("in", "Name: " + pays.getFirst_Name());
                        //Log.d("Payment", "othername: " + pays.getOther_Name());
                        //Log.d("Payment", "phone: " + pays.getPhone());

            /* The error before was cause by giving incorrect data type
       You were adding an object of type PaymentUsers yet the arraylist expects obejct of type DisabledUsers
             */
                        addproductionList.add(addproduction);

                    }

                }	                //create a pdf file and catch exception beacause file may not be created
                try {
                    createproductionReport(addproductionList);
                } catch (DocumentException | FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    private void createproductionReport(List<Addproduction> addproductionList) throws DocumentException, FileNotFoundException {
        BaseColor colorWhite = WebColors.getRGBColor("#ffffff");
        BaseColor colorBlue = WebColors.getRGBColor("#056FAA");
        BaseColor grayColor = WebColors.getRGBColor("#425066");


        Font white = new Font(Font.FontFamily.HELVETICA, 15.0f, Font.BOLD, colorWhite);
        FileOutputStream output = new FileOutputStream(productionFile);
        Document document = new Document(PageSize.A4);
        PdfPTable table = new PdfPTable(new float[]{6, 15, 15, 15, 20});
        table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        table.getDefaultCell().setFixedHeight(50);
        table.setTotalWidth(PageSize.A4.getWidth());
        table.setWidthPercentage(100);
        table.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);

        Chunk noText = new Chunk("No.", white);
        PdfPCell noCell = new PdfPCell(new Phrase(noText));
        noCell.setFixedHeight(50);
        noCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        noCell.setVerticalAlignment(Element.ALIGN_CENTER);
        noCell.setBackgroundColor(colorBlue);

        Chunk nameText = new Chunk("Date", white);
        PdfPCell nameCell = new PdfPCell(new Phrase(nameText));
        nameCell.setFixedHeight(50);
        nameCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        nameCell.setVerticalAlignment(Element.ALIGN_CENTER);
        nameCell.setBackgroundColor(colorBlue);

        Chunk contactText = new Chunk("Type", white);
        PdfPCell contactCell = new PdfPCell(new Phrase(contactText));
        contactCell.setFixedHeight(50);
        contactCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        contactCell.setVerticalAlignment(Element.ALIGN_CENTER);
        contactCell.setBackgroundColor(colorBlue);

        Chunk idText = new Chunk("MaizeType", white);
        PdfPCell idCell = new PdfPCell(new Phrase(idText));
        idCell.setFixedHeight(50);
        idCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        idCell.setVerticalAlignment(Element.ALIGN_CENTER);
        idCell.setBackgroundColor(colorBlue);

        Chunk typeText = new Chunk("Quantity", white);
        PdfPCell detailsCell = new PdfPCell(new Phrase(typeText));
        detailsCell.setFixedHeight(50);
        detailsCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        detailsCell.setVerticalAlignment(Element.ALIGN_CENTER);
        detailsCell.setBackgroundColor(colorBlue);

        Chunk footerText = new Chunk("SMARTFARMER - Copyright @ 2021");
        PdfPCell footCell = new PdfPCell(new Phrase(footerText));
        footCell.setFixedHeight(50);
        footCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        footCell.setVerticalAlignment(Element.ALIGN_BOTTOM);
        footCell.setColspan(5);

        table.addCell(noCell);
        table.addCell(nameCell);
        table.addCell(contactCell);
        table.addCell(idCell);
        table.addCell(detailsCell);
        table.setHeaderRows(1);

        PdfPCell[] cells = table.getRow(0).getCells();

        for (PdfPCell cell : cells) {
            cell.setBackgroundColor(grayColor);
        }
        for (int i = 0; i < addproductionList.size() ; i++){
            Addproduction addproduction = addproductionList.get(i);

            String id = String.valueOf(i +1);
            String Name = addproduction.getProductiondate();
            String contact= addproduction.getProductiontype();
            String idp = addproduction.getMaizetype();
            String details = addproduction.getProductionquantity();

            table.addCell( id + ". ");
            table.addCell(Name);
            table.addCell(contact);
            table.addCell(idp);
            table.addCell(details);

        }



        PdfPTable footTable = new PdfPTable(new float[]{6, 15, 20, 10, 20});
        footTable.setTotalWidth(PageSize.A4.getWidth());
        footTable.setWidthPercentage(100);
        footTable.addCell(footCell);

        PdfWriter.getInstance(document, output);
        document.open();
        Font g = new Font(Font.FontFamily.HELVETICA, 25.0f, Font.NORMAL, grayColor);
        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        String des = spinner.getSelectedItem().toString();


        String date = dateFormat.format(calendar.getTime());

        document.add(new Paragraph("REPORT ON PRODUCTION ON ("+des+") " +"AS AT "+date+"\n\n", g));
        document.add(table);
        document.add(footTable);
        document.close();
    }


    public boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }
    public void productuionReport()
    {
        if (hasPermissions(this, PERMISSIONS)) {
            DisplayReport();
        } else {
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
        }
    }

    private void DisplayReport()	    {
        pdfView.fromFile(productionFile)
                .pages(0,1,2,3,4,5)
                .enableSwipe(true)
                .swipeHorizontal(false)
                .enableDoubletap(true)
                .defaultPage(0)
                .load();

    }
}