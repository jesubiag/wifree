package utils;

import com.google.common.collect.ImmutableMap;
import io.ebean.Ebean;
import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import play.Application;
import play.db.Database;
import play.db.Databases;
import play.db.evolutions.Evolutions;
import play.test.Helpers;
import play.test.WithApplication;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

public class WiFreeSuite {

	public static Application app;
	public static String createDdl = "";
	public static String dropDdl = "";

	@BeforeClass
	public static void startApp() throws IOException {
		app = Helpers.fakeApplication(Helpers.inMemoryDatabase());
		Helpers.start(app);

		// Reading the evolution file
		String evolutionContent = FileUtils.readFileToString(
				app.getWrappedApplication().getFile("conf/evolutions/default/1.sql"));

		// Splitting the String to get Create & Drop DDL
		String[] splittedEvolutionContent = evolutionContent.split("# --- !Ups");
		String[] upsDowns = splittedEvolutionContent[1].split("# --- !Downs");
		createDdl = upsDowns[0];
		dropDdl = upsDowns[1];

	}

	@AfterClass
	public static void stopApp() {
		Helpers.stop(app);
	}

	@Before
	public void createCleanDb() {
		Ebean.execute(Ebean.createCallableSql(dropDdl));
		Ebean.execute(Ebean.createCallableSql(createDdl));
	}

}
