import java.nio.ByteBuffer;

public class PacketParser {

    public static PacketHeader readPacketHeader(ByteBuffer buffer) {
        // Reading the header fields from the buffer
        short packetID = buffer.getShort();
        short flags = buffer.getShort();
        short qdCount = buffer.getShort();
        short anCount = buffer.getShort();
        short nsCount = buffer.getShort();
        short arCount = buffer.getShort();
        return new PacketHeader(packetID, flags, qdCount, anCount, nsCount, arCount);
    }
}
