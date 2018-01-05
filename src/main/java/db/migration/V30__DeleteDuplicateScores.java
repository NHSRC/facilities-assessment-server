package db.migration;

import org.flywaydb.core.api.migration.spring.SpringJdbcMigration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCountCallbackHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class V30__DeleteDuplicateScores/* implements SpringJdbcMigration*/ {
//    @Override
    public void migrate(JdbcTemplate jdbcTemplate) throws Exception {
        String queryString = "SELECT a.facility_assessment_id AS facilityAssessmentId, a.checklist_id AS checklistId, a.checkpoint_id AS checkpointId, a.id AS checkpointScoreId FROM checkpoint_score a, checkpoint_score b\n" +
                "  WHERE a.checkpoint_id = b.checkpoint_id AND a.facility_assessment_id = b.facility_assessment_id AND a.checklist_id = b.checklist_id\n" +
                "        AND a.id != b.id\n" +
                "ORDER BY a.facility_assessment_id, a.checklist_id, a.checkpoint_id, a.last_modified_date;\n";
        RowMapper rowMapper = new RowMapper();
        jdbcTemplate.query(queryString, rowMapper);
        List<CheckpointScoreIdPair> resultList = rowMapper.getResultList();
        int lastCheckpointId = 0;
        Set<Integer> toDeleteCheckpointScores = new HashSet<>();
        for (CheckpointScoreIdPair checkpointScoreIdPair : resultList) {
            if (checkpointScoreIdPair.getCheckpointId() == lastCheckpointId) {
                toDeleteCheckpointScores.add(checkpointScoreIdPair.getCheckpointScoreId());
            } else {
                lastCheckpointId = checkpointScoreIdPair.getCheckpointId();
            }
        }

        for (int id : toDeleteCheckpointScores) {
            int updated = jdbcTemplate.update("DELETE FROM checkpoint_score WHERE id = ?", new Object[]{id}, new int[]{Types.BIGINT});
            if (updated == 0)
                System.out.println("Didn't find checkpoint_score with id= " + id);
            else
                System.out.println("Deleted checkpoint_score with id=" + id);
        }
    }

    public class RowMapper extends RowCountCallbackHandler {
        private List<CheckpointScoreIdPair> resultList = new ArrayList<>();

        @Override
        protected void processRow(ResultSet rs, int rowNum) throws SQLException {
            resultList.add(new CheckpointScoreIdPair(rs.getInt("checkpointId"), rs.getInt("checkpointScoreId")));
        }

        public List<CheckpointScoreIdPair> getResultList() {
            return resultList;
        }
    }
}