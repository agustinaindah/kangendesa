package com.kangendesa.app.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by agustinaindah on 27 Februari 2019
 */
public class ItemOrder implements Serializable {

    @SerializedName("ID")
    @Expose
    private Integer iD;
    @SerializedName("post_author")
    @Expose
    private String postAuthor;
    @SerializedName("post_date")
    @Expose
    private String postDate;
    @SerializedName("post_date_gmt")
    @Expose
    private String postDateGmt;
    @SerializedName("post_content")
    @Expose
    private String postContent;
    @SerializedName("post_title")
    @Expose
    private String postTitle;
    @SerializedName("post_excerpt")
    @Expose
    private String postExcerpt;
    @SerializedName("post_status")
    @Expose
    private String postStatus;
    @SerializedName("post_name")
    @Expose
    private String postName;
    @SerializedName("guid")
    @Expose
    private String guid;
    @SerializedName("post_type")
    @Expose
    private String postType;
    @SerializedName("m__order_key")
    @Expose
    private String mOrderKey;
    @SerializedName("m__customer_user")
    @Expose
    private String mCustomerUser;
    @SerializedName("m__payment_method")
    @Expose
    private String mPaymentMethod;
    @SerializedName("m__payment_method_title")
    @Expose
    private String mPaymentMethodTitle;
    @SerializedName("m__transaction_id")
    @Expose
    private String mTransactionId;
    @SerializedName("m__customer_ip_address")
    @Expose
    private String mCustomerIpAddress;
    @SerializedName("m__customer_user_agent")
    @Expose
    private String mCustomerUserAgent;
    @SerializedName("m__created_via")
    @Expose
    private String mCreatedVia;
    @SerializedName("m__date_completed")
    @Expose
    private String mDateCompleted;
    @SerializedName("m__completed_date")
    @Expose
    private String mCompletedDate;
    @SerializedName("m__date_paid")
    @Expose
    private String mDatePaid;
    @SerializedName("m__paid_date")
    @Expose
    private String mPaidDate;
    @SerializedName("m__billing_first_name")
    @Expose
    private String mBillingFirstName;
    @SerializedName("m__billing_last_name")
    @Expose
    private String mBillingLastName;
    @SerializedName("m__billing_company")
    @Expose
    private String mBillingCompany;
    @SerializedName("m__billing_address_1")
    @Expose
    private String mBillingAddress1;
    @SerializedName("m__billing_address_2")
    @Expose
    private String mBillingAddress2;
    @SerializedName("m__billing_city")
    @Expose
    private String mBillingCity;
    @SerializedName("m__billing_state")
    @Expose
    private String mBillingState;
    @SerializedName("m__billing_postcode")
    @Expose
    private String mBillingPostcode;
    @SerializedName("m__billing_country")
    @Expose
    private String mBillingCountry;
    @SerializedName("m__billing_email")
    @Expose
    private String mBillingEmail;
    @SerializedName("m__billing_phone")
    @Expose
    private String mBillingPhone;
    @SerializedName("m__order_currency")
    @Expose
    private String mOrderCurrency;
    @SerializedName("m__cart_discount")
    @Expose
    private String mCartDiscount;
    @SerializedName("m__cart_discount_tax")
    @Expose
    private String mCartDiscountTax;
    @SerializedName("m__order_shipping")
    @Expose
    private String mOrderShipping;
    @SerializedName("m__order_shipping_tax")
    @Expose
    private String mOrderShippingTax;
    @SerializedName("m__order_tax")
    @Expose
    private String mOrderTax;
    @SerializedName("m__order_total")
    @Expose
    private String mOrderTotal;
    @SerializedName("m__prices_include_tax")
    @Expose
    private String mPricesIncludeTax;
    @SerializedName("m__billing_address_index")
    @Expose
    private String mBillingAddressIndex;
    @SerializedName("m__shipping_address_index")
    @Expose
    private String mShippingAddressIndex;
    @SerializedName("order_detail")
    @Expose
    private OrderDetail orderDetail;

    public Integer getID() {
        return iD;
    }

    public void setID(Integer iD) {
        this.iD = iD;
    }

    public String getPostAuthor() {
        return postAuthor;
    }

    public void setPostAuthor(String postAuthor) {
        this.postAuthor = postAuthor;
    }

    public String getPostDate() {
        return postDate;
    }

    public void setPostDate(String postDate) {
        this.postDate = postDate;
    }

    public String getPostDateGmt() {
        return postDateGmt;
    }

    public void setPostDateGmt(String postDateGmt) {
        this.postDateGmt = postDateGmt;
    }

    public String getPostContent() {
        return postContent;
    }

    public void setPostContent(String postContent) {
        this.postContent = postContent;
    }

    public String getPostTitle() {
        return postTitle;
    }

    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }

    public String getPostExcerpt() {
        return postExcerpt;
    }

    public void setPostExcerpt(String postExcerpt) {
        this.postExcerpt = postExcerpt;
    }

    public String getPostStatus() {
        return postStatus;
    }

    public void setPostStatus(String postStatus) {
        this.postStatus = postStatus;
    }

    public String getPostName() {
        return postName;
    }

    public void setPostName(String postName) {
        this.postName = postName;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getPostType() {
        return postType;
    }

    public void setPostType(String postType) {
        this.postType = postType;
    }

    public String getMOrderKey() {
        return mOrderKey;
    }

    public void setMOrderKey(String mOrderKey) {
        this.mOrderKey = mOrderKey;
    }

    public String getMCustomerUser() {
        return mCustomerUser;
    }

    public void setMCustomerUser(String mCustomerUser) {
        this.mCustomerUser = mCustomerUser;
    }

    public String getMPaymentMethod() {
        return mPaymentMethod;
    }

    public void setMPaymentMethod(String mPaymentMethod) {
        this.mPaymentMethod = mPaymentMethod;
    }

    public String getMPaymentMethodTitle() {
        return mPaymentMethodTitle;
    }

    public void setMPaymentMethodTitle(String mPaymentMethodTitle) {
        this.mPaymentMethodTitle = mPaymentMethodTitle;
    }

    public String getMTransactionId() {
        return mTransactionId;
    }

    public void setMTransactionId(String mTransactionId) {
        this.mTransactionId = mTransactionId;
    }

    public String getMCustomerIpAddress() {
        return mCustomerIpAddress;
    }

    public void setMCustomerIpAddress(String mCustomerIpAddress) {
        this.mCustomerIpAddress = mCustomerIpAddress;
    }

    public String getMCustomerUserAgent() {
        return mCustomerUserAgent;
    }

    public void setMCustomerUserAgent(String mCustomerUserAgent) {
        this.mCustomerUserAgent = mCustomerUserAgent;
    }

    public String getMCreatedVia() {
        return mCreatedVia;
    }

    public void setMCreatedVia(String mCreatedVia) {
        this.mCreatedVia = mCreatedVia;
    }

    public String getMDateCompleted() {
        return mDateCompleted;
    }

    public void setMDateCompleted(String mDateCompleted) {
        this.mDateCompleted = mDateCompleted;
    }

    public String getMCompletedDate() {
        return mCompletedDate;
    }

    public void setMCompletedDate(String mCompletedDate) {
        this.mCompletedDate = mCompletedDate;
    }

    public String getMDatePaid() {
        return mDatePaid;
    }

    public void setMDatePaid(String mDatePaid) {
        this.mDatePaid = mDatePaid;
    }

    public String getMPaidDate() {
        return mPaidDate;
    }

    public void setMPaidDate(String mPaidDate) {
        this.mPaidDate = mPaidDate;
    }

    public String getMBillingFirstName() {
        return mBillingFirstName;
    }

    public void setMBillingFirstName(String mBillingFirstName) {
        this.mBillingFirstName = mBillingFirstName;
    }

    public String getMBillingLastName() {
        return mBillingLastName;
    }

    public void setMBillingLastName(String mBillingLastName) {
        this.mBillingLastName = mBillingLastName;
    }

    public String getMBillingCompany() {
        return mBillingCompany;
    }

    public void setMBillingCompany(String mBillingCompany) {
        this.mBillingCompany = mBillingCompany;
    }

    public String getMBillingAddress1() {
        return mBillingAddress1;
    }

    public void setMBillingAddress1(String mBillingAddress1) {
        this.mBillingAddress1 = mBillingAddress1;
    }

    public String getMBillingAddress2() {
        return mBillingAddress2;
    }

    public void setMBillingAddress2(String mBillingAddress2) {
        this.mBillingAddress2 = mBillingAddress2;
    }

    public String getMBillingCity() {
        return mBillingCity;
    }

    public void setMBillingCity(String mBillingCity) {
        this.mBillingCity = mBillingCity;
    }

    public String getMBillingState() {
        return mBillingState;
    }

    public void setMBillingState(String mBillingState) {
        this.mBillingState = mBillingState;
    }

    public String getMBillingPostcode() {
        return mBillingPostcode;
    }

    public void setMBillingPostcode(String mBillingPostcode) {
        this.mBillingPostcode = mBillingPostcode;
    }

    public String getMBillingCountry() {
        return mBillingCountry;
    }

    public void setMBillingCountry(String mBillingCountry) {
        this.mBillingCountry = mBillingCountry;
    }

    public String getMBillingEmail() {
        return mBillingEmail;
    }

    public void setMBillingEmail(String mBillingEmail) {
        this.mBillingEmail = mBillingEmail;
    }

    public String getMBillingPhone() {
        return mBillingPhone;
    }

    public void setMBillingPhone(String mBillingPhone) {
        this.mBillingPhone = mBillingPhone;
    }

    public String getMOrderCurrency() {
        return mOrderCurrency;
    }

    public void setMOrderCurrency(String mOrderCurrency) {
        this.mOrderCurrency = mOrderCurrency;
    }

    public String getMCartDiscount() {
        return mCartDiscount;
    }

    public void setMCartDiscount(String mCartDiscount) {
        this.mCartDiscount = mCartDiscount;
    }

    public String getMCartDiscountTax() {
        return mCartDiscountTax;
    }

    public void setMCartDiscountTax(String mCartDiscountTax) {
        this.mCartDiscountTax = mCartDiscountTax;
    }

    public String getMOrderShipping() {
        return mOrderShipping;
    }

    public void setMOrderShipping(String mOrderShipping) {
        this.mOrderShipping = mOrderShipping;
    }

    public String getMOrderShippingTax() {
        return mOrderShippingTax;
    }

    public void setMOrderShippingTax(String mOrderShippingTax) {
        this.mOrderShippingTax = mOrderShippingTax;
    }

    public String getMOrderTax() {
        return mOrderTax;
    }

    public void setMOrderTax(String mOrderTax) {
        this.mOrderTax = mOrderTax;
    }

    public String getMOrderTotal() {
        return mOrderTotal;
    }

    public void setMOrderTotal(String mOrderTotal) {
        this.mOrderTotal = mOrderTotal;
    }

    public String getMPricesIncludeTax() {
        return mPricesIncludeTax;
    }

    public void setMPricesIncludeTax(String mPricesIncludeTax) {
        this.mPricesIncludeTax = mPricesIncludeTax;
    }

    public String getMBillingAddressIndex() {
        return mBillingAddressIndex;
    }

    public void setMBillingAddressIndex(String mBillingAddressIndex) {
        this.mBillingAddressIndex = mBillingAddressIndex;
    }

    public String getMShippingAddressIndex() {
        return mShippingAddressIndex;
    }

    public void setMShippingAddressIndex(String mShippingAddressIndex) {
        this.mShippingAddressIndex = mShippingAddressIndex;
    }

    public OrderDetail getOrderDetail() {
        return orderDetail;
    }

    public void setOrderDetail(OrderDetail orderDetail) {
        this.orderDetail = orderDetail;
    }


}
