package com.dirty.plugin.simple_jython_plugin;

import junit.framework.TestCase;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{

	public void testCreateApp()
	{
		App app = new App();
		PyInterface pyInterface = app.makePyInterface();
		pyInterface.doSomething();
	}
}
