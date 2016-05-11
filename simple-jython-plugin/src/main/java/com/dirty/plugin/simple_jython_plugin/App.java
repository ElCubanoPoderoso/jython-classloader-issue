package com.dirty.plugin.simple_jython_plugin;

import org.python.core.Py;
import org.python.core.PyObject;
import org.python.core.PySystemState;
import org.python.util.PythonInterpreter;

import java.io.InputStream;

import javax.annotation.PreDestroy;

/**
 * Hello world!
 *
 */
public class App 
{
	private PythonInterpreter pythonInterpreter;
	
	public App()
	{
		PySystemState pySysState = new PySystemState();
		pySysState.setClassLoader(App.class.getClassLoader());
		this.pythonInterpreter = new PythonInterpreter(null, pySysState);
	}
	
	public PyInterface makePyInterface()
	{
		try
		{
			InputStream inputStream = App.class.getClassLoader().getResourceAsStream("python/PyInterfaceImpl.py");
			this.pythonInterpreter.execfile(inputStream);
			PyObject pyClass = this.pythonInterpreter.get("PyInterfaceImpl");
			PyObject pyInstance = pyClass.__call__(Py.javas2pys("SampleTestInput"));
			return (PyInterface) pyInstance.__tojava__(PyInterface.class);
		}
		finally
		{
			this.pythonInterpreter.cleanup();
		}
	}
	
	@PreDestroy
	public void destroyInstance()
	{
		pythonInterpreter.close();
		pythonInterpreter = null;
	}
}
