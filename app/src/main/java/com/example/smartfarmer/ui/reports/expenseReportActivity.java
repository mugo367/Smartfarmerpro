package com.example.smartfarmer.ui.reports;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import com.example.smartfarmer.Addincome;
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

public class expenseReportActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private DatabaseReference userRef;
    private DatabaseReference incomeRef;

    public static String[] PERMISSIONS = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    public static int PERMISSION_ALL = 12;

    List<Addincome> addexpenseList;

    public static File EFile;
    private File expensefile;
    private PDFView pdfView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_expense);

        Toolbar toolbar = findViewById(R.id.toolexpense);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getSupportActionBar().setTitle("Expense Report");
        pdfView = findViewById(R.id.expense_pdf_viewer);
        mAuth = FirebaseAuth.getInstance();
        userRef = FirebaseDatabase.getInstance().getReference().child("Users");
        incomeRef = FirebaseDatabase.getInstance().getReference().child("income_expense");



        addexpenseList = new ArrayList<>();

        //create file
        expensefile = new File("/storage/emulated/0/Report/");
        //check if they exist, if not create them(directory)
        if ( !expensefile.exists()) {
            expensefile.mkdirs();	        }
        EFile = new File(expensefile, "expense.pdf");
        //fetch payment and disabled users details;

        previewexpenseReport();
        fetchexpense();

        
    }
    private void fetchexpense() {
        incomeRef.orderByChild("transactiontype").equalTo("Expense").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    //creating an object and setting to displlay

                    FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                    Addincome addincome1 = snapshot.getValue(Addincome.class);
                    assert addincome1 != null;
                    assert firebaseUser != null;
                    if(addincome1.getId().equals(firebaseUser.getUid())) {

                        Addincome addexpense = new Addincome();
                        addexpense.setTransactiondate(snapshot.child("transactiondate").getValue().toString());
                        addexpense.setTransactionname(snapshot.child("transactionname").getValue().toString());
                        addexpense.setTransactioncost(snapshot.child("transactioncost").getValue().toString());
                        addexpense.setTransactiondetails(snapshot.child("transactiondetails").getValue().toString());

                        //this just log details fetched from db(you can use Timber for logging
                        //Log.d("in", "Name: " + pays.getFirst_Name());
                        //Log.d("Payment", "othername: " + pays.getOther_Name());
                        //Log.d("Payment", "phone: " + pays.getPhone());

            /* The error before was cause by giving incorrect data type
       You were adding an object of type PaymentUsers yet the arraylist expects obejct of type DisabledUsers
             */
                        addexpenseList.add(addexpense);
                    }
                }	                //create a pdf file and catch exception beacause file may not be created
                try {
                    createExpenseReport(addexpenseList);
                } catch (DocumentException | FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void createExpenseReport(List<Addincome> addexpenseList) throws DocumentException, FileNotFoundException {
        BaseColor colorWhite = WebColors.getRGBColor("#ffffff");
        BaseColor colorBlue = WebColors.getRGBColor("#056FAA");
        BaseColor grayColor = WebColors.getRGBColor("#425066");


        Font white = new Font(Font.FontFamily.HELVETICA, 15.0f, Font.BOLD, colorWhite);
        FileOutputStream output = new FileOutputStream(EFile);
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

        Chunk dateText = new Chunk("Date", white);
        PdfPCell dateCell = new PdfPCell(new Phrase(dateText));
        dateCell.setFixedHeight(50);
        dateCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        dateCell.setVerticalAlignment(Element.ALIGN_CENTER);
        dateCell.setBackgroundColor(colorBlue);

        Chunk nameText = new Chunk("Name", white);
        PdfPCell nameCell = new PdfPCell(new Phrase(nameText));
        nameCell.setFixedHeight(50);
        nameCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        nameCell.setVerticalAlignment(Element.ALIGN_CENTER);
        nameCell.setBackgroundColor(colorBlue);

        Chunk amountText = new Chunk("Cost", white);
        PdfPCell amountCell = new PdfPCell(new Phrase(amountText));
        amountCell.setFixedHeight(50);
        amountCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        amountCell.setVerticalAlignment(Element.ALIGN_CENTER);
        amountCell.setBackgroundColor(colorBlue);

        Chunk detailsText = new Chunk("Details", white);
        PdfPCell detailsCell = new PdfPCell(new Phrase(detailsText));
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
        table.addCell(dateCell);
        table.addCell(nameCell);
        table.addCell(amountCell);
        table.addCell(detailsCell);
        table.setHeaderRows(1);

        PdfPCell[] cells = table.getRow(0).getCells();

        for (PdfPCell cell : cells) {
            cell.setBackgroundColor(grayColor);
        }
        for (int i = 0; i < addexpenseList.size() ; i++){
            Addincome addexpense = addexpenseList.get(i);

            String id = String.valueOf(i +1);
            String Date = addexpense.getTransactiondate();
            String Name= addexpense.getTransactionname();
            String Cost = addexpense.getTransactioncost();
            String details = addexpense.getTransactiondetails();

            table.addCell( id + ". ");
            table.addCell(Date);
            table.addCell(Name);
            table.addCell(Cost);
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


        String date = dateFormat.format(calendar.getTime());


        document.add(new Paragraph("REPORT ON INCOMES AS AT "+date+"\n\n", g));
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
    public void previewexpenseReport()
    {
        if (hasPermissions(this, PERMISSIONS)) {
            DisplayReport();
        } else {
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
        }
    }

    private void DisplayReport()	    {
        pdfView.fromFile(EFile)
                .pages(0,2,1,3,3,3)
                .enableSwipe(true)
                .swipeHorizontal(false)
                .enableDoubletap(true)
                .defaultPage(0)
                .load();

    }
}