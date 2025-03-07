package com.yrh.ff_ticket.dao.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PairFlightsAndSchedules {
    private FlightsAndSchedules first;
    private FlightsAndSchedules second;
}
