package com.rayleigh.asm;

import junit.framework.TestCase;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.WordUtils;

import com.rayleigh.asm.render.*;

public class BuildTest extends TestCase {

	public void testStringUtils() {
		System.out.println(WordUtils.capitalize("name"));
	}

	public void testBuild() {
		BuildProperty p_name = new BuildProperty("name", String.class.getName());
		BuildProperty p_sex = new BuildProperty("sex",
				SimpleProperty.class.getName());
		ArrayList list = new ArrayList();
		list.add(p_name);
		list.add(p_sex);
		POBuildUtil util = new POBuildUtil();
		Class cls = util.build("com.rayleigh.asm.bean.BuildTest",
				"d:\\test.class", list);
		PropertyDescriptor[] properties = PropertyUtils
				.getPropertyDescriptors(cls);
		for (int i = 0; i < properties.length; i++) {
			System.out.println(properties[i].getName() + ":"
					+ properties[i].getPropertyType().getName());
		}
	}

	public void testBuildHBM() {
		RenderClass rc = new RenderClass();
		ArrayList list = new ArrayList();

		RenderProperty property = new RenderProperty();
		property.setName("oid");
		property.setField("oid");
		property.setLength(new Integer(15));
		property.setPrimary(true);
		property.setType(Long.class.getName());
		// property.setSequence("SEQ_PERSON");

		list.add(property);

		property = new RenderProperty();
		property.setName("name");
		property.setType(String.class.getName());
		property.setField("name");
		property.setLength(new Integer(20));

		list.add(property);

		rc.setProperties(list);
		rc.setClassName("com.rayleigh.asm.bean.Person");
		rc.setTableName("person1");

		FreemarkerRender render = new FreemarkerRender();
		render.render(rc, Templates.TEMPLATE_HIBERNATE3, "d:\\person.hbm.xml");
	}
}
