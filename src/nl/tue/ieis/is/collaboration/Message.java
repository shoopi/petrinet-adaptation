/*
 * 
 */
package nl.tue.ieis.is.collaboration;

import nl.tue.ieis.is.collaboration.enums.MessageStatus;

/**
 * The Class Message.
 */
public class Message {
	
	//Date sentTime;
	//Date receivedTime;
	/** The payload. */
	private String payload;
	
	/** The status. */
	private MessageStatus status;
	
	/** The header. */
	private CommunicationRelation header;
	
	/**
	 * Gets the payload.
	 *
	 * @return the payload
	 */
	public String getPayload() {
		return payload;
	}
	
	/**
	 * Gets the status.
	 *
	 * @return the status
	 */
	public MessageStatus getStatus() {
		return status;
	}
	
	/**
	 * Gets the header.
	 *
	 * @return the header
	 */
	public CommunicationRelation getHeader() {
		return header;
	}
	
	/**
	 * Sets the payload.
	 *
	 * @param payload the new payload
	 */
	public void setPayload(String payload) {
		this.payload = payload;
	}
	
	/**
	 * Sets the status.
	 *
	 * @param status the new status
	 */
	public void setStatus(MessageStatus status) {
		this.status = status;
	}
	
	/**
	 * Sets the header.
	 *
	 * @param header the new header
	 */
	public void setHeader(CommunicationRelation header) {
		this.header = header;
	}
	
	/**
	 * Instantiates a new message.
	 *
	 * @param payload the payload
	 * @param status the status
	 * @param header the header
	 */
	public Message(String payload, MessageStatus status,
			CommunicationRelation header) {
		super();
		this.payload = payload;
		this.status = status;
		this.header = header;
	}
	
	
}
