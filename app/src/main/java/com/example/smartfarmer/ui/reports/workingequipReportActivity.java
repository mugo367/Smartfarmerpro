package com.example.smartfarmer.ui.reports;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import com.example.smartfarmer.Addequipment;
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

public class workingequipReportActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference userRef;
    private DatabaseReference equipmentRef;

    public static String[] PERMISSIONS = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    public static int PERMISSION_ALL = 12;

    List<Addequipment> addequipmentList;

    public static File Equipfile;
    private File equipment;
    private PDFView pdfView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workingequip_report);


        Toolbar toolbar = findViewById(R.id.toolequip);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getSupportActionBar().setTitle("Equipment Report");

        pdfView = findViewById(R.id.equip_pdf_viewer);
        mAuth = FirebaseAuth.getInstance();
        userRef = FirebaseDatabase.getInstance().getReference().child("Users");
        equipmentRef = FirebaseDatabase.getInstance().getReference().child("addequipment");



        addequipmentList = new ArrayList<>();

        //create file
        equipment = new File("/storage/emulated/0/Report/");
        //check if they exist, if not create them(directory)
        if ( !equipment.exists()) {
            equipment.mkdirs();	        }
        Equipfile = new File(equipment, "workingequip.pdf");
        //fetch payment and disabled users details;

        equipmentReport();
        fetchequip();



    }
    private void fetchequip() {
        equipmentRef.orderByChild("equipstatus").equalTo("Working").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    //creating an object and setting to displlay

                    FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                    Addequipment addequipment = snapshot.getValue(Addequipment.class);
                    assert addequipment != null;
                    assert firebaseUser != null;
                    if(addequipment.getId().equals(firebaseUser.getUid())) {

                        Addequipment addequipment1 = new Addequipment();
                        addequipment1.setName(snapshot.child("name").getValue().toString());
                        addequipment1.setQuantity(snapshot.child("quantity").getValue().toString());



                        //this just log details fetched from db(you can use Timber for logging
                        //Log.d("in", "Name: " + pays.getFirst_Name());
                        //Log.d("Payment", "othername: " + pays.getOther_Name());
                        //Log.d("Payment", "phone: " + pays.getPhone());

            /* The error before was cause by giving incorrect data type
       You were adding an object of type PaymentUsers yet the arraylist expects obejct of type DisabledUsers
             */
                        addequipmentList.add(addequipment1);

                    }

                }	                //create a pdf file and catch exception beacause file may not be created
                try {
                    createequipmentReport(addequipmentList);
                } catch (DocumentException | FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    private void createequipmentReport(List<Addequipment> addequipmentList) throws DocumentException, FileNotFoundException {
        BaseColor colorWhite = WebColors.getRGBColor("#ffffff");
        BaseColor colorBlue = WebColors.getRGBColor("#056FAA");
        BaseColor grayColor = WebColors.getRGBColor("#425066");


        Font white = new Font(Font.FontFamily.HELVETICA, 15.0f, Font.BOLD, colorWhite);
        FileOutputStream output = new FileOutputStream(Equipfile);
        Document document = new Document(PageSize.A4);
        PdfPTable table = new PdfPTable(new float[]{6,45, 20});
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

        Chunk contactText = new Chunk("Quantity", white);
        PdfPCell contactCell = new PdfPCell(new Phrase(contactText));
        contactCell.setFixedHeight(50);
        contactCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        contactCell.setVerticalAlignment(Element.ALIGN_CENTER);
        contactCell.setBackgroundColor(colorBlue);


        Chunk footerText = new Chunk("SMARTFARMER - Copyright @ 2021");
        PdfPCell footCell = new PdfPCell(new Phrase(footerText));
        footCell.setFixedHeight(50);
        footCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        footCell.setVerticalAlignment(Element.ALIGN_BOTTOM);
        footCell.setColspan(5);

        table.addCell(noCell);
        table.addCell(nameCell);
        table.addCell(contactCell);

        table.setHeaderRows(1);

        PdfPCell[] cells = table.getRow(0).getCells();

        for (PdfPCell cell : cells) {
            cell.setBackgroundColor(grayColor);
        }
        for (int i = 0; i < addequipmentList.size() ; i++){
            Addequipment addequipment = addequipmentList.get(i);

            String id = String.valueOf(i +1);
            String Name = addequipment.getName();
            String quantity= addequipment.getQuantity();

            table.addCell( id + ". ");
            table.addCell(Name);
            table.addCell(quantity);


        }



        PdfPTable footTable = new PdfPTable(new float[]{6, 45, 20});
        footTable.setTotalWidth(PageSize.A4.getWidth());
        footTable.setWidthPercentage(100);
        footTable.addCell(footCell);

        PdfWriter.getInstance(document, output);
        document.open();
        Font g = new Font(Font.FontFamily.HELVETICA, 25.0f, Font.NORMAL, grayColor);

        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        String date = dateFormat.format(calendar.getTime());

        document.add(new Paragraph(" REPORT ON WORKING EQUIPMENT AS AT  " +date+ "\n\n", g));
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
    public void equipmentReport()
    {
        if (hasPermissions(this, PERMISSIONS)) {
            DisplayReport();
        } else {
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
        }
    }

    private void DisplayReport()	    {
        pdfView.fromFile(Equipfile)
                .pages(0,2,1,3,3,3)
                .enableSwipe(true)
                .swipeHorizontal(false)
                .enableDoubletap(true)
                .defaultPage(0)
                .load();

    }
}