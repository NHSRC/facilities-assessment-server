package org.nhsrc.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "indicator_definition")
public class IndicatorDefinition extends AbstractEntity {
    @Column(name = "name")
    private String name;

    @Column(name = "numerator")
    private String numerator;

    @Column(name = "denominator")
    private String denominator;

    @Column(name = "formula")
    private String formula;

    public String getName() {
        return name;
    }

    public String getNumerator() {
        return numerator;
    }

    public String getDenominator() {
        return denominator;
    }

    public String getFormula() {
        return formula;
    }
}