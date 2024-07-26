package com.application.main.model;

import org.springframework.data.annotation.Id;

import com.fasterxml.jackson.annotation.JsonFormat;

public class PoDTO {

	@Id
	private String id;
//	@Size(min = 10, max = 10, message = "PO number or package number must be 10 digits")
	private String poNumber;
	private String description;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private String poIssueDate;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private String deliveryDate;
	private String poStatus;
	private String poAmount;
	private int noOfInvoices;
	private String deliveryTimelines;
	private String deliveryPlant;
	private String paymentType;
	private String eic;
	private String receiver;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPoNumber() {
		return poNumber;
	}

	public void setPoNumber(String poNumber) {
		this.poNumber = poNumber;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPoIssueDate() {
		return poIssueDate;
	}

	public void setPoIssueDate(String poIssueDate) {
		this.poIssueDate = poIssueDate;
	}

	public String getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(String deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public String getPoStatus() {
		return poStatus;
	}

	public void setPoStatus(String poStatus) {
		this.poStatus = poStatus;
	}

	public String getPoAmount() {
		return poAmount;
	}

	public void setPoAmount(String poAmount) {
		this.poAmount = poAmount;
	}

	public int getNoOfInvoices() {
		return noOfInvoices;
	}

	public void setNoOfInvoices(int noOfInvoices) {
		this.noOfInvoices = noOfInvoices;
	}

	public String getDeliveryTimelines() {
		return deliveryTimelines;
	}

	public void setDeliveryTimelines(String deliveryTimelines) {
		this.deliveryTimelines = deliveryTimelines;
	}

	public String getDeliveryPlant() {
		return deliveryPlant;
	}

	public void setDeliveryPlant(String deliveryPlant) {
		this.deliveryPlant = deliveryPlant;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public String getEic() {
		return eic;
	}

	public void setEic(String eic) {
		this.eic = eic;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public PoDTO(String poNumber, String description, String poIssueDate, String deliveryDate, String poStatus,
			String poAmount, int noOfInvoices, String deliveryTimelines, String deliveryPlant, String paymentType,
			String eic, String receiver) {
		super();
		this.poNumber = poNumber;
		this.description = description;
		this.poIssueDate = poIssueDate;
		this.deliveryDate = deliveryDate;
		this.poStatus = poStatus;
		this.poAmount = poAmount;
		this.noOfInvoices = noOfInvoices;
		this.deliveryTimelines = deliveryTimelines;
		this.deliveryPlant = deliveryPlant;
		this.paymentType = paymentType;
		this.eic = eic;
		this.receiver = receiver;
	}

}
