package org.nhsrc.domain.scores;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;
import org.joda.time.LocalDateTime;
import org.springframework.data.domain.Persistable;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "scoring_process_detail")
public class ScoringProcessDetail implements Persistable<Integer> {
    public static final UUID Fixed_UUID = UUID.fromString("6773c069-54d6-40ae-9aca-9bddc2e91d43");

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Integer id;

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    @JsonIgnore
    public boolean isNew() {
        return this.id == null;
    }

    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "uuid", updatable = false, unique = true, nullable = false)
    private UUID uuid;

    @Column(name = "last_scored_until")
    private Date lastScoredUntil;

    public Date getLastScoredUntil() {
        return lastScoredUntil;
    }

    public void setLastScoredUntil(Date lastScoredUntil) {
        this.lastScoredUntil = lastScoredUntil;
    }

    public Date getSafeLastScoredUntilTime() {
        LocalDateTime time = new LocalDateTime(this.lastScoredUntil);
        time.minusMinutes(1);
        return time.toDate();
    }
}
