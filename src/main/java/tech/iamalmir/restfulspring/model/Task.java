package tech.iamalmir.restfulspring.model;


import com.fasterxml.jackson.annotation.JsonIgnore;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;
import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("tasks")
public class Task {

    @Id
    @Column("id")
    private UUID id;

    @JsonIgnore
    @Column("slug")
    private String slug;

    @Column("title")
    @NotBlank(message = "Title is required")
    private String title;

    @Column("task_description")
    private String taskDescription;

    @Column("is_completed")
    private boolean completed;

    @JsonIgnore
    @CreatedDate
    @Column("created_at")
    private Instant createdAt;

    @JsonIgnore
    @LastModifiedDate
    @Column("updated_at")
    private Instant updatedAt;

}
