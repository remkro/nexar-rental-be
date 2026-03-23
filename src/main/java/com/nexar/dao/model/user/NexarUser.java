package com.nexar.dao.model.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nexar.dao.model.AbstractEntity;
import com.nexar.dao.model.customer.Customer;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "nexar_user")
public class NexarUser extends AbstractEntity {
    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "password")
    @JsonIgnore
    private String password;

    @Column(name = "nexar_role")
    @Enumerated(EnumType.STRING)
    private NexarRole nexarRole;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Customer customer;

    public enum NexarRole {
        REGULAR_CUSTOMER("regular-customer", "Regular Customer", false),
        CORPORATE_CUSTOMER( "corporate-customer", "Corporate Customer", false),
        NEXAR_STAFF("staff", "Nexar Staff", true),
        NEXAR_ADMIN("admin", "Nexar Administrator", true);
        private final String shortName;
        private final String friendlyName;
        private final boolean internal;

        public String getShortName() {
            return this.shortName;
        }

        public String getFriendlyName() {
            return this.friendlyName;
        }

        public boolean isInternal() {
            return this.internal;
        }

        private NexarRole(final String shortName, final String friendlyName, final boolean internal) {
            this.shortName = shortName;
            this.friendlyName = friendlyName;
            this.internal = internal;
        }
    }
}
