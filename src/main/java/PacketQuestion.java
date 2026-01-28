public class PacketQuestion {
    byte[] domainName;
    short field_type;
    short field_class;

    PacketQuestion(byte[] domainName, short field_type, short field_class) {
        this.domainName = domainName;
        this.field_type = field_type;
        this.field_class = field_class;
    }

    public byte[] getDomainName() {
        return domainName;
    }

    public short getFieldType() {
        return field_type;
    }

    public short getFieldClass() {
        return field_class;
    }
}
