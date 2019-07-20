package com.kangendesa.app.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by agustinaindah on 21 Januari 2019
 */
public class ItemTour implements Serializable{

    @SerializedName("ID")
    @Expose
    private String iD;
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
    @SerializedName("post_name")
    @Expose
    private String postName;
    @SerializedName("guid")
    @Expose
    private String guid;
    @SerializedName("post_type")
    @Expose
    private String postType;
    @SerializedName("m_basic_destinasi")
    @Expose
    private String mBasicDestinasi;
    @SerializedName("m_basic_berhenti")
    @Expose
    private String mBasicBerhenti;
    @SerializedName("m_basic_aktivitas")
    @Expose
    private String mBasicAktivitas;
    @SerializedName("m_basic_trans")
    @Expose
    private MBasicTrans mBasicTrans;
    @SerializedName("m_basic_transport_img")
    @Expose
    private MBasicTransportImg mBasicTransportImg;
    @SerializedName("m_detail_meeting")
    @Expose
    private String mDetailMeeting;
    @SerializedName("m__regular_price")
    @Expose
    private String mRegularPrice;
    @SerializedName("m__price")
    @Expose
    private String mPrice;
    @SerializedName("m_price_choose")
    @Expose
    private MPriceChoose mPriceChoose;
    @SerializedName("m__sku")
    @Expose
    private String mSku;
    @SerializedName("m_max_travel")
    @Expose
    private String mMaxTravel;
    @SerializedName("m__sale_price")
    @Expose
    private String mSalePrice;
    @SerializedName("m_detail_meeting_desc")
    @Expose
    private String mDetailMeetingDesc;
    @SerializedName("m_map_google_geometry_lat")
    @Expose
    private String mMapGoogleGeometryLat;
    @SerializedName("m_map_google_geometry_lng")
    @Expose
    private String mMapGoogleGeometryLng;
    @SerializedName("m_overview_photos_featured_img")
    @Expose
    private List<String> mOverviewPhotosFeaturedImg = null;
    @SerializedName("m_overview_photos_img")
    @Expose
    private List<String> mOverviewPhotosImg = new ArrayList<>();
    @SerializedName("m_detail_schedule")
    @Expose
    private List<MDetailSchedule> mDetailSchedule = new ArrayList<>();
    @SerializedName("m_detail_faq")
    @Expose
    private List<MDetailFaq> mDetailFaq = new ArrayList<>();
    @SerializedName("m_conditions_img")
    @Expose
    private List<MConditionsImg> mConditionsImg = new ArrayList<>();
    @SerializedName("m_conditions_day")
    @Expose
    private List<MConditionsDay> mConditionsDay = new ArrayList<>();
    @SerializedName("taxonomy")
    @Expose
    private List<Taxonomy> taxonomy = new ArrayList<>();

    public String getID() {
        return iD;
    }

    public void setID(String iD) {
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

    public String getMBasicDestinasi() {
        return mBasicDestinasi;
    }

    public void setMBasicDestinasi(String mBasicDestinasi) {
        this.mBasicDestinasi = mBasicDestinasi;
    }

    public String getMBasicBerhenti() {
        return mBasicBerhenti;
    }

    public void setMBasicBerhenti(String mBasicBerhenti) {
        this.mBasicBerhenti = mBasicBerhenti;
    }

    public String getMBasicAktivitas() {
        return mBasicAktivitas;
    }

    public void setMBasicAktivitas(String mBasicAktivitas) {
        this.mBasicAktivitas = mBasicAktivitas;
    }

    public MBasicTrans getMBasicTrans() {
        return mBasicTrans;
    }

    public void setMBasicTrans(MBasicTrans mBasicTrans) {
        this.mBasicTrans = mBasicTrans;
    }

    public MBasicTransportImg getMBasicTransportImg() {
        return mBasicTransportImg;
    }

    public void setMBasicTransportImg(MBasicTransportImg mBasicTransportImg) {
        this.mBasicTransportImg = mBasicTransportImg;
    }

    public String getMDetailMeeting() {
        return mDetailMeeting;
    }

    public void setMDetailMeeting(String mDetailMeeting) {
        this.mDetailMeeting = mDetailMeeting;
    }

    public String getMRegularPrice() {
        return mRegularPrice;
    }

    public void setMRegularPrice(String mRegularPrice) {
        this.mRegularPrice = mRegularPrice;
    }

    public String getMPrice() {
        return mPrice;
    }

    public void setMPrice(String mPrice) {
        this.mPrice = mPrice;
    }

    public MPriceChoose getMPriceChoose() {
        return mPriceChoose;
    }

    public void setMPriceChoose(MPriceChoose mPriceChoose) {
        this.mPriceChoose = mPriceChoose;
    }

    public String getMSku() {
        return mSku;
    }

    public void setMSku(String mSku) {
        this.mSku = mSku;
    }

    public String getMMaxTravel() {
        return mMaxTravel;
    }

    public void setMMaxTravel(String mMaxTravel) {
        this.mMaxTravel = mMaxTravel;
    }

    public String getMSalePrice() {
        return mSalePrice;
    }

    public void setMSalePrice(String mSalePrice) {
        this.mSalePrice = mSalePrice;
    }

    public String getMDetailMeetingDesc() {
        return mDetailMeetingDesc;
    }

    public void setMDetailMeetingDesc(String mDetailMeetingDesc) {
        this.mDetailMeetingDesc = mDetailMeetingDesc;
    }

    public String getMMapGoogleGeometryLat() {
        return mMapGoogleGeometryLat;
    }

    public void setMMapGoogleGeometryLat(String mMapGoogleGeometryLat) {
        this.mMapGoogleGeometryLat = mMapGoogleGeometryLat;
    }

    public String getMMapGoogleGeometryLng() {
        return mMapGoogleGeometryLng;
    }

    public void setMMapGoogleGeometryLng(String mMapGoogleGeometryLng) {
        this.mMapGoogleGeometryLng = mMapGoogleGeometryLng;
    }

    public List<String> getMOverviewPhotosFeaturedImg() {
        return mOverviewPhotosFeaturedImg;
    }

    public void setMOverviewPhotosFeaturedImg(List<String> mOverviewPhotosFeaturedImg) {
        this.mOverviewPhotosFeaturedImg = mOverviewPhotosFeaturedImg;
    }

    public List<String> getMOverviewPhotosImg() {
        return mOverviewPhotosImg;
    }

    public void setMOverviewPhotosImg(List<String> mOverviewPhotosImg) {
        this.mOverviewPhotosImg = mOverviewPhotosImg;
    }

    public List<MDetailSchedule> getMDetailSchedule() {
        return mDetailSchedule;
    }

    public void setMDetailSchedule(List<MDetailSchedule> mDetailSchedule) {
        this.mDetailSchedule = mDetailSchedule;
    }

    public List<MDetailFaq> getMDetailFaq() {
        return mDetailFaq;
    }

    public void setMDetailFaq(List<MDetailFaq> mDetailFaq) {
        this.mDetailFaq = mDetailFaq;
    }

    public List<MConditionsImg> getMConditionsImg() {
        return mConditionsImg;
    }

    public void setMConditionsImg(List<MConditionsImg> mConditionsImg) {
        this.mConditionsImg = mConditionsImg;
    }

    public List<MConditionsDay> getMConditionsDay() {
        return mConditionsDay;
    }

    public void setMConditionsDay(List<MConditionsDay> mConditionsDay) {
        this.mConditionsDay = mConditionsDay;
    }

    public List<Taxonomy> getTaxonomy() {
        return taxonomy;
    }

    public void setTaxonomy(List<Taxonomy> taxonomy) {
        this.taxonomy = taxonomy;
    }

}
