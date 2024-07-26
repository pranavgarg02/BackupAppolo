package com.application.main.awsconfig;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.application.main.Repositories.InvoiceRepository;
import com.application.main.Repositories.PoSummaryRepository;
import com.application.main.model.DocDetails;
import com.application.main.model.Invoice;
import com.application.main.model.PoSummary;

@Service
public class AwsService {

	@Value("${bucket_name}")
	private String bucketName;
	@Value("${minio_url}")
	private String minioUrl;
	@Autowired
	private AWSClientConfigService s3client;
	private String token;
	
	@Autowired
	InvoiceRepository invoicerepository;
	
	@Autowired
	PoSummaryRepository porepo;
	public void createBucket(String token) {
		this.token = token;
		try {
			AmazonS3 awsClient = s3client.awsClientConfiguration(token);
			if (!awsClient.doesBucketExistV2(bucketName))
				awsClient.createBucket(bucketName);
		} catch (AmazonS3Exception e) {
			System.err.println(e.getErrorMessage());
		}
	}

	public String uploadFile(MultipartFile file) throws IOException {
		AmazonS3 awsClient = s3client.awsClientConfiguration(token);
		if (file == null)
			return null;
		ObjectMetadata obm = new ObjectMetadata();
		String fileName = file.getOriginalFilename();
		awsClient.putObject(bucketName, fileName, file.getInputStream(), obm);
		String invoiceFileUrl = minioUrl + "/" + bucketName + "/" + fileName;
		return invoiceFileUrl;

	}
public Date StringtoDate(String dateformat) {
	
	
}
	public HashMap<String, Object> uploadMongoFile(
			List<String> invoiceFileUrlList, 
			List<String> supportingDocumentUrls,
			String eic, String roleName, String poNumber, 
			String token, String alternateMobileNumber,
			String alternateEmail, Set<String> remarks, 
			String invoiceAmount, String invoiceDate, 
			String invoiceNumber,String username, 
			String createdBy, String deliveryPlant, 
			DocDetails invoicedetails,List<DocDetails> suppDocNameList, 
			String status, String receievedBy) throws IOException {

		Invoice invoice = new Invoice();
		invoice.setPoNumber(poNumber);
		invoice.setStatus("pending");
		invoice.setReceievedBy(eic);
		invoice.setCreatedBy(username);

		if (roleName != null && roleName.length() > 0) {
			System.out.println(roleName);
			invoice.setRoleName(roleName);
		}
		// Update the "eic" field if the parameter is provided
		if (eic != null && !eic.isEmpty()) {
			invoice.setEic(eic);
		}
		if (deliveryPlant != null && !eic.isEmpty()) {
			invoice.setDeliveryPlant(deliveryPlant);
		}
		List<DocDetails> invoiceobjectaslist = new ArrayList<>();
		if(invoicedetails!=null)  invoiceobjectaslist.add(invoicedetails);
		invoice.setInvoiceFile(invoiceobjectaslist);
		
		invoice.setSupportingDocument(suppDocNameList);
		if (alternateMobileNumber != null && !alternateMobileNumber.isEmpty()) {
			invoice.setAlternateMobileNumber(alternateMobileNumber);
		}
		invoice.setAlternateMobileNumber(alternateMobileNumber);
		invoice.setAlternateEmail(alternateEmail);
		invoice.setRemarks(remarks);
		invoice.setUsername(username);
		invoice.setInvoiceAmount(invoiceAmount);
		if (invoiceDate != null) {
			invoice.setInvoiceDate(invoiceDate);
		}
		invoice.setInvoiceDate(invoiceDate);
		invoice.setInvoiceNumber(invoiceNumber);
		invoice.setCreatedBy(createdBy);
		invoice.setReceievedBy(receievedBy);
		invoice.setCurrentDateTime(LocalDateTime.now().toString());
//        if(isinvoice) invoiceur=
		// invoice.setInvoice(isinvoice);
		invoice = invoicerepository.save(invoice);
		if(porepo.findByPoNumber(poNumber).getInvoiceobject()==null) new ArrayList<>().add(invoiceobjectaslist);
		else porepo.findByPoNumber(poNumber).getInvoiceobject().add(invoice);
		System.out.println("Before saving invoice to database");
		System.out.println("invoiceFileUrlList: " + invoiceFileUrlList);
		System.out.println("supportingDocumentUrls: " + supportingDocumentUrls);
		System.out.println("S3ServiceSaveRepo" + invoice);// saving
		System.out.println("After saving invoice to database");
		System.out.println("Invoice with details :-> \n" + invoice.toString() + " is saved succesfully");
		System.out.println("invoiceFileUrlList: " + invoiceFileUrlList);
		System.out.println("supportingDocumentUrls: " + supportingDocumentUrls);
		HashMap<String, Object> responseData = new HashMap<>();

		responseData.put("alternateNumber", alternateMobileNumber);
		responseData.put("alternativeEmail", alternateEmail);
		responseData.put("Eic", eic);
		responseData.put("roleName", roleName);
//		
		Set<String> remarkslist = new HashSet<>();
		remarkslist.addAll(remarks);
		responseData.put("Remarks", remarkslist);
		responseData.put("InvoiceAmount", invoiceAmount);
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		String invoiceDateStr = formatter.format(invoiceDate);
//		String validityDateStr=formatter.format(validityDate);
//		responseData.put("validityDate",validityDateStr);
		responseData.put("InvoiceDate", invoiceDateStr);
		responseData.put("Username", username);
		responseData.put("createdBy", createdBy);
		responseData.put("receievedBy", receievedBy);
		responseData.put("deliveryPlant", deliveryPlant);
		responseData.put("InvoiceNumber", invoiceNumber);

		return responseData;
	}
	
	public ResponseEntity<String> uploadCompliance(String token, MultipartFile file) {

		try {
			AmazonS3 awsClient = s3client.awsClientConfiguration(token);
			String fileName = file.getOriginalFilename();
			ObjectMetadata objm = new ObjectMetadata();
			awsClient.putObject("compliance", fileName, file.getInputStream(),objm);
			//String url = "minioUrl/compliance/" + fileName;
			String url=minioUrl + "/" + bucketName + "/" + fileName;
			return ResponseEntity.ok(url);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().body("not uploaded");
		}
	}

}
