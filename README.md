# jython-classloader-issue
Demonstrating a Proof Of Concept for an Issue with Jython 2.7 trying to dynamically load Jython generated classes that use it.

This might not explain the problem well but will have to suffice for a summary.  Please read the source code to understand the root issue.

The use case affecting this: I'm loading a java app dynamically by passing the .jar file along and having the classloader for it get the instances and load it dynamically (through SpringFramework).  The usecase requires being able to load these jars dynamically as the load-in jar may require an update or installing a new jar at runtime.

In attempts to integrate Jython to the application, I ran into a wall with redeploying the same .jar or deploying a new .jar as the application refreshes all instances of the subdeployments.  The Jython-standalone library fails to properly cast a Jython created PyObject to the appropriate Java interface if at any point, two classloaders have a class with the same name (even if the old classloader is closed and dereferenced.  Each instance instantiates their own JythonInterpreter.  This can happen if the app is redeployed dynamically during runtime.

After some difficulty, I was able to replicate a very basic POC that reproduces the issue on my machine.  At the time of recording this, I replicated this using jdk version 1.8.0_72 on Debian Jessie 8 OS.

The POC project creates a URL classloader that loads the jar dynamically.  The jar in itself starts an interpreter with the class loader of that class mapped to the interpreter and constructs the app building the interpreter via reflection.  Afterwards, it invokes a method via reflection on the class to build the python object.  The class casting only happens on the space of the .jar and not on the launcher.  The first time this is done, this is fine.  However, a repeat execution during the lifespan of the JVM causes this to fail casting the PyObject to the appropriate Java Interface, instead converting it to a PySingleton, failing the cast.

To reproduce this issue:
Build the simple-jython-plugin project (mvn clean install) and copy the generated jar file to  simple-python-launcher/src/main/resources/jars (assuming you don't change the code in the launcher), then build the simple-python-launcher (mvn clean install), which I'm expecting the unit test cases to pass, but instead fail.  I am leaving the .jar file in the resources for the launcher if you want to get straight to the point.

Result from JUnit:

```
Running com.dirty.plugin.simple_jar_file.AppTest
I WAS CONSTRUCTED SUCCESSFULLY!
You input SampleTestInput
Tests run: 2, Failures: 0, Errors: 1, Skipped: 0, Time elapsed: 3.57 sec <<< FAILURE!
testApp(com.dirty.plugin.simple_jar_file.AppTest)  Time elapsed: 0.905 sec  <<< ERROR!
java.lang.reflect.InvocationTargetException
	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
	at java.lang.reflect.Method.invoke(Method.java:498)
	at com.test.plugin.simple_jar_file.AppLoader.doSomething(AppLoader.java:23)
	at com.dirty.plugin.simple_jar_file.AppTest.testApp(AppTest.java:19)
Caused by: java.lang.ClassCastException: org.python.core.PySingleton cannot be cast to com.dirty.plugin.simple_jython_plugin.PyInterface
	at com.dirty.plugin.simple_jython_plugin.App.makePyInterface(App.java:35)
	... 34 more


Results :

Tests in error: 
  testApp(com.dirty.plugin.simple_jar_file.AppTest)

Tests run: 2, Failures: 0, Errors: 1, Skipped: 0
```

My question:  Why is my use case not working and what do I need to do to make this use case work?  Restarting the JVM is not considered an acceptable answer to this response as it fundamentally breaks the use case I must work with.
