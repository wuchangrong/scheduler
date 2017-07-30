package com.universe.softplat.scheduler.server;
	
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

/**
 * <p>time:2009-9-21</p>
 * @author wudongqing wudq@neusoft.com
 */
public class JobConfigParser implements ContentHandler{
	public static final String PARAM_NAME_LIST = "PARAM_NAME_LIST";
	//自定义任务属性
    private Map config;
    //自定义任务名称列表(处理顺序)
    private List jobList;    
    //临时变量，用于回调接口
	HashMap job = null;
	Properties jobMessage = null;
	//wudq加入，用来存放当前属性的顺序
	List paramNameList = null;
    
    public JobConfigParser(){
        config = new HashMap();
        jobList = new ArrayList();
    }

    public List loadJobConfig() throws IOException{    
    	String jobConfigFilePath = ConfigSchedular.getJobConfigFilePath();
        try{
            new FileInputStream(jobConfigFilePath);
        }
        catch(Exception exception){
            throw new IOException("\u65E0\u6CD5\u8BBF\u95EE\u8C03\u5EA6\u7C7B\u578B\u914D\u7F6E\u6587\u4EF6 " + jobConfigFilePath);
        }
        try
        {
            XMLReader xmlreader = XMLReaderFactory.createXMLReader("org.apache.xerces.parsers.SAXParser");
            xmlreader.setContentHandler(this);
            xmlreader.parse(jobConfigFilePath);
        }
        catch(Exception exception1){
        	exception1.printStackTrace();
            return null;
        }
        List result = new ArrayList();
        result.add(jobList);
        result.add(config);
        return result;
    }

    public void startElement(String s, String s1, String s2, Attributes attributes) throws SAXException{
        if(s1.equalsIgnoreCase("job")){
            job = new HashMap();
            paramNameList = new ArrayList();
            job.put(PARAM_NAME_LIST, paramNameList);
            
            String jobClass = attributes.getValue(0);
            config.put(jobClass, job);
            jobList.add(jobClass);
            
            for(int i = 1; i < attributes.getLength(); i++){
                job.put(attributes.getQName(i), attributes.getValue(i));
            }
        }else if(s1.equalsIgnoreCase("param")){
            jobMessage = new Properties();
            job.put(attributes.getValue(0), jobMessage);
            //加入参数列表
            paramNameList.add(attributes.getValue(0));
            
            for(int j = 1; j < attributes.getLength(); j++)
                jobMessage.setProperty(attributes.getQName(j), attributes.getValue(j));
        }
    }

    public void characters(char ac[], int i, int j)
        throws SAXException
    {
    }

    public void endDocument()
        throws SAXException
    {
    }

    public void endElement(String s, String s1, String s2)
        throws SAXException
    {
    }

    public void endPrefixMapping(String s)
        throws SAXException
    {
    }

    public void ignorableWhitespace(char ac[], int i, int j)
        throws SAXException
    {
    }

    public void processingInstruction(String s, String s1)
        throws SAXException
    {
    }

    public void setDocumentLocator(Locator locator)
    {
    }

    public void skippedEntity(String s)
        throws SAXException
    {
    }

    public void startDocument()
        throws SAXException
    {
    }

    public void startPrefixMapping(String s, String s1)
        throws SAXException
    {
    }

    public static void main(String args[])
        throws IOException
    {
        (new JobConfigParser()).loadJobConfig();
    }
}
