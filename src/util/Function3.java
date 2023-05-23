package util;

/**
 * A functional interface with 2 parameters and 1 return type.
 * 
 * @author Jake van Keulen
 *
 * @param <param1>     The type of the first parameter
 * @param <param2>     The type of the second parameter
 * @param <returnType> The return type
 */
@FunctionalInterface
public interface Function3<param1, param2, returnType> {
	public returnType apply(param1 param1, param2 param2);
}