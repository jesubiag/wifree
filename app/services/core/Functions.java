package services.core;

import services.TestingService;
import services.WiFreeFunction;
import services.WiFreeService;

import java.util.List;
import java.util.Map;

public class Functions {
	
	static Map<Class<? extends WiFreeService>, List<? super WiFreeFunction>> functions;
	
	static {
//		functions.
	}
	
	static public List<? super WiFreeFunction> get(Class<WiFreeService> service) {
		return functions.get(service);
	}
	
	static private void add(Class<WiFreeService> service, WiFreeFunction function) {
		final List<? super WiFreeFunction> serviceFunctions = functions.get(service);
		serviceFunctions.add(function);
		functions.put(service, serviceFunctions);
	}
	
	
}
