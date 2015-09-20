package com.rayleigh.asm.render;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.ServletContext;

import com.rayleigh.asm.util.Assistant;

import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.ObjectWrapper;
import freemarker.template.SimpleHash;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
/**
 * freeMarker模板渲染
 * @author courser.tijichen
 */
public class FreemarkerRender implements Render{
	
	private Configuration templateconfig;
	
	public  FreemarkerRender()
	{
		this.initialize();
	}
	/**
	 * 初始化freemarker配置
	 *
	 */
	public void initialize()
	{
		try
		{
			if (templateconfig==null)
			{
				templateconfig = new Configuration();
				templateconfig.setTemplateExceptionHandler(TemplateExceptionHandler.HTML_DEBUG_HANDLER);
				templateconfig.setObjectWrapper(ObjectWrapper.DEFAULT_WRAPPER);
				templateconfig.setTemplateLoader(new ClassTemplateLoader(this.getClass(),"/"));
				templateconfig.setTemplateUpdateDelay(1200);
				templateconfig.setDefaultEncoding("gb2312");
				templateconfig.setLocale(new java.util.Locale("zh_CN"));
				templateconfig.setNumberFormat("0.##########");
			}
		}
		catch (Exception e)
		{}
	}
	/**
	 * 渲染
	 *
	 */
	public void render(RenderClass target,String template,String outpath)
	{
		try
		{
			StringWriter writer = new StringWriter();
			Template tl = templateconfig.getTemplate(
					template,
					templateconfig.getLocale());
			SimpleHash root = new SimpleHash();
			root.put("entity", target);
			tl.process(root, writer);
			Assistant.createNewFile(outpath);
			FileOutputStream fos = new FileOutputStream(outpath);
			PrintWriter pwriter = new PrintWriter(fos);
			pwriter.write(writer.toString());
			pwriter.close();
			fos.close();   
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}
}
