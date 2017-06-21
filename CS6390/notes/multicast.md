# Multicast

### Multicast semantics
- Multicast *group*
    + Is represented by a class D IP address (more later)
    + Zero or more receivers from a multicast group
    + Senders are not part of the group
- *Open group semantics*
    + Receivers can join/leave at will.
    + Anyone knowing group address can send to it.
- IP based *best effort delivery* semantics: supports UDP only - no TCP!
    + If routers are reliable per-hop, this requires lots of buffer/state at
      each router
    + Otherwise, if source receives ack's from the receivers, we have ack
      implosion problem.

### Open group semantics
- Advantages
    + Sources do not need to know individual receivers (they just send)
    + Receivers do not need to know the sources either.
- Disadvantages


### Bridges and Extended LANs Review
- LANs have physical limitations (e.g., 2500m)
- Bridges connect two or more LAN segments (i.e. collision domains) together.
  They are *transparent* to hosts (does not change packets in any way).
    + Bridges see all messages in their attached LANs.
    + Copy message from one LAN to another if necessary.
- Bridge maintains a forwarding table.
    + Often incomplete (initially empty!)
    + Learn table entries based on source address of messages seen (not the
      destination address).
    + If destination is not on table, forward over all other ports. If answer
      receive, bridge would knows the destination of the previous message
      (throuth the source of the answer message).
    + Entries won't last forever, because host may move from one place to
      another.
- Bridges run a distributed spanning tree algorithm.

### Multicast in Extended LANs
A simple way: bridges propagate multicast packets across every segment of the
extended LAN. But way too inefficient, especially for multicast applications
with sparsely located receivers.

The question is: How to locate receivers? If bridges know which interface leads
to members of a given group, they will forward the packets on those interfaces
only. But, in general, bridges learn the interface when receivers send
something. Yet, the receiver might not send anything.

To learn the location of receivers, we force receivers (i.e., group members) to
periodically transmit a membership-report, with the form of:
- LAN source address = `G`,
- LAN destination address = `ALL-BRIDGES` multicast address.

#### Bridge Multicast Table.
- A bridge receiving a report records the incoming interface of the report as
  an outgoing interface for group G.
- It then forwards the report over all of its interfaces in the extended LAN.

#### Bridge algorithm (summary)
1. If a packet arrives and its source address is a multicast group,
    1. record arriving interface as an outgoing-interface with an age of zero
       for this multicast address,
    1. and forward to all other interfaces (flood).
1. Periodically increment age of outgoing interface of each group,
    1. when `age = expiry` threshold, delete this outgoing-interface info from
       the group's entry.
    1. If no outgoing-interfaces remain, delete the entire entry for the group.
1. If a packet arrives with a multicast destination address, forward a copy on
   every outgoing-interface recorded in the table entry for that address
   excluding the arriving interface.

#### Some Improvements
In the algorithm, if a LAN segment have many host in one group, then all of
them would send membership-report. But the bridges connecting to that LAN
segment only need one report to record the outgoing-interface in table, which
leads to some waste.

Thus, an efficient improvement to suppress unnecessary membership reports:
- Hosts send membership-reports as `(G,G)`, rather than `(G, ALL-BRIDGES)`.
- Then members of group `G` in the same LAN will not send a report if they
  receive the first membership-report.

  All the member in the same LAN will receive the membership-report because to
  join group `G`, their local network card is programmed to receive packets with
  destination address of `G`.

- Bridge on receiving a packet with address `(G,G)`, will changes it to
  `(G, ALL-BRIDGES)` and forwards to other interfaces.

  This change is vital.

## DVMRP (Distance Vector Multicast Routing Protocol)
### Basic Idea
Compute a `spanning (i.e. broadcast) tree` across all the links and prune it to
become a multicast tree. Specifically, a source-based shortest path spanning
tree.
Tree is rooted at the source site.
It corresponds to shortest path from each receiver to the source

__Observation:__ Every shortest-path multicast tree rooted at the sender is a
subtree of a single shortest-path spanning (i.e. broadcast) tree rooted at this
sender.

### Reverse Path Flooding (RPF) algorithm

