/*
 * Copyright (c) 1995, 2020, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */

package java.net;

import java.io.IOException;
import java.nio.channels.DatagramChannel;
import java.nio.channels.MulticastChannel;

/**
 * The multicast datagram socket class is useful for sending
 * and receiving IP multicast packets. A MulticastSocket is
 * a (UDP) DatagramSocket, with additional capabilities for
 * joining "groups" of other multicast hosts on the internet.
 * <P>
 * A multicast group is specified by a class D IP address
 * and by a standard UDP port number. Class D IP addresses
 * are in the range {@code 224.0.0.0} to {@code 239.255.255.255},
 * inclusive. The address 224.0.0.0 is reserved and should not be used.
 * <P>
 * One would join a multicast group by first creating a MulticastSocket
 * with the desired port, then invoking the
 * <CODE>joinGroup(InetAddress groupAddr)</CODE>
 * method:
 * <PRE>
 * // join a Multicast group and send the group salutations
 * ...
 * String msg = "Hello";
 * InetAddress mcastaddr = InetAddress.getByName("228.5.6.7");
 * InetSocketAddress group = new InetSocketAddress(mcastaddr, port);
 * NetworkInterface netIf = NetworkInterface.getByName("bge0");
 * MulticastSocket s = new MulticastSocket(6789);
 *
 * s.joinGroup(group, netIf);
 * byte[] msgBytes = msg.getBytes(StandardCharsets.UTF_8);
 * DatagramPacket hi = new DatagramPacket(msgBytes, msgBytes.length,
 *                                        group, 6789);
 * s.send(hi);
 * // get their responses!
 * byte[] buf = new byte[1000];
 * DatagramPacket recv = new DatagramPacket(buf, buf.length);
 * s.receive(recv);
 * ...
 * // OK, I'm done talking - leave the group...
 * s.leaveGroup(group, netIf);
 * </PRE>
 *
 * When one sends a message to a multicast group, <B>all</B> subscribing
 * recipients to that host and port receive the message (within the
 * time-to-live range of the packet, see below). The socket needn't
 * be a member of the multicast group to send messages to it.
 * <P>
 * When a socket subscribes to a multicast group/port, it receives
 * datagrams sent by other hosts to the group/port, as do all other
 * members of the group and port.  A socket relinquishes membership
 * in a group by the leaveGroup(SocketAddress mcastaddr, NetworkInterface netIf)
 * method.
 * <B>Multiple MulticastSockets</B> may subscribe to a multicast group
 * and port concurrently, and they will all receive group datagrams.
 *
 * <p> The {@code DatagramSocket} and {@code MulticastSocket}
 * classes define convenience methods to set and get several
 * socket options. Like {@code DatagramSocket} this class also
 * supports the {@link #setOption(SocketOption, Object) setOption}
 * and {@link #getOption(SocketOption) getOption} methods to set
 * and query socket options.
 * In addition to the socket options supported by
 * <a href="DatagramSocket.html#SocketOptions">{@code DatagramSocket}</a>, a
 * {@code MulticastSocket} supports the following socket options:
 * <blockquote>
 * <a id="MulticastOptions"></a>
 * <table class="striped">
 * <caption style="display:none">Socket options</caption>
 * <thead>
 *   <tr>
 *     <th scope="col">Option Name</th>
 *     <th scope="col">Description</th>
 *   </tr>
 * </thead>
 * <tbody>
 *   <tr>
 *     <th scope="row"> {@link java.net.StandardSocketOptions#IP_MULTICAST_IF IP_MULTICAST_IF} </th>
 *     <td> The network interface for Internet Protocol (IP) multicast datagrams </td>
 *   </tr>
 *   <tr>
 *     <th scope="row"> {@link java.net.StandardSocketOptions#IP_MULTICAST_TTL
 *       IP_MULTICAST_TTL} </th>
 *     <td> The <em>time-to-live</em> for Internet Protocol (IP) multicast
 *       datagrams </td>
 *   </tr>
 *   <tr>
 *     <th scope="row"> {@link java.net.StandardSocketOptions#IP_MULTICAST_LOOP
 *       IP_MULTICAST_LOOP} </th>
 *     <td> Loopback for Internet Protocol (IP) multicast datagrams </td>
 *   </tr>
 * </tbody>
 * </table>
 * </blockquote>
 * Additional (implementation specific) options may also be supported.
 *
 * @apiNote {@link DatagramChannel} implements the {@link MulticastChannel} interface
 *          and provides an alternative API for sending and receiving multicast datagrams.
 *          The {@link MulticastChannel} API supports both {@linkplain
 *          MulticastChannel#join(InetAddress, NetworkInterface) any-source} and
 *          {@linkplain MulticastChannel#join(InetAddress, NetworkInterface, InetAddress)
 *          source-specific} multicast.
 *
 * @author Pavani Diwanji
 * @since 1.1
 */
public class MulticastSocket extends DatagramSocket {

    @Override
    final MulticastSocket delegate() {
        return (MulticastSocket) super.delegate();
    }

    /**
     * Create a MulticastSocket that delegates to the given delegate if not null.
     * @param delegate the delegate, can be null.
     */
    MulticastSocket(MulticastSocket delegate)  {
        super(delegate);
    }


    /**
     * Constructs a multicast socket and binds it to any available port
     * on the local host machine.  The socket will be bound to the
     * {@link InetAddress#isAnyLocalAddress wildcard} address.
     *
     * <p>
     * If there is a security manager, its {@code checkListen} method is first
     * called with 0 as its argument to ensure the operation is allowed. This
     * could result in a SecurityException.
     * <p>
     * When the socket is created the
     * {@link DatagramSocket#setReuseAddress(boolean)} method is called to
     * enable the SO_REUSEADDR socket option.
     *
     * @throws    IOException if an I/O exception occurs while creating the
     * MulticastSocket
     * @throws    SecurityException if a security manager exists and its
     * {@code checkListen} method doesn't allow the operation.
     * @see SecurityManager#checkListen
     * @see java.net.DatagramSocket#setReuseAddress(boolean)
     * @see java.net.DatagramSocketImpl#setOption(SocketOption, Object)
     */
    public MulticastSocket() throws IOException {
        this(new InetSocketAddress(0));
    }

    /**
     * Constructs a multicast socket and binds it to the specified port
     * on the local host machine. The socket will be bound to the
     * {@link InetAddress#isAnyLocalAddress wildcard} address.
     *
     * <p>If there is a security manager,
     * its {@code checkListen} method is first called
     * with the {@code port} argument
     * as its argument to ensure the operation is allowed.
     * This could result in a SecurityException.
     * <p>
     * When the socket is created the
     * {@link DatagramSocket#setReuseAddress(boolean)} method is
     * called to enable the SO_REUSEADDR socket option.
     *
     * @param     port port to use
     * @throws    IOException if an I/O exception occurs
     *            while creating the MulticastSocket
     * @throws    SecurityException  if a security manager exists and its
     *            {@code checkListen} method doesn't allow the operation.
     * @throws    IllegalArgumentException  if port is  <a href="DatagramSocket.html#PortRange">
     *            out of range.</a>
     *
     * @see       SecurityManager#checkListen
     * @see       java.net.DatagramSocket#setReuseAddress(boolean)
     */
    public MulticastSocket(int port) throws IOException {
        this(new InetSocketAddress(port));
    }

    /**
     * Creates a multicast socket, bound to the specified local
     * socket address.
     * <p>
     * If the address is {@code null} an unbound socket will be created.
     *
     * <p>If there is a security manager,
     * its {@code checkListen} method is first called
     * with the SocketAddress port as its argument to ensure the operation is allowed.
     * This could result in a SecurityException.
     * <p>
     * When the socket is created the
     * {@link DatagramSocket#setReuseAddress(boolean)} method is
     * called to enable the SO_REUSEADDR socket option.
     *
     * @param    bindaddr Socket address to bind to, or {@code null} for
     *           an unbound socket.
     * @throws   IOException if an I/O exception occurs
     *           while creating the MulticastSocket
     * @throws   SecurityException  if a security manager exists and its
     *           {@code checkListen} method doesn't allow the operation.
     * @see      SecurityManager#checkListen
     * @see      java.net.DatagramSocket#setReuseAddress(boolean)
     *
     * @since 1.4
     */
    public MulticastSocket(SocketAddress bindaddr) throws IOException {
        this(createDelegate(bindaddr, MulticastSocket.class));
    }

    /**
     * Set the default time-to-live for multicast packets sent out
     * on this {@code MulticastSocket} in order to control the
     * scope of the multicasts.
     *
     * <p>The ttl is an <b>unsigned</b> 8-bit quantity, and so <B>must</B> be
     * in the range {@code 0 <= ttl <= 0xFF }.
     *
     * @param      ttl the time-to-live
     * @throws     IOException if an I/O exception occurs
     *             while setting the default time-to-live value
     * @deprecated use the setTimeToLive method instead, which uses
     *             <b>int</b> instead of <b>byte</b> as the type for ttl.
     * @see #getTTL()
     */
    @Deprecated
    public void setTTL(byte ttl) throws IOException {
        delegate().setTTL(ttl);
    }

    /**
     * Set the default time-to-live for multicast packets sent out
     * on this {@code MulticastSocket} in order to control the
     * scope of the multicasts.
     *
     * <P> The ttl <B>must</B> be in the range {@code  0 <= ttl <=
     * 255} or an {@code IllegalArgumentException} will be thrown.
     * Multicast packets sent with a TTL of {@code 0} are not transmitted
     * on the network but may be delivered locally.
     *
     * @param  ttl
     *         the time-to-live
     *
     * @throws  IOException
     *          if an I/O exception occurs while setting the
     *          default time-to-live value
     *
     * @see #getTimeToLive()
     * @since 1.2
     */
    public void setTimeToLive(int ttl) throws IOException {
        delegate().setTimeToLive(ttl);
    }

    /**
     * Get the default time-to-live for multicast packets sent out on
     * the socket.
     *
     * @throws    IOException if an I/O exception occurs
     * while getting the default time-to-live value
     * @return the default time-to-live value
     * @deprecated use the getTimeToLive method instead, which returns
     * an <b>int</b> instead of a <b>byte</b>.
     * @see #setTTL(byte)
     */
    @Deprecated
    public byte getTTL() throws IOException {
        return delegate().getTTL();
    }

    /**
     * Get the default time-to-live for multicast packets sent out on
     * the socket.
     * @throws    IOException if an I/O exception occurs while
     * getting the default time-to-live value
     * @return the default time-to-live value
     * @see #setTimeToLive(int)
     * @since 1.2
     */
    public int getTimeToLive() throws IOException {
        return delegate().getTimeToLive();
    }

    /**
     * Joins a multicast group. Its behavior may be affected by
     * {@code setInterface} or {@code setNetworkInterface}.
     *
     * <p>If there is a security manager, this method first
     * calls its {@code checkMulticast} method with the
     * {@code mcastaddr} argument as its argument.
     *
     * @param      mcastaddr is the multicast address to join
     * @throws     IOException if there is an error joining,
     *             or when the address is not a multicast address,
     *             or the platform does not support multicasting
     * @throws     SecurityException if a security manager exists and its
     *             {@code checkMulticast} method doesn't allow the join.
     * @deprecated This method does not accept the network interface on
     *             which to join the multicast group. Use
     *             {@link #joinGroup(SocketAddress, NetworkInterface)} instead.
     * @see        SecurityManager#checkMulticast(InetAddress)
     */
    @Deprecated(since="14")
    public void joinGroup(InetAddress mcastaddr) throws IOException {
        delegate().joinGroup(mcastaddr);
    }

    /**
     * Leave a multicast group. Its behavior may be affected by
     * {@code setInterface} or {@code setNetworkInterface}.
     *
     * <p>If there is a security manager, this method first
     * calls its {@code checkMulticast} method with the
     * {@code mcastaddr} argument as its argument.
     *
     * @param      mcastaddr is the multicast address to leave
     * @throws     IOException if there is an error leaving
     *             or when the address is not a multicast address.
     * @throws     SecurityException if a security manager exists and its
     *             {@code checkMulticast} method doesn't allow the operation.
     * @deprecated This method does not accept the network interface on which
     *             to leave the multicast group. Use
     *             {@link #leaveGroup(SocketAddress, NetworkInterface)} instead.
     * @see        SecurityManager#checkMulticast(InetAddress)
     */
    @Deprecated(since="14")
    public void leaveGroup(InetAddress mcastaddr) throws IOException {
        delegate().leaveGroup(mcastaddr);
    }

    /**
     * Joins the specified multicast group at the specified interface.
     *
     * <p>If there is a security manager, this method first
     * calls its {@code checkMulticast} method
     * with the {@code mcastaddr} argument
     * as its argument.
     *
     * @param  mcastaddr is the multicast address to join
     * @param  netIf specifies the local interface to receive multicast
     *         datagram packets, or {@code null} to defer to the interface set by
     *         {@link MulticastSocket#setInterface(InetAddress)} or
     *         {@link MulticastSocket#setNetworkInterface(NetworkInterface)}.
     *         If {@code null}, and no interface has been set, the behaviour is
     *         unspecified: any interface may be selected or the operation may fail
     *         with a {@code SocketException}.
     * @throws IOException if there is an error joining, or when the address
     *         is not a multicast address, or the platform does not support
     *         multicasting
     * @throws SecurityException if a security manager exists and its
     *         {@code checkMulticast} method doesn't allow the join.
     * @throws IllegalArgumentException if mcastaddr is {@code null} or is a
     *         SocketAddress subclass not supported by this socket
     * @see    SecurityManager#checkMulticast(InetAddress)
     * @see    DatagramChannel#join(InetAddress, NetworkInterface)
     * @since  1.4
     */
    public void joinGroup(SocketAddress mcastaddr, NetworkInterface netIf)
        throws IOException {
        delegate().joinGroup(mcastaddr, netIf);
    }

    /**
     * Leave a multicast group on a specified local interface.
     *
     * <p>If there is a security manager, this method first
     * calls its {@code checkMulticast} method with the
     * {@code mcastaddr} argument as its argument.
     *
     * @param  mcastaddr is the multicast address to leave
     * @param  netIf specifies the local interface or {@code null} to defer
     *         to the interface set by
     *         {@link MulticastSocket#setInterface(InetAddress)} or
     *         {@link MulticastSocket#setNetworkInterface(NetworkInterface)}.
     *         If {@code null}, and no interface has been set, the behaviour
     *         is unspecified: any interface may be selected or the operation
     *         may fail with a {@code SocketException}.
     * @throws IOException if there is an error leaving or when the address
     *         is not a multicast address.
     * @throws SecurityException if a security manager exists and its
     *         {@code checkMulticast} method doesn't allow the operation.
     * @throws IllegalArgumentException if mcastaddr is {@code null} or is a
     *         SocketAddress subclass not supported by this socket.
     * @see    SecurityManager#checkMulticast(InetAddress)
     * @since  1.4
     */
    public void leaveGroup(SocketAddress mcastaddr, NetworkInterface netIf)
        throws IOException {
        delegate().leaveGroup(mcastaddr, netIf);
     }

    /**
     * Set the multicast network interface used by methods
     * whose behavior would be affected by the value of the
     * network interface. Useful for multihomed hosts.
     *
     * @param      inf the InetAddress
     * @throws     SocketException if there is an error in
     *             the underlying protocol, such as a TCP error.
     * @deprecated The InetAddress may not uniquely identify
     *             the network interface. Use
     *             {@link #setNetworkInterface(NetworkInterface)} instead.
     * @see        #getInterface()
     */
    @Deprecated(since="14")
    public void setInterface(InetAddress inf) throws SocketException {
        delegate().setInterface(inf);
    }

    /**
     * Retrieve the address of the network interface used for
     * multicast packets.
     *
     * @return     An {@code InetAddress} representing the address
     *             of the network interface used for multicast packets,
     *             or if no interface has been set, an {@code InetAddress}
     *             representing any local address.
     * @throws     SocketException if there is an error in the
     *             underlying protocol, such as a TCP error.
     * @deprecated The network interface may not be uniquely identified by
     *             the InetAddress returned.
     *             Use {@link #getNetworkInterface()} instead.
     * @see        #setInterface(java.net.InetAddress)
     */
    @Deprecated(since="14")
    public InetAddress getInterface() throws SocketException {
        return delegate().getInterface();
    }

    /**
     * Specify the network interface for outgoing multicast datagrams
     * sent on this socket.
     *
     * @param netIf the interface
     * @throws    SocketException if there is an error in
     * the underlying protocol, such as a TCP error.
     * @see #getNetworkInterface()
     * @since 1.4
     */
    public void setNetworkInterface(NetworkInterface netIf)
        throws SocketException {
        delegate().setNetworkInterface(netIf);
    }

    /**
     * Get the multicast network interface set.
     *
     * @throws SocketException if there is an error in
     *         the underlying protocol, such as a TCP error.
     * @return The multicast {@code NetworkInterface} currently set. A placeholder
     *         NetworkInterface is returned when there is no interface set; it has
     *         a single InetAddress to represent any local address.
     * @see    #setNetworkInterface(NetworkInterface)
     * @since  1.4
     */
    public NetworkInterface getNetworkInterface() throws SocketException {
        return delegate().getNetworkInterface();
    }

    /**
     * Disable/Enable local loopback of multicast datagrams.
     * The option is used by the platform's networking code as a hint
     * for setting whether multicast data will be looped back to
     * the local socket.
     *
     * <p>Because this option is a hint, applications that want to
     * verify what loopback mode is set to should call
     * {@link #getLoopbackMode()}
     * @param      disable {@code true} to disable the LoopbackMode
     * @throws     SocketException if an error occurs while setting the value
     * @since      1.4
     * @deprecated Use {@link #setOption(SocketOption, Object)} with
     *             {@link java.net.StandardSocketOptions#IP_MULTICAST_LOOP}
     *             instead. The loopback mode is enabled by default,
     *             {@code MulticastSocket.setOption(StandardSocketOptions.IP_MULTICAST_LOOP, false)}
     *             disables it.
     * @see        #getLoopbackMode
     */
    @Deprecated(since="14")
    public void setLoopbackMode(boolean disable) throws SocketException {
        delegate().setLoopbackMode(disable);
    }

    /**
     * Get the setting for local loopback of multicast datagrams.
     *
     * @throws     SocketException if an error occurs while getting the value
     * @return     true if the LoopbackMode has been disabled
     * @since      1.4
     * @deprecated Use {@link #getOption(SocketOption)} with
     *             {@link java.net.StandardSocketOptions#IP_MULTICAST_LOOP}
     *             instead.
     * @see        #setLoopbackMode
     */
    @Deprecated(since="14")
    public boolean getLoopbackMode() throws SocketException {
        return delegate().getLoopbackMode();
    }

    /**
     * Sends a datagram packet to the destination, with a TTL (time-to-live)
     * other than the default for the socket.  This method
     * need only be used in instances where a particular TTL is desired;
     * otherwise it is preferable to set a TTL once on the socket, and
     * use that default TTL for all packets.  This method does <B>not
     * </B> alter the default TTL for the socket. Its behavior may be
     * affected by {@code setInterface}.
     *
     * <p>If there is a security manager, this method first performs some
     * security checks. First, if {@code p.getAddress().isMulticastAddress()}
     * is true, this method calls the
     * security manager's {@code checkMulticast} method
     * with {@code p.getAddress()} and {@code ttl} as its arguments.
     * If the evaluation of that expression is false,
     * this method instead calls the security manager's
     * {@code checkConnect} method with arguments
     * {@code p.getAddress().getHostAddress()} and
     * {@code p.getPort()}. Each call to a security manager method
     * could result in a SecurityException if the operation is not allowed.
     *
     * @param p is the packet to be sent. The packet should contain
     * the destination multicast ip address and the data to be sent.
     * One does not need to be the member of the group to send
     * packets to a destination multicast address.
     * @param ttl optional time to live for multicast packet.
     * default ttl is 1.
     *
     * @throws     IOException is raised if an error occurs i.e
     *             error while setting ttl.
     * @throws     SecurityException  if a security manager exists and its
     *             {@code checkMulticast} or {@code checkConnect}
     *             method doesn't allow the send.
     * @throws     PortUnreachableException may be thrown if the socket is connected
     *             to a currently unreachable destination. Note, there is no
     *             guarantee that the exception will be thrown.
     * @throws     IllegalArgumentException if the socket is connected,
     *             and connected address and packet address differ, or
     *             if the socket is not connected and the packet address
     *             is not set or if its port is out of range.
     *
     *
     * @deprecated Use the following code or its equivalent instead:
     *  ......
     *  int ttl = mcastSocket.getTimeToLive();
     *  mcastSocket.setTimeToLive(newttl);
     *  mcastSocket.send(p);
     *  mcastSocket.setTimeToLive(ttl);
     *  ......
     *
     * @see DatagramSocket#send
     * @see DatagramSocket#receive
     * @see SecurityManager#checkMulticast(java.net.InetAddress, byte)
     * @see SecurityManager#checkConnect
     */
    @Deprecated
    public void send(DatagramPacket p, byte ttl)
        throws IOException {
        delegate().send(p, ttl);
    }
}