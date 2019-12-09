public class FlowController {
    
    public void process() {
        ByteSource source = ByteSourceFactoy.getInstance().create();
        byte[] data = source.read();       
        
        Encrytor encryptor = new Encryptor();
        byte[] encryptedData = encryptor.encrypt(data);
        
        FileDataWriter writer = new FileDataWriter();
        writer.write(encryptedData);
    }
}