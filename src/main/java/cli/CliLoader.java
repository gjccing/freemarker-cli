package cli;

import freemarker.cache.FileTemplateLoader;
import freemarker.core.ParseException;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.Template;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStreamWriter;

import javax.json.Json;
import javax.json.JsonReader;
import javax.json.JsonValue;

public class CliLoader {

	public static void main(String[] args)
			throws MalformedTemplateNameException, ParseException, IOException,
			TemplateException {
		String templPath = args[0];
		String josnPath = args[1];
		File templFile = new File(templPath);
		JsonValue jdm = getJsonInFile(josnPath);
		Configuration cfg = new Configuration(Configuration.VERSION_2_3_23);
		cfg.setTemplateLoader(new FileTemplateLoader(templFile.getParentFile()));
		Template tpl = cfg.getTemplate(templFile.getName());
		tpl.process(jdm, new OutputStreamWriter(System.out));
	}
	
	private static JsonValue getJsonInFile(String path) throws FileNotFoundException {
		try (JsonReader jr = Json.createReader(new FileInputStream(path));) {
			return jr.read();
		}
	}
}
