package org.nhsrc.web;

import org.nhsrc.domain.Department;
import org.nhsrc.repository.DepartmentRepository;
import org.nhsrc.repository.Repository;
import org.nhsrc.web.contract.DepartmentRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;

@RestController
@RequestMapping("/api/")
public class DepartmentController {
    private DepartmentRepository departmentRepository;

    @Autowired
    public DepartmentController(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @RequestMapping(value = "/departments", method = {RequestMethod.POST, RequestMethod.PUT})
    @Transactional
    @PreAuthorize("hasRole('Checklist_Write')")
    public Department save(@RequestBody DepartmentRequest request) {
        Department department = Repository.findByUuidOrCreate(request.getUuid(), departmentRepository, new Department());
        department.setName(request.getName());
        department.setInactive(request.getInactive());
        return departmentRepository.save(department);
    }

    @RequestMapping(value = "/departments/{id}", method = {RequestMethod.DELETE})
    @Transactional
    @PreAuthorize("hasRole('Checklist_Write')")
    public Department delete(@PathVariable("id") Integer id) {
        return Repository.delete(id, departmentRepository);
    }
}