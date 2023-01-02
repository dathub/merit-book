package com.example.application.data.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "merit_items")
public class MeritItem extends AbstractEntity {

    private String title;
    private String description;
    private LocalDate eventDate;
    @Lob
    private byte[] image;

}
