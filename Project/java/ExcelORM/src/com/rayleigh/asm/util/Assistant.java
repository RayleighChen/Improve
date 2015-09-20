package com.rayleigh.asm.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;


public class Assistant {
	private static final Logger log = Logger.getLogger(Assistant.class);
	
	public static String convertRequestParameter(String parameter) throws Exception
	{
		return  convertRequestParameter(parameter,null);
	}
	
	
	public static String convertRequestParameter(String parameter,String format) throws Exception
	{
		if (parameter!=null)
		{
			if (format==null)
				format="utf-8";
			parameter = java.net.URLDecoder.decode(parameter,format);
		}
		return parameter;
	}
	//判断参数是否存在并长度大于0
	public static boolean notEmpty(HttpServletRequest request,String key)
	{
		if (request.getParameter(key)!=null && request.getParameter(key).length()>0)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public static void writeFileContent(String url,String content ,ServletContext context) throws Exception
	{
		writeFileContent( getFullPath(url,context),content);

	}
	
	public static String HTMLEncode(String txt) {
		if (txt!=null && txt.length()>0)
		{
			txt=txt.replaceAll("&","&amp;");
			txt=txt.replaceAll("<","&lt;");
			txt=txt.replaceAll(">","&gt;");
			txt=txt.replaceAll("\"","&quot;");
			txt=txt.replaceAll("'","&#146;");
		}
		return txt;
	}
	
	public static boolean existFile(String urlpath,ServletContext context)
	{
		File file = new File(getFullPath(urlpath, context));
		if (file.exists())
			return true;
		else
			return false;
	}
	
	public static void writeFileContent(String path,String content) throws Exception
	{
		File file = new File(path);
		if (!file.exists())
			file.createNewFile();
		OutputStreamWriter write= new OutputStreamWriter(new FileOutputStream(file),"GBK");
		BufferedWriter writer=new BufferedWriter(write);
		writer.write(content);
		writer.close();
		write.close();
	}
	public static String getFileContent(String url,ServletContext context) throws Exception
	{
		String filepath = getFullPath(url,context);
		return getFileContent(filepath);
	}
	
	public static String getFileContent(String path) throws Exception
	{

		StringBuffer buf = new StringBuffer();
		File file = new File(path);
		if (file.exists())
		{
			InputStreamReader read = new InputStreamReader(new FileInputStream(file),"GBK");
			BufferedReader reader=new BufferedReader(read);
			try
			{
				String content = reader.readLine();
				while (content!=null)
				{
					buf.append(content);
					content = reader.readLine();
				}
			}finally
			{
				if (reader!=null)
					reader.close();
				if (read!=null)
					read.close();
			}
		}
		return buf.toString();
	}
	
	//日期加(天数)
	public static java.util.Date addTimeByDay(java.util.Date date,int days) throws Exception
	{
		Calendar calendar=Calendar.getInstance();   
		 calendar.setTime(date);
		calendar.set(Calendar.DATE,calendar.get(Calendar.DATE)+days);
		return calendar.getTime();
	}
	

	public static java.util.Date addTimeByMinutes(java.util.Date date,int minutes) throws Exception
	{
		Calendar calendar=Calendar.getInstance();   
		 calendar.setTime(date);
		 calendar.set(Calendar.MINUTE,calendar.get(Calendar.MINUTE)+minutes);
		return calendar.getTime();
	}
	
	public static java.util.Date addTimeBySeconds(java.util.Date date,int seconds) throws Exception
	{
		Calendar calendar=Calendar.getInstance();   
		 calendar.setTime(date);
		 calendar.set(Calendar.SECOND,calendar.get(Calendar.SECOND)+seconds);
		return calendar.getTime();
	}
	
	//得到当前日期
	public static java.util.Date nowTime() throws Exception
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String datestr = sdf.format(java.util.Calendar.getInstance().getTime());
		
		return sdf.parse(datestr);
	}
	//得到当前时间
	public static java.util.Date nowFullTime() throws Exception
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String datestr = sdf.format(java.util.Calendar.getInstance().getTime());
		return sdf.parse(datestr);
	}
	
	public static java.util.Date nowFullTime(String format) throws Exception
	{
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		String datestr = sdf.format(java.util.Calendar.getInstance().getTime());
		return sdf.parse(datestr);
	}
	
	public static String convertDateStrToString(String datestr,String format) throws Exception
	{
		String result = null;
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		try
		{
			result = sdf.format(sdf.parse(datestr));
		}
		catch (Exception ex)
		{
			 sdf = new SimpleDateFormat("yyyy-MM-dd");
			result = sdf.format(sdf.parse(datestr));
		}
		return result;
	}
	
	public static String convertDateToString(java.util.Date date,String format) throws Exception
	{
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}
	
	public static java.util.Date formatDateStr(String datestr) throws Exception
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		return sdf.parse(datestr);
	}
	
	public static java.util.Date formatDateStr(String datestr,String format) throws Exception
	{
		java.util.Date result = null;
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		try
		{
			result = sdf.parse(datestr);
		}
		catch (Exception ex)
		{
			 sdf = new SimpleDateFormat("yyyy-MM-dd");
			result = sdf.parse(datestr);
		}
		return result;
	}
	
	public static java.util.Date formatFullDateStr(String datestr) throws Exception
	{
		java.util.Date result = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try
		{
			result = sdf.parse(datestr);
		}
		catch (Exception ex)
		{
			 sdf = new SimpleDateFormat("yyyy-MM-dd");
			result = sdf.parse(datestr);
		}
		return result;
	}
	
	//从一个路径获取文件名称
	public static String getFileName(String filepath)
	{
		filepath = filepath.replace('/', '\\');
		String filename = filepath;
		if (filepath.lastIndexOf('\\')>0)
		{
			filename = filepath.substring(filepath.lastIndexOf('\\')+1);
		}
		return filename;
	}
	
	public static void copy(ServletContext context,String form,String to ,boolean removeold) throws java.io.IOException
	{
		copy(getFullPath(form,context),getFullPath(to,context),removeold);
	}
	//文件拷贝和移动函数removeold 是否删除原文件
	public static void copy( String from, String to,boolean removeold )throws java.io.IOException
	{
		int BUFF_SIZE = 1024;
		byte[] buffer = new byte[ BUFF_SIZE ];
		java.io.InputStream in = null;
		java.io. OutputStream out = null;
		try
		{
		in = new FileInputStream( from );
		out = new FileOutputStream( to );
		while ( true )
		{
		  synchronized ( buffer )
		  {
		      int amountRead = in.read( buffer );
		      if ( amountRead == -1 )
		      {
		          break;
		      }
		      out.write( buffer, 0, amountRead );
		  }
		}
		}
		finally
		{
		if ( in != null )
		{
		  in.close();
		}
		if ( out != null )
		{
		  out.close();
		}
		}
		System.out.println("..........................Copy Finish");
		if (removeold)
		{
			java.io.File file =new  java.io.File(from);
			if (file.delete())
			{
				System.out.println("delete file:"+file.getPath());
				file.delete();
			}
		}
	}
	public static String getUploadPath(String path)
	{
		if (path.indexOf("/")!=0)
		{
			path = "/"+path;
		}
		if (path.lastIndexOf("/")!=path.length()-1)
		{
			path = path+"/";
		}
		return path;
	}
	//根据相对路径得到绝对路径
	public static String getFullPath(String url,ServletContext context)
	{
		url = url.replace('\\', '/');
		String path = context.getRealPath(url);
		return path.replace('/', '\\');
	}
	//根据相对路径删除文件
	public static boolean deleteFile(String url,ServletContext context) throws Exception
	{
		java.io.File file =new  java.io.File(getFullPath(url.replace('/', '\\'),context));
		String filepath = file.getPath();
		String ext = filepath.substring(filepath.lastIndexOf(".")+1);
		String minifilepath = filepath.substring(0,filepath.lastIndexOf("."))+"_mini."+ext;
		java.io.File minifile = new java.io.File(minifilepath);
		if (minifile.exists())
		{
			minifile.delete();
		}
		if (file.exists())
		{
			log.debug("delete file:"+file.getPath());
			return file.delete();
		}
		return false;
	}
	
	//根据一个虚拟路径在下面按日期建立目录 返回代表该日期的虚拟路径
	public static String createPathByDate(String url,ServletContext context)
	{
		String fullpath = getFullPath(url,context);
		if (fullpath.lastIndexOf('\\')!=fullpath.length()-1)
			fullpath +="\\";
		
		Calendar nowtime = Calendar.getInstance();
		fullpath +=String.valueOf(nowtime.get(Calendar.YEAR))+"\\";
		SimpleDateFormat sdf = new SimpleDateFormat("MMdd");
		fullpath+=sdf.format(nowtime.getTime())+"\\";
		File file = new File(fullpath);
		
		if (!file.exists())
			file.mkdirs();

		if (url.lastIndexOf('\\')!=url.length()-1 && url.lastIndexOf('/')!=url.length()-1)
			url +="/";
		url += String.valueOf(nowtime.get(Calendar.YEAR))+"/";
		url += sdf.format(nowtime.getTime())+"/";
		return url;
	}
	
	public static boolean createPath(String path,ServletContext context) throws Exception
	{
		String fullpath = getFullPath(path,context);
		File file = new File(fullpath);
		boolean result= true;
		
		if (!file.exists())
		{
			result  =file.mkdirs();
		}
		return result;
	}
	
	//通过传入一个相对地址得到在当前发布环境下的一个相对地址
	public static String getContextUrl(String url,HttpServletRequest request,ServletContext context)
	{
		if (request.getContextPath()!=null && request.getContextPath().length()>0)
		{
			if (url.indexOf("/")!=0)
				url = "/"+url;
			url = request.getContextPath()+url;
		}
		return url;
	}
	//检测request 里面是否有对应的值
	public static Object getValue(HttpServletRequest request,String key)
	{
		if (notEmpty(request, key))
			return request.getParameter(key);
		else 
			return request.getAttribute(key);
	}
	
	public static void createNewFile(String savepath) throws java.io.IOException
	{
		int pos = savepath.lastIndexOf("\\");
		String dir = savepath.substring(0,pos);
		File directory =new File(dir);
		if (!directory.exists())
			directory.mkdirs();
		File file = new File(savepath);
		if (file.exists())
			file.createNewFile();
	}
}
