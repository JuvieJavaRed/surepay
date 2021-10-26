package com.surepay.technical.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "report_transactions")
@Data
public class ReportTransactions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transactionId")
    private Long transactionId;

    @Column(name = "date_created")
    private Date createdDate;

    @Column(name = "description")
    private String description;

    @Column(name = "reason")
    private String reason;

}
