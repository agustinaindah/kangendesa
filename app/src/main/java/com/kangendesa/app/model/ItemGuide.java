package com.kangendesa.app.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by agustinaindah on 22 Januari 2019
 */
public class ItemGuide implements Serializable {

    @SerializedName("ID")
    @Expose
    private String iD;
    @SerializedName("user_login")
    @Expose
    private String userLogin;
    @SerializedName("user_pass")
    @Expose
    private String userPass;
    @SerializedName("user_nicename")
    @Expose
    private String userNicename;
    @SerializedName("user_email")
    @Expose
    private String userEmail;
    @SerializedName("user_url")
    @Expose
    private String userUrl;
    @SerializedName("user_registered")
    @Expose
    private String userRegistered;
    @SerializedName("user_activation_key")
    @Expose
    private String userActivationKey;
    @SerializedName("user_status")
    @Expose
    private String userStatus;
    @SerializedName("display_name")
    @Expose
    private String displayName;
    @SerializedName("m_nickname")
    @Expose
    private String mNickname;
    @SerializedName("m_first_name")
    @Expose
    private String mFirstName;
    @SerializedName("m_last_name")
    @Expose
    private String mLastName;
    @SerializedName("m_profile_image_id_attachment")
    @Expose
    private String mProfileImageIdAttachment;
    @SerializedName("m_profile_image_url")
    @Expose
    private String mProfileImageUrl;
    @SerializedName("m_kd_tgl_lahir_dmy")
    @Expose
    private String mKdTglLahirDmy;
    @SerializedName("m_kd_jk")
    @Expose
    private String mKdJk;
    @SerializedName("m_kd_bahasa")
    @Expose
    private String mKdBahasa;
    @SerializedName("m_kd_video_link")
    @Expose
    private String mKdVideoLink;
    @SerializedName("m_kd_alamat")
    @Expose
    private String mKdAlamat;
    @SerializedName("m_kd_domisili")
    @Expose
    private String mKdDomisili;
    @SerializedName("m_kd_tentang_diri")
    @Expose
    private String mKdTentangDiri;
    @SerializedName("m_kd_hobi")
    @Expose
    private String mKdHobi;
    @SerializedName("m_user_oauth_token")
    @Expose
    private String mUserOauthToken;

    public String getID() {
        return iD;
    }

    public void setID(String iD) {
        this.iD = iD;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public String getUserPass() {
        return userPass;
    }

    public void setUserPass(String userPass) {
        this.userPass = userPass;
    }

    public String getUserNicename() {
        return userNicename;
    }

    public void setUserNicename(String userNicename) {
        this.userNicename = userNicename;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserUrl() {
        return userUrl;
    }

    public void setUserUrl(String userUrl) {
        this.userUrl = userUrl;
    }

    public String getUserRegistered() {
        return userRegistered;
    }

    public void setUserRegistered(String userRegistered) {
        this.userRegistered = userRegistered;
    }

    public String getUserActivationKey() {
        return userActivationKey;
    }

    public void setUserActivationKey(String userActivationKey) {
        this.userActivationKey = userActivationKey;
    }

    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getMNickname() {
        return mNickname;
    }

    public void setMNickname(String mNickname) {
        this.mNickname = mNickname;
    }

    public String getMFirstName() {
        return mFirstName;
    }

    public void setMFirstName(String mFirstName) {
        this.mFirstName = mFirstName;
    }

    public String getMLastName() {
        return mLastName;
    }

    public void setMLastName(String mLastName) {
        this.mLastName = mLastName;
    }

    public String getMProfileImageIdAttachment() {
        return mProfileImageIdAttachment;
    }

    public void setMProfileImageIdAttachment(String mProfileImageIdAttachment) {
        this.mProfileImageIdAttachment = mProfileImageIdAttachment;
    }

    public String getMProfileImageUrl() {
        return mProfileImageUrl;
    }

    public void setMProfileImageUrl(String mProfileImageUrl) {
        this.mProfileImageUrl = mProfileImageUrl;
    }

    public String getMKdTglLahirDmy() {
        return mKdTglLahirDmy;
    }

    public void setMKdTglLahirDmy(String mKdTglLahirDmy) {
        this.mKdTglLahirDmy = mKdTglLahirDmy;
    }

    public String getMKdJk() {
        return mKdJk;
    }

    public void setMKdJk(String mKdJk) {
        this.mKdJk = mKdJk;
    }

    public String getMKdBahasa() {
        return mKdBahasa;
    }

    public void setMKdBahasa(String mKdBahasa) {
        this.mKdBahasa = mKdBahasa;
    }

    public String getMKdVideoLink() {
        return mKdVideoLink;
    }

    public void setMKdVideoLink(String mKdVideoLink) {
        this.mKdVideoLink = mKdVideoLink;
    }

    public String getMKdAlamat() {
        return mKdAlamat;
    }

    public void setMKdAlamat(String mKdAlamat) {
        this.mKdAlamat = mKdAlamat;
    }

    public String getMKdDomisili() {
        return mKdDomisili;
    }

    public void setMKdDomisili(String mKdDomisili) {
        this.mKdDomisili = mKdDomisili;
    }

    public String getMKdTentangDiri() {
        return mKdTentangDiri;
    }

    public void setMKdTentangDiri(String mKdTentangDiri) {
        this.mKdTentangDiri = mKdTentangDiri;
    }

    public String getMKdHobi() {
        return mKdHobi;
    }

    public void setMKdHobi(String mKdHobi) {
        this.mKdHobi = mKdHobi;
    }

    public String getMUserOauthToken() {
        return mUserOauthToken;
    }

    public void setMUserOauthToken(String mUserOauthToken) {
        this.mUserOauthToken = mUserOauthToken;
    }
}
