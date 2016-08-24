package MyFileSystem;

import java.lang.reflect.Field;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class DOMElementBuilder {
	
	/**
	 * Returns the tag of the provided class if converted to a DOM object. The parameter class itself does not
	 * need to be annotated as a DOMObject itself. This method simply translates the class name into lower case
	 * in order to determine the tag for the class.
	 * @param cls
	 * @return the tag associated with the parameter class
	 */
	public static String getTag(Class<?> cls) {
		return cls.getName().toLowerCase();
	}
	
	/**
	 * Builds a DOM element based on the object class's DOMObject annotation using the provided document. If the
	 * provided object's class does not have a DOMObject annotation, the returned element is empty.
	 * @param obj
	 * @param doc
	 * @return the created DOM element
	 */
	public static Element build(Object obj, Document doc) {
		Element e = doc.createElement(getTag(obj.getClass()));
		DOMObject domobj = obj.getClass().getAnnotation(DOMObject.class);
		if (domobj != null) {
			String[] attrs = domobj.attributes();
			for (String name : attrs) {
				String value = getObjectFieldValue(obj, name);
				e.setAttribute(name, value);
			}
			
			String[] elmts = domobj.elements();
			for (String name : elmts) {
				String value = getObjectFieldValue(obj, name);
				Element newE = doc.createElement(name);
				newE.setNodeValue(value);
				e.appendChild(newE);
			}
		}
		return e;
	}
	
	private static String getObjectFieldValue(Object obj, String fieldName) {
		try {
			Field field = obj.getClass().getField(fieldName);
			return String.valueOf(field.get(obj));
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			return null;
		}
	}
	
	
//	/**
//	 * The <code>StandardElementTags</code> class provides finalized string representations of generic
//	 * DOM element types for standardization purposes.
//	 * @author michaelstecklein
//	 *
//	 */
//	public static class StandardElementTags {
//		public final static String Array = "array";
//		public final static String String = "string";
//		public final static String Date = "date";
//		public final static String Day = "day";
//		public final static String Month = "month";
//		public final static String Year = "year";
//		public final static String LastUpdate = "lastupdate";
//	}
//
//	/*	SDate	*/
//	private static final String SDate_Tag = "sdate";
//	private static final String SDate_DayNumber_Tag = "daynumber";
//	
//	private static final String NoTagFound_Tag = "notagfound";
//
//	public static String getTag(Class<?> c) {
//		if (c == SDate.class)
//				return SDate_Tag;
//		return NoTagFound_Tag;
//	}
//	
//	public static Element create(DOMObject o, Document doc) {
//		Element e = doc.createElement(getTag(o.getClass()));
//		
//		/*	SDate	*/
//		if (o instanceof SDate) {
//			SDate sdate = (SDate)o;
//			Element dayNumber = doc.createElement(SDate_DayNumber_Tag);
//			Element day = doc.createElement(StandardElementTags.Day);
//			Element month = doc.createElement(StandardElementTags.Month);
//			Element year = doc.createElement(StandardElementTags.Year);
//			dayNumber.setNodeValue(String.valueOf(sdate.getDayNumber()));
//			day.setNodeValue(String.valueOf(sdate.getDay()));
//			month.setNodeValue(String.valueOf(sdate.getMonth()));
//			year.setNodeValue(String.valueOf(sdate.getYear()));
//			e.appendChild(dayNumber);
//			e.appendChild(day);
//			e.appendChild(month);
//			e.appendChild(year);
//		}
//		
//		return e;
//	}
//	
}
