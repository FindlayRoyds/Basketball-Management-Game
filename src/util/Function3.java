package util;

/**
 * A functional interface with 2 parameters and 1 return type.
 * 
 * @author Jake van Keulen
 *
 * @param <Param1>
 * @param <Param2>
 * @param <Return>
 */
@FunctionalInterface
public interface Function3<Param1, Param2, Return> {
	public Return apply(Param1 param1, Param2 param2);
}