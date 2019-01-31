package org.nhsrc.web;

import org.nhsrc.domain.Department;
import org.nhsrc.repository.DepartmentRepository;
import org.nhsrc.repository.Repository;
import org.nhsrc.web.contract.DepartmentRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
    public Department save(@RequestBody DepartmentRequest request) {
        Department department = Repository.findByUuidOrCreate(request.getUuid(), departmentRepository, new Department());
        department.setName(request.getName());
        department.setInactive(request.getInactive());
        return departmentRepository.save(department);
    }
}