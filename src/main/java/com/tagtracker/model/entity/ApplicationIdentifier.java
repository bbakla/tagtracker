//package com.tagtracker.model;
//
//import org.hibernate.annotations.NaturalId;
//
//import javax.persistence.Embeddable;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.validation.constraints.NotNull;
//import java.io.Serializable;
//import java.net.URL;
//
//@Embeddable
//public class ApplicationIdentifier implements Serializable {
//
//
//    @NaturalId(mutable = true)
//    @NotNull
//    private URL url;
//
//    @NotNull
//    private String tag;
//
//
//
//    public URL getUrl() {
//        return url;
//    }
//
//    public void setUrl(URL url) {
//        this.url = url;
//    }
//
//    public String getTag() {
//        return tag;
//    }
//
//    public void setTag(String tag) {
//        this.tag = tag;
//    }
//}
