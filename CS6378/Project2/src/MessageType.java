/**
 * Created by hanlin on 3/28/17.
 */
public enum MessageType {
    client_put,
    client_put_ack,
    client_put_fail,

    client_get,
    client_get_ack,

    server_put,
    server_put_ack,
    server_put_req,
    server_put_req_ack,
    server_put_fail,
    server_put_end,

    server_unavailable
}
