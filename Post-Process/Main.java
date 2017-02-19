import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * @author Sean Maloney
 *
 */
public class Main {

	public static void main(String[] args) throws IOException, InterruptedException{



		for(int i=25; i<50; i++){
			String html =  getAIOutputHTML(Config.primerHTML, "12000");

			
			//cut out a single html page
			html = html.substring(0, html.indexOf(Config.ender)+Config.ender.length()+1);
			
			//iff too short
			if(html.length() < 1200){
				i--;
				continue;
			}


			String css = getAIOutputCSS(Config.primerCSS, "12000");

			//remove quotes
			css = css.replaceAll("'", "");
			css = css.replaceAll("\"", "");
			
			//remove style tags
			css.replaceAll("css/style.css", "");


			//strip comments
			html = html.replaceAll("<!--", "");
			html = html.replaceAll("-->", "");

			//replace '
			html = html.replaceAll("'", "\"");

			//insert css script tag
			String cssLink = "\n<!--INSERTED CSS-->\n<head>\n<link rel=\"stylesheet\" "
					+ "type=\"text/css\" href=\""+Config.cssFilePath+"\">\n</head>\n<!--INSERTED CSS-->\n";
			if(html.indexOf(">") != -1){
				html = html.substring(0,html.indexOf(">")+1)+cssLink+html.substring(html.indexOf(">")+1,html.length()-1);
			}
			

			//make directories
			excuteCommand("mkdir Templates", Config.storeLocation);
			excuteCommand("mkdir template_"+i, Config.templatesRoot);
			excuteCommand("mkdir images", Config.templatesRoot+"/template_"+i);
			excuteCommand("mkdir css",  Config.templatesRoot+"/template_"+i);
			ArrayList<String> images = getImageList(Config.allImages);

			//parse images from html
			Pattern patternHTML = Pattern.compile("<img\\s+[^>]*?src=(\"|')([^\"']+)\\1");
			Matcher mHTML = patternHTML.matcher(html);

			Random rand = new Random();
			//pull images into dir html
			while(mHTML.find()){
				int imageIndex = rand.nextInt(images.size()-1);
				String htmlImg = mHTML.group(2);
				excuteCommand("cp "+Config.allImages+"/"+images.get(imageIndex) +" .", Config.templatesRoot+"/template_"+i+"/images");
				html = html.replace(htmlImg, "images/"+images.get(imageIndex));
			}


			//parse images from css
			Pattern patternCSS = Pattern.compile("url[\\s]*\\([\\s]*(?<url>[^\\)]*)[\\s]*\\)[\\s]*");
			Matcher mCSS = patternCSS.matcher(css);


			//pull images into dir for css
			while(mCSS.find()){
				int imageIndex = rand.nextInt(images.size()-1);
				String cssImg = mCSS.group(1);
				excuteCommand("cp "+Config.allImages+"/"+images.get(imageIndex) +" .", Config.templatesRoot+"/template_"+i+"/images");
				css = css.replace(cssImg, "../images/"+images.get(imageIndex));
			}


			PrintWriter outHTML = new PrintWriter(Config.templatesRoot+"/template_"+i+"/index.html");
			outHTML.println(html);
			outHTML.close();


			PrintWriter outCSS = new PrintWriter(Config.templatesRoot+"/template_"+i+"/css/theme.css");
			outCSS.println(css);
			outCSS.close();
			System.out.println("Iteration: "+i);
		}
	}

	private static String getAIOutputHTML(String primer, String length) {

		StringBuffer output = new StringBuffer();
		String command  = "/Users/Admin/torch/install/bin/th";

		Process p;
		try {
			p = Runtime.getRuntime().exec(new String[]{command,"sample.lua", "-checkpoint", "cv/html_249200.t7", "-length", ""+length+"", "-gpu", "-1","-start_text", ""+primer+""}, null, new File("/Users/Admin/torch/torch-rnn"));

			BufferedReader reader =
					new BufferedReader(new InputStreamReader(p.getInputStream()));

			String line = "";
			while ((line = reader.readLine())!= null) {
				output.append(line + "\n");
			}
			p.waitFor();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return output.toString();

	}

	private static String getAIOutputCSS(String primer, String length) {

		StringBuffer output = new StringBuffer();
		String command  = "/Users/Admin/torch/install/bin/th";

		Process p;
		try {
			p = Runtime.getRuntime().exec(new String[]{command,"sample.lua", "-checkpoint", "cv/css_366250.t7", "-length", ""+length+"", "-gpu", "-1","-start_text", ""+primer+""}, null, new File("/Users/Admin/torch/torch-rnn"));

			BufferedReader reader =
					new BufferedReader(new InputStreamReader(p.getInputStream()));

			String line = "";
			while ((line = reader.readLine())!= null) {
				output.append(line + "\n");
			}
			p.waitFor();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return output.toString();

	}

	private static void excuteCommand(String command, String path) {

		Process p;
		try {
			p = Runtime.getRuntime().exec(command, null, new File(path));
			p.waitFor();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static ArrayList<String> getImageList(String dir){
		ArrayList<String> output = new ArrayList<>();
		String command  = "ls";

		Process p;
		try {
			p = Runtime.getRuntime().exec(command, null, new File(dir));

			BufferedReader reader =
					new BufferedReader(new InputStreamReader(p.getInputStream()));

			String line = "";
			while ((line = reader.readLine())!= null) {
				output.add(line);
			}
			p.waitFor();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return output;

	}

}
