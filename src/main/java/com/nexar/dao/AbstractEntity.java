package com.nexar.dao;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

import java.util.Date;
import java.util.UUID;

@MappedSuperclass
public abstract class AbstractEntity {
    @Id
    @Column(updatable = false, nullable = false)
    private UUID id;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "creation_timestamp", updatable = false)
    private Date creationTimestamp;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "modification_timestamp")
    private Date modificationTimestamp;

    public AbstractEntity() {
    }

    @PrePersist
    public void onCreate() {
        if (this.id == null) {
            this.id = UUID.randomUUID();
        }

        this.creationTimestamp = new Date();
        this.modificationTimestamp = new Date();
    }

    @PreUpdate
    public void onUpdate() {
        this.modificationTimestamp = new Date();
    }

    public UUID getId() {
        return this.id;
    }

    public Date getCreationTimestamp() {
        return this.creationTimestamp;
    }

    public Date getModificationTimestamp() {
        return this.modificationTimestamp;
    }

    public void setId(final UUID id) {
        this.id = id;
    }

    public void setCreationTimestamp(final Date creationTimestamp) {
        this.creationTimestamp = creationTimestamp;
    }

    public void setModificationTimestamp(final Date modificationTimestamp) {
        this.modificationTimestamp = modificationTimestamp;
    }
}
