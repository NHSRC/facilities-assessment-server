package org.nhsrc.web;

import org.nhsrc.domain.security.Privilege;
import org.nhsrc.domain.security.Role;
import org.nhsrc.repository.Repository;
import org.nhsrc.repository.security.PrivilegeRepository;
import org.nhsrc.repository.security.RoleRepository;
import org.nhsrc.web.contract.RoleRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.transaction.Transactional;

@Controller
@RequestMapping("/api/")
public class RoleController {
    private RoleRepository roleRepository;
    private PrivilegeRepository privilegeRepository;

    @Autowired
    public RoleController(RoleRepository roleRepository, PrivilegeRepository privilegeRepository) {
        this.roleRepository = roleRepository;
        this.privilegeRepository = privilegeRepository;
    }

    @RequestMapping(value = "/roles", method = {RequestMethod.POST, RequestMethod.PUT})
    @Transactional
    public ResponseEntity save(@RequestBody RoleRequest request) {
        Role role = Repository.findByIdOrCreate(request.getId(), roleRepository, new Role());
        role.setName(request.getName());
        Repository.mergeChildren(request.getPrivilegeIds(), role.getPrivilegeIds(), privilegeRepository, toRemovePrivilege -> role.removePrivilege((Privilege) toRemovePrivilege), toAddPrivilege -> role.addPrivilege((Privilege) toAddPrivilege));
        return new ResponseEntity<>(roleRepository.save(role), HttpStatus.OK);
    }
}