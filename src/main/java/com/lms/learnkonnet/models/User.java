package com.lms.learnkonnet.models;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.lms.learnkonnet.models.enums.Role;
import com.lms.learnkonnet.models.relations.UserNotification;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "user")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "slug", nullable = false)
    private String slug;

    @Column(name = "email", nullable = false, unique = true)
//    @Email(message = "Email must be valid")
    private String email;

    @Column(name = "given_name", nullable = false)
//    @Pattern(regexp = "^[A-Za-z\\s]+$", message = "Invalid name")
    private String givenName;

    @Column(name = "family_name", nullable = false)
//    @Pattern(regexp = "^[A-Za-z\\s]+$", message = "Invalid name")
    private String familyName;

    @Column(name = "full_name", nullable = false)
//    @Pattern(regexp = "^[A-Za-z\\s]+$", message = "Invalid name")
    private String fullName;


    @Column(name = "code", nullable = false, unique = true)
    private String code;

    @Column(name = "birth", nullable = true)
    private Timestamp birth = null;

    @Column(name = "avatar", nullable = true)
    private String avatar;

    @Column(name = "is_has_password", nullable = false)
    private Boolean isHasPassword;

    @Column(name = "hash_password", nullable = true)
    private String hashPassword;

    @Column(name = "salt", nullable = true, unique = true)
    private String salt;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted = false;

    @JsonManagedReference
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "created_by")
    private User createdBy;

    @JsonManagedReference
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "updated_by")
    private User updatedBy;

    @CreationTimestamp
    @Column(name = "created_at")
    private Timestamp createdAt;

    @UpdateTimestamp
    @Column(name = "modified_at")
    private Timestamp modifiedAt;

    @Column(name = "is_actived", nullable = false)
    private Boolean isActived = false;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "user")
    private List<Member> members;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "user")
    private List<Course> courses;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "user")
    private List<UserNotification> notifications;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "user")
    private List<RefreshToken> refreshTokens;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Role userRole = this.role;
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(userRole.toString());
        return Collections.singletonList(authority);
    }

    @Override
    public String getPassword() {
        return this.hashPassword;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.isActived;
    }

}
