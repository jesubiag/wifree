package utils;

import models.PortalApp;
import models.types.PortalApplicationType;
import play.Logger;

/**
 * Created by jesu on 13/06/17.
 */
public class CustomTests {

	public static void main(String... args) {
		System.out.println("args = [" + args + "]");
		Logger.error("Object deleted={}", new PortalApp(null, PortalApplicationType.Image, "julio", false));
	}
}
