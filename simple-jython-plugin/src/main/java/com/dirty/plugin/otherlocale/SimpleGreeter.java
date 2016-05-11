package com.dirty.plugin.otherlocale;

public class SimpleGreeter 
{
	private String fooBar;
	public SimpleGreeter(String fooBar)
	{
		this.fooBar = fooBar;
	}
	
	public void echoMeSomething()
	{
		System.out.println("You input " + this.fooBar);
	}
}
