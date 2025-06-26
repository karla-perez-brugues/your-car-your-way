package com.p13.ycyw.model;

import com.p13.ycyw.enums.UserType;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "USERS", uniqueConstraints = {@UniqueConstraint(columnNames = "email")})
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "user_type", discriminatorType = DiscriminatorType.STRING)
public abstract class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 50)
    @Email
    private String email;

    @NotNull
    @Size(max = 20)
    @Column(name = "last_name")
    private String lastName;

    @NotNull
    @Size(max = 20)
    @Column(name = "first_name")
    private String firstName;

    @NotNull
    @Size(max = 120)
    private String password;

    @NotNull
    private Boolean admin;

    @OneToMany(mappedBy = "sender")
    private List<Message> messages;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Override
    public String getPassword() {
        return this.password;
    }

    // TODO: move to service ?
    public UserType getUserType() {
        return admin ? UserType.ADMIN : UserType.CUSTOMER;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
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
        return true;
    }
}
