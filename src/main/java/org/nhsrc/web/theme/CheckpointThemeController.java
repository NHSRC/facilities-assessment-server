package org.nhsrc.web.theme;

import org.nhsrc.domain.CheckpointTheme;
import org.nhsrc.repository.theme.CheckpointThemeRepository;
import org.nhsrc.service.ChecklistService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
public class CheckpointThemeController {
    private final ChecklistService checklistService;
    private final CheckpointThemeRepository checkpointThemeRepository;

    public CheckpointThemeController(ChecklistService checklistService, CheckpointThemeRepository checkpointThemeRepository) {
        this.checklistService = checklistService;
        this.checkpointThemeRepository = checkpointThemeRepository;
    }

    @RequestMapping(value = "/api/checkpointTheme/search/lastModifiedByState", method = {RequestMethod.GET})
    public Page<CheckpointTheme> findLastModifiedByState(@RequestParam("name") String name, @RequestParam("lastModifiedDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date lastModifiedDateTime, Pageable pageable) {
        List<Integer> checklists = checklistService.getChecklistsForState(name);
        return checkpointThemeRepository.findAllByCheckpointChecklistIdInAndLastModifiedDateGreaterThanOrderByLastModifiedDateAscIdAsc(checklists, lastModifiedDateTime, pageable);
    }
}
