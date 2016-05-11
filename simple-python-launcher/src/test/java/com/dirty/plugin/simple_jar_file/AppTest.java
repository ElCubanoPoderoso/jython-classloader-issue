package com.dirty.plugin.simple_jar_file;

import org.python.core.PySystemState;
import org.python.util.PythonInterpreter;

import com.test.plugin.simple_jar_file.AppLoader;

import junit.framework.TestCase;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
	public void testApp() throws Exception 
	{
		AppLoader app = new AppLoader();
		app.doSomething();
	}
	
	public void testAppAgain() throws Exception 
	{
		AppLoader app = new AppLoader();
		app.doSomething();
	}
}
