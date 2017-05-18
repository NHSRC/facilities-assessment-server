package org.nhsrc.referenceDataImport;

import org.nhsrc.domain.AreaOfConcern;

import java.io.Serializable;
import java.util.*;

public class AreasOfConcern implements Serializable {
    private Set<AreaOfConcern> areasOfConcern;

    public AreasOfConcern() {
        this.areasOfConcern = new HashSet<>();
    }

    public Set<AreaOfConcern> getAreasOfConcern() {
        return areasOfConcern;
    }

    public void setAreasOfConcern(Set<AreaOfConcern> areasOfConcern) {
        this.areasOfConcern = areasOfConcern;
    }

    public void addAreaOfConcern(AreaOfConcern areaOfConcern) {
        this.areasOfConcern.add(areaOfConcern);
    }

    public boolean isEmpty() {
        return this.areasOfConcern.isEmpty();
    }

    @Override
    public String toString() {
        return "AreasOfConcern{" +
                "areasOfConcern=" + areasOfConcern +
                '}';
    }

    public AreaOfConcern findByRef(String ref) {
        return this.areasOfConcern.stream().filter(areaOfConcern -> areaOfConcern.getReference().equals(ref)).findFirst().orElse(null);
    }
}
