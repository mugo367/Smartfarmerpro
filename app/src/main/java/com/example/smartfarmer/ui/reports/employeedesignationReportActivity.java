package com.example.smartfarmer.ui.reports;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import com.example.smartfarmer.Addemployee;
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

public class employeedesignationReportActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private DatabaseReference userRef;
    Spinner spinnerdes;
    Button generate;

    private DatabaseReference employeeRef;

    public static String[] PERMISSIONS = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    public static int PERMISSION_ALL = 12;

    List<Addemployee> addemployeedsgList;

    public static File EmdsgFile;
    private File empdsgfile;
    private PDFView pdfView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_employeedesignation);

        Toolbar toolbar = findViewById(R.id.tooldesignation);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getSupportActionBar().setTitle("Employee Designation Report");

        spinnerdes = findViewById(R.id.spinnerdesgn);
        generate = findViewById(R.id.generatedes);
        pdfView = findViewById(R.id.desn_pdf_viewer);
        mAuth = FirebaseAuth.getInstance();
        userRef = FirebaseDatabase.getInstance().getReference().child("Users");
        employeeRef = FirebaseDatabase.getInstance().getReference().child("addemployee");


        String des = spinnerdes.getSelectedItem().toString();
        if(des.equals("Manager")){
            addemployeedsgList = new ArrayList<>();

            //create file
            empdsgfile = new File("/storage/emulated/0/Report/");
            //check if they exist, if not create them(directory)
            if ( !empdsgfile.exists()) {
                empdsgfile.mkdirs();
            }


            EmdsgFile = new File(empdsgfile, "employeemanagerdesignation.pdf");
            generate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    previewempdesgReport();
                    fetchempdesgtype();
                }
            });
        }



    }
    private void fetchempdesgtype() {

        employeeRef.orderByChild("designation").equalTo("Manager").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                    Addemployee addemployee1 = snapshot.getValue(Addemployee.class);

                    assert addemployee1 != null;
                    assert firebaseUser != null;
                    if(addemployee1.getId().equals(firebaseUser.getUid())) {

                        //creating an object and setting to displlay
                        Addemployee addemployee = new Addemployee();
                        addemployee.setEmployeename(snapshot.child("employeename").getValue().toString());
                        addemployee.setContact(snapshot.child("contact").getValue().toString());
                        addemployee.setPersonid(snapshot.child("personid").getValue().toString());
                        addemployee.setDateofemp(snapshot.child("dateofemp").getValue().toString());


                        //this just log details fetched from db(you can use Timber for logging
                        //Log.d("in", "Name: " + pays.getFirst_Name());
                        //Log.d("Payment", "othername: " + pays.getOther_Name());
                        //Log.d("Payment", "phone: " + pays.getPhone());

            /* The error before was cause by giving incorrect data type
       You were adding an object of type PaymentUsers yet the arraylist expects obejct of type DisabledUsers
             */
                        addemployeedsgList.add(addemployee);
                    }

                }	                //create a pdf file and catch exception beacause file may not be created
                try {
                    createEmployeetypeReport(addemployeedsgList);
                } catch (DocumentException | FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    private void createEmployeetypeReport(List<Addemployee> addemployeedsgList) throws DocumentException, FileNotFoundException {
        BaseColor colorWhite = WebColors.getRGBColor("#ffffff");
        BaseColor colorBlue = WebColors.getRGBColor("#056FAA");
        BaseColor grayColor = WebColors.getRGBColor("#425066");


        Font white = new Font(Font.FontFamily.HELVETICA, 15.0f, Font.BOLD, colorWhite);
        FileOutputStream output = new FileOutputStream(EmdsgFile);

        Document document = new Document(PageSize.A4);
        PdfPTable table = new PdfPTable(new float[]{6, 15, 20, 10, 20});
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

        Chunk nameText = new Chunk("Name", white);
        PdfPCell nameCell = new PdfPCell(new Phrase(nameText));
        nameCell.setFixedHeight(50);
        nameCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        nameCell.setVerticalAlignment(Element.ALIGN_CENTER);
        nameCell.setBackgroundColor(colorBlue);

        Chunk contactText = new Chunk("Contact", white);
        PdfPCell contactCell = new PdfPCell(new Phrase(contactText));
        contactCell.setFixedHeight(50);
        contactCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        contactCell.setVerticalAlignment(Element.ALIGN_CENTER);
        contactCell.setBackgroundColor(colorBlue);

        Chunk idText = new Chunk("ID", white);
        PdfPCell idCell = new PdfPCell(new Phrase(idText));
        idCell.setFixedHeight(50);
        idCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        idCell.setVerticalAlignment(Element.ALIGN_CENTER);
        idCell.setBackgroundColor(colorBlue);

        Chunk typeText = new Chunk("DATE OF EMPLOYEMENT", white);
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
        for (int i = 0; i < addemployeedsgList.size() ; i++){
            Addemployee addemployee = addemployeedsgList.get(i);
            addemployeedsgList.clear();

            String id = String.valueOf(i + 1);
            String Name = addemployee.getEmployeename();
            String contact = addemployee.getContact();
            String idp = addemployee.getPersonid();
            String details = addemployee.getDateofemp();

            table.addCell(id + ". ");
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
        String des = spinnerdes.getSelectedItem().toString();
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        String date = dateFormat.format(calendar.getTime());
        document.add(new Paragraph(" REPORT ON EMPLOYEE DESIGNATION ("+des+") " +"AS AT "+date+"\n\n", g));
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
    public void previewempdesgReport()
    {
        if (hasPermissions(this, PERMISSIONS)) {
            DisplayReport();
        } else {
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
        }
    }

    private void DisplayReport()	    {
        pdfView.fromFile(EmdsgFile)
                .pages(0,1,2,3,4,5)
                .enableSwipe(true)
                .swipeHorizontal(false)
                .enableDoubletap(true)
                .defaultPage(0)
                .load();

    }

    @Override
    protected void onResume() {
        super.onResume();
        EmdsgFile.delete();
    }
}