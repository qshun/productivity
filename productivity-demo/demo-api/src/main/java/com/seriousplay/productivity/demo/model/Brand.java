package com.seriousplay.productivity.demo.model;


import org.jboss.resteasy.annotations.jaxrs.FormParam;
import org.jboss.resteasy.annotations.jaxrs.QueryParam;

import java.io.Serializable;
import java.util.Date;

public class Brand implements Serializable {
    private String id_;
    @FormParam
    @QueryParam
    private String name_;
    @FormParam
    private String description_;

    private Date create_time_;

    private String status_;

    private static final long serialVersionUID = 1L;

    public String getId_() {
        return id_;
    }

    public Brand withId_(String id_) {
        this.setId_(id_);
        return this;
    }

    public void setId_(String id_) {
        this.id_ = id_ == null ? null : id_.trim();
    }

    public String getName_() {
        return name_;
    }

    public Brand withName_(String name_) {
        this.setName_(name_);
        return this;
    }

    public void setName_(String name_) {
        this.name_ = name_ == null ? null : name_.trim();
    }

    public String getDescription_() {
        return description_;
    }

    public Brand withDescription_(String description_) {
        this.setDescription_(description_);
        return this;
    }

    public void setDescription_(String description_) {
        this.description_ = description_ == null ? null : description_.trim();
    }

    public Date getCreate_time_() {
        return create_time_;
    }

    public Brand withCreate_time_(Date create_time_) {
        this.setCreate_time_(create_time_);
        return this;
    }

    public void setCreate_time_(Date create_time_) {
        this.create_time_ = create_time_;
    }

    public String getStatus_() {
        return status_;
    }

    public Brand withStatus_(String status_) {
        this.setStatus_(status_);
        return this;
    }

    public void setStatus_(String status_) {
        this.status_ = status_ == null ? null : status_.trim();
    }
}