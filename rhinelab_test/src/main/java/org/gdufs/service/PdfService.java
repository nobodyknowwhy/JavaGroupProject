package org.gdufs.service;

//import com.itextpdf.kernel.font.PdfFont;
//import com.itextpdf.kernel.font.PdfFontFactory;
//import com.itextpdf.kernel.pdf.PdfDocument;
//import com.itextpdf.kernel.pdf.PdfWriter;
//import com.itextpdf.layout.Document;
//import com.itextpdf.layout.element.Image;
//import com.itextpdf.layout.element.Paragraph;
//import com.itextpdf.layout.property.TextAlignment;
import org.springframework.stereotype.Service;
//import com.itextpdf.*;
import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Service
public class PdfService {
//    public byte[] generatePdf(
//            String phone, String projectName, String type, String meaning,
//            int totalTime, Float expenditure, String status, String principal) {
//        try {
//            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//            PdfWriter writer = new PdfWriter(outputStream);
//            PdfDocument pdfDocument = new PdfDocument(writer);
//            Document document = new Document(pdfDocument);
//
//            PdfFont font = PdfFontFactory.createFont("STSongStd-Light", "UniGB-UCS2-H");
//            document.setFont(font);
//
//            Paragraph titleParagraph = new Paragraph("项目细况说明");
//            titleParagraph.setTextAlignment(TextAlignment.CENTER);
//            document.add(titleParagraph);
//            document.add(new Paragraph("项目名称：\t" + projectName));
//            document.add(new Paragraph("项目负责人：\t" + principal));
//            document.add(new Paragraph("联系电话：\t" + phone));
//            document.add(new Paragraph("项目类型：\t" + type));
//            document.add(new Paragraph("项目意义：\t" + meaning));
//            document.add(new Paragraph("项目周期：\t" + totalTime));
//            document.add(new Paragraph("项目经费：\t" + expenditure));
//            document.add(new Paragraph("项目状态：\t" + status));
//            document.close();
//            return outputStream.toByteArray();
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
    public String encodeName(String name) {
        String nameEncode;
        try {
            nameEncode = URLEncoder.encode(name, "UTF-8").replace("\\+", "%20");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            nameEncode = name;
        }
        return nameEncode;

    }
}
