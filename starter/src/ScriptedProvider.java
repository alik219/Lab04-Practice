import java.util.List;
    public final class ScriptedProvider implements Provider {
        private final List<String> commands;
        private int calls;
        public ScriptedProvider(List<String> commands) {
            this.commands=List.copyOf(commands); }
        public String next() { 
            int i=calls++;
            return i<commands.size()?commands.get(i):"UNKNOWN";
        }
        public int calls() {
            return calls; }
    }