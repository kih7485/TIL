package hello.proxy.pureproxy.proxy.code;

public class ProxtPatternClient {
    private Subject subject;

    public ProxtPatternClient(Subject subject) {
        this.subject = subject;
    }

    public void execute(){
        subject.operation();
    }

}
