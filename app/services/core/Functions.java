package services.core;

import core.PackageClassesFinder;
import operations.core.WiFreeRequest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Functions {
	
	private static Map<Class<? extends WiFreeRequest>, WiFreeFunction> allFunctions = new HashMap<>();
	
	static {
		try {
			initialize();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		}
	}
	
	public static <RQ extends WiFreeRequest> WiFreeFunction of(RQ request) {
		return allFunctions.get(request.getClass());
	}
	
	private static void initialize() throws IllegalAccessException, InstantiationException {
		final List<Class<? extends WiFreeFunction>> functionsClasses = PackageClassesFinder.getFunctionsClasses();
		for (Class<? extends WiFreeFunction> functionClass : functionsClasses) {
			put(functionClass.newInstance());
		}
	}
	
	private static <F extends WiFreeFunction> void put(F... serviceFunctions) {
		Arrays.asList(serviceFunctions).stream().forEach(function -> allFunctions.put(function.rqClass(), function));
	}
	
}
