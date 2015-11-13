/*
 * 
 */
package nl.tue.ieis.is.collaboration;

import nl.tue.ieis.is.collaboration.enums.MessageType;
import nl.tue.tm.is.ptnet.*;

/**
 * The Class CommunicationRelation.
 */
public class CommunicationRelation {
	
	/** The sender transition. */
	private Transition sender;
	
	/** The receiver transition. */
	private Transition receiver;
	
	/** The intermediary. */
	private Place intermediary;
	
	/** The message type. */
	private MessageType messageType; //Synchronous vs. Asynchronous
	
	/** The msg. */
	private Message msg;
	
	/**
	 * Gets the sender.
	 *
	 * @return the sender
	 */
	public Transition getSender() {
		return sender;
	}
	
	/**
	 * Gets the receiver.
	 *
	 * @return the receiver
	 */
	public Transition getReceiver() {
		return receiver;
	}
	
	/**
	 * Gets the intermediary.
	 *
	 * @return the intermediary
	 */
	public Place getIntermediary() {
		return intermediary;
	}
	
	/**
	 * Gets the message type.
	 *
	 * @return the message type
	 */
	public MessageType getMessageType() {
		return messageType;
	}
	
	/**
	 * Sets the sender.
	 *
	 * @param sender the new sender
	 */
	public void setSender(Transition sender) {
		this.sender = sender;
	}
	
	/**
	 * Sets the receiver.
	 *
	 * @param receiver the new receiver
	 */
	public void setReceiver(Transition receiver) {
		this.receiver = receiver;
	}
	
	/**
	 * Sets the intermediary.
	 *
	 * @param intermediary the new intermediary
	 */
	public void setIntermediary(Place intermediary) {
		this.intermediary = intermediary;
	}
	
	/**
	 * Sets the message type.
	 *
	 * @param messageType the new message type
	 */
	public void setMessageType(MessageType messageType) {
		this.messageType = messageType;
	}
	
	/**
	 * Instantiates a new communication relation.
	 *
	 * @param sender the sender
	 * @param receiver the receiver
	 * @param intermediary the intermediary
	 * @param messageType the message type
	 */
	public CommunicationRelation(Transition sender, Transition receiver,
			Place intermediary, MessageType messageType) {
		super();
		this.sender = sender;
		this.receiver = receiver;
		this.intermediary = intermediary;
		this.messageType = messageType;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return sender.getId() + " --> " + intermediary.getId() + " --> " + receiver.getId();
	}
	
	/**
	 * Gets the msg.
	 *
	 * @return the msg
	 */
	public Message getMsg() {
		return msg;
	}
	
	/**
	 * Sets the msg.
	 *
	 * @param msg the new msg
	 */
	public void setMsg(Message msg) {
		this.msg = msg;
	}
	
	
}
