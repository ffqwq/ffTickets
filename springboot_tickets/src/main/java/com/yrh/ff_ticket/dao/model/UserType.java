package com.yrh.ff_ticket.dao.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_type")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserType {
    @Id
    @Column(name = "type")
    private String type;
}
