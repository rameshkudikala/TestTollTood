package com.xiim.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import org.testng.annotations.Test;

public class SendMail {

	@Test
	public static void sendMail() throws Exception, MessagingException, AddressException {
		
		Thread.sleep(1000);

		DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
		Date date = new Date();

		Properties props = System.getProperties();
		
		zipDir("D:/XiiM_Reports", "E:/XiiM_Reports.zip");
		try{
			
			// Setup mail server
	        props.put("mail.smtp.host", "nextemail01.nextsphere.com");
	        props.put("mail.smtp.auth", "false");
	        props.put("mail.smtp.port", "25");
	        props.put("mail.smtp.starttls.enable","false");
	        
	        // Get session
	        Session session = Session.getDefaultInstance(props, null);

	        // Define message
	        MimeMessage message = new MimeMessage(session);

	        // Set the from address
	        message.setFrom(new InternetAddress("sairam.gundepuneni@nextsphere.com"));
	        
	        String[] to = {"ramesh.kudikala@nextsphere.com"};
	        String[] cc = {"Desaiah.dana@nextsphere.com"};
	        String[] bcc = {"ramesh.kudikala@nextsphere.com"};
	        
	        InternetAddress[] toAddress = new InternetAddress[to.length];
	        InternetAddress[] ccAddress = new InternetAddress[cc.length];
	        InternetAddress[] bccAddress = new InternetAddress[bcc.length];
	        
	        // get the to address
	        for( int i=0; i < to.length; i++ ) { 
	            toAddress[i] = new InternetAddress(to[i]);
	        }
	       
	        // Set the to address
	        for( int i=0; i < toAddress.length; i++) { 
	        	message.addRecipient(Message.RecipientType.TO, toAddress[i]);
	        }
	        
	        // get the cc address
	        for( int i=0; i < cc.length; i++ ) { 
	            ccAddress[i] = new InternetAddress(cc[i]);
	        }
	        
	        // Set the cc address
	        for( int i=0; i < ccAddress.length; i++) { 
	        	message.addRecipient(Message.RecipientType.CC, ccAddress[i]);
	        }
	        
	        // get the bcc address
	       for( int i=0; i < bcc.length; i++ ) { 
	            bccAddress[i] = new InternetAddress(bcc[i]);
	        }
	        
	        // Set the bcc address
	      for( int i=0; i < bccAddress.length; i++) { 
	        	message.addRecipient(Message.RecipientType.BCC, bccAddress[i]);
	        }
	        
	        // Set the subject
	        message.setSubject("XiiM Automation test Reports_"+dateFormat.format(date).toString());
	         
	      // Create the message part 
	         BodyPart messageBodyPart = new MimeBodyPart();

	         // Fill the message
	        // messageBodyPart.setText("hi");
	         messageBodyPart.setContent("Hello Team,\n\nPlease find the attached XiiM Automation reports.\n\nThanks & Regards,\nSairam Gundepuneni",  "text/plain");
	         
	         Multipart multipart = new MimeMultipart();
	         multipart.addBodyPart(messageBodyPart);

	         // Part two is attachment
	         messageBodyPart = new MimeBodyPart();
	         String filename = "XiiM_Reports"+"_"+dateFormat.format(date).toString()+".zip";
	         //String filename = System.getProperty("user.dir")+"\\EnrollmentPortalReports.zip";
	         System.out.println(filename);
	         Thread.sleep(10000);
	         DataSource source = new FileDataSource("E:/XiiM_Reports.zip");
	         messageBodyPart.setDataHandler(new DataHandler(source));
	         messageBodyPart.setFileName(filename);
	         multipart.addBodyPart(messageBodyPart);

	         // Put parts in message
	         message.setContent(multipart);

	         // Send message
	         
	        Transport.send(message,message.getAllRecipients());
	        System.out.println("Mail Sent ....SUCCESSFULLY");
			
		}catch(MessagingException e){
			System.out.println("Mail Not Sent.......");
			e.printStackTrace();
		}
	
	}
	
	public static void zipDir(String dirName, String nameZipFile) throws Exception {
        ZipOutputStream zip = null;
        FileOutputStream fW = null;
        fW = new FileOutputStream(nameZipFile);
        zip = new ZipOutputStream(fW);
        addFolderToZip("", dirName, zip);
        zip.close();
        fW.close();
    }
	
	public static void addFolderToZip(String path, String srcFolder, ZipOutputStream zip) throws Exception {
        File folder = new File(srcFolder);
        if (folder.list().length == 0) {
            addFileToZip(path , srcFolder, zip, true);
        }
        else {
            for (String fileName : folder.list()) {
                if (path.equals("")) {
                    addFileToZip(folder.getName(), srcFolder + "/" + fileName, zip, false);
                } 
                else {
                     addFileToZip(path + "/" + folder.getName(), srcFolder + "/" + fileName, zip, false);
                }
            }
        }
    }

	public static void addFileToZip(String path, String srcFile, ZipOutputStream zip, boolean flag) throws Exception {
        File folder = new File(srcFile);
        if (flag) {
            zip.putNextEntry(new ZipEntry(path + "/" +folder.getName() + "/"));
        }
        else {
            if (folder.isDirectory()) {
                addFolderToZip(path, srcFile, zip);
            }
            else {
                byte[] buf = new byte[1024];
                int len;
                FileInputStream in = new FileInputStream(srcFile);
                zip.putNextEntry(new ZipEntry(path + "/" + folder.getName()));
                while ((len = in.read(buf)) > 0) {
                    zip.write(buf, 0, len);
                }
            }
        }
    }
}