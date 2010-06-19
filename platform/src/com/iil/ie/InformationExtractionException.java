package com.iil.ie;

/**
 * 用于抛出关于InformationExtraction的Exception的类
 * <p>
 * 将在这个类中包含相应的异常信息
 * @author Ferry
 * @version 1.0
 */
public class InformationExtractionException extends Exception {
	
    /** Field containedException specifies a wrapped exception.  May be null. */
    Throwable containedException;

	/**
	 * InformationExtractionException的构造函数
	 * @param msg 需要赋予该类的相应的消息字符串
	 */
	public InformationExtractionException(String msg) {
		super(msg);
        this.containedException = null;
	}

    /**
     * Create a new InformationExtractionException wrapping an existing exception.
     *
     * @param e The exception to be wrapped.
     */
    public InformationExtractionException(Throwable e) {

        super(e.toString());

        this.containedException = e;
    }

    /**
     * Wrap an existing exception in a InformationExtractionException.
     *
     * <p>This is used for throwing processor exceptions before
     * the processing has started.</p>
     *
     * @param message The error or warning message, or null to
     *                use the message from the embedded exception.
     * @param e Any exception
     */
    public InformationExtractionException(String message, Throwable e) {

        super(((message == null) || (message.length() == 0))
              ? e.toString()
              : message);

        this.containedException = e;
    }

    /**
     * This method retrieves an exception that this exception wraps.
     *
     * @return An Throwable object, or null.
     * @see #getCause
     */
    public Throwable getException() {
        return containedException;
    }

    /**
     * Returns the cause of this throwable or <code>null</code> if the
     * cause is nonexistent or unknown.  (The cause is the throwable that
     * caused this throwable to get thrown.)
     */
    public Throwable getCause() {

        return ((containedException == this)
                ? null
                : containedException);
    }

    /**
     * Print the the trace of methods from where the error
     * originated.  This will trace all nested exception
     * objects, as well as this object.
     */
    public void printStackTrace() {
        printStackTrace(new java.io.PrintWriter(System.err, true));
    }

    /**
     * Print the the trace of methods from where the error
     * originated.  This will trace all nested exception
     * objects, as well as this object.
     * @param s The stream where the dump will be sent to.
     */
    public void printStackTrace(java.io.PrintStream s) {
        printStackTrace(new java.io.PrintWriter(s));
    }

    /**
     * Print the the trace of methods from where the error
     * originated.  This will trace all nested exception
     * objects, as well as this object.
     * @param s The writer where the dump will be sent to.
     */
    public void printStackTrace(java.io.PrintWriter s) {
        if (s == null) {
            s = new java.io.PrintWriter(System.err, true);
        }

        try {
            super.printStackTrace(s);
        } catch (Throwable e) {}

        Throwable exception = getException();

        exception.printStackTrace(s);
		s.flush();
    }
	
}