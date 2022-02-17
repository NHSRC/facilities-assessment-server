package org.nhsrc.web;

import org.nhsrc.domain.Theme;
import org.nhsrc.repository.Repository;
import org.nhsrc.repository.ThemeRepository;
import org.nhsrc.web.contract.ThemeRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;

@RestController
@RequestMapping("/api/")
public class ThemeController {
    private final ThemeRepository themeRepository;

    @Autowired
    public ThemeController(ThemeRepository themeRepository) {
        this.themeRepository = themeRepository;
    }

    @RequestMapping(value = "/themes", method = {RequestMethod.POST, RequestMethod.PUT})
    @Transactional
    @PreAuthorize("hasRole('Checklist_Write')")
    public Theme save(@RequestBody ThemeRequest request) {
        Theme theme = Repository.findByUuidOrCreate(request.getUuid(), themeRepository, new Theme());
        theme.setName(request.getName());
        theme.setInactive(request.isInactive());
        return themeRepository.save(theme);
    }

    @RequestMapping(value = "/themes/{id}", method = {RequestMethod.DELETE})
    @Transactional
    @PreAuthorize("hasRole('Checklist_Write')")
    public Theme delete(@PathVariable("id") Integer id) {
        return Repository.delete(id, themeRepository);
    }
}
