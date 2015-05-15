package com.shshop.control;

import com.shshop.command.Command;
import com.shshop.command.LoginCommand;
import com.shshop.command.InsertProductCommand;

public class CommandFactory {
	public static Command createCommand(String pathInfo) {
		switch (pathInfo) {
		case "/login":
			return new LoginCommand();
		case "/insertProduct":
			return new InsertProductCommand();
		default:
			return null;
		}
	}
}
