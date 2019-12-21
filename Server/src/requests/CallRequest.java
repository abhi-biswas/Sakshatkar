package requests;

public class CallRequest {
    private String caller,callee;
    private int port;
    public String getCaller(){return this.caller;}

    public String getCallee() {
        return callee;
    }

    public int getPort() {
        return port;
    }
}
