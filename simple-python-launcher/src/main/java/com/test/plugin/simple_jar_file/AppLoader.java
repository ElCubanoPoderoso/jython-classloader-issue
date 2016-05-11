package com.test.plugin.simple_jar_file;

import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * Hello world!
 *
 */
public class AppLoader 
{
	public void doSomething() throws Exception
	{
		URLClassLoader urlClassLoader = null;
		try
		{
			URL[] urlArray = new URL[]{this.getClass().getClassLoader().getResource("jars/simple-jython-plugin-0.0.1-SNAPSHOT.jar")};
			urlClassLoader = new URLClassLoader(urlArray);
			Class <?> clazz = urlClassLoader.loadClass("com.dirty.plugin.simple_jython_plugin.App");
			Object randomObject = clazz.newInstance();
			Method method = clazz.getMethod("makePyInterface");
			Object obj = method.invoke(randomObject);
			Method objMethod = obj.getClass().getMethod("doSomething");
			objMethod.invoke(obj);
			
		}
		finally
		{
			if (urlClassLoader != null)
				urlClassLoader.close();
		}
	}
}
