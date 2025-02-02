package com.rllivescore;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import org.bson.Document;

@RestController
public class WebController {
    @GetMapping("/matches")
    public List<Document> getMatches() {
        return MongoRetrieveData.displayData();
    }
}