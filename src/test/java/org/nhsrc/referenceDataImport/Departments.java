package org.nhsrc.referenceDataImport;

import org.nhsrc.domain.Department;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Departments implements Serializable {
    private List<Department> departments;

    public Departments() {
        this.departments = new ArrayList<>();
    }

    public List<Department> getDepartments() {
        return departments;
    }

    public void setDepartments(List<Department> departments) {
        this.departments = departments;
    }

    public void addDepartment(Department department) {
        this.departments.add(department);
    }
}
