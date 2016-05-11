from com.dirty.plugin.otherlocale import SimpleGreeter
from com.dirty.plugin.simple_jython_plugin import PyInterface
from com.dirty.plugin.simple_jython_plugin import App

class PyInterfaceImpl(PyInterface):
    def __init__(self, yourInput):
        self.greeter = SimpleGreeter(yourInput)

    def doSomething(self):
        print "I WAS CONSTRUCTED SUCCESSFULLY!"
        self.greeter.echoMeSomething()
