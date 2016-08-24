package MyFileSystem;

/**
 * The DOMObject annotation can be used to describe how to break down a class into a DOM object. The 'attributes' field
 * will contain a list of the names of methods or fields that correspond to attributes of the DOM object. The 'elements'
 * field will contain a list of the names of methods or fields that correspond to elements within the DOM object.
 * @author michaelstecklein
 *
 */
public @interface DOMObject {
	
	/**
	 * The 'attributes' field will contain a list of the names of methods or fields that correspond to attributes of
	 * the DOM object.
	 * @return the list of names of method or field names
	 */
	String[] attributes() default {};
	
	/**
	 * The 'elements' field will contain a list of the names of methods or fields that correspond to elements of
	 * the DOM object.
	 * @return the list of names of method or field names
	 */
	String[] elements() default {};
}
