package ru.petapp.taskmenagementsystem.taskmenagment.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "books")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(name = "default_gen", sequenceName = "books_seq", allocationSize = 1)
//@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "id")
//@JsonIdentityInfo(generator = ObjectIdGenerators.UUIDGenerator.class, property = "@json_id")
public class Task
      extends GenericModel {
    
    @Column(name = "title", nullable = false)
    private String taskTitle;

    @Column(name = "description")
    private String description;

    @Column(name = "status", nullable = false)
    @Enumerated
    private Status status;

    @Column(name = "priority", nullable = false)
    @Enumerated
    private Priority priority;

    @Column(name = "author", nullable = false)
    private String author;
    
    @Column(name = "performer", nullable = false)
    private String performer;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "FK_RENT_BOOK_INFO_USER"))
    private User user;
}
