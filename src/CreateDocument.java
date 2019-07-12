import java.io.File;
import java.io.FileOutputStream;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

public class CreateDocument {

   public static void main(String[] args)throws Exception  {
   
      //Blank Document
      XWPFDocument document = new XWPFDocument(); 
		
      //Write the Document in file system
      FileOutputStream out = new FileOutputStream( new File("C:\\Users\\grant\\OneDrive\\Documents\\Java\\createdocument.docx"));
      
      // Create paragraph
      XWPFParagraph paragraph = document.createParagraph();
      XWPFRun run = paragraph.createRun();
      run.setText("Hello world");
      
      document.write(out);
      out.close();
      document.close();
      System.out.println("createdocument.docx written successully");
   }
}