package utils

import java.util.function.{Function => JFunction}

object ScalaHelper {
	
	implicit def toJavaFunction[U, V](f: (U) => V): JFunction[U, V] = new JFunction[U, V] {
		override def apply(t: U): V = f(t)
		
		override def compose[T](before: JFunction[_ >: T, _ <: U]):
		JFunction[T, V] = toJavaFunction(f.compose(x => before.apply(x)))
		
		override def andThen[W](after: JFunction[_ >: V, _ <: W]):
		JFunction[U, W] = toJavaFunction(f.andThen(x => after.apply(x)))
	}
	
	implicit def fromJavaFunction[U, V](f: JFunction[U,V]): (U) => V = f.apply
	
}
