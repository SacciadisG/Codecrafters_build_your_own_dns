public class PacketAnswer {
    byte[] domainName;
    short field_type;
    short field_class;
    int field_ttl;
    short field_rdlength;
    int field_rdata;

    PacketAnswer(byte[] domainName, short field_type, short field_class, int field_ttl, short field_rdlength, int field_rdata) {
        this.domainName = domainName;
        this.field_type = field_type;
        this.field_class = field_class;
        this.field_ttl = field_ttl;
        this.field_rdlength = field_rdlength;
        this.field_rdata = field_rdata;
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

    public int getFieldTTL() {
        return field_ttl;
    }

    public short getFieldRDLENGTH() {
        return field_rdlength;
    }

    public int getFieldRDATA() {
        return field_rdata;
    }
}