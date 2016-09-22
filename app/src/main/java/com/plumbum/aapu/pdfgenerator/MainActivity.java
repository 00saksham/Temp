package com.plumbum.aapu.pdfgenerator;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.ElementList;
import com.itextpdf.tool.xml.html.Tags;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        File pdfFolder = new File(Environment.getExternalStorageDirectory(), "pdfdemo");
        if (!pdfFolder.exists()) {
            pdfFolder.mkdir();
        }

        String location = Environment.getExternalStorageDirectory() + "/pdfdemo/";

        Date date = new Date();
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(date);

        File myFile = new File(location + timeStamp + ".pdf");

        MainActivity.class.getResourceAsStream("index.html");
        final String HTML = "res/index.html";
        final String CSS = "res/style.css";


        OutputStream output = null;
        try {
            output = new FileOutputStream(myFile);

            //Step 1
            Document document = new Document(PageSize.A4);

            //Step 2
            PdfWriter.getInstance(document, output);

            //Step 3
            document.open();

            //Step 4 Add content
            document.add(new Paragraph("Hello"));
            document.add(new Paragraph("Bi Bi"));
            document.add(new Paragraph(""));

            ElementList elements = FillTemplateHelper.parseHtml(HTML, CSS, Tags.getHtmlTagProcessorFactory());
            for (Element e : elements) {
                document.add(e);
            }

            //Step 5: Close the document
            document.close();

            Toast.makeText(this, "Done " + location,
                    Toast.LENGTH_LONG).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}