package core;

public enum MessageType {
    /* Connection initialization message.
    * A node would send this message after establish the connection with
    * the target server so that the target server add this node into its network map.
    * Message content should be null, since senderID is contained in data field. */
    INIT_CONNECTION,

    /* Vote data initialization message.
    * After set up all the nodes in the network, user should ask node to initializa vote data. */
    INIT_VOTE,

    /* When a site S receives an update to the file f, S issues a LOCK-REQUEST to its local lock man-ager.
    * When the lock request is granted, S sends a VOTE_REQ message to all the sites. */
    VOTE_REQ,

    /* When a site S, receives a VOTE-REQ, it issues a LOCK-REQUEST to its local lock manager.
    * When the lock request is granted, S sends VOTE_REQ_ACK to S, containing the values VN, SC, and DS. */
    VOTE_REQ_ACK,
    /* When the lock request is denied, S sends VOTE_REQ_NACK to S, containing no content. */
    VOTE_REQ_NACK,

    /* If S does not belong to a distinguished partition,
    * it aborts the update, issues a RELEASE-LOCK request to its local lock manager,
    * and sends ABORT messages to all the participants.*/
    ABORT,

    /* Once the copy at S is current, S commits the update to the file f together with the
    * modification to its VN, SC, and DS, and sends to each S, the COMMIT message along
    * with the missing updates (if necessary), the new update to f, and the new values for
    * VN, SC, and DS. */
    COMMIT,
}
