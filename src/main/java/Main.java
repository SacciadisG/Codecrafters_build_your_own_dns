import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;

public class Main {

  // Helper function to convert the domain name to byte array for the packet sections
  private static byte[] encodeDomain(String domain) {
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    for (String label : domain.split("\\.")) {
      byte[] labelBytes = label.getBytes(StandardCharsets.UTF_8);
      out.write(labelBytes.length);
      out.writeBytes(label.getBytes());
    }
    out.write(0); // End of the domain
    return out.toByteArray();
  }

  private static void writePacketHeader(ByteBuffer buffer) {
    // 1. Packet ID (Expected: 1234)
    short field_ID = 1234;

    // 2. Assemble the Flags 
    // QR=1 (Response), OPCODE=0, AA=0, TC=0, RD=0, RA=0, Z=0, RCODE=0
    // Binary: 10000000 00000000 -> Hex: 0x8000
    short flags = (short) 0x8000; 

    // 3. The Counts (Expected: all 0)
    short qdCount = 1;
    short anCount = 1;
    short nsCount = 0;
    short arCount = 0;

    // Writing to the buffer (This fills indices 0 through 11)
    buffer.putShort(field_ID);
    buffer.putShort(flags);
    buffer.putShort(qdCount);
    buffer.putShort(anCount);
    buffer.putShort(nsCount);
    buffer.putShort(arCount);
  }

  private static void writePacketQuestionSection(ByteBuffer buffer) {
    String domainName = "codecrafters.io";
    short field_TYPE = 1;
    short field_CLASS = 1;

    buffer.put(encodeDomain(domainName))
          .putShort(field_TYPE)
          .putShort(field_CLASS);
  }

  private static void writePacketAnswerSection(ByteBuffer buffer) {
    String domainName = "codecrafters.io";
    short field_TYPE = 1;
    short field_CLASS = 1;
    int field_TTL = 5;
    short field_RDLENGTH = 4;
    int field_RDATA = 0x08080808; // 1.2.3.4

    buffer.put(encodeDomain(domainName))
          .putShort(field_TYPE)
          .putShort(field_CLASS)
          .putInt(field_TTL)
          .putShort(field_RDLENGTH)
          .putInt(field_RDATA);

  }

  public static void main(String[] args){
    // You can use print statements as follows for debugging, they'll be visible when running tests.
    System.out.println("Logs from your program will appear here!");
    
    try(DatagramSocket serverSocket = new DatagramSocket(2053)) {
      while(true) {
        final byte[] buf = new byte[512];
        final DatagramPacket packet = new DatagramPacket(buf, buf.length);
        serverSocket.receive(packet);
        System.out.println("Received data");

        byte[] bufResponse = new byte[512];
        // Wrap byte array using ByteBuffer for easy writing
        ByteBuffer responseBuffer = ByteBuffer.wrap(bufResponse);
        responseBuffer.order(ByteOrder.BIG_ENDIAN);

        writePacketHeader(responseBuffer);
        writePacketQuestionSection(responseBuffer);
        writePacketAnswerSection(responseBuffer);
        bufResponse = responseBuffer.array();

        final DatagramPacket packetResponse = new DatagramPacket(bufResponse, bufResponse.length, packet.getSocketAddress());
        serverSocket.send(packetResponse);
      }
    } catch (IOException e) {
        System.out.println("IOException: " + e.getMessage());
    }
  }
}
