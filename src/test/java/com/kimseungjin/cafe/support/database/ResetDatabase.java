package com.kimseungjin.cafe.support.database;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class ResetDatabase {

    @Autowired private Flyway flyway;

    protected void resetDatabase() {
        flyway.clean();
        flyway.migrate();
    }
}
