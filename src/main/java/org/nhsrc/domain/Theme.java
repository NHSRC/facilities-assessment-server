package org.nhsrc.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

@Entity
@Table(name = "theme")
public class Theme extends AbstractEntity {
    @Column(name = "name", unique = true, nullable = false)
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static List<String> getThemeNames(String fullName) {
        return Arrays.stream(fullName.split(",")).map(String::trim).collect(Collectors.toList());
    }
}
