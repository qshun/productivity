package com.seriousplay.productivity.demo.model;

import java.util.Date;

public class Brand {
    private String id_;

    private String name_;

    private String description_;

    private Date create_time_;

    private String status_;

    public String getId_() {
        return id_;
    }

    public void setId_(String id_) {
        this.id_ = id_ == null ? null : id_.trim();
    }

    public String getName_() {
        return name_;
    }

    public void setName_(String name_) {
        this.name_ = name_ == null ? null : name_.trim();
    }

    public String getDescription_() {
        return description_;
    }

    public void setDescription_(String description_) {
        this.description_ = description_ == null ? null : description_.trim();
    }

    public Date getCreate_time_() {
        return create_time_;
    }

    public void setCreate_time_(Date create_time_) {
        this.create_time_ = create_time_;
    }

    public String getStatus_() {
        return status_;
    }

    public void setStatus_(String status_) {
        this.status_ = status_ == null ? null : status_.trim();
    }
}