package com.kangendesa.app.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by agustinaindah on 18 Februari 2019
 */
public class ItemPaymentHistory implements Serializable {

    @SerializedName("konfirmasi_id")
    @Expose
    private String konfirmasiId;
    @SerializedName("konfirmasi_order_no")
    @Expose
    private String konfirmasiOrderNo;
    @SerializedName("konfirmasi_email")
    @Expose
    private String konfirmasiEmail;
    @SerializedName("konfirmasi_bank_tujuan")
    @Expose
    private String konfirmasiBankTujuan;
    @SerializedName("konfirmasi_bank_pengirim")
    @Expose
    private String konfirmasiBankPengirim;
    @SerializedName("konfirmasi_rekening_name")
    @Expose
    private String konfirmasiRekeningName;
    @SerializedName("konfirmasi_transfer_method")
    @Expose
    private String konfirmasiTransferMethod;
    @SerializedName("konfirmasi_nominal")
    @Expose
    private String konfirmasiNominal;
    @SerializedName("konfirmasi_status")
    @Expose
    private String konfirmasiStatus;
    @SerializedName("konfirmasi_tanggal")
    @Expose
    private String konfirmasiTanggal;
    @SerializedName("konfirmasi_tanggal_now")
    @Expose
    private String konfirmasiTanggalNow;
    @SerializedName("konfirmasi_images")
    @Expose
    private String konfirmasiImages;
    @SerializedName("ID")
    @Expose
    private String iD;

    public String getKonfirmasiId() {
        return konfirmasiId;
    }

    public void setKonfirmasiId(String konfirmasiId) {
        this.konfirmasiId = konfirmasiId;
    }

    public String getKonfirmasiOrderNo() {
        return konfirmasiOrderNo;
    }

    public void setKonfirmasiOrderNo(String konfirmasiOrderNo) {
        this.konfirmasiOrderNo = konfirmasiOrderNo;
    }

    public String getKonfirmasiEmail() {
        return konfirmasiEmail;
    }

    public void setKonfirmasiEmail(String konfirmasiEmail) {
        this.konfirmasiEmail = konfirmasiEmail;
    }

    public String getKonfirmasiBankTujuan() {
        return konfirmasiBankTujuan;
    }

    public void setKonfirmasiBankTujuan(String konfirmasiBankTujuan) {
        this.konfirmasiBankTujuan = konfirmasiBankTujuan;
    }

    public String getKonfirmasiBankPengirim() {
        return konfirmasiBankPengirim;
    }

    public void setKonfirmasiBankPengirim(String konfirmasiBankPengirim) {
        this.konfirmasiBankPengirim = konfirmasiBankPengirim;
    }

    public String getKonfirmasiRekeningName() {
        return konfirmasiRekeningName;
    }

    public void setKonfirmasiRekeningName(String konfirmasiRekeningName) {
        this.konfirmasiRekeningName = konfirmasiRekeningName;
    }

    public String getKonfirmasiTransferMethod() {
        return konfirmasiTransferMethod;
    }

    public void setKonfirmasiTransferMethod(String konfirmasiTransferMethod) {
        this.konfirmasiTransferMethod = konfirmasiTransferMethod;
    }

    public String getKonfirmasiNominal() {
        return konfirmasiNominal;
    }

    public void setKonfirmasiNominal(String konfirmasiNominal) {
        this.konfirmasiNominal = konfirmasiNominal;
    }

    public String getKonfirmasiStatus() {
        return konfirmasiStatus;
    }

    public void setKonfirmasiStatus(String konfirmasiStatus) {
        this.konfirmasiStatus = konfirmasiStatus;
    }

    public String getKonfirmasiTanggal() {
        return konfirmasiTanggal;
    }

    public void setKonfirmasiTanggal(String konfirmasiTanggal) {
        this.konfirmasiTanggal = konfirmasiTanggal;
    }

    public String getKonfirmasiTanggalNow() {
        return konfirmasiTanggalNow;
    }

    public void setKonfirmasiTanggalNow(String konfirmasiTanggalNow) {
        this.konfirmasiTanggalNow = konfirmasiTanggalNow;
    }

    public String getKonfirmasiImages() {
        return konfirmasiImages;
    }

    public void setKonfirmasiImages(String konfirmasiImages) {
        this.konfirmasiImages = konfirmasiImages;
    }

    public String getID() {
        return iD;
    }

    public void setID(String iD) {
        this.iD = iD;
    }
}
