import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/** Public examples of required behaviour. Add independent tests of your own. */
public final class PublicChecks {
    @FunctionalInterface public interface Action { void run() throws Exception; }
    public record Case(String name, Action action) {}
    public static void equal(Object expected, Object actual) {
        if (!Objects.equals(expected, actual)) throw new AssertionError("Expected " + expected + "; got " + actual);
    }
    public static void require(boolean condition, String message) { if (!condition) throw new AssertionError(message); }
    public static void rejects(Class<? extends Throwable> type, Action action) throws Exception {
        try { action.run(); } catch (Throwable ex) {
            if (type.isInstance(ex)) return;
            throw new AssertionError("Expected " + type.getSimpleName() + "; got " + ex, ex);
        }
        throw new AssertionError("Expected " + type.getSimpleName() + "; operation returned normally");
    }
    public static List<Case> cases() {
        List<Case> cases = new ArrayList<>();

cases.add(new Case("valid three-call trace", () -> { ScriptedProvider p=new ScriptedProvider(java.util.List.of("READ","LIST","PROPOSE")); equal("AWAITING_APPROVAL",new Runner().run(p,6)); equal(3,p.calls()); }));
cases.add(new Case("six invalid outputs", () -> { ScriptedProvider p=new ScriptedProvider(java.util.List.of()); equal("STEP_LIMIT",new Runner().run(p,6)); equal(6,p.calls()); }));
cases.add(new Case("early proposal consumes attempt", () -> { ScriptedProvider p=new ScriptedProvider(java.util.List.of("PROPOSE","READ","LIST","PROPOSE")); equal("AWAITING_APPROVAL",new Runner().run(p,6)); equal(4,p.calls()); }));
cases.add(new Case("early finish", () -> { ScriptedProvider p=new ScriptedProvider(java.util.List.of("FINISH")); equal("NO_ACTION",new Runner().run(p,6)); equal(1,p.calls()); }));
cases.add(new Case("provider failure counted", () -> { int[] calls={0}; equal("PROVIDER_ERROR",new Runner().run(() -> { calls[0]++; throw new java.io.IOException("fixture failure"); },6)); equal(1,calls[0]); }));
for (int limit : new int[]{0,7}) cases.add(new Case("invalid limit "+limit, () -> { ScriptedProvider p=new ScriptedProvider(java.util.List.of()); rejects(IllegalArgumentException.class, () -> new Runner().run(p,limit)); equal(0,p.calls()); }));
cases.add(new Case("ordering state does not leak", () -> { Runner r=new Runner(); equal("STEP_LIMIT",r.run(new ScriptedProvider(java.util.List.of("READ","LIST")),2)); equal("STEP_LIMIT",r.run(new ScriptedProvider(java.util.List.of("PROPOSE")),1)); }));
cases.add(new Case("null response consumes attempt", () -> { int[] n={0}; equal("STEP_LIMIT",new Runner().run(() -> {n[0]++;return null;},2)); equal(2,n[0]); }));

        return cases;
    }
    public static void main(String[] args) {
        int failed = 0;
        for (Case c : cases()) {
            try { c.action().run(); System.out.println("PASS " + c.name()); }
            catch (Throwable ex) { failed++; System.out.println("FAIL " + c.name() + ": " + ex); }
        }
        System.out.println("CHECKS " + cases().size() + "; FAILED " + failed);
        if (failed != 0) System.exit(1);
    }
}
