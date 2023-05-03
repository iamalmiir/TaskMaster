package tech.iamalmir.restfulspring.models;


import com.fasterxml.jackson.annotation.JsonIgnore;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;
import java.util.UUID;

import jakarta.persistence.GeneratedValue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("users")
public class User {

    @Id
    @Column("id")
    @GeneratedValue
    private UUID id;

    @Column("name")
    @NotBlank(message = "Name is required.")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "Name can only contain letters")
    private String name;

    @Column("email")
    @NotBlank(message = "Email is required.")
    @Email(regexp = "([a-z])+@([a-z])+\\.com", message = "Email should match the pattern a-z @ a-z .com")
    private String email;

    @Column("password")
    @Min(8)
    @Max(32)
    private String password;

    @Column("provider_id")
    @JsonIgnore
    private String providerId;

    @Column("picture_url")
    @JsonIgnore
    private String providerUrl;

    @Column("created_at")
    @JsonIgnore
    @CreatedDate
    private Instant createdAt;

    @Column("updated_at")
    @JsonIgnore
    @LastModifiedDate
    private Instant updatedAt;

}
