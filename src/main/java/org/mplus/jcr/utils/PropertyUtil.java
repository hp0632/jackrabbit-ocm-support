package org.mplus.jcr.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class PropertyUtil {
	
	private static final Log log = LogFactory.getLog(PropertyUtil.class);
	
	private static String JCR_DEFAULT_PROPERTIES = "jcr.properties";
	
	/**
	 * load filepath ,but this filepath just one thing ,not many
	 * load properties then take the conf val and home key
	 * @throws FileNotFoundException 
	 */
	public static Properties load(String... filepath){
		Properties pro = new Properties();
		InputStream is = null;
		if (isEmpty(filepath) ==true) {
			is = PropertyUtil.class.getClassLoader().getResourceAsStream(JCR_DEFAULT_PROPERTIES);
		}else {
			is = PropertyUtil.class.getClassLoader().getResourceAsStream(filepath[0]);
		}
		try {
			pro.load(is);
		} catch (IOException e) {
			log.error(e.getMessage());
		} finally{
			if (is != null) {
				try {
					is.close();
					is = null;
				} catch (IOException e) {
					log.error(e.getMessage());
				}
			}
		}
		return pro;
	}
	
	
	/**
	 * @author:hp
	 * @description:判断所给的数组是否为空
	 * @param filepath
	 * @return
	 */
	public static boolean isEmpty(Object object){
		if(object instanceof Object[]){
			return ((Object[])object).length ==0;
		}else if(object == null){
			return false;
		}else {
			try {
                return Array.getLength(object) == 0;
            } catch (IllegalArgumentException ex) {
                throw new IllegalArgumentException("Unsupported object type: " + object.getClass().getName());
            }
		}
	}
	
	
}
