package org.yi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.yi.model.Block;

import com.google.gson.GsonBuilder;

@Controller
public class BlockController {
	@GetMapping(value = "/read",produces = "application/json")
	@ResponseBody//不加的话无法找到这里，404
	public String test() {
		Block block = new Block(null);
		String blockJson = new GsonBuilder().setPrettyPrinting().create().toJson(block);
		return blockJson;
	}
}
