public class PacketHeader {
    short packetID;
    short flags; // QR (1 bit), OPCODE (4 bits), AA (1 bit), TC (1 bit), RD (1 bit), RA (1 bit), Z (1 bit), RCODE (4 bits)
    short qdCount;
    short anCount;
    short nsCount;
    short arCount;

    public PacketHeader(short packetID, short flags, short qdCount, short anCount, short nsCount, short arCount) {
        this.packetID = packetID;
        this.flags = flags;
        this.qdCount = qdCount;
        this.anCount = anCount;
        this.nsCount = nsCount;
        this.arCount = arCount;
    }

    public short getPacketID() {
        return packetID;
    }

    public short getFlags() {
        return flags;
    }

    public short getQdCount() {
        return qdCount;
    }

    public short getAnCount() {
        return anCount;
    }

    public short getNsCount() {
        return nsCount;
    }

    public short getArCount() {
        return arCount;
    }

    // Methods to extract individual flag components
    public boolean getQRFlag() {
        return (flags & 0x8000) != 0;
    }

    // Note: OPCODE is 4 bits, so we need to shift right by 11 and mask with 0x0F
    public short getOPCode() {
        return (short) ((flags >> 11) & 0x0F);
    }

    public boolean getAAFlag() {
        return (flags & 0x0400) != 0;
    }

    public boolean getTCFlag() {
        return (flags & 0x0200) != 0;
    }

    public boolean getRDFlag() {
        return (flags & 0x0100) != 0;
    }

    public boolean getRAFlag() {
        return (flags & 0x0080) != 0;
    }

    // Note: RCode is 4 bits, so we need to shift right by 11 and mask with 0x0F
    public short getRCode() {
        return (short) (flags & 0x000F);
    }




}
