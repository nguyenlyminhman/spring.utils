package spring.utils.queries;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/api/utils/queries")
public class QueryController {

    private final JdbcTemplate jdbcTemplate;

    @RequestMapping(value = "/select", produces = "application/json;charset=utf-8", method = RequestMethod.POST)
    public ResponseEntity<Object> selectStatement(@RequestBody QueryModel queryModel) {
        try {
            String querySql = queryModel.getQuery().replaceAll("[\\r\\n]+", " ");
            List<Map<String, Object>> result = jdbcTemplate.queryForList(querySql);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error query: " + e.getMessage());
        }
    }

    @RequestMapping(value = "/update", produces = "application/json;charset=utf-8", method = RequestMethod.POST)
    public ResponseEntity<Object> updateStatement(@RequestBody QueryModel queryModel) {
        try {
            String querySql = queryModel.getQuery().replaceAll("[\\r\\n]+", " ");
            jdbcTemplate.update(querySql);
            return ResponseEntity.ok("Done");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error query: " + e.getMessage());
        }
    }
}
