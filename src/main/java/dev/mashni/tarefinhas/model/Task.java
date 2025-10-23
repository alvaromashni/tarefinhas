package dev.mashni.tarefinhas.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long taskId;

    @NotBlank(message = "O título não pode ser em branco")
    @NotNull(message = "O título não pode ser nulo")
    @Column(name = "title")
    private String taskTitle;

    @Column(name = "description")
    private String taskDescription;

}
