package fun.play.interview;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ReflectionDemo {
	static class Obj{
		int a;
		int b;
		
		public Obj() { }
		
		public int sum(int i) {
			return a + b + i;
		}
		
		@Override
		public String toString() {
			return "Obj [a=" + a + ", b=" + b + "]";
		}
	}
	
	public static void main(String[] args) {
		Field[] fields = Obj.class.getDeclaredFields();
		Obj obj;
		try {
			Constructor<Obj> constructor = Obj.class.getConstructor();
			obj = constructor.newInstance();
		} catch (NoSuchMethodException | SecurityException | InstantiationException 
				 | IllegalAccessException | IllegalArgumentException | InvocationTargetException e1) {
			System.err.println("construct error::");
			e1.printStackTrace();
			return;
		}
		
		try {
			for (int i = 0; i < fields.length; i++) {
				fields[i].set(obj, Integer.parseInt(args[i]));
			}
			System.err.printf("obj created by reflection:%s\n",obj);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			Method method = Obj.class.getMethod("sum", int.class);
			try {
				Object invoke = method.invoke(obj, 3);
				System.err.println("invoke sum:"+invoke);
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				e.printStackTrace();
			}
		} catch (NoSuchMethodException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
