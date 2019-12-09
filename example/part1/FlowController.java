public class FlowController {
    
    private boolean useFile;
    
    public FlowController(boolean useFile) {
        this.useFile = useFile;
    }
    
    public void process() {
        byte[] data = null;
        if (useFile) {
            FileDataReader fileReader = new FileDataReader();
       		data = fileReader.read();
        }else {
            SocketDataReader socketReader = new SocketDataReader();
            data = socketReader.read();
        }
        
        Encrytor encryptor = new Encryptor();
        byte[] encryptedData = encryptor.encrypt(data);
        
        FileDataWriter writer = new FileDataWriter();
        writer.write(encryptedData);
    }
}