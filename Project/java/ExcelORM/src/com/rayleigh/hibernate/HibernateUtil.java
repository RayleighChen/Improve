package com.rayleigh.hibernate;

import java.io.FileOutputStream;
import java.net.URL;
import java.net.URLConnection;

import org.hibernate.cfg.Configuration;

import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.dom4j.Document;
import org.dom4j.Element;

import java.io.FileOutputStream;
import java.net.URL;
import java.net.URLConnection;

import org.hibernate.cfg.Configuration;

import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.dom4j.Document;
import org.dom4j.Element;

public class HibernateUtil {
	
	public static void addMapping(Configuration config,URL hbml)
	{
		config.addURL(hbml);
	}
	/**
	 * 把hbm.xml的路径加入到cfg.xml的mapping结点
	 *
	 * @param cfg.xml的路径
	 * @param hbm.xml的路径
	 */
	public static void updateHbmCfg(URL url,String hbm)
	{
		try
		{
			SAXReader reader = new SAXReader();
			Document doc = reader.read(url);
			Element element = (Element)doc.getRootElement()
			.selectSingleNode("session-factory");
			
			Element hbmnode = element.addElement("mapping");
			hbmnode.addAttribute("resource", hbm);
			String filepath = url.getFile();
			if (filepath.charAt(0)=='/')
				filepath = filepath.substring(1);
			FileOutputStream outputstream = new FileOutputStream(filepath);
			XMLWriter writer = new XMLWriter(outputstream);
			writer.write(doc);
			outputstream.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
