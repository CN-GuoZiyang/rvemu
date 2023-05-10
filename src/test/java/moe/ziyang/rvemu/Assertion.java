package moe.ziyang.rvemu;

public interface Assertion {
    void Assert(Core core);
}

class GPRAssertion implements Assertion {

    private final int index;
    private final long value;

    public GPRAssertion(int index, long value) {
        this.index = index;
        this.value = value;
    }

    @Override
    public void Assert(Core core) {
        assert core.gprs.read(index) == value;
    }
}
