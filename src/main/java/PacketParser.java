import java.io.ByteArrayOutputStream;
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

    public static PacketQuestion readPacketQuestionSection(ByteBuffer buffer) {
        // Reading the domain name
        ByteArrayOutputStream domainOut = new ByteArrayOutputStream();
        while (true) {
            byte len = buffer.get();
            if (len == 0) break; // End of domain name
            byte[] labelBytes = new byte[len];
            buffer.get(labelBytes);
            domainOut.write(len);
            domainOut.writeBytes(labelBytes);
        }
        byte[] domainName = domainOut.toByteArray();

        short field_type = buffer.getShort();
        short field_class = buffer.getShort();
        return new PacketQuestion(domainName, field_type, field_class);
    }

    public static PacketAnswer readPacketAnswerSection(ByteBuffer buffer) {
        // Reading the domain name
        ByteArrayOutputStream domainOut = new ByteArrayOutputStream();
        while (true) {
            byte len = buffer.get();
            if (len == 0) break; // End of domain name
            byte[] labelBytes = new byte[len];
            buffer.get(labelBytes);
            domainOut.write(len);
            domainOut.writeBytes(labelBytes);
        }
        byte[] domainName = domainOut.toByteArray();

        short field_type = buffer.getShort();
        short field_class = buffer.getShort();
        int field_ttl = buffer.getInt();
        short field_rdlength = buffer.getShort();
        int field_rdata = buffer.getInt();
        return new PacketAnswer(domainName, field_type, field_class, field_ttl, field_rdlength, field_rdata);
    }
}
