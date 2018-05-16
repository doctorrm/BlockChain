import org.yi.model.Block;

import com.google.gson.GsonBuilder;

public class Appler {
	public static void main(String[] args) {
		Block block = new Block(null);
		String blockJson = new GsonBuilder().setPrettyPrinting().create().toJson(block);
		System.out.println(blockJson);
	}
}
