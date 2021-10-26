package com.surepay.technical.entities;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Data
@Table(name = "transactions")
public class Transactions {

    @Id
    @Column(name = "reference")
    private Long reference;

    @Column(name = "account_number")
    private String accountNumber;

    @Column(name = "start_balance")
    private BigDecimal startBalance;

    @Column(name = "mutation")
    private BigDecimal mutation;

    @Column(name = "description")
    private String description;

    @Column(name = "end_balance")
    private BigDecimal endBalance;

}
