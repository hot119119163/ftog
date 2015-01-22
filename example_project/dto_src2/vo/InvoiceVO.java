package vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class InvoiceVO implements Serializable{
	private final static long serialVersionUID = 2;
	public long invoiceId;
    
	public Date paymentDue;
	public float VAT;
	public ArrayList<WorkTimeVO> workTimes;
	
	public Date invoiceDate;
	public String yourReference;
	public String yourOrderNumber;
	public String deliveryConditions;
	public String deliveryMethod;
	public Date deliveryDate;
	
	public String clientNo;//temporary
	
	public String ourReference;
	public String paymentConditions;
	public float delayInterest;
	
	public float totalNet;
	public float totalWithoutVAT;
	public float VATCrowns;
	public float toPay;
	
	public float itemsSum0;
	public float itemsSum1;
	public float itemsSum2;
	public float itemsSum3;
	
	public Date paidDate;
}
