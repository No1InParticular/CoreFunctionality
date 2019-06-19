package com.no1inparticular.corefunctionality.groups;

import java.util.ArrayList;
import java.util.List;

import com.no1inparticular.corefunctionality.CoreFunctionality;

public class Rank {
	
	public String name;
	public String prefix;
	public List<String> permissions;
	public List<String> members;
	public List<String> inheritance;
	public CoreFunctionality main;
	
	public Rank(String name, String prefix) {
		this.name = name;
		this.prefix = prefix;
		permissions = new ArrayList<String>();
		members = new ArrayList<String>();
		inheritance = new ArrayList<String>();
	}
}	
