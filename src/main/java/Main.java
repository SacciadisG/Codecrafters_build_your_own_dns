import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class Main {

  public static void main(String[] args){
    // You can use print statements as follows for debugging, they'll be visible when running tests.
    System.out.println("Logs from your program will appear here!");
    
    try(DatagramSocket serverSocket = new DatagramSocket(2053)) {
      while(true) {
        final byte[] buf = new byte[512];
        final DatagramPacket packet = new DatagramPacket(buf, buf.length);
        serverSocket.receive(packet);
        System.out.println("Received data");

        // Accessing the received data
        byte[] receivedData = packet.getData();
        int receivedLength = packet.getLength();

        ByteBuffer readBuffer = ByteBuffer.wrap(receivedData, 0, receivedLength);
        readBuffer.order(ByteOrder.BIG_ENDIAN); // Standard for network protocols (DNS)

        byte[] bufResponse = new byte[512];
        ByteBuffer responseBuffer = ByteBuffer.wrap(bufResponse);
        responseBuffer.order(ByteOrder.BIG_ENDIAN);

        PacketWriter.writePacketHeader(responseBuffer, PacketParser.readPacketHeader(readBuffer));
        PacketWriter.writePacketQuestionSection(responseBuffer, PacketParser.readPacketQuestionSection(readBuffer));
        System.out.println("Writing answer section");
        PacketWriter.writePacketAnswerSection(responseBuffer, PacketParser.readPacketAnswerSection(readBuffer));
        System.out.println("Finished writing packet"); 
        bufResponse = responseBuffer.array();

        final DatagramPacket packetResponse = new DatagramPacket(bufResponse, bufResponse.length, packet.getSocketAddress());
        serverSocket.send(packetResponse);
      }
    } catch (IOException e) {
        System.out.println("IOException: " + e.getMessage());
    }
  }
}
