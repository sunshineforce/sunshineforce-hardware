import entry.ParseUdp;

public class Bootstrap {
    public static void main(String args[]){
        ParseUdp parseUdp = new ParseUdp();
        while(true){
            parseUdp.parse();
        }
    }
}
