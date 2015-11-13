/*
 * 
 */
package nl.tue.ieis.is.collaboration;

import java.util.List;

import nl.tue.tm.is.ptnet.PTNet;

/**
 * The Class Party.
 */
public class Party {

	/** The id. */
	private String id;
	
	/** The orchestration. */
	private PTNet orchestration;
	
	/** The history. */
	private MarkingHistory history;
	
	/** The sent messages. */
	private List<Message> sentMessages;
	
	/** The received messages. */
	private List<Message> receivedMessages;

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	
	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(String id) {
		this.id = id;
	}
	
	/**
	 * Gets the orchestration.
	 *
	 * @return the orchestration
	 */
	public PTNet getOrchestration() {
		return orchestration;
	}
	
	/**
	 * Gets the history.
	 *
	 * @return the history
	 */
	public MarkingHistory getHistory() {
		return history;
	}
	
	/**
	 * Gets the sent messages.
	 *
	 * @return the sent messages
	 */
	public List<Message> getSentMessages() {
		return sentMessages;
	}
	
	/**
	 * Gets the received messages.
	 *
	 * @return the received messages
	 */
	public List<Message> getReceivedMessages() {
		return receivedMessages;
	}
	
	/**
	 * Sets the orchestration.
	 *
	 * @param orchestration the new orchestration
	 */
	public void setOrchestration(PTNet orchestration) {
		this.orchestration = orchestration;
	}
	
	/**
	 * Sets the history.
	 *
	 * @param history the new history
	 */
	public void setHistory(MarkingHistory history) {
		this.history = history;
	}
	
	/**
	 * Sets the sent messages.
	 *
	 * @param sentMessages the new sent messages
	 */
	public void setSentMessages(List<Message> sentMessages) {
		this.sentMessages = sentMessages;
	}
	
	/**
	 * Sets the received messages.
	 *
	 * @param receivedMessages the new received messages
	 */
	public void setReceivedMessages(List<Message> receivedMessages) {
		this.receivedMessages = receivedMessages;
	}
	
	/**
	 * Instantiates a new party.
	 *
	 * @param id the id
	 * @param orchestration the orchestration
	 * @param history the history
	 * @param sentMessages the sent messages
	 * @param receivedMessages the received messages
	 */
	public Party(String id, PTNet orchestration, MarkingHistory history,
			List<Message> sentMessages, List<Message> receivedMessages) {
		super();
		this.id = id;
		this.orchestration = orchestration;
		this.history = history;
		this.sentMessages = sentMessages;
		this.receivedMessages = receivedMessages;
	}
}
