package com.yrh.ff_ticket.dao.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "passengers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Passengers {

    @Id
    @Column(name = "passenger_id")
    private String passenger_id;

    @Column(name = "passenger_name")
    private String passenger_name;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users users;
}
