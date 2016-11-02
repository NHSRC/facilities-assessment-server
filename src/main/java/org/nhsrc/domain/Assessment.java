package org.nhsrc.domain;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "assessment")
public class Assessment extends AbstractTransactionalEntity {
    @OneToOne
    @JoinColumn(name = "facility_id")
    @NotNull
    private Facility facility;

    @OneToOne
    @JoinColumn(name = "checklist_id")
    @NotNull
    private Checklist checklist;

    @Column(name = "start_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private java.util.Date startDate;

    @Column(name = "end_date", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private java.util.Date endDate;


}
