package com.nexar.dao.model.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nexar.dao.model.AbstractEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "nexar_user")
public class NexarUser extends AbstractEntity {
    private String firstName;

    private String lastName;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "password")
    @JsonIgnore
    private String password;

    @Column(name = "nexar_role")
    @Enumerated(EnumType.STRING)
    private NexarRole nexarRole;

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
