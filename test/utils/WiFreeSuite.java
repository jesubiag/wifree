package utils;

import com.google.common.collect.ImmutableMap;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import play.db.Database;
import play.db.Databases;
import play.db.evolutions.Evolutions;
import play.test.WithApplication;

public class WiFreeSuite extends WithApplication {

	protected static Database database;

	@BeforeClass
	public static void setUpDatabase() {
		database = Databases.inMemory(
				"WiFreeTestDatabase",
				ImmutableMap.of("MODE", "PostgreSQL"),
				ImmutableMap.of("logStatements", true)
		);
		Evolutions.applyEvolutions(database);
	}

	@AfterClass
	public static void shutdownDatabase() {
		database.shutdown();
	}

}
