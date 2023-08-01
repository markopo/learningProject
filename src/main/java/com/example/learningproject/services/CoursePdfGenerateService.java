package com.example.learningproject.services;

import com.example.learningproject.model.Course;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
public class CoursePdfGenerateService {

    private static final Logger logger = LoggerFactory.getLogger(CoursePdfGenerateService.class);

    private static final Float SPACING = 8F;

    private static final Float MARGIN = 50F;

    private static final Font HEADING_FONT = new Font(Font.HELVETICA, 20, Font.BOLD);

    public ByteArrayInputStream getPdf(Course course) {
        Document document = new Document(PageSize.A4, MARGIN, MARGIN, MARGIN, MARGIN);
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, out);
            document.open();

            document.add(new Header("Title", course.getTitle()));

            var cc = createParaGraph(("Course Code: " + course.getCourseCode() + "").toUpperCase(), true);
            document.add(cc);

            var image = getImage(course);
            if(image != null) {
                logger.info("adding image: {}", course.getCourseCode());
                document.add(image);
            }

            var dd = createParaGraph("DESCRIPTION: ", true);
            document.add(dd);

            var descriptionParaGraphs = course.getDescription().split(System.lineSeparator());

            for (String d : descriptionParaGraphs) {
                var pd = createParaGraph(d, false);
                document.add(pd);
            }
            document.close();

        } catch (Exception exception) {
            logger.error("CoursePdfGenerateService->getPdf error: {0}", exception.getMessage());
        }

        return new ByteArrayInputStream(out.toByteArray());
    }

    private Paragraph createParaGraph(String text, Boolean isHeading) {
        var p = isHeading ? new Paragraph(text, HEADING_FONT) : new Paragraph(text);
        p.setAlignment(Element.ALIGN_CENTER);
        p.setSpacingAfter(SPACING);
        return p;
    }


    private Image getImage(Course course) throws IOException {
        if(course == null) return null;

        String path;
        Image image = null;

        switch (course.getCourseCode()) {
            case "java-001":
                path = "src/main/resources/java-logo.png";
                break;
            case "csharp-001":
                path = "src/main/resources/c-logo.png";
                break;
            case "javascript-001":
                path = "src/main/resources/js-logo.png";
                break;
            case "sql-001":
                path = "src/main/resources/sql-logo.png";
                break;
            case "html5-001":
                path = "src/main/resources/html5-logo.png";
                break;
            default:
                path = null;
        }


        if(path != null) {
            image = Image.getInstance(Files.readAllBytes(Paths.get(path)));
            image.scaleToFit(100F, 100F);
            image.setAlignment(Element.ALIGN_CENTER);
            image.setSpacingAfter(SPACING);
        }

        return image;
    }
}
