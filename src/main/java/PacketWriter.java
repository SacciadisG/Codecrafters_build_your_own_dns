import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class PacketWriter {

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

    // Function to write the (response) packet header
    public static void writePacketHeader(ByteBuffer buffer, PacketHeader receivedHeader) {
        // Set the QR as 1, mimic the OPCODE and the RD from the request, and set AA, TC, RA, Z, as 0. 
        // Set the RCODE to 0 if OPCode is 0, else 4.

        // Assemble the Flags
        short flags = (short) (0x8000 | (receivedHeader.getOPCode() << 11) | (receivedHeader.getRDFlag() ? 0x0100 : 0));
        if (receivedHeader.getOPCode() == 0) {
            flags |= 0x0000; // RCODE = 0
        } else {
            flags |= 0x0004; // RCODE = 4
        }

        // Set the Counts 
        short qdCount = 1;
        short anCount = 1;
        short nsCount = 0;
        short arCount = 0;

        buffer.putShort(receivedHeader.getPacketID());
        buffer.putShort(flags);
        buffer.putShort(qdCount);
        buffer.putShort(anCount);
        buffer.putShort(nsCount);
        buffer.putShort(arCount);
    }

    // Function to write the Question Section of the (response) packet
    public static void writePacketQuestionSection(ByteBuffer buffer) {
        String domainName = "codecrafters.io";
        short field_TYPE = 1;
        short field_CLASS = 1;

        buffer.put(encodeDomain(domainName))
            .putShort(field_TYPE)
            .putShort(field_CLASS);
    }

    // Function to write the Answer Section of the (response) packet
    public static void writePacketAnswerSection(ByteBuffer buffer) {
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
}
