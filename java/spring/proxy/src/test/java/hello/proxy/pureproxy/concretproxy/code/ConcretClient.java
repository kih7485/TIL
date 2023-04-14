package hello.proxy.pureproxy.concretproxy.code;

public class ConcretClient {
    private ConcretLogic concretLogic;

    public ConcretClient(ConcretLogic concretLogic) {
        this.concretLogic = concretLogic;
    }

    public void execute(){
        concretLogic.operation();
    }
}
