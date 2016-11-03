package org.nhsrc.web;

import org.nhsrc.decorator.EntityMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class EntityTypeMappingController {
    @Autowired
    private EntityMetadata entityMetadata;

    @RequestMapping(value = "/entities", method = RequestMethod.GET)
    public ResponseEntity<?> entityTypeMapping() {
        return new ResponseEntity<>(entityMetadata.getEntityTypeMap(), HttpStatus.OK);
    }
}
