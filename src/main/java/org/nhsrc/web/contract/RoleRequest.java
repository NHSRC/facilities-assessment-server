package org.nhsrc.web.contract;

import java.util.List;

public class RoleRequest {
    private int id;
    private String name;
    private List<Integer> privilegeIds;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Integer> getPrivilegeIds() {
        return privilegeIds;
    }

    public void setPrivilegeIds(List<Integer> privilegeIds) {
        this.privilegeIds = privilegeIds;
    }
}