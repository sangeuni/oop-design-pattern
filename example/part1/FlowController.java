public class FlowController {
    
    public void process() {
        FileDataReader reader = new FileDataReader();
        byte[] data = reader.read();
        
        Encrytor encryptor = new Encryptor();
        byte[] encryptedData = encryptor.encrypt(data);
        
        FileDataWriter writer = new FileDataWriter();
        writer.write(encryptedData);
    }
}